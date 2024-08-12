/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AccountAccountGroupsPage} from '../pages/account-admin-web/AccountAccountGroupsPage';
import {AccountContactAddressPage} from '../pages/account-admin-web/AccountContactAddressPage';
import {AccountEntriesManagementPortletPage} from '../pages/account-admin-web/AccountEntriesManagementPortletPage';
import {AccountManagementWidgetPage} from '../pages/account-admin-web/AccountManagementWidgetPage';
import {AccountOrganizationsPage} from '../pages/account-admin-web/AccountOrganizationsPage';
import {AccountRolesPage} from '../pages/account-admin-web/AccountRolesPage';
import {AccountUsersPage} from '../pages/account-admin-web/AccountUsersPage';
import {AccountsPage} from '../pages/account-admin-web/AccountsPage';
import {EditAccountChannelDefaultsPage} from '../pages/account-admin-web/EditAccountChannelDefaultsPage';
import {EditAccountContactAddressPage} from '../pages/account-admin-web/EditAccountContactAddressPage';
import {EditAccountContactInformationPage} from '../pages/account-admin-web/EditAccountContactInformationPage';
import {EditAccountContactPage} from '../pages/account-admin-web/EditAccountContactPage';
import {EditAccountEmailAddressPage} from '../pages/account-admin-web/EditAccountEmailAddressPage';
import {EditAccountPage} from '../pages/account-admin-web/EditAccountPage';
import {EditAccountPhonePage} from '../pages/account-admin-web/EditAccountPhonePage';
import {EditAccountWebsitePage} from '../pages/account-admin-web/EditAccountWebsitePage';

const accountsPagesTest = test.extend<{
	accountAccountGroupsPage: AccountAccountGroupsPage;
	accountContactAddressPage: AccountContactAddressPage;
	accountEntriesManagementPortletPage: AccountEntriesManagementPortletPage;
	accountManagementWidgetPage: AccountManagementWidgetPage;
	accountOrganizationsPage: AccountOrganizationsPage;
	accountRolesPage: AccountRolesPage;
	accountUsersPage: AccountUsersPage;
	accountsPage: AccountsPage;
	editAccountChannelDefaultsPage: EditAccountChannelDefaultsPage;
	editAccountContactAddressPage: EditAccountContactAddressPage;
	editAccountContactInformationPage: EditAccountContactInformationPage;
	editAccountContactPage: EditAccountContactPage;
	editAccountEmailAddressPage: EditAccountEmailAddressPage;
	editAccountPage: EditAccountPage;
	editAccountPhonePage: EditAccountPhonePage;
	editAccountWebsitePage: EditAccountWebsitePage;
}>({
	accountAccountGroupsPage: async ({page}, use) => {
		await use(new AccountAccountGroupsPage(page));
	},
	accountContactAddressPage: async ({page}, use) => {
		await use(new AccountContactAddressPage(page));
	},
	accountEntriesManagementPortletPage: async ({page}, use) => {
		await use(new AccountEntriesManagementPortletPage(page));
	},
	accountManagementWidgetPage: async ({page}, use) => {
		await use(new AccountManagementWidgetPage(page));
	},
	accountOrganizationsPage: async ({page}, use) => {
		await use(new AccountOrganizationsPage(page));
	},
	accountRolesPage: async ({page}, use) => {
		await use(new AccountRolesPage(page));
	},
	accountUsersPage: async ({page}, use) => {
		await use(new AccountUsersPage(page));
	},
	accountsPage: async ({page}, use) => {
		await use(new AccountsPage(page));
	},
	editAccountChannelDefaultsPage: async ({page}, use) => {
		await use(new EditAccountChannelDefaultsPage(page));
	},
	editAccountContactAddressPage: async ({page}, use) => {
		await use(new EditAccountContactAddressPage(page));
	},
	editAccountContactInformationPage: async ({page}, use) => {
		await use(new EditAccountContactInformationPage(page));
	},
	editAccountContactPage: async ({page}, use) => {
		await use(new EditAccountContactPage(page));
	},
	editAccountEmailAddressPage: async ({page}, use) => {
		await use(new EditAccountEmailAddressPage(page));
	},
	editAccountPage: async ({page}, use) => {
		await use(new EditAccountPage(page));
	},
	editAccountPhonePage: async ({page}, use) => {
		await use(new EditAccountPhonePage(page));
	},
	editAccountWebsitePage: async ({page}, use) => {
		await use(new EditAccountWebsitePage(page));
	},
});

export {accountsPagesTest};
