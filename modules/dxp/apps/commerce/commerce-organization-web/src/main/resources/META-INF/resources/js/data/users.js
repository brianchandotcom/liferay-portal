/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ACCOUNTS_ROLE_TYPE_ID,
	ORGANIZATIONS_ROLE_TYPE_ID,
} from '../utils/constants';
import {fetchFromHeadless} from '../utils/fetch';

export const ACCOUNTS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';
export const ROLES_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/roles';
export const ORGANIZATIONS_ROOT_ENDPOINT =
	'/o/headless-admin-user/v1.0/organizations';
export const USER_ACCOUNT_FULL_NAME_DEFINITION =
	'/o/headless-admin-user/v1.0/user-account-full-name-definition';
export const USERS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/user-accounts';

export function getUsersByEmails(emails) {
	const filterString = emails
		.map((email) => `emailAddress eq '${email}'`)
		.join(' or ');
	const url = new URL(
		`${themeDisplay.getPathContext()}${USERS_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('filter', filterString);

	return fetchFromHeadless(url);
}

export function getOrganizationRoles() {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ROLES_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('pageSize', 100);
	url.searchParams.append('restrictFields', 'rolePermissions');
	url.searchParams.append('types', ORGANIZATIONS_ROLE_TYPE_ID);

	return fetchFromHeadless(url, {}, null, true).then(
		(jsonResponse) => jsonResponse.items
	);
}

export function getAccountRoles(accountId) {
	const genericRolesURL = new URL(
		`${themeDisplay.getPathContext()}${ROLES_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	genericRolesURL.searchParams.append('pageSize', 100);
	genericRolesURL.searchParams.append('restrictFields', 'rolePermissions');
	genericRolesURL.searchParams.append('types', ACCOUNTS_ROLE_TYPE_ID);

	const specificRolesURL = `${ACCOUNTS_ROOT_ENDPOINT}/${accountId}/account-roles`;

	return Promise.allSettled([
		fetchFromHeadless(genericRolesURL),
		fetchFromHeadless(specificRolesURL),
	]).then(([genericRolesResponse, specificRolesResponse]) => {
		return [
			...genericRolesResponse.value.items,
			...specificRolesResponse.value.items,
		];
	});
}

export function getUser(id) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${USERS_ROOT_ENDPOINT}/${id}`,
		themeDisplay.getPortalURL()
	);

	return fetchFromHeadless(url);
}

export function getUsers(query) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${USERS_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('search', query);
	url.searchParams.append('pageSize', 20);

	return fetchFromHeadless(url);
}

export function deleteUser(id) {
	return fetchFromHeadless(
		`${USERS_ROOT_ENDPOINT}/${id}`,
		{method: 'DELETE'},
		null,
		true
	);
}

export function removeUserFromOrganization(userEmail, organizationId) {
	return fetchFromHeadless(
		`${ORGANIZATIONS_ROOT_ENDPOINT}/${organizationId}/user-accounts/by-email-address/${userEmail}`,
		{
			method: 'DELETE',
		}
	);
}

export function addUserEmailsToAccount(accountId, roleIds, emails) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ACCOUNTS_ROOT_ENDPOINT}/${accountId}/user-accounts/by-email-address`,
		themeDisplay.getPortalURL()
	);

	if (roleIds) {
		url.searchParams.append('accountRoleIds', `${roleIds}`);
	}

	return fetchFromHeadless(url, {
		body: JSON.stringify(emails),
		method: 'POST',
	}).then((response) => response.items);
}

export function addUserEmailsToOrganization(organizationId, roleIds, emails) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${ORGANIZATIONS_ROOT_ENDPOINT}/${organizationId}/user-accounts/by-email-address`,
		themeDisplay.getPortalURL()
	);

	if (roleIds) {
		url.searchParams.append('organizationRoleIds', `${roleIds}`);
	}

	return fetchFromHeadless(url, {
		body: JSON.stringify(emails),
		method: 'POST',
	}).then((response) => response.items);
}

export function removeUserFromAccount(userEmail, accountId) {
	return fetchFromHeadless(
		`${ACCOUNTS_ROOT_ENDPOINT}/${accountId}/user-accounts/by-email-address`,
		{
			body: JSON.stringify([userEmail]),
			method: 'DELETE',
		}
	);
}

export function updateUser(id, details) {
	return fetchFromHeadless(`${USERS_ROOT_ENDPOINT}/${id}`, {
		body: JSON.stringify(details),
		method: 'PATCH',
	});
}

export function getUserFullNameDefinition(languageId) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${USER_ACCOUNT_FULL_NAME_DEFINITION}`,
		themeDisplay.getPortalURL()
	);

	if (languageId) {
		url.searchParams.append('languageId', languageId);
	}

	return fetchFromHeadless(url);
}
