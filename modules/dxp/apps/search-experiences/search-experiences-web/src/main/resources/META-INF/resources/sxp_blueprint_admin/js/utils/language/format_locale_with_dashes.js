/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Formats locale from default pattern with underscore to pattern with
 * dashes (BCP47).
 *
 * Example:
 * formatLocaleWithDashes("en_US")
 * => "en-US"
 *
 * @param {string} locale Language identifier
 * @returns {string}
 */
export default function formatLocaleWithDashes(locale) {
	return locale.replaceAll('_', '-');
}
