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

import { ACCOUNTS_ROLE_TYPE_ID, ORGANIZATIONS_ROLE_TYPE_ID } from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';

export const USERS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/user-accounts';
export const ROLES_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/roles';
export const ACCOUNT_ROLES_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';

export function getUsersByEmails(emails) {
	const filterString = emails
		.map((email) => `emailAddress eq '${email}'`)
		.join(' or ');
	const url = new URL(USERS_ROOT_ENDPOINT, themeDisplay.getPortalURL());

	url.searchParams.append('filter', filterString);

	return fetchFromHeadless(url);
}

export function getOrganizationRoles() {
	const url = new URL(ROLES_ROOT_ENDPOINT, themeDisplay.getPortalURL());
	
	url.searchParams.append('pageSize', 100);
	url.searchParams.append('types', ORGANIZATIONS_ROLE_TYPE_ID);
	
	return fetchFromHeadless(url, {}, null, true)
		.then(jsonResponse => jsonResponse.items);
}

export function getAccountRoles(accountId) {
	const genericRolesUrl = new URL(ROLES_ROOT_ENDPOINT, themeDisplay.getPortalURL());

	genericRolesUrl.searchParams.append('pageSize', 100);
	genericRolesUrl.searchParams.append('types', ACCOUNTS_ROLE_TYPE_ID);

	const specificRolesUrl = new URL(`${ACCOUNT_ROLES_ROOT_ENDPOINT}/${accountId}/account-roles`, themeDisplay.getPortalURL()) 

	return Promise.allSettled([
		fetchFromHeadless(genericRolesUrl),
		fetchFromHeadless(specificRolesUrl)
	]).then(([
		genericRolesResponse, 
		specificRolesResponse
	]) => {
		return [
			...genericRolesResponse.value.items, 
			...specificRolesResponse.value.items
		];
	});
}

export function getUsers(query) {
	const url = new URL(USERS_ROOT_ENDPOINT, themeDisplay.getPortalURL());

	url.searchParams.append('search', query);
	url.searchParams.append('pageSize', 20);

	return fetchFromHeadless(url);
}

export function deleteUser(id) {
	const url = new URL(
		`${USERS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url, {method: 'DELETE'}, null, true);
}
