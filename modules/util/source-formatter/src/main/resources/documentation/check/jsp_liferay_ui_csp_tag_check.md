## JSPLiferayUICSPTagCheck

Avoid using `<liferay-ui:csp>` tag in `*.jsp` or `*.jspf`. Use a React component
instead.

The `<liferay-ui:csp>` tag was intended for wrapping legacy inline code to
comply with CSP (Content Security Policy). New code should use React components
in `.js` files instead. The tag also uses jsoup HTML parsing with significant
performance overhead.

See [LPD-73599](https://liferay.atlassian.net/browse/LPD-73599) for more information.