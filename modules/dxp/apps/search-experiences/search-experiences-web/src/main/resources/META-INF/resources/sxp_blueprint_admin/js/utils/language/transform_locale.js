/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Converts some of the more unique regional locales that differ from
 * the expected format.
 *
 * @param {string} locale Language identifier
 * @returns {string}
 */
export default function transformLocale(locale) {
	const languages = {
		'zh-Hans-CN': 'zh-CN',
		'zh-Hant-TW': 'zh-TW',
	};

	return languages[locale] || locale;
}
