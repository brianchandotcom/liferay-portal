import {parse, stringify} from '../../router/queryString';
import pathToRegexp from 'path-to-regexp';

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