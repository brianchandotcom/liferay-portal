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

import {contextUrl} from '../constants';

const userBaseURL = '/o/headless-admin-user/v1.0';
const workflowBaseURL = '/o/headless-admin-workflow/v1.0';

const headers = {
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
};

function publishDefinitionRequest(requestBody) {
	return fetch(`${workflowBaseURL}/workflow-definitions/deploy`, {
		body: JSON.stringify(requestBody),
		headers: {
			...headers,
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});
}

function retrieveAccountRoles(accountId) {
	return fetch(`${userBaseURL}/accounts/${accountId}/account-roles`, {
		headers,
		method: 'GET',
	});
}

function retrieveDefinitionRequest(definitionId, versionNumber) {
	let url = `${workflowBaseURL}/workflow-definitions/by-name/${definitionId}`;

	if (versionNumber) {
		url = `${url}?version=${versionNumber}`;
	}

	return fetch(url, {
		headers,
		method: 'GET',
	});
}

function retrieveRoleById(roleId) {
	return fetch(`${window.location.origin}${userBaseURL}/roles/${roleId}`, {
		headers,
		method: 'GET',
	});
}

function retrieveRoles() {
	return fetch(
		`${window.location.origin}${contextUrl}${userBaseURL}/roles?pageSize=-1`,
		{
			headers,
			method: 'GET',
		}
	);
}

function retrieveUsersBy(filterType, keywords) {
	let filterParameter = String();
	for (const keyword of keywords) {
		filterParameter =
			filterParameter + filterType + " eq '" + keyword + "' or ";
	}
	filterParameter = encodeURIComponent(filterParameter)
		.replace(/'/g, '%27')
		.slice(0, -8);

	const url = new URL(
		`${window.location.origin}${userBaseURL}/user-accounts?filter=${filterParameter}`
	);

	return fetch(url, {
		headers,
		method: 'GET',
	});
}

function saveDefinitionRequest(requestBody) {
	return fetch(`${workflowBaseURL}/workflow-definitions/save`, {
		body: JSON.stringify(requestBody),
		headers: {
			...headers,
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});
}

export {
	headers,
	userBaseURL,
	workflowBaseURL,
	publishDefinitionRequest,
	retrieveAccountRoles,
	retrieveDefinitionRequest,
	retrieveRoleById,
	retrieveRoles,
	retrieveUsersBy,
	saveDefinitionRequest,
};
