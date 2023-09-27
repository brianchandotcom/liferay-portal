/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const userLanguageId = Liferay.ThemeDisplay.getLanguageId();

/**
 * Get the label according to the locale
 */

export function getLocalizableLabel(
	creationLanguageId: Liferay.Language.Locale,
	labels: LocalizedValue<string> | undefined,
	fallback?: string
) {
	if (!labels) {
		return fallback ?? '';
	}

	return (
		labels[userLanguageId] ??
		labels[defaultLanguageId] ??
		labels[creationLanguageId] ??
		fallback ??
		labels['en_US'] ??
		''
	);
}

/**
 * Checks if the string includes the query
 */
export function stringIncludesQuery(str: string, query: string) {
	return str !== undefined && query !== undefined
		? str.toLowerCase().includes(query.toLowerCase())
		: false;
}

/**
 * Convert the received string into the format of a URL parameter
 */
export function stringToURLParameterFormat(str: string) {

	// @ts-ignore

	const spacesReplaced = str.replaceAll(' ', '%20');
	const urlParameter = spacesReplaced.replaceAll("'", '%27');

	return urlParameter;
}
