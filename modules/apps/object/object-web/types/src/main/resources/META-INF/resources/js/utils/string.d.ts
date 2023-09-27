/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Transform first letter in lowercase
 */
export declare function firstLetterLowercase(str: string): string;

/**
 * Transform first letter in uppercase
 */
export declare function firstLetterUppercase(str: string): string;

/**
 *
 * Check if the first letter of a string is uppercase
 */
export declare function checkIfFirstLetterIsUppercase(str: string): boolean;

/**
 * Normalize languageId to be used in the
 * frontend with themeDisplay.getDefaultLanguageId()
 */
export declare function normalizeLanguageId(languageId: string): string;

/**
 * Format string removing spaces and special characters
 */
export declare function removeAllSpecialCharacters(str: string): string;

/**
 * Separate CamelCase string
 */
export declare function separateCamelCase(str: string): string;

/**
 * Verify if string contains any special characters
 */
export declare function specialCharactersInString(str: string): boolean;

/**
 * Normalize string in camel case pattern.
 */
export declare function toCamelCase(
	str: string,
	removeSpecialCharacters?: boolean
): string;
