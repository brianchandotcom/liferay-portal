# @liferay/eslint-plugin

> An ESLint plugin that helps enforce the [Liferay Frontend Guidelines](https://github.com/liferay/liferay-frontend-guidelines).

## Overview

| Preset    | Extends                                                                                                              | Description                                                                                                                                                                                           |
| --------- | -------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `general` | [eslint:recommended](https://eslint.org/docs/rules/), [prettier](https://github.com/prettier/eslint-config-prettier) | Base configuration, suitable for general projects                                                                                                                                                     |
| `react`   | `general`                                                                                                            | `general`, plus rules from [eslint-plugin-react](https://github.com/yannickcr/eslint-plugin-react) and [react-hooks](https://reactjs.org/docs/hooks-rules.html), suitable for projects that use React |
| `metal`   | `react`                                                                                                              | Like `react`, but turns off rules that cause false positives in [Metal components](http://metaljs.com/)                                                                                               |
| `portal`  | `react`                                                                                                              | Default for projects inside [liferay-portal](https://github.com/liferay/liferay-portal) itself                                                                                                        |

## Installation

```
$ npm install --save-dev eslint @liferay/eslint-plugin
```

### Coming from @liferay/eslint-config?

If you previously used `@liferay/eslint-config`, upgrading to use this plugin is very simple. All of the same rules apply, you just need to update your `.eslintrc` to include the plugin and specify which preset you are using.

```
$ npm install --save-dev @liferay/eslint-plugin
```

```js
module.exports = {
	plugins: ['@liferay'],
	extends: ['plugin:@liferay/general'],
};
```

## Usage

Once the `@liferay/eslint-plugin` package is installed, you can use it by specifying `@liferay` in the [`extends`](http://eslint.org/docs/user-guide/configuring#extending-configuration-files) section of your [ESLint configuration](http://eslint.org/docs/user-guide/configuring).

```js
module.exports = {
	plugins: ['@liferay'],
	extends: ['plugin:@liferay/general'],
};
```

This preset provides a reasonable starting point for an independent open source project. You can also specify `plugin:@liferay/react`, `plugin:@liferay/metal`, or `plugin:@liferay/portal`.

### liferay-portal

In [liferay-portal](https://github.com/liferay/liferay-portal) itself we extend the `@liferay/portal` preset instead, which activates some rules specific to liferay-portal. This preset assumes the use of React, and also provides a set of custom rules that are described in detail in the `@liferay/portal` section below.

This extension is applied automatically by [@liferay/npm-scripts](https://github.com/liferay/liferay-frontend-projects/tree/master/projects/npm-tools/packages/npm-scripts), so you don't have to configure it explicitly.

> **An important disclaimer about the use of ESLint in liferay-portal**
>
> JavaScript code that appears inline inside JSP files and other templates is only lightly checked by ESLint, because JSP is an impoverished environment where we have to work with context-free snippets of text as opposed to fully-formed, valid JS modules. Our long-term strategy is to move as much code as possible out of JSP and into React components, but in the interim, please be aware that the level of safety provided by the linter inside JSP is greatly reduced.

### React

For React projects outside of liferay-portal, you can extend `@liferay/react` instead:

```js
module.exports = {
	plugins: ['@liferay'],
	extends: ['plugin:@liferay/react'],
};
```

### metal-jsx

For legacy projects inside liferay-portal that use [metal-jsx](https://www.npmjs.com/package/metal-jsx), we have a "metal" preset:

```js
module.exports = {
	plugins: ['@liferay'],
	extends: ['plugin:@liferay/metal'],
};
```

Use this preset to stop ESLint from [spuriously warning that variables that are used as JSX components are unused](https://github.com/yannickcr/eslint-plugin-react/blob/master/docs/rules/jsx-uses-vars.md).

### Copyright headers

The included [`eslint-plugin-notice`](https://www.npmjs.com/package/eslint-plugin-notice) plug-in can be used to enforce the use of uniform copyright headers across a project by placing a template named `copyright.js` in the project root (for example, see [the file defining the headers used in @lifeary/eslint-plugin itself](https://github.com/liferay/liferay-frontend-projects/blob/master/projects/eslint-plugin/copyright.js)) and configuring the rule:

```javascript
const path = require('path');

module.exports = {
	plugins: ['@liferay'],
	extends: ['plugin:@liferay/general'],
	rules: {
		'notice/notice': [
			'error',
			{
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
	},
};
```

Explicit configuration is required in order to make overrides possible; for example:

-   `top-level/`
    -   `.eslintrc.js`
    -   `copyright.js`
    -   `mid-level/`
        -   `.eslintrc.js`
        -   `copyright.js`
        -   `bottom-level/`
            -   `.eslintrc.js`

If we were to provide configuration by default, then if `bottom-level/.eslintrc.js` does an `extends: ['plugin:@liferay/general']`, then the default configuration would be considered more local than the one provided by `mid-level`, causing the wrong `copyright.js` to be used.

### Base rules

| Rule or preset                                                                                                                          | Where we use it                | Notes                                                                                                                              |
| --------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------- |
| [eslint-config-prettier](https://github.com/prettier/eslint-config-prettier)                                                            | @liferay                       | Preset that turns off ESLint rules that conflict with [Prettier](https://prettier.io/)                                             |
| [eslint:recommended](https://eslint.org/docs/rules/)                                                                                    | @liferay                       | Preset bundled with [ESLint](https://eslint.org/docs/rules/)                                                                       |
| [default-case](https://eslint.org/docs/rules/default-case)                                                                              | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [@liferay/portal/deprecation](./rules/portal/docs/rules/deprecation.md)                                                                 | @liferay/portal                | [\#55](https://github.com/liferay/eslint-config-liferay/pull/55)                                                                   |
| [@liferay/portal/no-explicit-extend](./rules/portal/docs/rules/no-explicit-extend.md)                                                   | @liferay/portal                | [\#54](https://github.com/liferay/eslint-config-liferay/pull/54)                                                                   |
| [@liferay/portal/no-global-fetch](./rules/portal/docs/rules/no-global-fetch.md)                                                         | @liferay/portal                | [\#62](https://github.com/liferay/eslint-config-liferay/pull/62)                                                                   |
| [@liferay/portal/no-loader-import-specifier](./rules/portal/docs/rules/no-loader-import-specifier.md)                                   | @liferay/portal                | [\#122](https://github.com/liferay/eslint-config-liferay/issues/122)                                                               |
| [@liferay/portal/no-metal-plugins](./rules/portal/docs/rules/no-metal-plugins.md)                                                       | @liferay/portal                | [\#61](https://github.com/liferay/eslint-config-liferay/pull/61)                                                                   |
| [@liferay/portal/no-react-dom-render](./rules/portal/docs/rules/no-react-dom-render.md)                                                 | @liferay/portal                | [\#71](https://github.com/liferay/eslint-config-liferay/pull/71)                                                                   |
| [@liferay/portal/no-side-navigation](./rules/portal/docs/rules/no-side-navigation.md)                                                   | @liferay/portal                | [\#44](https://github.com/liferay/eslint-config-liferay/pull/44)                                                                   |
| [@liferay/array-is-array](./rules/general/docs/rules/array-is-array.md)                                                                 | @liferay                       | [\#139](https://github.com/liferay/eslint-config-liferay/issues/139)                                                               |
| [@liferay/destructure-requires](./rules/general/docs/rules/destructure-requires.md)                                                     | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/empty-line-between-elements](./rules/general/docs/rules/empty-line-between-elements.md)                                       | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/expect-assert](./rules/general/docs/rules/expect-assert.md)                                                                   | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/group-imports](./rules/general/docs/rules/group-imports.md)                                                                   | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/import-extensions](./rules/general/docs/rules/import-extensions.md)                                                           | @liferay                       | [\#137](https://github.com/liferay/eslint-config-liferay/issues/137)                                                               |
| [@liferay/imports-first](./rules/general/docs/rules/imports-first.md)                                                                   | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-abbreviations](./rules/general/docs/rules/no-abbreviations.md)                                                             | @liferay                       | [\#437](https://github.com/liferay/liferay-frontend-projects/issues/437)                                                           |
| [@liferay/no-absolute-import](./rules/general/docs/rules/no-absolute-import.md)                                                         | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-anonymous-exports](./rules/general/docs/rules/no-anonymous-exports.md)                                                     | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-arrow](./rules/general/docs/rules/no-arrow.md)                                                                             | @liferay                       | [\#179](https://github.com/liferay/eslint-config-liferay/issues/179)                                                               |
| [@liferay/no-conditional-object-keys](./rules/general/docs/rules/no-conditional-object-keys.md)                                         | @liferay                       | [\#108](https://github.com/liferay/eslint-config-liferay/issues/108)                                                               |
| [@liferay/no-duplicate-class-names](./rules/general/docs/rules/no-duplicate-class-names.md)                                             | @liferay                       | [\#108](https://github.com/liferay/eslint-config-liferay/issues/108)                                                               |
| [@liferay/no-duplicate-imports](./rules/general/docs/rules/no-duplicate-imports.md)                                                     | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-dynamic-require](./rules/general/docs/rules/no-dynamic-require.md)                                                         | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-get-data-attribute](./rules/general/docs/rules/no-get-data-attribute.md)                                                   | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/no-it-should](./rules/general/docs/rules/no-it-should.md)                                                                     | @liferay                       | [\#43](https://github.com/liferay/eslint-config-liferay/pull/43)                                                                   |
| [@liferay/no-length-jsx-expression](./rules/general/docs/rules/no-length-jsx-expression.md)                                             | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/no-require-and-call](./rules/general/docs/rules/no-require-and-call.md)                                                       | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/no-typeof-object](./rules/general/docs/rules/no-typeof-object.md)                                                             | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/no-use-strict-in-module](./rules/general/docs/rules/no-use-strict-in-module.md)                                               | @liferay                       | [\#94](https://github.com/liferay/eslint-config-liferay/issues/94)                                                                 |
| [@liferay/padded-test-blocks](./rules/general/docs/rules/padded-test-blocks.md)                                                         | @liferay                       | [\#75](https://github.com/liferay/eslint-config-liferay/pull/75)                                                                   |
| [@liferay/prefer-length-check](./rules/general/docs/rules/prefer-length-check.md)                                                       | @liferay                       | [\#75](https://github.com/liferay/eslint-config-liferay/pull/75)                                                                   |
| [@liferay/ref-name-suffix](./rules/general/docs/rules/ref-name-suffix.md)                                                               | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/sort-class-names](./rules/general/docs/rules/sort-class-names.md)                                                             | @liferay                       | [\#108](https://github.com/liferay/eslint-config-liferay/issues/108)                                                               |
| [@liferay/sort-import-destructures](./rules/general/docs/rules/sort-import-destructures.md)                                             | @liferay                       | [\#124](https://github.com/liferay/eslint-config-liferay/issues/124)                                                               |
| [@liferay/sort-imports](./rules/general/docs/rules/sort-imports.md)                                                                     | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [@liferay/trim-class-names](./rules/general/docs/rules/trim-class-names.md)                                                             | @liferay                       | [\#108](https://github.com/liferay/eslint-config-liferay/issues/108)                                                               |
| [@liferay/use-state-naming-pattern](./rules/general/docs/rules/use-state-naming-pattern.md)                                             | @liferay                       | [\#108](https://github.com/liferay/eslint-config-liferay/issues/108)                                                               |
| [no-console](https://eslint.org/docs/rules/no-console)                                                                                  | @liferay                       | [\#79](https://github.com/liferay/eslint-config-liferay/pull/79)                                                                   |
| [no-eval](https://eslint.org/docs/rules/no-eval)                                                                                        | @liferay                       | [\#432](https://github.com/liferay/liferay-frontend-projects/issues/432)                                                           |
| [no-for-of-loops/no-for-of-loops](https://www.npmjs.com/package/eslint-plugin-no-for-of-loops) (default: off)                           | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [no-only-tests/no-only-tests](https://www.npmjs.com/package/eslint-plugin-no-only-tests)                                                | @liferay                       | [\#22](https://github.com/liferay/eslint-config-liferay/pull/22)                                                                   |
| [no-restricted-globals](https://eslint.org/docs/rules/no-restricted-globals)                                                            | @liferay/portal                | [\#109](https://github.com/liferay/eslint-config-liferay/issues/109)                                                               |
| [no-return-assign](https://eslint.org/docs/rules/no-return-assign)                                                                      | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [no-unused-expressions](https://eslint.org/docs/rules/no-unused-expressions)                                                            | @liferay                       | [\#19](https://github.com/liferay/eslint-config-liferay/issues/19)                                                                 |
| [no-unused-vars](https://eslint.org/docs/rules/no-unused-vars)                                                                          | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [notice/notice](https://www.npmjs.com/package/eslint-plugin-notice)                                                                     | @liferay                       | [\#26](https://github.com/liferay/eslint-config-liferay/pull/26)                                                                   |
| [object-shorthand](https://eslint.org/docs/rules/object-shorthand)                                                                      | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [prefer-const](https://eslint.org/docs/rules/prefer-const)                                                                              | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [quote-props](https://eslint.org/docs/rules/quote-props)                                                                                | @liferay                       | [\#30](https://github.com/liferay/eslint-config-liferay/pull/30)                                                                   |
| [radix](https://eslint.org/docs/rules/radix)                                                                                            | @liferay                       | [\#66](https://github.com/liferay/eslint-config-liferay/pull/66)                                                                   |
| [react-hooks/exhaustive-deps](https://www.npmjs.com/package/eslint-plugin-react-hooks)                                                  | @liferay/react                 | [Rules of Hooks](https://reactjs.org/docs/hooks-rules.html)                                                                        |
| [react-hooks/rules-of-hooks](https://www.npmjs.com/package/eslint-plugin-react-hooks)                                                   | @liferay/react                 | [Rules of Hooks](https://reactjs.org/docs/hooks-rules.html)                                                                        |
| [react/forbid-foreign-prop-types](https://github.com/yannickcr/eslint-plugin-react/blob/master/docs/rules/forbid-foreign-prop-types.md) | @liferay/react                 | [\#301](https://github.com/liferay/liferay-npm-tools/issues/301)                                                                   |
| [react/jsx-curly-brace-presence](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-curly-brace-presence.md)     | @liferay/react                 | [\#421](https://github.com/liferay/liferay-frontend-projects/issues/421)                                                           |
| [react/jsx-fragments](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-fragments.md)                           | @liferay/react                 | [\#58](https://github.com/liferay/eslint-config-liferay/pull/58)                                                                   |
| [react/jsx-key](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-key.md)                                       | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/jsx-no-comment-textnodes](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-no-comment-textnodes.md)     | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/jsx-no-duplicate-props](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-no-duplicate-props.md)         | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/jsx-no-undef](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-no-undef.md)                             | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/jsx-sort-props](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-sort-props.md)                         | @liferay/react                 | [\#58](https://github.com/liferay/eslint-config-liferay/pull/51)                                                                   |
| [react/jsx-uses-react](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-uses-react.md)                         | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/jsx-uses-vars](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/jsx-uses-vars.md)                           | @liferay/react, @liferay/metal | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42), [\#50](https://github.com/liferay/eslint-config-liferay/pull/50) |
| [react/no-children-prop](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-children-prop.md)                     | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-danger-with-children](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-danger-with-children.md)       | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-direct-mutation-state](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-direct-mutation-state.md)     | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-is-mounted](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-is-mounted.md)                           | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-render-return-value](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-render-return-value.md)         | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-string-refs](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-string-refs.md)                         | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-unescaped-entities](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-unescaped-entities.md)           | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/no-unknown-property](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/no-unknown-property.md)               | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [react/require-render-return](https://github.com/yannickcr/eslint-plugin-react/blob/HEAD/docs/rules/require-render-return.md)           | @liferay/react                 | [\#42](https://github.com/liferay/eslint-config-liferay/pull/42)                                                                   |
| [sort-destructure-keys/sort-destructure-keys](https://github.com/mthadley/eslint-plugin-sort-destructure-keys)                          | @liferay                       | [\#60](https://github.com/liferay/liferay-frontend-guidelines/issues/60)                                                           |
| [sort-keys](https://eslint.org/docs/rules/sort-keys)                                                                                    | @liferay                       | [\#63](https://github.com/liferay/eslint-config-liferay/pull/63)                                                                   |
| [spaced-comment](https://eslint.org/docs/rules/spaced-comment)                                                                          | @liferay                       | [\#225](https://github.com/liferay/liferay-frontend-projects/issues/225)                                                           |

### Custom rules

#### `@liferay`

The bundled `@liferay` plugin includes the following [rules](./rules/general/docs/rules):

-   [@liferay/array-is-array](./rules/general/docs/rules/array-is-array.md): Enforces (and autofixes) the use of `Array.isArray()` over `instanceof Array`.
-   [@liferay/destructure-requires](./rules/general/docs/rules/destructure-requires.md): Enforces (and autofixes) that `require` statements use destructuring.
-   [@liferay/empty-line-between-elements](./rules/general/docs/rules/empty-line-between-elements.md): Enforces (and autofixes) a missing empty line between sibling elements.
-   [@liferay/expect-assert](./rules/general/docs/rules/expect-assert.md): Enforces that every `expect()` should assert something.
-   [@liferay/group-imports](./rules/general/docs/rules/group-imports.md): Enforces (and autofixes) `import` and `require` grouping.
-   [@liferay/import-extensions](./rules/general/docs/rules/import-extensions.md): Enforces consistent usage/omission of file extensions in imports.
-   [@liferay/imports-first](./rules/general/docs/rules/imports-first.md): Enforces that imports come first in the file.
-   [@liferay/no-abbreviations](./rules/general/docs/rules/no-abbreviations.md): Enforces the use of the expanded word if it finds a registered abbreviation in an identifier.
-   [@liferay/no-absolute-import](./rules/general/docs/rules/no-absolute-import.md): Enforces that imports do not use absolute paths.
-   [@liferay/no-anonymous-exports](./rules/general/docs/rules/no-anonymous-exports.md): Enforce the use of named function for export instead of arrow function.
-   [@liferay/no-arrow](./rules/general/docs/rules/no-arrow.md): Bans arrow functions (for IE; not on by default).
-   [@liferay/no-conditional-object-keys](./rules/general/docs/rules/no-conditional-object-keys.md): Enforces that engineers don't use a truthy or falsy value that `Object.keys()` can return.
-   [@liferay/no-duplicate-class-names](./rules/general/docs/rules/no-duplicate-class-names.md): Enforces (and autofixes) uniqueness of class names inside JSX `className` attributes.
-   [@liferay/no-duplicate-imports](./rules/general/docs/rules/no-duplicate-imports.md): Enforces at most one `import` of any given module per file.
-   [@liferay/no-dynamic-require](./rules/general/docs/rules/no-dynamic-require.md): Enforces that `require()` calls use static arguments.
-   [@liferay/no-get-data-attribute](./rules/general/docs/rules/no-get-data-attribute.md): Enforces the use of `dataset` instead of `getAttribute()`.
-   [@liferay/no-it-should](./rules/general/docs/rules/no-it-should.md): Enforces that `it()` descriptions start with a verb, not with "should".
-   [@liferay/no-length-jsx-expression](./rules/general/docs/rules/no-it-should.md): Enforces that JSX expressions that check against length should make sure 0 isn't rendered".
-   [@liferay/no-require-and-call](./rules/general/docs/rules/no-require-and-call.md): Enforces that the result of a `require()` call at the top level is not immediately called.
-   [@liferay/no-typeof-object](./rules/general/docs/rules/no-typeof-object.md): Enforces the explicit check against `null` instead of checking for type.
-   [@liferay/no-use-strict-in-module](./rules/general/docs/rules/no-use-strict-in-module.md): Bans the use of `'use strict';` inside modules.
-   [@liferay/padded-test-blocks](./rules/general/docs/rules/padded-test-blocks.md): Enforces blank lines between test blocks (`it()` etc).
-   [@liferay/prefer-length-check](./rules/general/docs/rules/prefer-length-check.md): Bans the use of binary expressions, and instead enforces using member expression.
-   [@liferay/ref-name-suffix](./rules/general/docs/rules/ref-name-suffix.md): This rule enforces (and autofixes) `useRef` hooks from React so that the variable name is suffixed with `Ref`.
-   [@liferay/sort-class-names](./rules/general/docs/rules/sort-class-names.md): Enforces (and autofixes) ordering of class names inside JSX `className` attributes.
-   [@liferay/sort-import-destructures](./rules/general/docs/rules/sort-import-destructures.md): Enforces (and autofixes) ordering of destructured names in `import` statements.
-   [@liferay/sort-imports](./rules/general/docs/rules/sort-imports.md): Enforces (and autofixes) `import` and `require` ordering.
-   [@liferay/trim-class-names](./rules/general/docs/rules/trim-class-names.md): Enforces (and autofixes) that class names inside JSX `className` attributes do not have leading or trailing whitespace.
-   [@liferay/use-state-naming-pattern](./rules/general/docs/rules/use-state-naming-pattern.md): Enforces (and autofixes) that destructured values from useState follow the pattern `[val, setVal]`(val can be anything).

#### `@liferay/portal`

The bundled `@liferay/portal` plugin includes the following [rules](./rules/portal/docs/rules):

-   [@liferay/portal/deprecation](./rules/portal/docs/rules/deprecation.md): Enforces standard formatting of `@deprecated` annotations.
-   [@liferay/portal/no-document-cookie](./rules/portal/docs/rules/no-document-cookie.md): Prevents saving and reading cookies without user consent.
-   [@liferay/portal/no-explicit-extend](./rules/portal/docs/rules/no-explicit-extend.md): Prevents unnecessary extensions in ESLint and Babel configuration files.
-   [@liferay/portal/no-global-fetch](./rules/portal/docs/rules/no-global-fetch.md): Prevents usage of unwrapped fetch to avoid possible issues related to security misconfiguration.
-   [@liferay/portal/no-global-storage](./rules/portal/docs/rules/no-global-storage.md): Prevents usage of unwrapped local/sessionStorage to avoid storing data without consent.
-   [@liferay/portal/no-loader-import-specifier](./rules/portal/docs/rules/no-loader-import-specifier.md): Ensures that ".scss" files imported via the loader are used only for side-effects.
-   [@liferay/portal/no-localhost-reference](./rules/portal/docs/rules/no-localhost-reference.md): Enforces that no code should explicitly reference `localhost` as a literal value.
-   [@liferay/portal/no-metal-plugins](./rules/portal/docs/rules/no-metal-plugins.md): Prevents usage of deprecated `metal-*` plugins and utilities.
-   [@liferay/portal/no-react-dom-create-portal](./rules/portal/docs/rules/no-react-dom-create-portal.md): Prevents the direct use of the `ReactDOM.createPortal` API.
-   [@liferay/portal/no-react-dom-render](./rules/portal/docs/rules/no-react-dom-render.md): Prevents direct usage of `ReactDOM.render` in favor of our wrapper.
-   [@liferay/portal/no-side-navigation](./rules/portal/docs/rules/no-side-navigation.md): Guards against the use of the legacy jQuery `sideNavigation` plugin.

#### `@liferay/aui`

The bundled `@liferay/aui` plugin includes the following [rules](./rules/aui/docs/rules):

-   [@liferay/aui/no-all](./rules/aui/docs/rules/no-all.md): Prevents usage of `A.all()`.
-   [@liferay/aui/no-array](./rules/aui/docs/rules/no-array.md): Prevents usage of `A.Array`.
-   [@liferay/aui/no-each](./rules/aui/docs/rules/no-each.md): Prevents usage of `A.each()`.
-   [@liferay/aui/no-get-body](./rules/aui/docs/rules/no-get-body.md): Prevents usage of `A.getBody()`.
-   [@liferay/aui/no-io](./rules/aui/docs/rules/no-io.md): Prevents usage of `A.io()`.
-   [@liferay/aui/no-merge](./rules/aui/docs/rules/no-merge.md): Prevents usage of `A.merge()`.
-   [@liferay/aui/no-modal](./rules/aui/docs/rules/no-modal.md): Prevents usage of `A.Modal()`.
-   [@liferay/aui/no-node](./rules/aui/docs/rules/no-node.md): Prevents usage of `A.Node()`.
-   [@liferay/aui/no-object](./rules/aui/docs/rules/no-object.md): Prevents usage of `A.Object`.
-   [@liferay/aui/no-one](./rules/aui/docs/rules/no-one.md): Prevents usage of `A.one()`.
-   [@liferay/aui/no-url](./rules/aui/docs/rules/no-url.md): Prevents usage of `A.Url()`.

## License

MIT
