/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const BRACKETS_QUOTES_REGEX = new RegExp(/[[\]"]/, 'g');

/**
 * Function to remove brackets and quotations from a string.
 *
 * @param {String} value String with brackets and quotes
 * @return {String}
 */
export function removeBrackets(value) {
	return value.replace(BRACKETS_QUOTES_REGEX, '');
}
