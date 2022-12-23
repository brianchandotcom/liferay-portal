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
