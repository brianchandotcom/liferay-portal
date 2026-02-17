# Disallow direct use of `ReactDOM.render` (no-react-dom-render)

This rule guards against the direct use of the `ReactDOM.render` API, in favor of our [custom `render` wrapper](https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-js/frontend-js-react-web/src/main/resources/META-INF/resources/js/render.es.js) implemented in `frontend-js-react-web`.

At the time of writing, the wrapper provides:

-   Automatic unmounting in response to "destroyPortlet" events.
-   Transparent provisioning of the `ClayIconSpriteContext` needed by Clay.

If you'd like to propose further useful additions, please [create an issue](https://github.com/liferay/liferay-frontend-guidelines/issues/new) in the [liferay-frontend-guidelines](https://github.com/liferay/liferay-frontend-guidelines) repo.

## Rule Details

Examples of **incorrect** code for this rule:

```js
import ReactDOM from 'react-dom';

ReactDOM.render(element, container);
```

Examples of **correct** code for this rule:

```js
import {render} from 'frontend-js-react-web';

render(element, container);
```

## Further Reading

-   [LPS-99399 Mitigate memory leaks in React portlets](https://issues.liferay.com/browse/LPS-99399)
