/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getValueFromItem = function (
	item: any,
	fieldName?: string | string[]
): string {
	if (!fieldName) {
		return '';
	}

	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => {
			if (key === 'LANG') {
				return (
					acc[Liferay.ThemeDisplay.getLanguageId()] ||
					acc[
						Liferay.ThemeDisplay.getDefaultLanguageId() ||
							acc[Liferay.ThemeDisplay.getBCP47LanguageId()]
					]
				);
			}

			return acc[key];
		}, item);
	}

	return item[fieldName];
};

export default getValueFromItem;
