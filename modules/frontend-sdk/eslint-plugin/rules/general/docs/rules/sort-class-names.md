# Sort class names inside the "className" JSX attribute (sort-class-names)

This rule enforces (and autofixes) that the class names inside the "className" attribute of a JSX element are sorted. This rule also works with any attribute that looks "className-ish" (that is, attributes of the form "someClassName" containing string values).

## Rule Details

Examples of **incorrect** code for this rule:

```js
<div className="a c b d"></div>
<div className={'bar foo'}></div>
<CustomPopover triggerClassName="z y x" />
```

Examples of **correct** code for this rule:

```js
<div className="a b c d"></div>
<div className="a b"></div>
<div className={'foo bar'}></div>
<CustomPopover triggerClassName="x y z" />
```

### Limitations

Note that this only looks at string literals and simple template literals (that is, template literals without interpolated expressions).

It does not currently check calls to the popular [classnames](https://www.npmjs.com/package/classnames) NPM package:

```js
// `classNames()` calls are not checked:
<div className={classNames('a', 'b')}></div>
```

## Related

-   [no-duplicate-class-names](./no-duplicate-class-names.md)
-   [trim-class-names](./trim-class-names.md)

## Further Reading

-   [Issue \#108: Sort class names in JSX className attributes](https://github.com/liferay/eslint-config-liferay/issues/108)
