/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Appends given parameters to the given URL.
 * @param {String | Object} params Parameters to be added on to the base url
 * @param {String} baseUrl Base url
 * @return {String} url URL constructed from base url and params
 * @review
 */
export default function addParams(params, baseUrl) {
	if (!params || (typeof params !== 'object' && typeof params !== 'string')) {
		throw new TypeError('Parameter params must be an object or string');
	}

	if (typeof baseUrl !== 'string') {
		throw new TypeError('Parameter baseUrl must be a string');
	}

	const url = baseUrl.startsWith('/')
		? new URL(baseUrl, location.href)
		: new URL(baseUrl);

	if (typeof params === 'object') {
		Object.entries(params).forEach(([key, value]) => {
			url.searchParams.append(key, value);
		});
	}
	else {
		const searchParams = new URLSearchParams(params.trim());

		searchParams.forEach((value, key) => {
			if (value) {
				url.searchParams.append(key, value);
			}
			else {
				url.searchParams.append(key, '');
			}
		});
	}

	return url.toString();
}
