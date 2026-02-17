# Disallow use of global web storage (no-global-storage)

This rule guards against the direct use of the web storage API (`localStorage` and `sessionStorage`). To comply with data protection regulations, Liferay users can disable storing data that is not fundamental for the main purpose of the site to work, and that applies to web storage. Both the global Liferay object and the `frontend-js-web` module offer a thin wrapper around this API with added user consent enforcement.

## Rule Details

Examples of **incorrect** code for this rule:

```js
function doSomething() {
	return localStorage.setItem('key', 'value');
}

function doSomething() {
	return sessionStorage.setItem('key', 'value');
}
```

Examples of **correct** code for this rule:

```js
import {localStorage, sessionStorage} from 'frontend-js-web';

function doSomething() {
	return localStorage.setItem('key', 'value', localStorage.TYPES.NECESSARY);
}

function doSomethingElse() {
	return sessionStorage.getItem('key', sessionStorage.TYPES.FUNCTIONAL);
}

function doSomethingElse() {
	return Liferay.Util.LocalStorage.setItem(
		'key',
		'value',
		Liferay.Util.LocalStorage.TYPES.NECESSARY
	);
}
```

## Further Reading

-   [LPS-155901 Manage local and session storage from the cookie manager](https://issues.liferay.com/browse/LPS-155901)
-   [Technical Draft](https://liferay.atlassian.net/l/cp/sWhsAp11)
