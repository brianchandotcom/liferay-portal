/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getCSSLoadJavaScript(webContextPath, cssPath) {
	return `
const link = document.createElement('link');
link.setAttribute('rel', 'stylesheet');
link.setAttribute('type', 'text/css');
link.setAttribute(
	'href',
	Liferay.ThemeDisplay.getCDNHost() + Liferay.ThemeDisplay.getPathContext() + '/o${webContextPath}/${cssPath}'
);
if (Liferay.CSP) {
	link.setAttribute('nonce', Liferay.CSP.nonce);
}
document.querySelector('head').appendChild(link);
`;
}
