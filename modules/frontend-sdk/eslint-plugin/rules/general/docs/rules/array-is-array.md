# Force usage of `Array.isArray()` (array-is-array)

Because it is [surprisingly difficult to reliably determine whether something is an `Array` in JavaScript](http://web.mit.edu/jwalden/www/isArray.html), [`Array.isArray()`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/isArray) should be used instead.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const isArray = maybeArray instanceof Array;
```

Examples of **correct** code for this rule:

```js
const isArray = Array.isArray(maybeArray);
```

## Further Reading

-   https://github.com/liferay/eslint-config-liferay/issues/139
