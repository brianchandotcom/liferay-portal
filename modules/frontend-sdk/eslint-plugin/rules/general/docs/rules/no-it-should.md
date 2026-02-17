# `it()` strings should not start with "should" (no-it-should)

This rule enforces that `it()` descriptions start with a verb, not with "should".

## Rule Details

Descriptions that start with "should" can usually be rewritten more concisely and without loss of information by dropping the "should" and starting with an appropriate verb. This is useful because it reduces the likelihood we'll have to introduce an ugly linebreak that harms readability.

Examples of **incorrect** code for this rule:

```js
it('should reload after clicking', () => {
	// ...
});
```

Examples of **correct** code for this rule:

```js
it('reloads after clicking', () => {
	// ...
});
```

## Further Reading

-   [Liferay Frontend Guidelines for testing](https://github.com/liferay/liferay-frontend-guidelines/blob/master/guidelines/general/testing.md)
