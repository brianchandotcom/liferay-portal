# Top-level `require` values should not be immediately called (no-require-and-call)

To keep `require` statements simple, this rule disallows immediately calling the function returned by a `require()`.

## Rule Details

Examples of **incorrect** code for this rule:

```js
const stuff = require('loader')();

require('other')();
```

Examples of **correct** code for this rule:

```js
const loader = require('loader');

const other = require('other');

const stuff = loader();

other();
```

Note how this mirrors the pattern we must follow with `import` declarations (which are always statements and cannot be immediately called):

```js
import loader from 'loader';

import other from 'other';

const stuff = loader();

other();
```

## Further Reading

-   [Initial motivation for this rule](https://github.com/liferay/eslint-config-liferay/issues/94).
