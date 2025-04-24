## PropertiesLanguageKeysCheck

Language keys should not have HTML markup.

### Example

Incorrect:

```
click-this-link=Click this <a href="{0}">link</a>.
```

Correct:

```
click-this-link=Click this {0}link{1}.
```