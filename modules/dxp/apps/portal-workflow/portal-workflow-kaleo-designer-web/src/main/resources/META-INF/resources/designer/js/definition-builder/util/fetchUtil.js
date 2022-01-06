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

import {fetch} from 'frontend-js-web';

const baseURL = '/o/headless-admin-workflow/v1.0';

const headers = {
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
};

function publishDefinitionRequest(requestBody) {
	return fetch(`${baseURL}/workflow-definitions/deploy`, {
		body: JSON.stringify(requestBody),
		headers: {
			...headers,
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});
}

function retrieveDefinitionRequest(definitionId) {
	return fetch(`${baseURL}/workflow-definitions/by-name/${definitionId}`, {
		headers,
		method: 'GET',
	});
}

function saveDefinitionRequest(requestBody) {
	return fetch(`${baseURL}/workflow-definitions/save`, {
		body: JSON.stringify(requestBody),
		headers: {
			...headers,
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});
}

export {
	baseURL,
	headers,
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
};
