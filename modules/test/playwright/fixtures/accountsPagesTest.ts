/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AccountAccountGroupsPage} from '../pages/account-admin-web/AccountAccountGroupsPage';
import {AccountAddressesPage} from '../pages/account-admin-web/AccountAddressesPage';
import {AccountCategorySelectorPage} from '../pages/account-admin-web/AccountCategorySelectorPage';
import {AccountContactAddressPage} from '../pages/account-admin-web/AccountContactAddressPage';
import {AccountDefaultAddressSelectorPage} from '../pages/account-admin-web/AccountDefaultAddressSelectorPage';
import {AccountEntriesManagementPortletPage} from '../pages/account-admin-web/AccountEntriesManagementPortletPage';
import {AccountGroupAccountSelectorPage} from '../pages/account-admin-web/AccountGroupAccountSelectorPage';
import {AccountGroupAccountsPage} from '../pages/account-admin-web/AccountGroupAccountsPage';
import {AccountGroupsPage} from '../pages/account-admin-web/AccountGroupsPage';
import {AccountInstanceSettingsAccountAddressSubtypePage} from '../pages/account-admin-web/AccountInstanceSettingsAccountAddressSubtypePage';
import {AccountInstanceSettingsPage} from '../pages/account-admin-web/AccountInstanceSettingsPage';
import {AccountManagementWidgetPage} from '../pages/account-admin-web/AccountManagementWidgetPage';
import {AccountOrganizationSelectorPage} from '../pages/account-admin-web/AccountOrganizationSelectorPage';
import {AccountOrganizationsPage} from '../pages/account-admin-web/AccountOrganizationsPage';
import {AccountPersonUserSelectorPage} from '../pages/account-admin-web/AccountPersonUserSelectorPage';
import {AccountRoleSelectorPage} from '../pages/account-admin-web/AccountRoleSelectorPage';
import {AccountRolesPage} from '../pages/account-admin-web/AccountRolesPage';
import {AccountTagSelectorPage} from '../pages/account-admin-web/AccountTagSelectorPage';
import {AccountUserInvitePage} from '../pages/account-admin-web/AccountUserInvitePage';
import {AccountUserSelectorPage} from '../pages/account-admin-web/AccountUserSelectorPage';
import {AccountUsersAccountSelectorPage} from '../pages/account-admin-web/AccountUsersAccountSelectorPage';
import {AccountUsersPage} from '../pages/account-admin-web/AccountUsersPage';
import {AccountsPage} from '../pages/account-admin-web/AccountsPage';
import {EditAccountAddressPage} from '../pages/account-admin-web/EditAccountAddressPage';
import {EditAccountChannelDefaultsPage} from '../pages/account-admin-web/EditAccountChannelDefaultsPage';
import {EditAccountContactAddressPage} from '../pages/account-admin-web/EditAccountContactAddressPage';
import {EditAccountContactInformationPage} from '../pages/account-admin-web/EditAccountContactInformationPage';
import {EditAccountContactPage} from '../pages/account-admin-web/EditAccountContactPage';
import {EditAccountEmailAddressPage} from '../pages/account-admin-web/EditAccountEmailAddressPage';
import {EditAccountGroupPage} from '../pages/account-admin-web/EditAccountGroupPage';
import {EditAccountPage} from '../pages/account-admin-web/EditAccountPage';
import {EditAccountPhonePage} from '../pages/account-admin-web/EditAccountPhonePage';
import {EditAccountRolePage} from '../pages/account-admin-web/EditAccountRolePage';
import {EditAccountWebsitePage} from '../pages/account-admin-web/EditAccountWebsitePage';
import {EmailDomainsInstanceSettingsPage} from '../pages/account-admin-web/EmailDomainsInstanceSettingsPage';

const accountsPagesTest = test.extend<{
	accountAccountGroupsPage: AccountAccountGroupsPage;
	accountAddressesPage: AccountAddressesPage;
	accountCategorySelectorPage: AccountCategorySelectorPage;
	accountContactAddressPage: AccountContactAddressPage;
	accountDefaultAddressSelectorPage: AccountDefaultAddressSelectorPage;
	accountEntriesManagementPortletPage: AccountEntriesManagementPortletPage;
	accountGroupAccountSelectorPage: AccountGroupAccountSelectorPage;
	accountGroupAccountsPage: AccountGroupAccountsPage;
	accountGroupsPage: AccountGroupsPage;
	accountInstanceSettingsAccountAddressSubtypePage: AccountInstanceSettingsAccountAddressSubtypePage;
	accountInstanceSettingsPage: AccountInstanceSettingsPage;
	accountManagementWidgetPage: AccountManagementWidgetPage;
	accountOrganizationSelectorPage: AccountOrganizationSelectorPage;
	accountOrganizationsPage: AccountOrganizationsPage;
	accountPersonUserSelectorPage: AccountPersonUserSelectorPage;
	accountRoleSelectorPage: AccountRoleSelectorPage;
	accountRolesPage: AccountRolesPage;
	accountTagSelectorPage: AccountTagSelectorPage;
	accountUserInvitePage: AccountUserInvitePage;
	accountUserSelectorPage: AccountUserSelectorPage;
	accountUsersAccountSelectorPage: AccountUsersAccountSelectorPage;
	accountUsersPage: AccountUsersPage;
	accountsPage: AccountsPage;
	editAccountAddressPage: EditAccountAddressPage;
	editAccountChannelDefaultsPage: EditAccountChannelDefaultsPage;
	editAccountContactAddressPage: EditAccountContactAddressPage;
	editAccountContactInformationPage: EditAccountContactInformationPage;
	editAccountContactPage: EditAccountContactPage;
	editAccountEmailAddressPage: EditAccountEmailAddressPage;
	editAccountGroupPage: EditAccountGroupPage;
	editAccountPage: EditAccountPage;
	editAccountPhonePage: EditAccountPhonePage;
	editAccountRolePage: EditAccountRolePage;
	editAccountWebsitePage: EditAccountWebsitePage;
	emailDomainsInstanceSettingsPage: EmailDomainsInstanceSettingsPage;
}>({
	accountAccountGroupsPage: async ({page}, use) => {
		await use(new AccountAccountGroupsPage(page));
	},
	accountAddressesPage: async ({page}, use) => {
		await use(new AccountAddressesPage(page));
	},
	accountCategorySelectorPage: async ({page}, use) => {
		await use(new AccountCategorySelectorPage(page));
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
	accountGroupAccountSelectorPage: async ({page}, use) => {
		await use(new AccountGroupAccountSelectorPage(page));
	},
	accountGroupAccountsPage: async ({page}, use) => {
		await use(new AccountGroupAccountsPage(page));
	},
	accountGroupsPage: async ({page}, use) => {
		await use(new AccountGroupsPage(page));
	},
	accountInstanceSettingsAccountAddressSubtypePage: async ({page}, use) => {
		await use(new AccountInstanceSettingsAccountAddressSubtypePage(page));
	},
	accountInstanceSettingsPage: async ({page}, use) => {
		await use(new AccountInstanceSettingsPage(page));
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
	accountRoleSelectorPage: async ({page}, use) => {
		await use(new AccountRoleSelectorPage(page));
	},
	accountRolesPage: async ({page}, use) => {
		await use(new AccountRolesPage(page));
	},
	accountTagSelectorPage: async ({page}, use) => {
		await use(new AccountTagSelectorPage(page));
	},
	accountUserInvitePage: async ({page}, use) => {
		await use(new AccountUserInvitePage(page));
	},
	accountUserSelectorPage: async ({page}, use) => {
		await use(new AccountUserSelectorPage(page));
	},
	accountUsersAccountSelectorPage: async ({page}, use) => {
		await use(new AccountUsersAccountSelectorPage(page));
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
	editAccountGroupPage: async ({page}, use) => {
		await use(new EditAccountGroupPage(page));
	},
	editAccountPage: async ({page}, use) => {
		await use(new EditAccountPage(page));
	},
	editAccountPhonePage: async ({page}, use) => {
		await use(new EditAccountPhonePage(page));
	},
	editAccountRolePage: async ({page}, use) => {
		await use(new EditAccountRolePage(page));
	},
	editAccountWebsitePage: async ({page}, use) => {
		await use(new EditAccountWebsitePage(page));
	},
	emailDomainsInstanceSettingsPage: async ({page}, use) => {
		await use(new EmailDomainsInstanceSettingsPage(page));
	},
});

export {accountsPagesTest};
