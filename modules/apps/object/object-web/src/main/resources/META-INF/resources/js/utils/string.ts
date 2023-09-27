/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Transform first letter in lowercase
 */
export function firstLetterLowercase(str: string): string {
	return str.charAt(0).toLowerCase() + str.slice(1);
}

/**
 * Transform first letter in uppercase
 */
export function firstLetterUppercase(str: string): string {
	return str.charAt(0).toUpperCase() + str.slice(1);
}

/**
 *
 * Check if the first letter of a string is uppercase
 */

export function checkIfFirstLetterIsUppercase(str: string) {
	return str.charAt(0) === str.charAt(0).toUpperCase();
}

/**
 * Normalize languageId to be used in the
 * frontend with themeDisplay.getDefaultLanguageId()
 */
export function normalizeLanguageId(languageId: string): string {
	return languageId.replace(/_/g, '-');
}

/**
 * Format string removing spaces and special characters
 */
export function removeAllSpecialCharacters(str: string): string {
	return str.replace(/[^A-Z0-9]/gi, '');
}

/**
 * Separate CamelCase string
 */
export function separateCamelCase(str: string): string {
	const separatedCamelCaseString = str.replace(/([a-z])([A-Z])/g, '$1 $2');

	return separatedCamelCaseString;
}

/**
 * Verify if string contains any special characters
 */
export function specialCharactersInString(str: string) {
	const replaceString = removeAllSpecialCharacters(str);

	if (replaceString.normalize() === str.normalize()) {
		return false;
	}

	return true;
}

/**
 * Normalize string in camel case pattern.
 */
export function toCamelCase(
	str: string,
	removeSpecialCharacters?: boolean
): string {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	if (removeSpecialCharacters) {
		return firstLetterLowercase(removeAllSpecialCharacters(join));
	}

	return firstLetterLowercase(join);
}
