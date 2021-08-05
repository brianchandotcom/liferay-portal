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

import {USERS_PROPERTY_NAME_IN_ACCOUNT} from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';

const ACCOUNTS_MOVING_ENDPOINT =
	'/o/headless-admin-user/v1.0/organizations/move-accounts';
const ACCOUNTS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';

export function getAccounts(query, organizationIds = []) {
	const url = new URL(ACCOUNTS_ROOT_ENDPOINT, themeDisplay.getPortalURL());

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
	const url = new URL(
		`${ACCOUNTS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {method: 'DELETE'}, null, true);
}

export function changeOrganizationParent(accountId, source, target) {
	const url = new URL(
		`${ACCOUNTS_MOVING_ENDPOINT}/${source}/${target}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {
		body: JSON.stringify([accountId]),
		method: 'PATCH',
	});
}

export function getAccount(id) {
	const accountUrl = new URL(
		`${ACCOUNTS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	accountUrl.searchParams.append(
		'nestedFields',
		USERS_PROPERTY_NAME_IN_ACCOUNT
	);

	return fetchFromHeadless(accountUrl);
}

export function updateAccount(id, details) {
	const url = new URL(
		`${ACCOUNTS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {
		body: JSON.stringify(details),
		method: 'PATCH',
	});
}

export function createAccount(name, organizationIds) {
	const url = new URL(ACCOUNTS_ROOT_ENDPOINT, themeDisplay.getPortalURL());

	return fetchFromHeadless(url, {
		body: JSON.stringify({
			name,
			organizationIds,
		}),
		method: 'POST',
	});
}
