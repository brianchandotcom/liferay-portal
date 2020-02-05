## JavaLocalSensitiveComparisonCheck

When we compare localized values, we have to use `java.text.Collator` to compare
those values. If we compare the values using regular String comparison
(`java.util.String#compareTo`), we can run into problems with languages that use
special characters.

For example, the character &Aacute; in Spanish, should be considered as next
to `A`, but regular String comparison would give incorrect results, as
&Aacute; comes lexicographically after `Z`.

### Example

Incorrect:

```java
@Override
public int compare(String value1, String value2) {
    String localizedValue1 = getLocalizedValue(value1, _locale);
    String localizedValue2 = getLocalizedValue(value2, _locale);

    return localizedValue1.compareTo(localizedValue2);
}
```

Correct:

```java
@Override
public int compare(String value1, String value2) {
    Collator collator = Collator.getInstance(_locale);

    String localizedValue1 = getLocalizedValue(value1, _locale);
    String localizedValue2 = getLocalizedValue(value2, _locale);

    return collator.compare(localizedValue1, localizedValue2);
}
```