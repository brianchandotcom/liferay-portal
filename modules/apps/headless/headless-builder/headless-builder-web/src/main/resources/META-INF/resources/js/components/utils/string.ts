/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * If string does not start with a forward slash, add it.
 */
export function beginStringWithForwardSlash(str: string) {
	if (Array.from(str)[0] !== '/') {
		str = '/' + str;
	}

	return str;
}

/**
 * If string does not end with a forward slash, add it.
 */
export function endStringWithForwardSlash(str: string) {
	if (str.slice(-1) !== '/') {
		str = str + '/';
	}

	return str;
}

/**
 * Returns a substring of the received one, capped at maxLengh.
 */
export function limitStringInputLengh(str: string, maxLengh: number) {
	if (str.length > maxLengh) {
		return str.substring(0, maxLengh);
	}

	return str;
}

/**
 * Make valid url path string (Only numbers, low case letters and dashes).
 */
export function makeURLPathString(str: string) {
	return replaceSpacesWithDash(str)
		.toLowerCase()
		.replace(/[^0-9a-z-]/g, '');
}

/**
 * Make valid url path string with forward slashes in between (Only numbers, low case letters, dashes).
 */
export function makeURLPathStringWithForwardSlashes(str: string) {
	return removeLeadingForwardSlash(replaceSpacesWithDash(str))
		.toLowerCase()
		.replace(/[^0-9a-z/-]|(\/+)/g, '/');
}

/**
 * If string starts with a forward slash, remove it.
 */
export function removeLeadingForwardSlash(str: string) {
	if (Array.from(str)[0] === '/') {
		str = str.substring(1);
	}

	return str;
}

/**
 * Replace blank spaces in string with dash.
 */
export function replaceSpacesWithDash(str: string) {
	return str.replace(/\s+/g, '-');
}

/**
 * If string is not wrapped in forward slashes, wrap it.
 */
export function wrapStringInForwardSlashes(str: string) {
	return endStringWithForwardSlash(beginStringWithForwardSlash(str));
}
