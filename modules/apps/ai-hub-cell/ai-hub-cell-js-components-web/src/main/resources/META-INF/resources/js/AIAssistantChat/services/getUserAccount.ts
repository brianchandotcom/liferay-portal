/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

async function getUserAccount(id: string) {
	const response = await fetch(
		`/o/headless-admin-user/v1.0/user-accounts/${id}`
	);

	if (!response.ok) {
		throw new Error('Failed to fetch user data.');
	}

	return response.json();
}

export {getUserAccount};
