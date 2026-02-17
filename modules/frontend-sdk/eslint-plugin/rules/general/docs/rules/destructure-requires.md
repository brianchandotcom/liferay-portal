# `require` statements should use destructuring (destructure-requires)

For uniformity, all `require` statements should take the form of variable declarations that make the `require()` call and directly bind the result to one or more values.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const init = require('foo').init;

const alias = require('bar').thing;
```

Examples of **correct** code for this rule:

```js
const {init} = require('foo');

const {thing: alias} = require('bar');
```

Note that the form that uses destructuring is similar to the way bindings are made with `import` declarations:

```js
import {init} from 'foo';

import {thing as alias} from 'bar';
```

## Further Reading

-   [Initial motivation for this rule](https://github.com/liferay/eslint-config-liferay/issues/94).
