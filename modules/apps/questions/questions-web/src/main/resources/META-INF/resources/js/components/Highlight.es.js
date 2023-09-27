/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hljs from 'highlight.js/lib/core';
import java from 'highlight.js/lib/languages/java';
import javascript from 'highlight.js/lib/languages/javascript';
import plaintext from 'highlight.js/lib/languages/plaintext';

import 'highlight.js/styles/monokai-sublime.css';
import React, {useEffect, useRef} from 'react';

hljs.configure({
	languages: ['language-java', 'language-javascript', 'html', 'plaintext'],
});
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('java', java);
hljs.registerLanguage('plaintext', plaintext);

function Highlight(props) {
	const {children, element: Element, innerHTML} = props;
	const elementRef = useRef(null);

	const highlightCode = () => {
		if (elementRef.current) {
			const nodes = elementRef.current.querySelectorAll('pre code');
			for (let i = 0; i < nodes.length; i++) {
				hljs.highlightBlock(nodes[i]);
			}
		}
	};

	useEffect(highlightCode, []);
	const elProps = {ref: elementRef};

	if (innerHTML) {
		elProps.dangerouslySetInnerHTML = {__html: children};
		if (Element) {
			return <Element {...elProps} />;
		}

		return <div {...elProps} />;
	}
	if (Element) {
		return <Element {...elProps}>{children}</Element>;
	}

	return (
		<pre ref={elementRef}>
			<code>{children}</code>
		</pre>
	);
}

export default Highlight;
