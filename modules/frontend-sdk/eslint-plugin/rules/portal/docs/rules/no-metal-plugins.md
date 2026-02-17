# Disallow use of deprecated metal plugins (no-metal-plugins)

This rule guards against the use of various deprecated metal plugins such as:

-   [metal-ajax](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-ajax)
-   [metal-aop](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-aop)
-   [metal-clipboard](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-clipboard)
-   [metal-debounce](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-debounce)
-   [metal-keyboard-focus](https://github.com/metal/metal-keyboard-focus)
-   [metal-promise](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-promise)
-   [metal-storage](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-storage)
-   [metal-structs](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-structs)
-   [metal-uri](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-uri)
-   [metal-useragent](https://github.com/metal/metal-plugins/tree/eabc06702f498722ca3c32f0d19f441c14221d1d/packages/metal-useragent)

## Rule Details

Examples of **incorrect** code for this rule:

```js
import Ajax from 'metal-ajax';

import {AOP} from 'metal-aop';

import Clipboard from 'metal-clipboard';

import {debounce} from 'metal-debounce';

import KeyboardFocusManager from 'metal-keyboard-focus';

import {CancellablePromise} from 'metal-promise';

import {LocalStorageMechanism, Storage} from 'metal-storage';

import {MultiMap} from 'metal-structs';

import URI from 'metal-uri';

import UA from 'metal-useragent';
```

Examples of **correct** code for this rule:

```js
import {fetch} from 'frontend-js-web';
import {AOP} from 'frontend-js-web';
import ClipboardJS from 'clipboard';
import {debounce} from 'frontend-js-web';
import {KeyboardFocusManager} from 'frontend-js-web';
```

## Further Reading

-   [LPS-96715 Streamline Metal usage in Liferay Portal (Part I)](https://issues.liferay.com/browse/LPS-96715)
-   [LPS-96717 Link frontend-js-web dependencies](https://issues.liferay.com/browse/LPS-96717)
-   [LPS-96718 Include metal-aop inside liferay-portal](https://issues.liferay.com/browse/LPS-96718)
-   [LPS-96719 Include metal-ajax inside liferay-portal](https://issues.liferay.com/browse/LPS-96719)
-   [LPS-96720 Include metal-promise inside liferay-portal](https://issues.liferay.com/browse/LPS-96720)
-   [LPS-96722 Include metal-clipboard inside liferay-portal](https://issues.liferay.com/browse/LPS-96722)
-   [LPS-96723 Include metal-debounce inside liferay-portal](https://issues.liferay.com/browse/LPS-96723)
-   [LPS-96724 Include metal-keyboard-focus inside liferay-portal](https://issues.liferay.com/browse/LPS-96724)
-   [LPS-96725 Include metal-storage inside liferay-portal](https://issues.liferay.com/browse/LPS-96725)
-   [LPS-96726 Include metal-structs inside liferay-portal](https://issues.liferay.com/browse/LPS-96726)
-   [LPS-96727 Replace metal-uri with URL in liferay-portal](https://issues.liferay.com/browse/LPS-96727)
-   [LPS-96728 Include metal-user-agent inside liferay-portal](https://issues.liferay.com/browse/LPS-96728)
-   [LPS-96766 Configure ESLint to warn about use of deprecated metal-\* plugins](https://issues.liferay.com/browse/LPS-96766)
-   [LPS-96777 Replace Promise with async/await](https://issues.liferay.com/browse/LPS-96777)
-   [LPS-99236 Remove metal-clipboard from liferay-portal](https://issues.liferay.com/browse/LPS-99236)
-   [LPS-99416 Update senna to version 3.0.0-milestone.1](https://issues.liferay.com/browse/LPS-99416)
