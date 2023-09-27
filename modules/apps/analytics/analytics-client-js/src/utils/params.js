/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getSearchParams() {
	if (window.URLSearchParams) {
		return new window.URLSearchParams(location.search);
	}

	const dict = {
		get(attr) {
			return this[attr];
		},
	};

	let searchString = location.search;

	if (searchString.charAt(0) === '?') {
		searchString = searchString.slice(1);
	}

	const pairs = searchString.split('&');

	pairs.forEach((pair) => {
		const equalIndex = pair.indexOf('=');

		if (equalIndex > -1) {
			const key = pair.slice(0, equalIndex);
			const value = pair.slice(equalIndex + 1);

			dict[key] = value;
		}
		else {
			dict[pair] = '';
		}
	});

	return dict;
}
