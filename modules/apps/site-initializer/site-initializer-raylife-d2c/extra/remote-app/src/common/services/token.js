/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../utils/liferay';
import {getRaylifeAuthentication} from './raylife-authentication';

const baseURL =
	Liferay.ThemeDisplay.getPortalURL() + Liferay.ThemeDisplay.getPathContext();

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
