# No arrow functions (no-arrow)

This rule can be selectively applied in the rare contexts where we don't transpile our source and we want to be sure that an arrow function doesn't end up causing IE to explode.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const add = (a, b) => a + b;
```

Examples of **correct** code for this rule:

```js
function add(a, b) {
	return a + b;
}
```

## See also

-   https://github.com/liferay/eslint-config-liferay/issues/179
