/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isDefined from './is_defined';

/**
 * Used for converting a JSON string to display in a code mirror editor.
 * @param {String} jsonString The JSON string to convert.
 * @return {String} The converted JSON string.
 */
export default function parseAndPrettifyJSON(json) {
	if (!isDefined(json) || json === '') {
		return '';
	}

	try {
		return JSON.stringify(JSON.parse(json), null, 2);
	}
	catch (error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}

		return json;
	}
}
