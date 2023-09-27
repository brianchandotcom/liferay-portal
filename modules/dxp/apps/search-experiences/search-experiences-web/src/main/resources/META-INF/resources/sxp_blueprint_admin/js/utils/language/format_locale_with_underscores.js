/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Formats locale from pattern with dashes (BCP47) to default pattern
 * with underscore.
 *
 * Example:
 * formatLocaleWithUnderscore("en-US")
 * => "en_US"
 *
 * @param {string} locale Language identifier
 * @returns {string}
 */
export default function formatLocaleWithUnderscores(locale) {
	return locale.replaceAll('-', '_');
}
