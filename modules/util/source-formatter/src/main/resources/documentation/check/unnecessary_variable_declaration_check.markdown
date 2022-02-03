## UnnecessaryVariableDeclarationCheck

No need to declare variable that is reassigned another value without being used.

### Example

Incorrect:

```java
...

String s = "";

s = "example";
```

Correct:

```java
...

String s = "example";
```

---

No need to declare variable that is returned right after.

### Example

Incorrect:

```java
public String method(String a, String b) {
	String s = a + b;

	return s;
}
```

Correct:

```java
public String method(String a, String b) {
	return a + b;
}
```

### Example

Incorrect:

```java
public List<String> method() {
	List<String> list = new ArrayList<>();

	list.add("a");
	list.add("b");

	return list;
}
```

Correct:

```java
public List<String> method() {
	return new ListUtil.fromArray("a", "b");
}
```
---

Use `String.valueOf()` to combine lines.

### Example

Incorrect:

```java
UUID uuid = UUID.randomUUID();

String sourceFileName = uuid.toString();
```

Correct:

```java
String sourceFileName = String.valueOf(UUID.randomUUID());
```