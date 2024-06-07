/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Traverses through an object and apply `encodeURIComponent` on any
 * string values that would break the OWASP rule.
 *
 * OWASP Rule:
 * https://github.com/coreruleset/coreruleset/blob/v3.3.0/rules/REQUEST-932-APPLICATION-ATTACK-RCE.conf#L332-L366
 *
 * @param {Object | String} item The JSON object to traverse
 * @return {Object}
 */
export default function traverseAndEncodeJSONStrings(item) {
	if (typeof item === 'string') {
		return encodeURIComponent(item);
	}

	if (typeof item === 'object') {
		if (Array.isArray(item)) {
			return item.map(traverseAndEncodeJSONStrings);
		}
		else {
			const encodedObject = {};

			for (const [key, value] of Object.entries(item)) {
				encodedObject[traverseAndEncodeJSONStrings(key)] =
					traverseAndEncodeJSONStrings(value);
			}

			return encodedObject;
		}
	}

	return item;
}
