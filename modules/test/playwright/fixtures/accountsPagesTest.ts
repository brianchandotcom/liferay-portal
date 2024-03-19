/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {AccountsPage} from '../pages/account-admin-web/AccountsPage';
import {EditAccountContactInformationPage} from '../pages/account-admin-web/EditAccountContactInformationPage';
import {EditAccountContactPage} from '../pages/account-admin-web/EditAccountContactPage';
import {EditAccountPage} from '../pages/account-admin-web/EditAccountPage';
import {EditAccountPhonePage} from '../pages/account-admin-web/EditAccountPhonePage';

const accountsPagesTest = test.extend<{
	accountsPage: AccountsPage;
	editAccountContactInformationPage: EditAccountContactInformationPage;
	editAccountContactPage: EditAccountContactPage;
	editAccountPage: EditAccountPage;
	editAccountPhonePage: EditAccountPhonePage;
}>({
	accountsPage: async ({page}, use) => {
		await use(new AccountsPage(page));
	},
	editAccountContactInformationPage: async ({page}, use) => {
		await use(new EditAccountContactInformationPage(page));
	},
	editAccountContactPage: async ({page}, use) => {
		await use(new EditAccountContactPage(page));
	},
	editAccountPage: async ({page}, use) => {
		await use(new EditAccountPage(page));
	},
	editAccountPhonePage: async ({page}, use) => {
		await use(new EditAccountPhonePage(page));
	},
});

export {accountsPagesTest};
