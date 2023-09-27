/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, openToast} from 'frontend-js-web';

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export function fetchFromHeadless(
	url,
	params = {},
	successMessage,
	showErrorMessage
) {
	return fetch(url, {
		headers,
		...params,
	})
		.then(handleFetchResponse)
		.then((data) => handleResponseOk(data, successMessage))
		.catch((error) => handleResponseNotOk(error, showErrorMessage));
}

export function handleFetchResponse(response) {
	return response
		.json()
		.then((data) =>
			response.ok ? Promise.resolve(data) : Promise.reject(data)
		)
		.catch((error) =>
			response.ok
				? Promise.resolve(null)
				: Promise.reject({...error, title: error.title || error.status})
		);
}

export function handleResponseOk(jsonResponse, message) {
	if (message) {
		openToast({
			message,
			type: 'success',
		});
	}

	return jsonResponse;
}

export function handleResponseNotOk(error, showError = false) {
	console.error(error);

	if (showError) {
		openToast({
			message: error.title,
			type: 'danger',
		});
	}

	throw error;
}
