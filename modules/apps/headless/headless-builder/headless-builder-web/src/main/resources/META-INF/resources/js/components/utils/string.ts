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
