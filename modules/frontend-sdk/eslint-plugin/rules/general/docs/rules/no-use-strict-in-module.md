# Disallow 'use strict' in es modules (no-use-strict-in-module)

You never need `'use strict'` in an ES module ([spec](http://www.ecma-international.org/ecma-262/6.0/#sec-strict-mode-code)). We can infer if file is an esmodule if it uses export/import syntax.

## Rule Details

Examples of **incorrect** code for this rule:

```js
'use strict';

export default () => {};
```

```js
'use strict';

import foo from 'bar';
```

Examples of **correct** code for this rule:

```js
import foo from 'bar';
//...
```

```js
//...
export default foo;
```

```js
//...
export foo;
```

## Further Reading

-   [Initial motivation for this rule](https://github.com/liferay/liferay-frontend-projects/issues/20).
