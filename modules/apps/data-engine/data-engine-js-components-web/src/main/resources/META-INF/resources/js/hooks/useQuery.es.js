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

import qs from 'qs';

const qsOptions = {allowDots: true, arrayFormat: 'bracket'};

export const toQuery = (queryString = '', defaultQuery = {}, scope = false) => {
	const query = queryString.length
		? qs.parse(queryString.substr(1), qsOptions)
		: {};

	const currentQuery = scope ? query[scope] : query;

	return {...defaultQuery, ...currentQuery};
};

export const toQueryString = (query) => {
	return query ? `${qs.stringify(query, qsOptions)}` : '';
};

export default (history, defaultQuery = {}, scope = false) => {
	const {location} = history;
	const {pathname, search} = location;
	const currentQuery = toQuery(search, defaultQuery, scope);

	return [
		currentQuery,
		(query) => {
			const scopedQuery = scope ? {[scope]: query} : query;

			history.push(
				`${pathname}?${toQueryString({
					...toQuery(search),
					...scopedQuery,
				})}`
			);
		},
	];
};
