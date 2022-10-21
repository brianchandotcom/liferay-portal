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

const DeliveryAPI = 'o/headless-admin-user';

const fetchHeadless = async (url, options) => {
	const response = await fetch(`${window.location.origin}/${url}`, {
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
