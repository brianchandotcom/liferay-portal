/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DeliveryAPI = 'o/headless-admin-user';
const baseURL = window.location.origin + Liferay.ThemeDisplay.getPathContext();

const fetchHeadless = async (url, options) => {
	const response = await fetch(`${baseURL}/${url}`, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (!response.ok) {
		const error = new Error('An error occurred while fetching the data.');

		error.info = await response.json();
		error.status = response.status;
		throw error;
	}

	const data = await response.json();

	return data;
};

export function createUserAccount(
	firstName,
	lastName,
	emailAddress,
	password,
	captcha
) {
	const userPayload = {
		alternateName: `${emailAddress.split('@')[0]}`,
		emailAddress,
		familyName: lastName,
		givenName: firstName,
		password,
	};

	return fetchHeadless(
		`${DeliveryAPI}/v1.0/user-accounts?captchaText=${captcha}`,
		{
			body: JSON.stringify(userPayload),
			method: 'POST',
		}
	);
}

export function createAccount(name) {
	const accountPayload = {
		name,
		status: 0,
		type: 'business',
	};

	return fetchHeadless(`${DeliveryAPI}/v1.0/accounts`, {
		body: JSON.stringify(accountPayload),
		method: 'POST',
	});
}
