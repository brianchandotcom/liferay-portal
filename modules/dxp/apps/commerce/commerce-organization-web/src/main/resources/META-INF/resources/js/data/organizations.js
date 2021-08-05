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

import {
	ACCOUNTS_PROPERTY_NAME,
	ORGANIZATIONS_PROPERTY_NAME,
	USERS_PROPERTY_NAME_IN_ORGANIZATION,
} from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';
import {getAccounts} from './accounts';

export const ORGANIZATIONS_ROOT_ENDPOINT =
	'/o/headless-admin-user/v1.0/organizations';

export const createOrganizations = (names, parentOrganizationId) => {
	const url = new URL(
		ORGANIZATIONS_ROOT_ENDPOINT,
		themeDisplay.getPortalURL()
	);

	return Promise.allSettled(
		names.map((name) =>
			fetchFromHeadless(url, {
				body: JSON.stringify({
					name,
					parentOrganization: {id: parentOrganizationId},
				}),
				method: 'POST',
			})
		)
	);
};

export function getOrganization(id) {
	const url = new URL(
		`${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append(
		'nestedFields',
		`${ORGANIZATIONS_PROPERTY_NAME},${ACCOUNTS_PROPERTY_NAME},${USERS_PROPERTY_NAME_IN_ORGANIZATION}`
	);

	return fetchFromHeadless(url).then((organization) => {
		return getAccounts(null, [organization.id]).then(
			(accountsResponse) => ({
				...organization,
				[ACCOUNTS_PROPERTY_NAME]: accountsResponse.items,
			})
		);
	});
}

export function getOrganizations(pageSize) {
	const url = new URL(
		ORGANIZATIONS_ROOT_ENDPOINT,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('pageSize', pageSize);

	return fetchFromHeadless(url);
}

export function deleteOrganization(id) {
	const url = new URL(
		`${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {method: 'DELETE'}, null, true);
}

export function updateOrganization(id, body) {
	const url = new URL(
		`${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {
		body: JSON.stringify(body),
		method: 'PATCH',
	});
}
