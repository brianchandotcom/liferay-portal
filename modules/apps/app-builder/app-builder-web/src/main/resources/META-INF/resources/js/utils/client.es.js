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

export const addItem = (endpoint, item) => {
	return fetch(getURL(endpoint), {
		body: JSON.stringify(item),
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json'
		},
		method: 'POST'
	}).then(response => {
		return response.json();
	});
};

export const confirmDelete = endpoint => item =>
	new Promise((resolve, reject) => {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) {
			deleteItem(endpoint + item.id)
				.then(() => resolve(true))
				.catch(error => reject(error));
		} else {
			resolve(false);
		}
	});

export const deleteItem = endpoint => {
	return fetch(getURL(endpoint), {
		method: 'DELETE'
	});
};

export const getItem = endpoint => {
	return fetch(getURL(endpoint), {
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json'
		},
		method: 'GET'
	}).then(response => {
		return response.json();
	});
};

export const getURL = (path, params = {['p_auth']: Liferay.authToken}) => {
	const uri = new URL(`${window.location.origin}${path}`);
	const keys = Object.keys(params);

	keys.forEach(key => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

export const updateItem = (endpoint, item) => {
	return fetch(getURL(endpoint), {
		body: JSON.stringify(item),
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json'
		},
		method: 'PUT'
	}).then(response => {
		return response.json();
	});
};
