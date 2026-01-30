/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getURL, {URLType} from './getURL.mjs';

export default function getCSSLoadJavaScript(
	urlType,
	urlPrefix,
	webContextPath,
	cssPath,
	hash
) {
	let code = `
let url = '${getURL(urlType, urlPrefix, webContextPath, cssPath, hash)}';
`;

	if (urlType === URLType.SASS_CSS_FILE) {
		code += `
// RTL language support
if(window.getComputedStyle(document.documentElement).direction === 'rtl') {
	url = '${getURL(
		URLType.SASS_CSS_FILE,
		urlPrefix,
		webContextPath,
		cssPath.replace(/\.css$/, '_rtl.css'),
		hash
	)}';
}
`;
	}

	code += `
// Bundle hashed files support
if (import.meta.url.includes('/js/-/')) {
	url = '../../' + url;
}
`;

	code += `
// Resolve URL relative to current module
url = import.meta.resolve(url);
`;

	code += `
// Append to HTML
const link = document.createElement('link');
link.setAttribute('rel', 'stylesheet');
link.setAttribute('type', 'text/css');
link.setAttribute('href', url);
if (Liferay.CSP) {
	link.setAttribute('nonce', Liferay.CSP.nonce);
}
document.querySelector('head').appendChild(link);
`;

	return code;
}
