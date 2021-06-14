/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	const element = useRef(null);

	const highlightCode = () => {
		if (element.current) {
			const nodes = element.current.querySelectorAll('pre code');
			for (let i = 0; i < nodes.length; i++) {
				hljs.highlightBlock(nodes[i]);
			}
		}
	};

	useEffect(highlightCode, []);
	const elProps = {ref: element};

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
		<pre ref={element}>
			<code>{children}</code>
		</pre>
	);
}

export default Highlight;
