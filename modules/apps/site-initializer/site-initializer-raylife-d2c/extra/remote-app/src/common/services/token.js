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

import {getRaylifeAuthentication} from './raylife-authentication';

const baseURL = Liferay.ThemeDisplay.getPortalURL();

export async function getGuestPermissionToken() {
	const authentication = await getRaylifeAuthentication();

	const options = {
		body: new URLSearchParams({
			client_id: authentication?.clientId || '',
			client_secret: authentication?.clientSecret || '',
			grant_type: 'client_credentials',
		}),
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		method: 'POST',
	};

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	return fetch(`${baseURL}/o/oauth2/token`, options)
		.then((response) => response.json())
		.catch((error) => console.error(error));
}
