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

import pathToRegexp from 'path-to-regexp';

import {parse, stringify} from '../../router/queryString.es';

export function getFiltersParam(queryString) {
	const queryParams = parse(queryString);

	return queryParams.filters || {};
}

export function getFilterValues(filterKey, filtersParam) {
	let filterValues = filtersParam[filterKey] || [];

	if (!Array.isArray(filterValues)) {
		filterValues = [filterValues];
	}

	return filterValues;
}

export function getSelectedItemsQuery(items, key, queryString) {
	const queryParams = parse(queryString);

	const filtersParam = queryParams.filters || {};

	queryParams.filters = {
		...filtersParam,
		[key]: items.filter(item => item.active).map(item => item.key)
	};

	return stringify(queryParams);
}

export function getRequestUrl(queryString, requestUrl, excludedValues = []) {
	const filtersParam = getFiltersParam(queryString);

	const requestFilter = Object.keys(filtersParam).reduce(
		(queryParams, filterKey) => {
			const filterValues = getFilterValues(filterKey, filtersParam);

			const filterQuery = filterValues
				.filter(filterValue => !excludedValues.includes(filterValue))
				.map(filterValue => `${filterKey}=${filterValue}`)
				.join('&');

			return `${queryParams}&${filterQuery}`;
		},
		''
	);

	return requestUrl + requestFilter;
}

export function isSelected(filterKey, itemKey, queryString) {
	const filtersParam = getFiltersParam(queryString);

	const filterValues = getFilterValues(filterKey, filtersParam);

	return filterValues.includes(itemKey);
}

export function pushToHistory(filterQuery, routerProps) {
	const {
		history,
		location: {search},
		match: {params, path}
	} = routerProps;

	const pathname = pathToRegexp.compile(path)({...params, page: 1});

	if (filterQuery !== search) {
		history.push({
			pathname,
			search: filterQuery
		});
	}
}

export function removeFilters(queryString) {
	const queryParams = parse(queryString);

	queryParams.filters = null;

	return stringify(queryParams);
}

export function removeItem(filterKey, itemToRemove, queryString) {
	const queryParams = parse(queryString);

	const filtersParam = queryParams.filters || {};

	const filterValues = getFilterValues(filterKey, filtersParam);

	filtersParam[filterKey] = filterValues.filter(
		filterValue => filterValue != itemToRemove.key
	);

	queryParams.filters = filtersParam;

	return stringify(queryParams);
}

export function verifySelectedItems(filter, filtersParam) {
	const filterValues = getFilterValues(filter.key, filtersParam);

	filter.items.forEach(item => {
		item.active = filterValues.includes(item.key);
	});

	return filter;
}

export function reduceFilters(filterItems, paramKey) {
	return filterItems.reduce(
		(acc, cur) => `&${paramKey}=${cur.key}${acc}`,
		''
	);
}
