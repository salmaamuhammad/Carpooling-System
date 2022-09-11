/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carpooling_package;

import java.util.Scanner;

/**
 *
 * CarPooling class
 */
public class CarPooling {

   
    /**
     * * * REFERENCES:
 * [1] Dr. Sherin Moussa - Dr. Sally Saad. "Lecture 3 - Methods and Strings", Ain Shams University, Feb 2020.
 * [2] Dr. Sherin Moussa - Dr. Sally Saad. "Lecture 5 - Polymorphism and Javadoc", Ain Shams University, March 2020.
 * [3] Dr. Sherin Moussa - Dr. Sally Saad. "Lecture 7 - Exceptions Handling", Ain Shams University, March 2020.
 * [4] "Object Oriented Programming Concepts", @see <a href="https://docs.oracle.com/javase/tutorial/java/concepts/inheritance.html"> Inheritance </a>
 * [5] "Overriding in Java",  @see <a href="https://docs.oracle.com/javase/tutorial/java/IandI/override.html"> Overriding </a>
 * 

     * @throws myException which is a user defined exception thrown in case 
     * of unavailable data indexing 
     * @param args
     */
    public static void main(String[] args) throws myException {
        // TODO code application logic here
        int numPassengers = 20, numCars=25, subscribeAge=35, numTripsToSubscribe=3;
        passengers[] passengersArr = new passengers[numPassengers];
        Scanner scan = new Scanner(System.in);
        /** POLYMORPHISM */
        passengersArr[0] = new nonsubscribers(20, 3, "Amira");
        passengersArr[1] = new subscribers(45, 4, "Samir");
        passengersArr[2] = new subscribers(50, 3, "Jossef");
        
        Route route1 = new Route("Heliopolis", "Tagamoa");
        route1.setTripPrice(50.0f);
        Route route2 = new Route("Alex", "Cairo");
        route2.setTripPrice(250.0f);
        Route route3 = new Route("Tagamoa", "Heliopolis");
        route3.setTripPrice(50.0f);
        Route route4 = new Route("Safir", "Mokattam");
        route4.setTripPrice(60.0f);
        Route route5 = new Route("Maadi", "Abbassia");
        route5.setTripPrice(40.0f);
        Route route6 = new Route("Triumph", "Zamalek");
        route6.setTripPrice(75.0f);
        
        Car[] carArr = new Car[numCars];
        carArr[0] = new Car("20FA", route1, 10, 5, "Ali");
        carArr[1] = new Car("29SE", route2, 10, 5, "Sami");
        carArr[2] = new Car("56GD", route3, 3, 3, "Ahmad");
        carArr[3] = new Car("48WF", route4, 18, 4, "Mary");
        carArr[4] = new Car("83JX", route5, 16, 5, "Ibrahim");
        carArr[5] = new Car("36SE", route6, 20, 4, "Mazen");
       
        passengersArr[0].reserveTicket(carArr[0], route1.getTripPrice());
        passengersArr[1].reserveTicket(carArr[1], route2.getTripPrice());
        passengersArr[1].subscribe(route2.getTripPrice());
        passengersArr[2].reserveTicket(carArr[1], route2.getTripPrice());
        passengersArr[2].subscribe(route2.getTripPrice());
       
        int choice;
        do
        {
        /** Index of the car which its route is being searched for */
        int carIndex=0;
        System.out.println("Please press [1] to Reserve a ticket,  [2] to Display passengers reserved with the cars' details, " + 
                "[3] to Complain/Review or [4] to Exit.");
        choice = scan.nextInt();
        if(choice==1)
        {
            /** Boolean variable to mark whether the route is found*/ 
            boolean routeFound = false;
            System.out.println("Please enter the destination route you want");
            String searchRoute = scan.next();
            try
            {
            for(int i=0;i<Car.carCount;i++)
            {
             if(searchRoute.equals(carArr[i].getDestinLoc().toLowerCase()))
             {
                 routeFound=true;
                 break;
             }
             carIndex++;
            }
            if(!routeFound)
                 throw new myException("Unfortunately, the location you entered is currently unavailable"); 
            else if(!carArr[carIndex].checkCarCapacity())
                throw new myException("Unfortunately, the car capacity is full");
            }
            catch(myException e)
              {
                System.out.println(e.getMessage());
              }
            
            if(routeFound && (carArr[carIndex].checkCarCapacity()))
            {
                try
                {
                System.out.println("Please enter your age, number of trips you want to reserve and name (Respictivily)");
                System.out.println("Noting that: maximum number of trips for this car is:" + carArr[carIndex].getNumTripsPerDay());
                float age = scan.nextFloat();
                int numTrips = scan.nextInt();
                String name = scan.next();
                if(age>=subscribeAge && numTrips >=numTripsToSubscribe)
                    passengersArr[passengers.getPassengersCount()] = new subscribers(age, numTrips, name);
                else
                    passengersArr[passengers.getPassengersCount()] = new nonsubscribers(age, numTrips, name);

                passengersArr[(passengers.getPassengersCount()-1)].reserveTicket(carArr[carIndex], carArr[carIndex].carRoute.getTripPrice());
               
                if(passengersArr[(passengers.getPassengersCount()-1)] instanceof subscribers)
                {
                    passengersArr[(passengers.getPassengersCount()-1)].subscribe(carArr[carIndex].carRoute.getTripPrice());
                }
                } /**Java-defined exceptions
                   *
                   */
                  catch(ArrayIndexOutOfBoundsException e)
                   {
                    System.out.println("Unfortunately the list of passengers is full");
                   }
                  catch(NullPointerException e)
                   {
                      System.out.println("This car doesn't exist");
                   }
               
            }
        }
        else if(choice==2){
        for(int i=0;i<passengers.getPassengersCount();i++)
        {
           passengersArr[i].display();
        }
        }
        else if(choice==3)
        {
            System.out.println("Please enter your review\n" + "(satisfied) / (unsatisfied) ?");
            String review = scan.next();
            if(review.equals("unsatisfied"))
               passengersArr[passengers.getPassengersCount()-1].unsubscribe(carArr[carIndex].carRoute.getTripPrice());
            else if(!review.equals("unsatisfied") || !review.equals("satisfied"))
                System.out.println("Invalid entry");
        }
        else if(choice==4)
            break;
        else
            System.out.println("Invalid Entry");
  
        } while(choice!=4);
        }
    
}
/** CAR class */
class Car
{
    public String carCode;
    private final int numTripsPerDay;
    public Route carRoute;
    private final int maxCapacity;
    private final String driverName;
    public int currentCarCapacity=0;
    public static int carCount=0;
    /**Car constructor
     * 
     * @param carCode
     * @param carRoute
     * @param numTripsPerDay
     * @param maxCapacity
     * @param driverName 
     */
    public Car(String carCode, Route carRoute, int numTripsPerDay, int maxCapacity, String driverName) {
        carCount++;
        this.carCode = carCode;
        this.carRoute = carRoute;
        this.numTripsPerDay = numTripsPerDay;
        this.maxCapacity = maxCapacity;
        this.driverName = driverName;
    }

    /** 
     * 
     * @return the start location of route 
     */
    public String getStartLoc()
    {
        return carRoute.startLocation;
    }
    /**
     * 
     * @return the destination location of route 
     */
    public String getDestinLoc()
    {
        return carRoute.destinationLocation;
    }
    /**
     * 
     * @return the car code 
     */
    public String getCarCode() {
        return carCode;
    }
    /**
     * 
     * @return the number if trips per day
     */
    public int getNumTripsPerDay() {
        return numTripsPerDay;
    }

    /**
     * 
     * @param carRoute, sets the car route 
     */
    public void setCarRoute(Route carRoute) {
        this.carRoute = carRoute;
    }

    /**
     * 
     * @return the maximum capacity of the car 
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * 
     * @return the car driver's name 
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Checks the capacity of the car, whether the current capacity reached the maximum capacity or not
     * @return boolean flag (full or not)
     */
    public boolean checkCarCapacity()
    {
        if(currentCarCapacity<maxCapacity)
            return true;
        else
            return false;
    }
    /**
     * Searches for the given route in the available routes provided
     * @param searchroute, the given route
     * @return boolean variable (found or not)
     */
    public boolean searchRoutes(String searchroute)
    {
        if(searchroute.toLowerCase().equals(carRoute.getDestinationLocation()))
            return true;
        else
            return false;
    }
    
}
/**
 * 
 * ROUTE class
 */
class Route
{
    public String startLocation;
    public String destinationLocation;
    private float tripPrice;
    /**
     * Route class Constructor
     * @param startLocation
     * @param destinationLocation 
     */
    public Route(String startLocation, String destinationLocation) {
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
    }

    /**
     * 
     * @return the route destination location 
     */
    public String getDestinationLocation() {
        return destinationLocation;
    }

    /**
     * 
     * @param tripPrice
     * sets the trip price of a certain route
     */
    public void setTripPrice(float tripPrice) {
        this.tripPrice = tripPrice;
    }

    /**
     * 
     * @return the trip's price 
     */
    public float getTripPrice() {
        return tripPrice;
    }
    
}

/**
 * 
 * TICKET class
 */
class Ticket
{
    Car car;
    float price;
    String carCode;

    /**
     * Ticket constructor 
     * @param carCode
     * @param price of ticket
     */
    public Ticket(String carCode, float price) {
        this.carCode = carCode;
        this.price = price;
    }
    
}

/**
 * 
 * INTERPHASE implements some of passengers' functions
 * Factory design pattern
 * 
 */
interface functions
{
    public String Review();
    public void reserveTicket(Car c, float price);
    public void subscribe(float price);
    public void unsubscribe(float price);
}
/**
 * ABSTRACT class of PASSENGERS, implements the functions of interface
 * 
 */
abstract class passengers implements functions
{
    protected Ticket ticket;
    /**
     * FINAL attributes of passenger's age, number of trips he wants to reserve and his name
     */
    protected final float age;
    protected final float numTripsToBeReserved;
    protected final String passengerName;
    protected Car car;
    /** STATIC attribute of passengers counter */
    protected static int passengersCount=0;
    
    /**
     * passengers abstract class constructor 
     * @param age
     * @param numTripsToBeReserved
     * @param passengerName 
     */
    public passengers(float age, int numTripsToBeReserved, String passengerName) {
        passengersCount++;
        this.age = age;
        this.numTripsToBeReserved = numTripsToBeReserved;
        this.passengerName = passengerName;
    }

    /**
     * 
     * @return the passengers counter 
     */
    public static int getPassengersCount() {
        return passengersCount;
    }
   
    /**
     * function OVERRIDING
     * Review function, where the passenger can complain/review
     * @return the review message
     */
    @Override
    public String Review()
    {
        //momkn as2lo fl main hwa satisfied wla la, lw ah a subscribe , la a unsubscribe
        Scanner scan = new Scanner(System.in);
        String revMsg = scan.next();
        return revMsg;
    }
    /**
     * function OVERRIDING
     * unsubscribe function, unsubscribes a frequent passenger based on their review
     * @param price of the trip's ticket
     */
   @Override
    public void unsubscribe(float price)
    {
            float discountAmount = 0.5f*price;
            float priceAfterRemovingDiscount;
            priceAfterRemovingDiscount = price + discountAmount;
            ticket.price=priceAfterRemovingDiscount;
           
    }
    /**
     * function OVERRIDING
     * subscribe function, subscribes a frequent passenger if his age is >=35 years
     * and number of trips he want to reserve >=3 trips
     * @param price 
     */
    @Override
    public void subscribe(float price)
    {
      float discountAmount = 0.5f*price;
      float priceAfterDiscount = price - discountAmount;
      ticket.price=priceAfterDiscount;
    }
    /**
     * function OVERRIDING
     * function that reserves a ticket which links a certain car to certain passenger,
     * and increments the counter of car capacity
     * @param c
     * @param price 
     */
    @Override
    public void reserveTicket(Car c, float price)
    {
       
       car=c;
       c.currentCarCapacity++;
       Ticket t = new Ticket(c.carCode, price);
       ticket=t;
    }
    /**
     * FINAL method
     * displaying passengers along with the details of the car they rode and their routes.
     */
    public final void display()
    {
        System.out.println("Passenger's Name: " + passengerName + "   Age: " + age);
        System.out.println("Number of car trips they reserved: " + numTripsToBeReserved);
        System.out.println("Code of the Car they rode: " + car.carCode);
        System.out.println("Driver's name: " + car.getDriverName());
        System.out.println("Their ticket price: " + ticket.price);
        System.out.println("The route they took:\t From " + car.getStartLoc() + "\t To: " + car.getDestinLoc());
        System.out.println();
        }
    }

/**
 * INHERITED
 * SUBSCRIBERS class that is inherited from passengers class
 */
class subscribers extends passengers
{
    /**
     * subscribers constructor 
     * @param age
     * @param numTripsToBeReserved
     * @param passengerName 
     */
    public subscribers(float age, int numTripsToBeReserved, String passengerName) {
        super(age, numTripsToBeReserved, passengerName);
    }
}
/**
 * INHERITED
 * NONSUBSCRIBERS class that is inherited from passengers class
 */
class nonsubscribers extends passengers
{
    /**
     * nonsubscribers constructor
     * @param age
     * @param numTripsToBeReserved
     * @param passengerName 
     */
    public nonsubscribers(float age, int numTripsToBeReserved, String passengerName) {
        super(age, numTripsToBeReserved, passengerName);
    }
 
}
/**
 * USER-DEFINED EXCEPTION
 * 
 */
class myException extends Exception
{

    public myException() {
    }
/**
 * myException constructor
 * @param e 
 */
    public myException(String e) {
        super(e);
    }
    
}
