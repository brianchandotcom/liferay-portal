## Static Blocks

When possible, do not use static blocks to make method calls on variables.
Instead, make those calls inside the variable definition.

### Examples

---

```java
private static final Map<String, String> _fruitMap =
    new HashMap<String, String>() {
        {
            put("Apple", "Green");
            put("Banana", "Yellow");
            put("Orange", "Orange");
            put("Pear", "Green");
            put("Strawberry", "Red");
        }
    };
private static final Map<String, String> _vegetableMap =
    new HashMap<String, String>() {
        {
            put("Beet", "Red");
            put("Carrot, "Orange");
            put("Eggplant", "Purple");
            put("Potato", "Yellow");
            put("Spinach", "Green");
        }
    };
```

Instead of

```java
private static final Map<String, String> _fruitMap = new HashMap<>();
private static final Map<String, String> _vegetableMap = new HashMap<>();

static {
    _fruitMap.put("Apple", "Green");
    _fruitMap.put("Banana", "Yellow");
    _fruitMap.put("Orange", "Orange");
    _fruitMap.put("Pear", "Green");
    _fruitMap.put("Strawberry", "Red");

    _vegetableMap.put("Beet", "Red");
    _vegetableMap.put("Carrot, "Orange");
    _vegetableMap.put("Eggplant", "Purple");
    _vegetableMap.put("Potato", "Yellow");
    _vegetableMap.put("Spinach", "Green");
}
```

---

```java
private static final List<Vegetable> _greenVegetablesList =
    new ArrayList<String>() {
        {
            try {
                for (Vegetable vegetable : _getVegetableList()) {
                    if (vegetable.isGreen()) {
                        add(vegetable);
                    }
                }
            catch (Exception e) {
                _log.error(e, e);
            }
        }
    };
```

Instead of

```java
private static final List<Vegetable> _greenVegetablesList = new ArrayList<>();

static {
    try {
        for (Vegetable vegetable : _getVegetableList()) {
            if (vegetable.isGreen()) {
                _greenVegetablesList.add(vegetable);
            }
        }
    catch (Exception e) {
        _log.error(e, e);
    }
}
```