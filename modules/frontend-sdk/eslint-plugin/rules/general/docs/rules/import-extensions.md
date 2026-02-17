# Omit extensions consistently with `import`, `export` and `require` (import-extensions)

This rule enforces that `import`/`export` statements and `require` calls &mdash; henceforth referred to as just "imports" &mdash; use (or omit) file extensions consistently.

## Rule Details

This rule complains if it finds an import with an unnecessary ".js" extension.

In its current form, it follows some simple heuristics and is not configurable (unlike more complex rules, such as the third-party [imports/extensions rule](https://github.com/benmosher/eslint-plugin-import/blob/HEAD/docs/rules/extensions.md)):

-   Imports should omit the unnecessary ".js" extension.
-   NPM package names (which may end in ".js" are exempted).

Based on the [current usages in liferay-portal](https://gist.github.com/wincent/1a6bbd06aec797032b6918153bef5d87) (via `git grep "import.+\\.js';" -- '*.js'`) and [clay](https://gist.github.com/wincent/775fdb7a0bc117c2fa8c66cd97b2d76f) (via `git grep "import.+\\.(js|ts|tsx)';" -- '*.ts' '*.tsx' '*.js'`) we believe this simpler approach should be sufficient, but we can add configurability in the future if that proves not to be the case.

## Examples

Examples of **incorrect** code for this rule:

```js
import templates from './Something.soy.js';

import {Util} from './Util.es.js';

import * as Billboard from './billboard.js';

export {thing} from './other.js';
```

Examples of **correct** code for this rule:

```js
import templates from './Something.soy';

import {Util} from './Util.es';

// OK because "billboard.js" is the name of an NPM package:
import {Data} from 'billboard.js';

export {thing} from './other';
```

## See also

-   https://github.com/liferay/eslint-config-liferay/issues/137
