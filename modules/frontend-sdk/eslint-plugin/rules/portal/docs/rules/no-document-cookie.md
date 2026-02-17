# Disallow use of document.cookie (no-document-cookie)

This rule guards against the direct use of the global cookie API in `document.cookie`. To comply with data protection regulations, Liferay users can disable the storage of data that is not fundamental for the main purpose of the site to work, and that applies to cookies. Both the global Liferay object and the `frontend-js-web` module offer a thin wrapper around this API with added user consent enforcement.

## Rule Details

Examples of **incorrect** code for this rule:

```js
function doSomething(name) {
	return document.cookie
		.split('; ')
		.find((v) => v.startsWith(name))
		?.split('=')[0];
}

function doSomethingElse(name, value) {
	document.cookie += `${name}=${value}`;
}
```

Examples of **correct** code for this rule:

```js
import {getCookie, setCookie, COOKIE_TYPES} from 'frontend-js-web';

function doSomething(name) {
	return getCookie(name, COOKIE_TYPES.NECESSARY);
}

function doSomethingElse(name, value, expires) {
	return setCookie(name, value, COOKIE_TYPES.FUNCTIONAL, {expires});
}

function doSomethingOther(name, value) {
	return Liferay.Util.Cookie.set(
		name,
		value,
		Liferay.Util.Cookie.TYPES.PERSONALIZATION
	);
}
```

## Further Reading

-   [LPS-151966 Create a JS API for cookie management, which enforces user's consent](https://issues.liferay.com/browse/LPS-151966)
