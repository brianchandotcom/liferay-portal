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

const asFilterObject = (items, key, name, pinned = false) => ({
	items,
	key,
	name,
	pinned
});

const getFiltersParam = queryString => {
	const queryParams = parse(queryString);

	return queryParams.filters || {};
};

const getFilterResults = (filterKeys, filterTitles, filterValues) => {
	const filterResults = [];

	filterKeys.forEach((filterKey, index) => {
		if (filterValues[filterKey]) {
			filterResults.push(
				asFilterObject(
					filterValues[filterKey],
					filterKey,
					filterTitles[index]
				)
			);
		}
	});

	return filterResults;
};

const getFilterValues = (filterKey, filtersParam) => {
	let filterValues = filtersParam[filterKey] || [];

	if (!Array.isArray(filterValues)) {
		filterValues = [filterValues];
	}

	return filterValues;
};

const getSelectedItemsQuery = (items, key, queryString) => {
	const queryParams = parse(queryString);

	const filtersParam = queryParams.filters || {};

	queryParams.filters = {
		...filtersParam,
		[key]: items.filter(item => item.active).map(item => item.key)
	};

	return stringify(queryParams);
};

const getSelectedItems = filterResults => {
	return filterResults.filter(filter => {
		filter.items = filter.items
			? filter.items.filter(item => item.active)
			: [];

		return filter.items.length > 0;
	});
};

const handleFilterItems = (items, selectedKeys) =>
	items.map((item, index) => {
		const key = item.key || String(item.id);

		return {
			...item,
			active: selectedKeys && selectedKeys.includes(key),
			dividerAfter: item.dividerAfter && !!items[index + 1],
			key
		};
	});

const mergeItemsArray = (baseItems = [], ...items) => {
	items = items.filter(value => value !== undefined && value !== null);
	return baseItems.concat(...items);
};

const pushToHistory = (filterQuery, routerProps) => {
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
};

const reduceFilters = (filterItems, paramKey) => {
	return filterItems.reduce(
		(acc, cur) => `&${paramKey}=${cur.key}${acc}`,
		''
	);
};

const removeFilters = queryString => {
	const queryParams = parse(queryString);

	queryParams.filters = null;
	queryParams.search = null;

	return stringify(queryParams);
};

const removeItem = (filterKey, itemToRemove, queryString) => {
	const queryParams = parse(queryString);

	const filtersParam = queryParams.filters || {};

	const filterValues = getFilterValues(filterKey, filtersParam);

	filtersParam[filterKey] = filterValues.filter(
		filterValue => filterValue != itemToRemove.key
	);

	queryParams.filters = filtersParam;

	return stringify(queryParams);
};

export {
	asFilterObject,
	getFiltersParam,
	getFilterResults,
	getFilterValues,
	getSelectedItems,
	getSelectedItemsQuery,
	handleFilterItems,
	mergeItemsArray,
	pushToHistory,
	reduceFilters,
	removeFilters,
	removeItem
};
