/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function fixLocaleKeys(name_i18n: LocalizedValue<string>) {
	const newTranslationsObject: {[key: string]: string} = {};

	for (const [key, value] of Object.entries(name_i18n)) {
		newTranslationsObject[key.replace(/-/g, '_')] = value;
	}

	return newTranslationsObject;
}
