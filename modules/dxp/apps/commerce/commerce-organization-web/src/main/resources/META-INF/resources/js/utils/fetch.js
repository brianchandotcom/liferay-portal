/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
