## JavaCollatorUtilCheck

Instead of getting an instance of Collator by calling
`java.text.Collator.getInstance(java.util.Locale)` we should be calling
`com.liferay.portal.kernel.util.CollatorUtil.getInstance(java.util.Locale)`

Example:

```java
public PortletIdComparator(Locale locale) {
    _locale = locale;

    _collator = CollatorUtil.getInstance(_locale);
}
```

`java.text.Collator` may handle space and other characters in a non-expected
order.

See also:

- http://jan.baresovi.cz/dr/en/java-collator-spaces
- https://docs.oracle.com/javase/7/docs/api/java/text/RuleBasedCollator.html
- https://stackoverflow.com/questions/15230339/collator-compares-strings-weird