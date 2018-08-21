## Static Collections

When possible, do not use a static block to add values to a set

### Lists

```java
private static final List<String> _fruitList = Arrays.asList(
    "Apple", "Banana", "Kiwi", "Mango", "Orange", "Peach", "Pear",
    "Strawberry"));
```

Instead of

```java
private static final List<String> _fruitList = new ArrayList<>();

static {
    _fruitList.add("Apple");
    _fruitList.add("Banana");
    _fruitList.add("Kiwi");
    _fruitList.add("Mango");
    _fruitList.add("Orange");
    _fruitList.add("Peach");
    _fruitList.add("Pear");
    _fruitList.add("Strawberry");
}
```

### Maps

```java
private static final Map<String, String> _fruitMap =
    new HashMap<String, String>() {
        {
            put("Apple", "Green");
            put("Banana, "Yellow");
            put("Kiwi", "Brown");
            put("Mango", "Yellow");
            put("Orange", "Orange");
            put("Peach", "Orange");
            put("Pear", "Green");
            put("Strawberry", "Red");
        }
    };
```

Instead of

```java
private static final Map<String, String> _fruitMap = new HashMap<>();

static {
    _fruitMap.put("Apple", "Green");
    _fruitMap.put("Banana, "Yellow");
    _fruitMap.put("Kiwi", "Brown");
    _fruitMap.put("Mango", "Yellow");
    _fruitMap.put("Orange", "Orange");
    _fruitMap.put("Peach", "Orange");
    _fruitMap.put("Pear", "Green");
    _fruitMap.put("Strawberry", "Red");
}
```

### Sets

```java
private static final Set<String> _fruitSet = new HashSet<>(
    Arrays.asList(
        "Apple", "Banana", "Kiwi", "Mango", "Orange", "Peach", "Pear",
        "Strawberry"));
```

Instead of

```java
private static final Set<String> _fruitSet = new HashSet<>();

static {
    _fruitSet.add("Apple");
    _fruitSet.add("Banana");
    _fruitSet.add("Kiwi");
    _fruitSet.add("Mango");
    _fruitSet.add("Orange");
    _fruitSet.add("Peach");
    _fruitSet.add("Pear");
    _fruitSet.add("Strawberry");
}
```