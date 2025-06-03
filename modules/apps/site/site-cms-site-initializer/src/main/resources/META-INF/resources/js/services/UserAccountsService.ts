/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UserAccount} from '../types/UserAccount';
import ApiHelper from './ApiHelper';

async function getUserAccounts(): Promise<UserAccount[]> {
	const {data, error} = await ApiHelper.get<{items: UserAccount[]}>(
		'/o/headless-admin-user/v1.0/user-accounts'
	);

	if (data) {
		return data.items;
	}

	throw new Error(error);
}

export default {
	getUserAccounts,
};
