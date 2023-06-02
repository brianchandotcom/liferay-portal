/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const baseURL = Liferay.ThemeDisplay.getPortalURL();
const userSessionId = Liferay.ThemeDisplay.getUserId();
let userRoles = [];
let userAdditionalInfosId = '';
let sendingRole = false;
const userAccountData = {
	accountName: '',
	userName: Liferay.ThemeDisplay.getUserName(),
};

const getUserAccountsById = async () => {
	try {
		const response = await fetch(
			`${baseURL}/o/c/useradditionalinfos/?filter=r_userToUserAddInfo_userId eq '${userSessionId}'`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (response.ok) {
			const userAdditionalInfos = await response.json();
			for (const userInfo of userAdditionalInfos?.items || []) {
				if (userInfo.acceptInviteStatus === false) {
					userAdditionalInfosId = userInfo.id;
					getMyUserAccount(userInfo);
					const arrayRoles = userInfo.roles.split('/');
					userRoles = arrayRoles.filter((role) => role !== '');
				}
			}
		}
		else {
			console.error('Failed to fetch user data:', response.status);
		}
	}
	catch (error) {
		console.error('An error occurred:', error);
	}
};

const getMyUserAccount = async (userAdditional) => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/my-user-account`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (response.ok) {
			let accountId;
			let roleId;
			const userAccount = document.querySelector('#user-account b');
			const myUser = await response.json();
			myUser?.accountBriefs?.map((userAccountBriefs) => {
				userAccount.textContent = userAccountBriefs?.name;
				userAccountData.accountName = userAccountBriefs?.name;

				if (
					userAdditional?.r_accountEntryToUserAdditionalInfo_accountEntryId ===
					userAccountBriefs.id
				) {
					accountId = userAccountBriefs.id;
					userAccountBriefs.roleBriefs?.map((role) => {
						roleId = role.id;
					});
				}
			});

			if (accountId && roleId && myUser.id) {
				deleteRoleFromUser(accountId, roleId, myUser.id);
			}
		}
		else {
			console.error('Failed to fetch user data:', response.status);
		}
	}
	catch (error) {
		console.error('An error occurred:', error);
	}
};

const deleteRoleFromUser = async (accountId, roleId, myUserId) => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${myUserId}`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'DELETE',
			}
		);

		if (response.ok) {
			getRolesId(accountId, myUserId);
		}
		else {
			console.error('Failed to fetch user data:', response.status);
		}
	}
	catch (error) {
		console.error('An error occurred:', error);
	}
};

const getRolesId = async (accountId, myUserId) => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (response.ok) {
			const userAccountRole = await response.json();
			userAccountRole?.items?.map((accountRoles) => {
				if (userRoles.includes(accountRoles.name)) {
					sendingRole = sendRolesApi(
						accountRoles.id,
						accountId,
						myUserId
					);
				}
			});
			if (sendingRole) {
				updateInviteStatus();
			}
		}
		else {
			console.error('Failed to fetch user data:', response);
		}
	}
	catch (error) {
		console.error('An error occurred:', error);
	}
};

const sendRolesApi = async (roleId, accountId, userId) => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`,
			{
				headers: {
					'Content-Type': 'application/json',
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			}
		);
		if (response.ok) {
			return true;
		}
		else {
			console.error('Failed to fetch user data:', response);

			return false;
		}
	}
	catch (error) {
		console.error('An error occurred:', error);

		return false;
	}
};

const updateInviteStatus = async () => {
	try {
		const response = await fetch(
			`${baseURL}/o/c/useradditionalinfos/${userAdditionalInfosId}`,
			{
				body: JSON.stringify({
					acceptInviteStatus: true,
				}),
				headers: {
					'Content-Type': 'application/json',
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'PATCH',
			}
		);
		if (response.ok) {
			const userAccountJsonData = JSON.stringify(userAccountData);
			localStorage.setItem('userAccountData', userAccountJsonData);
			window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/dashboard`;
		}
		else {
			console.error('Failed to fetch user data:', response);
		}
	}
	catch (error) {
		console.error('An error occurred:', error);
	}
};

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

getUserAccountsById();
