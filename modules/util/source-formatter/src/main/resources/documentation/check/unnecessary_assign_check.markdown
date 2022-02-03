## UnnecessaryAssignCheck

No need to assign call to variable that is reassigned another value without
being used.

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

No need to assign call to variable that is returned right after.

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

No need to assign call to variable that is not used after the call.

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