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

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json'
};

const parseJSON = (response, resolve, reject) =>
	response
		.text()
		.then(text => resolve(text ? JSON.parse(text) : {}))
		.catch(error => reject(error));

const parseResponse = response =>
	new Promise((resolve, reject) => {
		if (response.ok) {
			parseJSON(response, resolve, reject);
		}
		else {
			parseJSON(response, reject, reject);
		}
	});

export const addItem = (endpoint, item) =>
	fetch(getURL(endpoint), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'POST'
	}).then(response => parseResponse(response));

export const confirmDelete = endpoint => item =>
	new Promise((resolve, reject) => {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) {
			deleteItem(endpoint + item.id)
				.then(() => resolve(true))
				.catch(error => reject(error));
		}
		else {
			resolve(false);
		}
	});

export const deleteItem = endpoint =>
	fetch(getURL(endpoint), {
		headers: HEADERS,
		method: 'DELETE'
	}).then(response => parseResponse(response));

export const getItem = (endpoint, params) =>
	fetch(getURL(endpoint, params), {
		headers: HEADERS,
		method: 'GET'
	}).then(response => parseResponse(response));

export const getURL = (path, params) => {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params
	};

	const uri = new URL(`${window.location.origin}${path}`);
	const keys = Object.keys(params);

	keys.forEach(key => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

export const request = (endpoint, method = 'GET') =>
	fetch(getURL(endpoint), {
		headers: HEADERS,
		method
	}).then(response => parseResponse(response));

export const updateItem = (endpoint, item, params) =>
	fetch(getURL(endpoint, params), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'PUT'
	}).then(response => parseResponse(response));
