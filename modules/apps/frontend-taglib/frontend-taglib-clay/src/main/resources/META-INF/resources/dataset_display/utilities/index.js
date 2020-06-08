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
	const regex = new RegExp('{(.*?)}', 'mg');

	return url.replace(regex, (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('|')
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
	const pagination = `&pageSize=${delta}&page=${page}`;
	const searchParamString = searchParam ? `&search=${searchParam}` : '';
	const sortingString = sorting.length
		? `&orderBy=${JSON.stringify(sorting)}`
		: ``;

	const url = `${apiUrl}${
		apiUrl.indexOf('?') > -1 ? '&' : '?'
	}${authString}${currentUrlString}${pagination}${sortingString}${searchParamString}`;

	return executeAsyncAction(url, 'GET').then((response) => response.json());
}
