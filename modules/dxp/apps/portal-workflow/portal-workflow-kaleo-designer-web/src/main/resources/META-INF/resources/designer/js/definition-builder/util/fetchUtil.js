/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

function retrieveDefinitionRequest(definitionName, versionNumber) {
	let url = `${workflowBaseURL}/workflow-definitions/by-name/${definitionName}`;

	if (versionNumber) {
		url = `${url}?version=${versionNumber}`;
	}

	return fetch(url, {
		headers,
		method: 'GET',
	});
}

function retrieveRoleById(roleId) {
	return fetch(
		`${window.location.origin}${contextUrl}${userBaseURL}/roles/${roleId}`,
		{
			headers,
			method: 'GET',
		}
	);
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
		`${window.location.origin}${contextUrl}${userBaseURL}/user-accounts?filter=${filterParameter}`
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
