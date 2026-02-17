# Force JSX expressions to not check against .length without prefixing with `!!`

Because it we want to avoid accidentally rendering a literal "0" when checking for length in a JSX expression.

## Rule Details

Examples of **incorrect** code for this rule:

```js
<div>{items.length && <div />}</div>
```

Examples of **correct** code for this rule:

```js
<div>{!!items.length && <div />}</div>
```
