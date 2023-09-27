/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Function to get valid classNames and return them sorted.
 *
 * @param {Array} items Array of objects with classNames
 * @return {Array} Array of classNames
 */
export default function filterAndSortClassNames(items = []) {
	return items
		.map(({className}) => className)
		.filter((item) => item)
		.sort();
}
