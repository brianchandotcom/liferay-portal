# Disallow use of global fetch (no-global-fetch)

This rule guards against the direct use of the `fetch` API. As a secured-environment, Liferay Portal requests often rely on specific security headers and tokens being set in the requests. The `frontend-js-web` module offers a thin wrapper around `fetch` that takes care of the most common configuration to avoid issues.

## Rule Details

Examples of **incorrect** code for this rule:

```js
function doSomething(url) {
	return fetch(foo);
}
```

Examples of **correct** code for this rule:

```js
import {fetch} from 'frontend-js-web';

function doSomething(url) {
	return fetch(foo);
}

function doSomethingElse(url) {
	return Liferay.Util.fetch(url);
}
```

## Further Reading

-   [LPS-94712 Make a utility that encapsulates common 'fetch' configuration and use](https://issues.liferay.com/browse/LPS-94712)
-   [LPS-96778 Standardize Ajax requests in frontend-js-web](https://issues.liferay.com/browse/LPS-96778)
-   [LPS-96933 Standardize Ajax requests in frontend-js-aui-web](https://issues.liferay.com/browse/LPS-96933)
-   [LPS-98655 Extend 'fetch' utility to set 'x-csrf-token' header by default](https://issues.liferay.com/browse/LPS-98655)
-   [LPS-98888 Standardize Ajax requests in layout modules](https://issues.liferay.com/browse/LPS-98888)
-   [LPS-98940 Standardize Ajax requests in collaboration modules #1](https://issues.liferay.com/browse/LPS-98940)
-   [LPS-98941 Standardize Ajax requests in collaboration modules #2](https://issues.liferay.com/browse/LPS-98941)
-   [LPS-98942 Standardize Ajax requests in collaboration modules #3](https://issues.liferay.com/browse/LPS-98942)
-   [LPS-98946 Standardize Ajax requests in wem1 (asset, mobile-device-rules, journal)](https://issues.liferay.com/browse/LPS-98946)
-   [LPS-98947 Standardize Ajax requests in users modules](https://issues.liferay.com/browse/LPS-98947)
-   [LPS-98948 Standardize Ajax requests in site modules](https://issues.liferay.com/browse/LPS-98948)
-   [LPS-98949 Standardize Ajax requests in segments](https://issues.liferay.com/browse/LPS-98949)
-   [LPS-98953 Standardize Ajax requests in change-tracking modules](https://issues.liferay.com/browse/LPS-98953)
-   [LPS-98962 Standardize Ajax requests in dynamic-data-mapping](https://issues.liferay.com/browse/LPS-98962)
-   [LPS-99339 Standardize Ajax requests in frontend-js-aui-web, part 2](https://issues.liferay.com/browse/LPS-99339)
-   [LPS-99414 Standardize Ajax requests in common #1](https://issues.liferay.com/browse/LPS-99414)
-   [LPS-99610 Standardize Ajax requests in common #2](https://issues.liferay.com/browse/LPS-99610)
-   [LPS-99611 Standardize Ajax requests in product-navigation](https://issues.liferay.com/browse/LPS-99611)
-   [LPS-99612 Standardize Ajax requests in staging and export-import](https://issues.liferay.com/browse/LPS-99612)
