# Use `dataset` instead of `getAttribute()` for `data-*` attribtues (no-get-data-attribute)

This rule prohibits use of `getAttribute()` for `data-*` html attributes.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const foo = el.getAttribute('data-foo');
```

Examples of **correct** code for this rule:

```js
const foo = el.dataset.foo;
```
