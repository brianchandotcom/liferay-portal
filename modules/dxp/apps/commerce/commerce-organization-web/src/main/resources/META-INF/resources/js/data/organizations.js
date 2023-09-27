/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ACCOUNTS_PROPERTY_NAME,
	ORGANIZATIONS_PROPERTY_NAME,
	USERS_PROPERTY_NAME_IN_ORGANIZATION,
} from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';

export const ORGANIZATIONS_ROOT_ENDPOINT =
	'/o/headless-admin-user/v1.0/organizations';

export function createOrganizations(names, parentOrganizationId) {
	return Promise.allSettled(
		names.map((name) =>
			fetchFromHeadless(ORGANIZATIONS_ROOT_ENDPOINT, {
				body: JSON.stringify({
					name,
					parentOrganization: {id: parentOrganizationId},
				}),
				method: 'POST',
			})
		)
	);
}

export function getOrganization(id) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append(
		'nestedFields',
		`${ORGANIZATIONS_PROPERTY_NAME},${ACCOUNTS_PROPERTY_NAME},${USERS_PROPERTY_NAME_IN_ORGANIZATION}`
	);

	return fetchFromHeadless(url);
}

export function getOrganizations(pageSize) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ORGANIZATIONS_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('pageSize', pageSize);

	return fetchFromHeadless(url);
}

export function deleteOrganization(id) {
	return fetchFromHeadless(
		`${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`,
		{method: 'DELETE'},
		null,
		true
	);
}

export function updateOrganization(id, body) {
	return fetchFromHeadless(`${ORGANIZATIONS_ROOT_ENDPOINT}/${id}`, {
		body: JSON.stringify(body),
		method: 'PATCH',
	});
}
