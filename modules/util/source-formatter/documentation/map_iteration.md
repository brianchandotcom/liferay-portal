## Map Iteration

Use the `entrySet()` iterator when retrieving values from a `Map`, instead of
retrieving values from the `Map` while iterating over the `Map` keys using
`keySet()`. Performance of this is better than calling `Map.get(key)` for each
entry while iterating also.

### Example

Incorrect:

```java
for (String name : map.keySet()) {
    String value = map.getValue(name);

    ...

}
```

Correct:

```java
for (Map.Entry<String, String> entry : map.entrySet()) {
    String value = entry.getValue();

    ...

}
```