/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../../../common/services/ApiHelper';

async function getUserAccount(id: string) {
	return ApiHelper.get(`/o/headless-admin-user/v1.0/user-accounts/${id}`)
		.then((response) => {
			return response.data;
		})
		.catch(() => {
			throw new Error('Failed to fetch user data.');
		});
}

export {getUserAccount};
