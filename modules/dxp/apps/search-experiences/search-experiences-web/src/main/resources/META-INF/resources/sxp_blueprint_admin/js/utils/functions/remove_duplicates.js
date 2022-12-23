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
 * Removes any duplicate items in an array. If 'property' is provided,
 * it removes duplicate object items with the same property.
 *
 * @param {Array} array An array of items
 * @param {string=} property Name of the property to compare
 * @returns {Array}
 */
export default function removeDuplicates(array, property) {
	if (!property) {
		return array.filter(
			(item, position, self) => self.indexOf(item) === position
		);
	}

	const uniqueArray = [];

	array.forEach((item1) => {
		if (
			uniqueArray.findIndex(
				(item2) => item2[property] === item1[property]
			) === -1
		) {
			uniqueArray.push(item1);
		}
	});

	return uniqueArray;
}
