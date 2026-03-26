/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

const ShadowDomContainer = ({className, html, previewStyles, styles, zoom}) => {
	const hostRef = useRef(null);
	const shadowRootRef = useRef(null);

	useEffect(() => {
		if (hostRef.current && !shadowRootRef.current) {
			shadowRootRef.current = hostRef.current.attachShadow({
				mode: 'open',
			});
		}
	}, []);

	useEffect(() => {
		if (!shadowRootRef.current) {
			return;
		}

		const shadowRoot = shadowRootRef.current;

		let content = '';

		if (previewStyles) {
			content += previewStyles.replace(/:root(?=[{\s,>~+])/g, ':host');
		}

		if (styles) {
			content += `<style>${styles}</style>`;
		}

		if (className) {
			content += `<div class="${className}">${html}</div>`;
		}
		else {
			content += html;
		}

		shadowRoot.innerHTML = content;
	}, [className, html, previewStyles, styles]);

	const hostStyle = zoom
		? {
				transform: `scale(${zoom})`,
				transformOrigin: '0 0',
				width: `${100 / zoom}%`,
			}
		: undefined;

	return <div ref={hostRef} style={hostStyle} />;
};

export default ShadowDomContainer;
