/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getSearchParams(): URLSearchParams {
	if (window.URLSearchParams) {
		return new window.URLSearchParams(location.search);
	}

	const params = new Map();
	const searchString = location.search.startsWith('?')
		? location.search.slice(1)
		: location.search;

	searchString.split('&').forEach((pair) => {
		if (!pair) {
			return;
		}

		const [key, value] = pair.includes('=')
			? pair.split('=').map(decodeURIComponent)
			: [pair, ''];
		params.set(key, value);
	});

	const searchParams = {
		get: (key: string) => params.get(key),
	} as URLSearchParams;

	return searchParams;
}
