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
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

const parseJSON = (response, resolve, reject) =>
	response
		.text()
		.then((text) => resolve(text ? JSON.parse(text) : {}))
		.catch((error) => reject(error));

const parseResponse = (response) =>
	new Promise((resolve, reject) => {
		if (response.ok) {
			parseJSON(response, resolve, reject);
		}
		else {
			parseJSON(response, reject, reject);
		}
	});

export const getURL = (path, params) => {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params,
	};

	const uri = new URL(`${window.location.origin}${path}`);
	const keys = Object.keys(params);

	keys.forEach((key) => {
		if (Array.isArray(params[key])) {
			params[key].forEach((value) => uri.searchParams.append(key, value));
		}
		else {
			uri.searchParams.set(key, params[key]);
		}
	});

	return uri.toString();
};

export const request = ({endpoint, method = 'GET', params = {}}) =>
	fetch(getURL(endpoint, params), {
		headers: HEADERS,
		method,
	}).then((response) => parseResponse(response));
