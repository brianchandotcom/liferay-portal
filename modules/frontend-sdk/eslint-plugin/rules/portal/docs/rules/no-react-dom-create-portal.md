# Disallow direct use of `ReactDOM.createPortal` (no-react-dom-create-portal)

This rule guards against the direct use of the `ReactDOM.createPortal` API, in favor of our [custom `createPortal` wrapper](https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-js/frontend-js-react-web/src/main/resources/META-INF/resources/js/ReactPortal.tsx) implemented in `frontend-js-react-web`.

At the time of writing, the wrapper provides:

-   Wrapping portal in a `div` by default
-   Adds `.lfr-tooltip-scope` class to wrapper for scoping DXP tooltips

If you'd like to propose further useful additions, please [create an issue](https://github.com/liferay/liferay-frontend-guidelines/issues/new) in the [liferay-frontend-guidelines](https://github.com/liferay/liferay-frontend-guidelines) repo.

## Rule Details

Examples of **incorrect** code for this rule:

```js
ReactDOM.createPortal(element, container);
```

Examples of **correct** code for this rule:

```js
import {ReactPortal} from 'frontend-js-react-web';

<ReactPortal container={container}>{element}</ReactPortal>;
```
