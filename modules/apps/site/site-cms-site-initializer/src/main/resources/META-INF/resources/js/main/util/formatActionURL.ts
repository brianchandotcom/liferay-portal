/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function getValueFromItem(fieldName?: string | string[], item: any) {
	if (!fieldName) {
		return null;
	}

	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => {
			if (key === 'LANG') {
				return (
					acc[Liferay.ThemeDisplay.getLanguageId()] ||
					acc[Liferay.ThemeDisplay.getDefaultLanguageId()]
				);
			}

			return acc[key];
		}, item);
	}

	return item[fieldName];
}

export default function formatActionURL(item: any, url: string) {
	if (!url) {
		return '';
	}

	let regex = new RegExp('{(.*?)}', 'mg');

	let replacedUrl = url.replace(regex, (matched) =>
		getValueFromItem(
			matched.substring(1, matched.length - 1).split('.'),
			item
		)
	);

	regex = new RegExp('(%7B.*?%7D)', 'mg');

	replacedUrl = replacedUrl.replace(regex, (matched) =>
		getValueFromItem(
			matched.substring(3, matched.length - 3).split('.'),
			item
		)
	);

	return replacedUrl;
}
