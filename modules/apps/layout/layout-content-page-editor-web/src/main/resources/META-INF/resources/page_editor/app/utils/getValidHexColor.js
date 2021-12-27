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

const MAX_HEX_LENGTH = 6;
const REGEX_HEX = /^([0-9A-F]{3}){1,2}$/i;

export function getValidHexColor(value) {
	const hexColor = value.replace('#', '').substring(0, MAX_HEX_LENGTH);
	const isValid = REGEX_HEX.test(hexColor);

	if (isValid) {
		return `#${
			value.length < MAX_HEX_LENGTH
				? hexColor
						.split('')
						.map((hex) => hex + hex)
						.join('')
				: hexColor
		}`;
	}

	return '';
}
