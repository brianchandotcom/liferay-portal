import { parse, stringify } from '../../router/queryString';

export function getFiltersParam(queryString) {
	const queryParams = parse(queryString);

	return queryParams.filters || {};
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

export function pushToHistory(filterQuery, routerProps) {
	const {
		history,
		location: { pathname, search }
	} = routerProps;

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

export function removeItem(filter, itemToRemove, queryString) {
	const queryParams = parse(queryString);

	const filtersParam = queryParams.filters || {};

	const currentFilter = filtersParam[filter.key] || [];

	filtersParam[filter.key] = currentFilter.filter(
		itemKey => itemKey != itemToRemove.key
	);

	queryParams.filters = filtersParam;

	return stringify(queryParams);
}