## NullAssertionInIfStatementCheck

Null check for variable should always be first before using it in if-statement.

### Examples

```java
public boolean method1(List<String> list) {
	if ((list == null) || list.isEmpty()) {
		return false;
	}

	return true;
}
```

Instead of

```java
public boolean method1(List<String> list) {
	if (list.isEmpty() || (list == null)) {
		return false;
	}

	return true;
}
```

---

```java
public boolean method2(List<String> list) {
	if ((list != null) && (list.size() == 3)) {
		return false;
	}

	return true;
}
```

Instead of

```java
public boolean method2(List<String> list) {
	if ((list.size() == 3) && (list != null)) {
		return false;
	}

	return true;
}
```

---

```java
public boolean method3(
	List<String> nameList1, List<String> nameList2, String name) {

	if (((nameList1 != null) && (nameList2 != null) &&
		 (nameList1.size() > nameList2.size)) ||
		((nameList1 != null) && (nameList1.size() > 5) &&
		 name.equals("Liferay"))) {

		return false;
	}

	return true;
}
```

Instead of

```java
public boolean method3(
	List<String> nameList1, List<String> nameList2, String name) {

	if (((nameList1 != null) && (nameList2 != null) &&
		 (nameList1.size() > nameList2.size)) ||
		((nameList1.size() > 5) && name.equals("Liferay") &&
		 (nameList1 != null))) {

		return false;
	}

	return true;
}
```