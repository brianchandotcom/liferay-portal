/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const baseURL = Liferay.ThemeDisplay.getPortalURL();
const myUserId = Liferay.ThemeDisplay.getUserId();

const fetcher = async (url, {method = 'GET', ...options} = {}) => {
	const response = await fetch(`${baseURL}${url}`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method,
		...options,
	});

	if (response.ok) {
		if (method === 'DELETE' || response.status === 204) {
			return;
		}

		return response.json();
	}

	console.error('Failed to fetch user data:', response.status);

	throw new Error(response.json());
};

const getMyUserAccount = () =>
	fetcher(`/o/headless-admin-user/v1.0/my-user-account`);

const getMyUserAditionalInfos = async () => {
	const userAdditionalInfos = await fetcher(
		`/o/c/useradditionalinfos?filter=r_userToUserAddInfo_userId eq '${myUserId}' and contains(sendType,'shipping') and acceptInviteStatus eq false&nestedFields=user`
	);

	return userAdditionalInfos?.items ?? [];
};

const unassignUserRole = async (accountId, roleId) => {
	return fetcher(
		`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${myUserId}`,
		{
			method: 'DELETE',
		}
	);
};

const getAccountRoles = async (accountId) => {
	const userAccountRole = await fetcher(
		`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles`
	);

	return userAccountRole?.items ?? [];
};

const sendRolesApi = async (roleId, accountId) => {
	return fetcher(
		`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${myUserId}`,
		{
			method: 'POST',
		}
	);
};

const updateInviteStatus = async (userAdditionalInfosId) => {
	return fetcher(`/o/c/useradditionalinfos/${userAdditionalInfosId}`, {
		body: JSON.stringify({
			acceptInviteStatus: true,
		}),
		method: 'PATCH',
	});
};

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const inviteRoles = {
	Admin: ['Account Administrator'],
	Customer: ['Account Buyer', 'Account Member'],
	Publisher: ['App Editor'],
};

function checkAccountTypeByRole(userRoles) {
	const hasCustomerRoles = userRoles.some((value) =>
		isRoleMatch(value, ['Customer'] || ['Customer', 'Admin'])
	);

	const hasPublisherRoles = userRoles.some((value) =>
		isRoleMatch(value, ['Publisher'] || ['Publisher', 'Admin'])
	);

	return hasCustomerRoles
		? 'customer-dashboard'
		: hasPublisherRoles
		? 'publisher-dashboard'
		: 'home';
}

function isRoleMatch(value, roles) {
	return roles.some((role) =>
		inviteRoles[role].some(
			(roleValue) =>
				roleValue.trim().toLowerCase() === value.trim().toLowerCase()
		)
	);
}

const main = async () => {
	const userAccountContainer = document.querySelector(
		'#loading-fragment strong'
	);

	const userAdditionalInfos = await getMyUserAditionalInfos();

	for (const userAdditionalInfo of userAdditionalInfos || []) {
		if (userAdditionalInfo.acceptInviteStatus) {
			continue;
		}

		const userRoles = userAdditionalInfo.roles.split('/').filter(Boolean);
		const finalURL = checkAccountTypeByRole(userRoles);

		const myUserAccount = await getMyUserAccount();

		const [accountBrief] = myUserAccount.accountBriefs ?? [];
		const [roleBrief] = accountBrief.roleBriefs ?? [];

		if (accountBrief && userAccountContainer) {
			userAccountContainer.textContent = accountBrief.name;
		}

		if (accountBrief?.id && roleBrief?.id) {
			const accountId = accountBrief.id;

			await unassignUserRole(accountId, roleBrief?.id);
			const accountRoles = await getAccountRoles(accountId);

			for (const accountRole of accountRoles) {
				if (userRoles.includes(accountRole.name)) {
					await sendRolesApi(accountRole.id, accountId);
					await updateInviteStatus(userAdditionalInfo.id);

					localStorage.setItem(
						'userAccountData',
						JSON.stringify({
							accountName: accountBrief.name,
							userName: Liferay.ThemeDisplay.getUserName(),
						})
					);

					window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/${finalURL}`;
				}
			}
		}
	}
};

main();
