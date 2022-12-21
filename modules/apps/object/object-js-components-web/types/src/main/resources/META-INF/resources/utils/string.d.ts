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
 * Get the label according to the locale
 */
export declare function getLocalizableLabel(
	creationLanguageId: Locale,
	labels: LocalizedValue<string> | undefined,
	fallback?: string
): string;

/**
 * Checks if the string includes the query
 */
export declare function stringIncludesQuery(
	str: string,
	query: string
): boolean;

/**
 * Convert the received string into the format of a URL parameter
 */
export declare function stringToURLParameterFormat(str: string): any;
