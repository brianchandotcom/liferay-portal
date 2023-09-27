/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {USERS_PROPERTY_NAME_IN_ACCOUNT} from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';

const ACCOUNTS_MOVING_ENDPOINT =
	'/o/headless-admin-user/v1.0/organizations/move-accounts';
const ACCOUNTS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';

export function getAccounts(query, organizationIds = []) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ACCOUNTS_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	if (query) {
		url.searchParams.append('search', query);
	}

	if (organizationIds.length) {
		url.searchParams.append(
			'filter',
			`${organizationIds
				.map((id) => `(organizationIds eq '${id}')`)
				.join(' or ')}`
		);
	}

	return fetchFromHeadless(url);
}

export function deleteAccount(id) {
	return fetchFromHeadless(
		`${ACCOUNTS_ROOT_ENDPOINT}/${id}`,
		{method: 'DELETE'},
		null,
		true
	);
}

export function changeOrganizationParent(accountId, source, target) {
	return fetchFromHeadless(
		`${ACCOUNTS_MOVING_ENDPOINT}/${source}/${target}`,
		{
			body: JSON.stringify([accountId]),
			method: 'PATCH',
		}
	);
}

export function getAccount(id) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ACCOUNTS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('nestedFields', USERS_PROPERTY_NAME_IN_ACCOUNT);

	return fetchFromHeadless(url);
}

export function updateAccount(id, details) {
	return fetchFromHeadless(`${ACCOUNTS_ROOT_ENDPOINT}/${id}`, {
		body: JSON.stringify(details),
		method: 'PATCH',
	});
}

export function createAccount(name, organizationIds) {
	return fetchFromHeadless(ACCOUNTS_ROOT_ENDPOINT, {
		body: JSON.stringify({
			name,
			organizationIds,
		}),
		method: 'POST',
	});
}
