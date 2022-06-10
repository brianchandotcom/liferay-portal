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

import {openConfirmModal} from 'frontend-js-web';

const HEADERS = {
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

function fetchItem(url, options) {
	return new Promise((resolve, reject) => {
		let isOk;

		fetch(url, options)
			.then((response) => {
				isOk = response.ok;

				return response.json();
			})
			.then((data) => {
				if (isOk) {
					resolve(data);
				}
				else {
					reject(data);
				}
			})
			.catch((error) => reject(error));
	});
}

export function getURL(path, params) {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params,
	};

	let pathContext = themeDisplay.getPathContext();

	if (!pathContext || pathContext === '/') {
		pathContext = '';
	}

	const uri = new URL(`${window.location.origin}${pathContext}${path}`);

	const keys = Object.keys(params);

	keys.forEach((key) => uri.searchParams.set(key, params[key]));

	return uri.toString();
}

export function addItem(endpoint, item) {
	return fetchItem(getURL(endpoint), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'POST',
	});
}

export function deleteItem(endpoint) {
	return fetch(getURL(endpoint), {
		method: 'DELETE',
	});
}

export function confirmDelete(endpoint) {
	return (item) =>
		new Promise((resolve, reject) => {
			openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						deleteItem(endpoint + item.id)
							.then(() => resolve(true))
							.catch((error) => reject(error));
					}
					else {
						resolve(false);
					}
				},
			});
		});
}

export function request(endpoint, method = 'GET') {
	return fetch(getURL(endpoint), {
		headers: HEADERS,
		method,
	});
}

export function getItem(endpoint) {
	return fetch(getURL(endpoint), {
		headers: HEADERS,
		method: 'GET',
	}).then((response) => response.json());
}

export function updateItem(endpoint, item, params) {
	return fetchItem(getURL(endpoint, params), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'PUT',
	});
}
