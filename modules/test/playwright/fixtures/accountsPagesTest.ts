/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AccountAccountGroupsPage} from '../pages/account-admin-web/AccountAccountGroupsPage';
import {AccountAddressesPage} from '../pages/account-admin-web/AccountAddressesPage';
import {AccountContactAddressPage} from '../pages/account-admin-web/AccountContactAddressPage';
import {AccountDefaultAddressSelectorPage} from '../pages/account-admin-web/AccountDefaultAddressSelectorPage';
import {AccountEntriesManagementPortletPage} from '../pages/account-admin-web/AccountEntriesManagementPortletPage';
import {AccountManagementWidgetPage} from '../pages/account-admin-web/AccountManagementWidgetPage';
import {AccountOrganizationSelectorPage} from '../pages/account-admin-web/AccountOrganizationSelectorPage';
import {AccountOrganizationsPage} from '../pages/account-admin-web/AccountOrganizationsPage';
import {AccountPersonUserSelectorPage} from '../pages/account-admin-web/AccountPersonUserSelectorPage';
import {AccountRolesPage} from '../pages/account-admin-web/AccountRolesPage';
import {AccountUserSelectorPage} from '../pages/account-admin-web/AccountUserSelectorPage';
import {AccountUsersPage} from '../pages/account-admin-web/AccountUsersPage';
import {AccountsPage} from '../pages/account-admin-web/AccountsPage';
import {EditAccountAddressPage} from '../pages/account-admin-web/EditAccountAddressPage';
import {EditAccountChannelDefaultsPage} from '../pages/account-admin-web/EditAccountChannelDefaultsPage';
import {EditAccountContactAddressPage} from '../pages/account-admin-web/EditAccountContactAddressPage';
import {EditAccountContactInformationPage} from '../pages/account-admin-web/EditAccountContactInformationPage';
import {EditAccountContactPage} from '../pages/account-admin-web/EditAccountContactPage';
import {EditAccountEmailAddressPage} from '../pages/account-admin-web/EditAccountEmailAddressPage';
import {EditAccountPage} from '../pages/account-admin-web/EditAccountPage';
import {EditAccountPhonePage} from '../pages/account-admin-web/EditAccountPhonePage';
import {EditAccountWebsitePage} from '../pages/account-admin-web/EditAccountWebsitePage';
import {EmailDomainsInstanceSettingsPage} from '../pages/account-admin-web/EmailDomainsInstanceSettingsPage';

const accountsPagesTest = test.extend<{
	accountAccountGroupsPage: AccountAccountGroupsPage;
	accountAddressesPage: AccountAddressesPage;
	accountContactAddressPage: AccountContactAddressPage;
	accountDefaultAddressSelectorPage: AccountDefaultAddressSelectorPage;
	accountEntriesManagementPortletPage: AccountEntriesManagementPortletPage;
	accountManagementWidgetPage: AccountManagementWidgetPage;
	accountOrganizationSelectorPage: AccountOrganizationSelectorPage;
	accountOrganizationsPage: AccountOrganizationsPage;
	accountPersonUserSelectorPage: AccountPersonUserSelectorPage;
	accountRolesPage: AccountRolesPage;
	accountUserSelectorPage: AccountUserSelectorPage;
	accountUsersPage: AccountUsersPage;
	accountsPage: AccountsPage;
	editAccountAddressPage: EditAccountAddressPage;
	editAccountChannelDefaultsPage: EditAccountChannelDefaultsPage;
	editAccountContactAddressPage: EditAccountContactAddressPage;
	editAccountContactInformationPage: EditAccountContactInformationPage;
	editAccountContactPage: EditAccountContactPage;
	editAccountEmailAddressPage: EditAccountEmailAddressPage;
	editAccountPage: EditAccountPage;
	editAccountPhonePage: EditAccountPhonePage;
	editAccountWebsitePage: EditAccountWebsitePage;
	emailDomainsInstanceSettingsPage: EmailDomainsInstanceSettingsPage;
}>({
	accountAccountGroupsPage: async ({page}, use) => {
		await use(new AccountAccountGroupsPage(page));
	},
	accountAddressesPage: async ({page}, use) => {
		await use(new AccountAddressesPage(page));
	},
	accountContactAddressPage: async ({page}, use) => {
		await use(new AccountContactAddressPage(page));
	},
	accountDefaultAddressSelectorPage: async ({page}, use) => {
		await use(new AccountDefaultAddressSelectorPage(page));
	},
	accountEntriesManagementPortletPage: async ({page}, use) => {
		await use(new AccountEntriesManagementPortletPage(page));
	},
	accountManagementWidgetPage: async ({page}, use) => {
		await use(new AccountManagementWidgetPage(page));
	},
	accountOrganizationSelectorPage: async ({page}, use) => {
		await use(new AccountOrganizationSelectorPage(page));
	},
	accountOrganizationsPage: async ({page}, use) => {
		await use(new AccountOrganizationsPage(page));
	},
	accountPersonUserSelectorPage: async ({page}, use) => {
		await use(new AccountPersonUserSelectorPage(page));
	},
	accountRolesPage: async ({page}, use) => {
		await use(new AccountRolesPage(page));
	},
	accountUserSelectorPage: async ({page}, use) => {
		await use(new AccountUserSelectorPage(page));
	},
	accountUsersPage: async ({page}, use) => {
		await use(new AccountUsersPage(page));
	},
	accountsPage: async ({page}, use) => {
		await use(new AccountsPage(page));
	},
	editAccountAddressPage: async ({page}, use) => {
		await use(new EditAccountAddressPage(page));
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
	emailDomainsInstanceSettingsPage: async ({page}, use) => {
		await use(new EmailDomainsInstanceSettingsPage(page));
	},
});

export {accountsPagesTest};
