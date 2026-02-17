# Prefer using .length instead of .length === 0 or .length < 0

This rule prohibits use of binary expressions but instead using member expression.

## Rule Details

Examples of **incorrect** code for this rule:

```js
if (items.length === 0) {
}
```

```js
if (items.length > 0) {
}
```

Examples of **correct** code for this rule:

```js
if (!items.length) {
}
```

```js
if (items.length) {
}
```

```js
if (!!items.length) {
}
```
