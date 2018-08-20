## Static Collections

When possible, do not use a static block to add values to a set

Use

```java
private static final Set<String> _fruit = new HashSet<>(
    Arrays.asList(
        "Apple", "Banana", "Kiwi", "Mango", "Orange", "Peach", "Pear",
        "Strawberry"));
```

Instead of

```java
private static final Set<String> _fruit = new HashSet<>();

static {
    _fruit.add("Apple");
    _fruit.add("Banana");
    _fruit.add("Kiwi");
    _fruit.add("Mango");
    _fruit.add("Orange");
    _fruit.add("Peach");
    _fruit.add("Pear");
    _fruit.add("Strawberry");
}
```