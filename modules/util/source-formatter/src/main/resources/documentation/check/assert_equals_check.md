## AssertEqualsCheck

Use `Assert.assertTrue` and `Assert.assertFalse` to simplify code, when possible:

```java
Assert.assertFalse(
	_hasColumn(objectDefinition.getDBTableName(), "able"));
Assert.assertTrue(
	_hasColumn(objectDefinition.getDBTableName(), "able_"));
```

Instead of

```java
Assert.assertEquals(
	false, _hasColumn(objectDefinition.getDBTableName(), "able"));
Assert.assertEquals(
	true, _hasColumn(objectDefinition.getDBTableName(), "able_"));
```