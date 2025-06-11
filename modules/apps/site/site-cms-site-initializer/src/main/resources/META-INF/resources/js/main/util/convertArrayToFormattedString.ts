/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function convertArrayToFormattedString(array: string[]) {
	const separator = ' ';
	const and = Liferay.Language.get('and').toLowerCase();

	if (array.length) {
		return array.reduce((acc, val, index) => {
			if (index === array.length - 1) {
				return `${acc}${array.length > 2 ? ',' : ''}${separator}${and}${separator}${val}`;
			}

			return `${acc},${separator}${val}`;
		});
	}

	return '';
}
