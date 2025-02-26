/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const userLanguageId = Liferay.ThemeDisplay.getLanguageId();

/**
 * Transform first letter in uppercase
 */
export function firstLetterUppercase(str: string): string {
	return str.charAt(0).toUpperCase() + str.slice(1);
}

/**
 * Retrieves the label from a LocalizedValue<string> based on various language ID sources.
 *
 * If the language configured by the user in Account Settings takes precedence over the
 * instance's default, there is no need to pass values returned by
 * Liferay.ThemeDisplay.getLanguageId() or Liferay.ThemeDisplay.getDefaultLanguageId(),
 * as they are automatically considered.
 *
 * The order of precedence for resolving a label is as follows:
 *
 * 1. The `preferredLanguageId` optional argument;
 * 2. The language configured by the user in Account Settings;
 * 3. The language configured as the default in Instance Settings;
 * 4. The `fallbackLanguageId` optional argument;
 * 5. The actual label string passed as the `fallbackLabel` optional argument;
 * 6. The `en_US` language ID;
 * 7. An empty string.
 *
 * @param {string} fallbackLabel A literal string to be used as the actual label.
 * @param {Locale} fallbackLanguageId A language ID to be used as a last resort.
 * @param {LocalizedValue<string> | undefined} labels The localized labels object.
 * @param {Locale} preferredLanguageId The first language ID that the function will attempt to use.
 */
export function getLocalizableLabel({
	fallbackLabel,
	fallbackLanguageId,
	labels,
	preferredLanguageId,
}: {
	fallbackLabel?: string;
	fallbackLanguageId?: Liferay.Language.Locale;
	labels: LocalizedValue<string> | undefined;
	preferredLanguageId?: Liferay.Language.Locale;
}): string {
	if (!labels) {
		return fallbackLabel ?? '';
	}

	return (
		(preferredLanguageId && labels[preferredLanguageId]) ??
		(userLanguageId && labels[userLanguageId]) ??
		(defaultLanguageId && labels[defaultLanguageId]) ??
		(fallbackLanguageId && labels[fallbackLanguageId]) ??
		fallbackLabel ??
		labels['en_US'] ??
		''
	);
}

/**
 * Format string removing spaces and special characters
 */
export function removeAllSpecialCharacters(str: string): string {
	return str.replace(/[^A-Z0-9]/gi, '');
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
