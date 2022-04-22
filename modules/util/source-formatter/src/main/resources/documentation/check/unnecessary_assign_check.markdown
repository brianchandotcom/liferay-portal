## UnnecessaryAssignCheck

No need to assign a variable that will be reassigned without being used first.

### Example

Incorrect:

```java
...

s = "";

s = "example";
```

Correct:

```java
...

s = "example";
```

---

No need to assign a variable if it will be returned immediately after.

### Example

Incorrect:

```java
public String method(String a, String b) {
	...

	s = a + b;

	return s;
}
```

Correct:

```java
public String method(String a, String b) {
	...

	return a + b;
}
```

### Example

Incorrect:

```java
public List<String> method() {
	...

	list = new ArrayList<>();

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

No need to assign a method call's output to a variable if it will not be used after.

### Example

Incorrect:

```java
public String method(String a, String b) {
	...

	s = s.trim();

	return a + b;
}
```

Correct:

```java
public String method(String a, String b) {
	...

	return a + b;
}
```

---

Use `String.valueOf()` to combine lines.

### Example

Incorrect:

```java
UUID uuid = UUID.randomUUID();

sourceFileName = uuid.toString();
```

Correct:

```java
sourceFileName = String.valueOf(UUID.randomUUID());
```