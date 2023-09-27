/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MAX_HEX_LENGTH = 6;
const REGEX_HEX = /^([0-9A-F]{3}){1,2}$/i;

export default function getValidHexColor(value: string) {
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
