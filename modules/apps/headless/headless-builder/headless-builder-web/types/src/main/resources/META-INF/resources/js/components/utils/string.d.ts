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
 * Replace blank spaces in string with dash.
 */
export declare function replaceSpacesWithDash(str: string): string;

/**
 * If string is not wrapped in forward slashes, wrap it.
 */
export declare function wrapStringInForwardSlashes(str: string): string;
