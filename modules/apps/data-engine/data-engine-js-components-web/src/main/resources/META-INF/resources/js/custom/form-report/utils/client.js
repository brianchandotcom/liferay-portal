/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export function getURL(path, params) {
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
}

export function request({endpoint, method = 'GET', params = {}}) {
	return fetch(getURL(endpoint, params), {
		headers: HEADERS,
		method,
	}).then((response) => parseResponse(response));
}
