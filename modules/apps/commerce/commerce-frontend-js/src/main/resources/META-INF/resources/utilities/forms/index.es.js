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

import {isFormElement, toJSON} from './formsHelper.es';

function doSubmit(apiUrl, method = 'POST', json = {}) {
	const headers = new Headers({
		Accept: 'application/json',
		'Content-Type': 'application/json',
		'x-csrf-token': Liferay.authToken
	});

	const options = {
		body: JSON.stringify(json),
		credentials: 'include',
		headers,
		method
	};

	return fetch(apiUrl, options)
		.then(response => response.json())
		.catch(error => {
			throw new Error(error);
		});
}

export function apiSubmit(formElement, API_URL = null) {
	if (isFormElement(formElement)) {
		const jsonData = toJSON(new FormData(formElement)),
			method = formElement.method;

		return doSubmit(API_URL, method, jsonData);
	}

	return Promise.reject(new Error('Not a form.'));
}

export default {apiSubmit};
