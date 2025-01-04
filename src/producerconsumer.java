public class producerconsumer {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();
        Thread produceThread = new Thread(new producer(buffer));
        Thread consumeThread = new Thread(new consumer(buffer));
        produceThread.start();
        consumeThread.start();
    }
}

class SharedBuffer{
    public int data;
    public boolean isEmpty = true;

    public synchronized void produce(int value) throws InterruptedException{
        if(!isEmpty){
            wait();
        }
        data = value;
        isEmpty = false;
        System.out.println("Produced: " + data);

        notify();

    }
    public synchronized void consume() throws InterruptedException{
        if(isEmpty){
            wait();
        }
        System.out.println("Consumed: " + data);
        isEmpty = true;
        notify();
    }

}

class producer implements Runnable{
    private SharedBuffer buffer;
    public producer(SharedBuffer buffer){
        this.buffer = buffer;
    }
    public void run(){
        try{
            for(int i=0;i<=5;i++){
                buffer.produce(i);
                Thread.sleep(1000);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

class consumer implements Runnable{
    private SharedBuffer buffer;
    public consumer(SharedBuffer buffer){
        this.buffer = buffer;
    }
    public void run(){
        try{
            for(int i=0;i<=5;i++){
                buffer.consume();
                Thread.sleep(1500);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
