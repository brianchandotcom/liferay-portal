/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
