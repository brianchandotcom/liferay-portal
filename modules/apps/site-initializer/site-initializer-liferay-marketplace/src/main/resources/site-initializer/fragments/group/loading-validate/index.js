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

const getUserAccountsById = async () => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/user-accounts/${userSessionId}`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'GET',
			}
		);

		if (response.ok) {
			const user = [];
			const userData = await response.json();
			user.push(userData);
			setAccountInUser(user);
		} else {
			console.error('Failed to fetch user data:', response.status);
		}
	} catch (error) {
		console.error('An error occurred:', error);
	}
};

const getRolesId = async () => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/accounts/'${userSessionId}'/account-roles`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'GET',
			}
		);

		if (response.ok) {
			const roleId = '';
			const userAccount = await response.json();
			roleId = userAccount.id;
		} else {
			console.error('Failed to fetch user data:', response.status);
		}
	} catch (error) {
		console.error('An error occurred:', error);
	}
};

getUserAccountsById();

const setAccountInUser = (userLog) => {
	let externalReferenceCode = '';
	let emailAddress = '';
	let accountId = '';

	const userAccount = document.querySelector('#user-account b');
	userLog?.map((user) => {
		emailAddress = user.emailAddress;
		user?.accountBriefs?.map((userAccountBriefs) => {
			externalReferenceCode = userAccountBriefs.externalReferenceCode;
			accountId = userAccountBriefs.id;
			userAccount.textContent = userAccountBriefs?.name;
		});
	});

	postAccount(accountId, externalReferenceCode, emailAddress);
};

const postAccount = async (userId, erc, email) => {
	try {
		const response = await fetch(
			`${baseURL}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${erc}/account-roles/${userId}/user-accounts/by-email-address/${email}`,
			{
				headers: {
					'content-type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			}
		);

		if (response.ok) {
			await response.json();
		} else {
			console.error('Failed to fetch user data:', response.status);
		}
	} catch (error) {
		console.error('An error occurred:', error);
	}
};
