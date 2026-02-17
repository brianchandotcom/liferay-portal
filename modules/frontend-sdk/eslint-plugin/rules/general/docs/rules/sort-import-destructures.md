# Sort destructured names in `import` statements (sort-import-destructures)

This rule enforces (and autofixes) that destructured names in `import` statements are sorted.

## Rule Details

Examples of **incorrect** code for this rule:

```js
import {xyz, abc} from 'other';

import main, {second as alias, first as nickname} from 'something';
```

Examples of **correct** code for this rule:

```js
import {abc, xyz} from 'other';

import main, {first as nickname, second as alias} from 'something';
```

Note how the sorting is based on the name of the imported export (eg. `first`, `second`) and not the local name (eg. `nickname`, `alias`).

## Further Reading

-   https://github.com/liferay/eslint-config-liferay/issues/124
