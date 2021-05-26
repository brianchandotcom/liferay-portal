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
