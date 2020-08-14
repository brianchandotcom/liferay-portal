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

export function getData(apiURL, query) {
	const url = new URL(apiURL);

	if (query) {
		url.searchParams.append('search', query);
	}

	return fetch(url, {
		method: 'GET',
	}).then((data) => data.json());
}

export function getSchemaString(object, path) {
	if (!Array.isArray(path)) {
		return object[path];
	}
	else {
		return path.reduce((acc, path) => acc[path], object);
	}
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
	if (!fieldName) {
		return null;
	}
	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => acc[key], item);
	}

	return item[fieldName];
}

export function executeAsyncAction(url, method = 'GET') {
	return fetch(url, {
		method,
	});
}

export function formatActionUrl(url, item) {
	const replacedUrl = url.replace(new RegExp('{(.*?)}', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('|')
		)
	);

	return replacedUrl.replace(new RegExp('(%7B.*?%7D)', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('|')
		)
	);
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

export function loadData(
	apiURL,
	currentUrl,
	filters,
	searchParam,
	delta,
	page = 1,
	sorting = []
) {
	const url = new URL(apiURL, themeDisplay.getPortalURL());

	url.searchParams.append('currentUrl', currentUrl);

	if (filters.length) {
		url.searchParams.append('filter', createOdataFilter(filters));
	}

	url.searchParams.append('page', page);
	url.searchParams.append('pageSize', delta);

	if (searchParam) {
		url.searchParams.append('search', searchParam);
	}

	if (sorting.length) {
		url.searchParams.append(
			'sort',
			sorting.map((item) => `${item.key}:${item.direction}`).join(',')
		);
	}

	return executeAsyncAction(url, 'GET').then((response) => response.json());
}
