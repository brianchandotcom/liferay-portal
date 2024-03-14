/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {AccountsPage} from '../pages/account-admin-web/AccountsPage';
import {EditAccountPage} from '../pages/account-admin-web/EditAccountPage';

const accountsPagesTest = test.extend<{
	accountsPage: AccountsPage;
	editAccountPage: EditAccountPage;
}>({
	accountsPage: async ({page}, use) => {
		await use(new AccountsPage(page));
	},
	editAccountPage: async ({page}, use) => {
		await use(new EditAccountPage(page));
	},
});

export {accountsPagesTest};
