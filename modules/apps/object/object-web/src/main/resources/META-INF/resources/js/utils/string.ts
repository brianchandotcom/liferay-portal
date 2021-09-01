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

type TRemoveAllSpecialCharacters = (str: string) => string;

type TFirstLetterLowercase = (str: string) => string;

type TFirstLetterUppercase = (str: string) => string;

/**
 * Format string removing spaces and special characters
 */
export const removeAllSpecialCharacters: TRemoveAllSpecialCharacters = (
	str
) => {
	return str.replace(/[^A-Z0-9]/gi, '');
};

/**
 * Transform first letter in lowercase
 */
export const firstLetterLowercase: TFirstLetterLowercase = (str) => {
	return str.charAt(0).toLowerCase() + str.slice(1);
};

/**
 * Transform first letter in uppercase
 */
export const firstLetterUppercase: TFirstLetterUppercase = (str) => {
	return str.charAt(0).toUpperCase() + str.slice(1);
};
