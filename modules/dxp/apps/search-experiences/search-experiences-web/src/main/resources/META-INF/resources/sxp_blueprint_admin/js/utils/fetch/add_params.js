/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Modifies the url to include parameters.
 * @param {string} url The base url to fetch.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {string} The modified url.
 */
export default function addParams(url, params) {
	const contextURL = url.startsWith(Liferay.ThemeDisplay.getPathContext())
		? url
		: `${Liferay.ThemeDisplay.getPathContext()}${url}`;

	const fetchURL = new URL(contextURL, Liferay.ThemeDisplay.getPortalURL());

	Object.keys(params).forEach((key) => {
		if (params[key] !== null) {
			fetchURL.searchParams.append(key, params[key]);
		}
	});

	return fetchURL;
}
