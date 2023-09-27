/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import qs from 'qs';

const qsOptions = {allowDots: true, arrayFormat: 'bracket'};

export function toQuery(queryString = '', defaultQuery = {}, scope = false) {
	const query = queryString.length
		? qs.parse(queryString.substr(1), qsOptions)
		: {};

	const currentQuery = scope ? query[scope] : query;

	return {...defaultQuery, ...currentQuery};
}

export function toQueryString(query) {
	return query ? `${qs.stringify(query, qsOptions)}` : '';
}

export default function useQuery(history, defaultQuery = {}, scope = false) {
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
}
