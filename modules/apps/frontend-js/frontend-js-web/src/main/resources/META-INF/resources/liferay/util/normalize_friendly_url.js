/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns url formatted as a lowercase String
 * @param {String} text Input text
 * @return {String} newText URL String constructed from input text
 */
export default function normalizeFriendlyURL(text) {
	if (typeof text !== 'string') {
		throw new TypeError('parameter text must be a string');
	}

	return text
		.replace(/[^a-z0-9_-]/gi, '-')
		.replace(/^-+/, '')
		.replace(/--+/, '-')
		.toLowerCase();
}
