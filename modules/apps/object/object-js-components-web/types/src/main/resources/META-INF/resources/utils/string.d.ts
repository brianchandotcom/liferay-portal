/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Get the label according to the locale
 */
export declare function getLocalizableLabel(
	creationLanguageId: Liferay.Language.Locale,
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
