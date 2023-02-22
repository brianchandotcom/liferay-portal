/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * Recursively traverses the criteria object to build an Ac Gramar filter query
 * string. Properties is required to parse the correctly with or without quotes
 * and formatting the query differently for certain types like collection.
 * @returns An AC grammar query string built from the criteria object.
 */
function buildEventQueryString(criteria) {
	const {items} = criteria;
	const operatorName = items[0]?.operatorName;
	const value = items[0]?.value;

	let query = `activities.filterByCount(filter='()',operator='${operatorName}',value=${value})`;

	query = items[0]?.operatorNot ? `((not ${query}))` : `(${query})`;

	return query;
}

export {buildEventQueryString};
