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

import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-admin-user';

export async function createAccount(
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

	const accountPayload = {
		name: `${firstName} ${lastName}`,
		status: 0,
		type: 'business',
	};

	await axios.post(
		`${DeliveryAPI}/v1.0/user-accounts?captchaText=${captcha}`,
		userPayload
	);

	return axios.post(`${DeliveryAPI}/v1.0/accounts`, accountPayload);
}
