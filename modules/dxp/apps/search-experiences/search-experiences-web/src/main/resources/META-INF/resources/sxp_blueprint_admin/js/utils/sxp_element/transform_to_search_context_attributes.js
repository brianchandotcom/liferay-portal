/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Converts the attributes list to the format expected by the
 * `searchContextAttributes` property.
 *
 * For example:
 * Input: [{key: 'key1', value: 'value1'}, {key: 'key2', value: 'value2'}]
 * Output: {key1: 'value1', key2: 'value2'}
 * @param {array} attributes A list of objects with `key` and `value` properties.
 */
export default function transformToSearchContextAttributes(attributes) {
	return attributes
		.filter((attribute) => attribute.key) // Removes empty keys
		.reduce(
			(searchContextAttributes, attribute) => ({
				...searchContextAttributes,
				[attribute.key]: attribute.value,
			}),
			{}
		);
}
