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
const toQuery = (string, defaultQuery = {}) => {
	const query = {...defaultQuery};
	const params = new URLSearchParams(string);

	params.forEach((value, key) => {
		query[key] = value;
	});

	return query;
};

const toString = (object, queryString = '') => {
	const params = new URLSearchParams(queryString);

	Object.keys(object).forEach(key => {
		if (object[key]) {
			params.set(key, object[key]);
		} else {
			params.delete(key);
		}
	});

	return params.toString();
};

export default (history, defaultQuery = {}) => {
	const {location} = history;
	const {pathname, search} = location;

	return [
		toQuery(search, defaultQuery),
		query => history.push(`${pathname}?${toString(query, search)}`)
	];
};
