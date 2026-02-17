# Avoid using `A.Modal`

This rule prohibits using `A.Modal()` and proposes the usage of `Liferay.Util.openModal()`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
var modal = new A.Modal({modalOptions});
```

Examples of **correct** code for this rule:

```js
Liferay.Util.openModal({modalOptions});
```
