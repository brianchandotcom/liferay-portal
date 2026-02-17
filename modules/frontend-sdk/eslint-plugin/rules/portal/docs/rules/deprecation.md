# Deprecation annotations must be well-formatted (deprecation)

Ensures that deprecation notices all have the same format.

For simple deviations from the expected format, the rule can apply an autofix (see [the tests](../../tests/lib/rules/deprecation.js)).

## Rule Details

Examples of **incorrect** code for this rule:

```js
/**
 * @deprecated Since 7.1 — use Liferay.foo instead.
 */

/**
 * @deprecated From 7.2.x with no replacement
 */
```

Examples of **correct** code for this rule:

```js
/**
 * @deprecated As of Judson (7.1.x), replaced by Liferay.foo
 */

/**
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
```

In short, all notices should contain one of the following version descriptors:

-   Bunyan (6.0.x)
-   Paton (6.1.x)
-   Newton (6.2.x)
-   Wilberforce (7.0.x)
-   Judson (7.1.x)
-   Mueller (7.2.x)
-   Athanasius (7.3.x)
-   Cavanaugh (7.4.x)

And replacement information of the form:

-   `replaced by REPLACEMENT`; or
-   `with no direct replacement`

As an exception to the above requirement, this lint rule does not complain about notices that do not specify replacement information; this is because there are some existing old notices in liferay-portal that do not currently have it. Be aware that this exception may be removed in the future:

```js
// Don't do this, although it is allowed, for now...

/**
 * @deprecated As of Wilberforce (7.0.x)
 */
```

## Further Reading

-   [eslint-config-liferay/#55](https://github.com/liferay/eslint-config-liferay/pull/55)
