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

import createOdataFilter from './odata';

export function getData(apiUrl, query) {
	let url = apiUrl;

	if (query) {
		url += (url.includes('?') ? '&' : '?') + `search=${query}`;
	}

	return fetch(url, {
		...fetchParams,
		method: 'GET'
	}).then(data => data.json());
}

export function liferayNavigate(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	} else {
		window.location.href = url;
	}
}

export function getValueFromItem(item, fieldName) {
	if (!fieldName) {
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

export function excludeFromList(matchingList, againstList) {
	const matcher = JSON.stringify(matchingList);

	return againstList.filter(item => !matcher.includes(JSON.stringify(item)));
}

export function executeAsyncAction(url, method = 'GET', body = null) {
	return fetch(url, {
		...fetchParams,
		body,
		method
	});
}

export function formatActionUrl(url, item) {
	var regex = new RegExp('{(.*?)}', 'mg');

	var replacedUrl = url.replace(regex, matched =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('|')
		)
	);

	regex = new RegExp('(%7B.*?%7D)', 'mg');

	replacedUrl = replacedUrl.replace(regex, matched =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('|')
		)
	);

	return replacedUrl;
}

export function getRandomId() {
	return Math.random()
		.toString(36)
		.substr(2, 9);
}

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
	'x-csrf-token': Liferay.authToken
});

export const fetchParams = {
	credentials: 'include',
	headers: Liferay.staticEnvHeaders || fetchHeaders
};

export function createSortingString(values) {
	if (!values.length) return null;

	return values
		.map(value => {
			return `${
				Array.isArray(value.fieldName)
					? value.fieldName[0]
					: value.fieldName
			}:${value.direction}`;
		})
		.join(',');
}

export function loadData(
	apiUrl,
	currentUrl,
	filters,
	searchParam,
	delta,
	page = 1,
	sorting = []
) {
	const authString = `p_auth=${window.Liferay.authToken}`;
	const currentUrlString = `&currentUrl=${encodeURIComponent(currentUrl)}`;
	const paginationString = `&pageSize=${delta}&page=${page}`;
	const searchParamString = searchParam ? `&search=${searchParam}` : '';
	const sortingString = sorting.length
		? `&sort=${sorting
				.map(item => `${item.key}:${item.direction}`)
				.join(',')}`
		: ``;
	const filtersString = filters.length
		? `&filter=${createOdataFilter(filters)}`
		: '';

	const url = `${apiUrl}${
		apiUrl.indexOf('?') > -1 ? '&' : '?'
	}${authString}${currentUrlString}${paginationString}${sortingString}${filtersString}${searchParamString}`;

	return executeAsyncAction(url, 'GET').then(response => response.json());
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
						[item[keyName]]: item
					}
				};
			} else {
				return {
					...data,
					unsortable: data.unsortable.concat(item)
				};
			}
		},
		{
			sorted: {},
			unsortable: []
		}
	);

	const sortedItems = [
		...Object.values(arrangedItems.sorted),
		...arrangedItems.unsortable
	];

	return sortedItems;
}
