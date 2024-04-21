package machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Machine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    public Machine() {
        initState();
    }

    public void printState() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water\n", this.water);
        System.out.printf("%d ml of milk\n", this.milk);
        System.out.printf("%d g of coffee beans\n", this.beans);
        System.out.printf("%d disposable cups\n", this.cups);
        System.out.printf("$%d of money\n", this.money);
        System.out.println();
    }


    private void initState() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.money = 550;
    }

    public void processAction(String action) {
        Scanner scanner = new Scanner(System.in);

        switch (action) {
            case "buy":
                processBuy(scanner);
                break;
            case "fill":
                fill(scanner);
                break;
            case "take":
                take();
                break;
            case "remaining":
                printState();
                break;
            case "exit":
                System.exit(0);
                return;
        }
        System.out.println();
    }

    private void processBuy(Scanner scanner) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");

        if (scanner.hasNextInt()) {
            int drinkId = scanner.nextInt();
            Coffee coffee = Coffee.values()[drinkId - 1];

            if (isEnoughResources(coffee)) {
                makeCoffee(coffee);
            } else {
                promptLackingResources(coffee);
            }
        }
    }

    private void promptLackingResources(Coffee coffee) {
        List<String> lacking = new ArrayList<>();
        if (this.water < coffee.water) {
            lacking.add("water");
        }
        if (this.milk < coffee.milk)  {
            lacking.add("milk");
        }
        if (this.beans < coffee.beans) {
            lacking.add("beans");
        }
        if (this.cups == 0) {
            lacking.add("cups");
        }
        System.out.printf("Sorry, not enough %s!\n", String.join(", ", lacking));
    }

    private boolean isEnoughResources(Coffee coffee) {
        return this.water >= coffee.water
                && this.milk >= coffee.milk
                && this.beans >= coffee.beans
                && this.cups >= 1;
    }

    private void makeCoffee(Coffee coffee) {
        System.out.println("I have enough resources, making you a coffee!");
        this.water -= coffee.water;
        this.milk -= coffee.milk;
        this.beans -= coffee.beans;
        this.money += coffee.cost;
        this.cups--;
    }

    private void fill(Scanner scanner) {
        System.out.println("Write how many ml of water you want to add:");
        int waterAdd = scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add:");
        int milkAdd = scanner.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        int beansAdd = scanner.nextInt();

        System.out.println("Write how many disposable cups you want to add:");
        int cupsAdd = scanner.nextInt();

        this.water += waterAdd;
        this.milk += milkAdd;
        this.beans += beansAdd;
        this.cups += cupsAdd;
    }

    private void take() {
        System.out.printf("I gave you $%d\n", money);
        this.money = 0;
    }

}
