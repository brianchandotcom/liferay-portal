/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * If string does not start with a forward slash, add it.
 */
export declare function beginStringWithForwardSlash(str: string): string;

/**
 * If string does not end with a forward slash, add it.
 */
export declare function endStringWithForwardSlash(str: string): string;

/**
 * Returns a substring of the received one, capped at maxLengh.
 */
export declare function limitStringInputLengh(
	str: string,
	maxLengh: number
): string;

/**
 * Make valid url path string (Only numbers, low case letters and dashes).
 */
export declare function makeURLPathString(str: string): string;

/**
 * Make valid url path string with forward slashes in between (Only numbers, low case letters, dashes).
 */
export declare function makeURLPathStringWithForwardSlashes(
	str: string
): string;

/**
 * If string starts with a forward slash, remove it.
 */
export declare function removeLeadingForwardSlash(str: string): string;

/**
 * Replace blank spaces in string with dash.
 */
export declare function replaceSpacesWithDash(str: string): string;

/**
 * If string is not wrapped in forward slashes, wrap it.
 */
export declare function wrapStringInForwardSlashes(str: string): string;
