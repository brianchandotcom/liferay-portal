/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @param href link to CSS file
 * @param rtlHREF optional link to RTL version of CSS file
 */
export default function getCSSLoadJavaScript(href, rtlHREF = undefined) {
	let rtlCode = '';

	if (rtlHREF) {
		rtlCode = `
if (window.getComputedStyle(document.documentElement).direction === 'rtl') {
	href = '${rtlHREF}';
}
`;
	}

	return `
let href = '${href}';${rtlCode}
href = import.meta.resolve(href);
const link = document.createElement('link');
link.setAttribute('rel', 'stylesheet');
link.setAttribute('type', 'text/css');
link.setAttribute('href', href);
if (Liferay.CSP) {
	link.setAttribute('nonce', Liferay.CSP.nonce);
}
document.querySelector('head').appendChild(link);
`;
}
