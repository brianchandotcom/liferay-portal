# Consecutive test blocks (eg. `it()` etc) should be separated by a blank line (padded-test-blocks)

This rule enforces that `it()` calls and other test blocks are separated by a blank line.

## Rule Details

Examples of **incorrect** code for this rule:

```js
describe('thing', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});
	it('is red', () => {
		// ...
	});
	it('is loud', () => {
		// ...
	});
});
```

Examples of **correct** code for this rule:

```js
describe('thing', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('is red', () => {
		// ...
	});

	it('is loud', () => {
		// ...
	});
});
```

## Further Reading

-   [#65](https://github.com/liferay/eslint-config-liferay/issues/65)
