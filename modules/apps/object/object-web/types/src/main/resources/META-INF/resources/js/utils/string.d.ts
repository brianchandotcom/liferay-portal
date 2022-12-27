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
