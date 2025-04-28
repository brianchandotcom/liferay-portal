/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {HEADERS_ALL_LANGUAGES} from '../../../services/api';
import {IPermissionItem} from '../../components/forms/PermissionsTable';

async function putPermissions(
	permissionsApiUrl: string,
	permissions: IPermissionItem[]
): Promise<IPermissionItem[]> {
	const response = await fetch(permissionsApiUrl, {
		body: JSON.stringify(permissions),
		headers: HEADERS_ALL_LANGUAGES,
		method: 'PUT',
	});

	if (response.ok) {
		const json = await response.json();

		return json.items;
	}
	else {
		throw new Error(
			`PUT request failed to update permissions at ${permissionsApiUrl} using the following provided data: ${JSON.stringify(permissions)}`
		);
	}
}

async function getPermissions(
	permissionsApiUrl: string
): Promise<IPermissionItem[]> {
	const response = await fetch(permissionsApiUrl, {
		headers: HEADERS_ALL_LANGUAGES,
		method: 'GET',
	});

	if (response.ok) {
		const json = await response.json();

		return json.items;
	}
	else {
		throw new Error(
			`GET request failed to fetch the entity's permissions from ${permissionsApiUrl}`
		);
	}
}

export default {
	getPermissions,
	putPermissions,
};
