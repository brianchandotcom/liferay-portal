# Don't use negated `!Object.keys()` or double negated `!!Object.keys()` expressions as they always evaluate to `false` and `true`, respectively

This rule enforces that engineers don't use a truthy or falsy value that `Object.keys()` can return. Instead, use an expression that is more precise.

## Rule Details

Examples of **incorrect** code for this rule:

```js
if (Object.keys({foo: 'bar'})) {
	// do your magic
}

if (!Object.keys({foo: 'bar'})) {
	// do your magic
}

!!Object.keys({foo: 'bar'}) && 'test';

const negatedObjectKeys = !Object.keys({foo: 'bar'});

!Object.keys({foo: 'bar'}) && true;
```

Examples of **correct** code for this rule:

```js
if (Object.keys({foo: 'bar'}).length) {
	// do your magic
}

Object.keys({foo: 'bar'}).length && 'test';

if (Object.keys({foo: 'bar'}).find((i) => true)) {
	// do your magic
}
```

## Further Reading

-   https://github.com/liferay/liferay-frontend-projects/issues/10
