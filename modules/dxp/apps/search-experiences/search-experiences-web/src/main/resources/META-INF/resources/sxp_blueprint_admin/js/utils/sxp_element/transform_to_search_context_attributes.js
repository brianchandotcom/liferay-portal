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
