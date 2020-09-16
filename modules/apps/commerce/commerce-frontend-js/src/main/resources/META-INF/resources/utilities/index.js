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

import {fetch} from 'frontend-js-web';

import createOdataFilter from './odata';

export function getAcceptLanguageHeaderParam() {
	const browserLang = navigator.language || navigator.userLanguage;
	const themeLang = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

	if (browserLang === themeLang) {
		return browserLang;
	}

	return `${browserLang}, ${themeLang};q=0.8`;
}

export const fetchHeaders = new Headers({
	Accept: 'application/json',
	'Accept-Language': getAcceptLanguageHeaderParam(),
	'Content-Type': 'application/json',
});

export const fetchParams = {
	headers: Liferay.staticEnvHeaders || fetchHeaders,
};

export function getData(apiUrl, query, page, pageSize) {
	const url = new URL(apiUrl, Liferay.ThemeDisplay.getPortalURL());

	if (query) {
		url.searchParams.append('search', query);
	}

	if (page) {
		url.searchParams.append('page', page);
	}

	if (pageSize) {
		url.searchParams.append('pageSize', pageSize);
	}

	return fetch(url, {
		...fetchParams,
	}).then((data) => data.json());
}

export function liferayNavigate(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	}
	else {
		window.location.href = url;
	}
}

export function getValueFromItem(item, fieldName) {
	if (!fieldName || typeof item === 'string') {
		return null;
	}

	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => {
			if (key === 'LANG') {
				return (
					acc[Liferay.ThemeDisplay.getLanguageId()] ||
					acc[Liferay.ThemeDisplay.getDefaultLanguageId()]
				);
			}

			return acc[key];
		}, item);
	}

	return item[fieldName];
}

export function gHash(string) {
	let hash = 0;

	if (string.length === 0) {
		return hash;
	}

	[...string].forEach((char) => {
		hash = (hash << 7) - hash + char.charCodeAt();
		hash = hash & hash;
	});

	return hash;
}

export function excludeFromList(matchingList, againstList) {
	const matcher = JSON.stringify(matchingList);

	return againstList.filter(
		(item) => !matcher.includes(JSON.stringify(item))
	);
}

export function executeAsyncAction(url, method = 'GET', body = null) {
	return fetch(url, {
		...fetchParams,
		body,
		method,
	});
}

export function formatActionUrl(url, item) {
	var regex = new RegExp('{(.*?)}', 'mg');

	var replacedUrl = url.replace(regex, (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('.')
		)
	);

	regex = new RegExp('(%7B.*?%7D)', 'mg');

	replacedUrl = replacedUrl.replace(regex, (matched) =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('.')
		)
	);

	return replacedUrl;
}

export function getRandomId() {
	return Math.random().toString(36).substr(2, 9);
}

export function createSortingString(values) {
	if (!values.length) {
		return null;
	}

	return values
		.map((value) => {
			return `${
				Array.isArray(value.fieldName)
					? value.fieldName[0]
					: value.fieldName
			}:${value.direction}`;
		})
		.join(',');
}

export function getFiltersString(filters, providedFilters) {
	let filtersString = '';

	if (filters.length || providedFilters) {
		filtersString = '&filter=';
	}

	if (providedFilters) {
		filtersString += providedFilters;
	}

	if (providedFilters && filters.length) {
		filtersString += ' and ';
	}

	if (filters.length) {
		filtersString += createOdataFilter(filters);
	}

	return filtersString;
}

export function loadData(
	apiUrl,
	currentUrl,
	delta,
	filters,
	page = 1,
	searchQuery,
	sorting = []
) {
	let formattedUrl = apiUrl;
	let providedFilters = '';

	const authParam = `p_auth=${window.Liferay.authToken}`;
	const currentUrlParam = `&currentUrl=${encodeURIComponent(currentUrl)}`;
	const pageSizeParam = `&pageSize=${delta}`;
	const pageParam = `&page=${page}`;
	const searchParam = searchQuery ? `&search=${searchQuery}` : '';
	const sortingParam = sorting.length
		? `&sort=${sorting
				.map((item) => `${item.key}:${item.direction}`)
				.join(',')}`
		: ``;

	const regex = new RegExp('[?|&]filter=(.*)[&.+]?', 'mg');

	formattedUrl = formattedUrl.replace(regex, (matched) => {
		providedFilters = matched.replace(/[?|&]filter=/, '');

		return '';
	});

	const filtersParam = getFiltersString(filters, providedFilters);

	const url = `${formattedUrl}${
		formattedUrl.indexOf('?') > -1 ? '&' : '?'
	}${authParam}${currentUrlParam}${pageSizeParam}${pageParam}${sortingParam}${searchParam}${filtersParam}`;

	return executeAsyncAction(url, 'GET').then((response) => response.json());
}

export function serializeParameters(parameters) {
	return Array.isArray(parameters) ? `?${parameters.join('&')}` : '';
}

export function sortByKey(items, keyName) {
	const arrangedItems = items.reduce(
		(data, item) => {
			if (typeof item[keyName] === 'number') {
				return {
					...data,
					sorted: {
						...data.sorted,
						[item[keyName]]: item,
					},
				};
			}
			else {
				return {
					...data,
					unsortable: data.unsortable.concat(item),
				};
			}
		},
		{
			sorted: {},
			unsortable: [],
		}
	);

	const sortedItems = [
		...Object.values(arrangedItems.sorted),
		...arrangedItems.unsortable,
	];

	return sortedItems;
}
