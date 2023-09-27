/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
