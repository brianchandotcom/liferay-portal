/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {AccountsPage} from '../../pages/account-admin-web/AccountsPage';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	accountsPagesTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test.describe('Test for Organization Account visibility depending on Permissions', () => {
	test('LPD-28116 Edit Organizations permission visibility', async ({
		accountsPage,
		apiHelpers,
		context,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization1.id,
			user.emailAddress
		);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: [
						'EDIT_ORGANIZATIONS',
						'MANAGE_USERS',
						'UPDATE',
						'VIEW',
						'VIEW_ORGANIZATIONS',
					],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['MANAGE_AVAILABLE_ACCOUNTS'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'organization',
		});

		await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
			role.id,
			user.id,
			organization1.id
		);

		const account = await apiHelpers.headlessAdminUser.postAccount();
		apiHelpers.data.push({id: account.id, type: 'account'});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization1.id
		);

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				`${user.alternateName}`
			)
		).click();

		const pagePromise = context.waitForEvent('page');

		await usersAndOrganizationsPage.impersonateUserMenuItem.click();

		const newPage = await pagePromise;
		accountsPage = new AccountsPage(newPage);

		await accountsPage.goto();
		await (await accountsPage.accountsTableRowLink(account.name)).click();
		await accountsPage.organizationsTab.click();
		await accountsPage.newButton.click();

		await expect(
			accountsPage.organizationAssignmentFrame.getByText(
				organization2.name,
				{exact: true}
			)
		).toHaveCount(0);
	});

	test('LPD-28116 Manage Organizations Permission visibility', async ({
		accountsPage,
		apiHelpers,
		context,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization1.id,
			user.emailAddress
		);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			roleType: 'organization',
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: [
						'MANAGE_ORGANIZATIONS',
						'MANAGE_USERS',
						'UPDATE',
						'VIEW',
						'VIEW_ORGANIZATIONS',
					],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['MANAGE_AVAILABLE_ACCOUNTS'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
			role.id,
			user.id,
			organization1.id
		);

		const account = await apiHelpers.headlessAdminUser.postAccount();
		apiHelpers.data.push({id: account.id, type: 'account'});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization1.id
		);

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				`${user.alternateName}`
			)
		).click();

		const pagePromise = context.waitForEvent('page');

		await usersAndOrganizationsPage.impersonateUserMenuItem.click();

		const newPage = await pagePromise;
		accountsPage = new AccountsPage(newPage);

		await accountsPage.goto();
		await (await accountsPage.accountsTableRowLink(account.name)).click();
		await accountsPage.organizationsTab.click();
		await accountsPage.newButton.click();

		await expect(
			accountsPage.organizationAssignmentFrame.getByText(
				organization1.name,
				{exact: true}
			)
		).toBeVisible();
		await expect(
			accountsPage.organizationAssignmentFrame.getByText(
				organization2.name,
				{exact: true}
			)
		).toBeVisible();
	});

	test('LPD-28116 No Edit or Manage Organizations permission', async ({
		accountsPage,
		apiHelpers,
		context,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization1.id,
			user.emailAddress
		);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			roleType: 'organization',
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: [
						'MANAGE_USERS',
						'UPDATE',
						'VIEW',
						'VIEW_ORGANIZATIONS',
					],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['MANAGE_AVAILABLE_ACCOUNTS'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
			role.id,
			user.id,
			organization1.id
		);

		const account = await apiHelpers.headlessAdminUser.postAccount();
		apiHelpers.data.push({id: account.id, type: 'account'});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization1.id
		);

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				`${user.alternateName}`
			)
		).click();

		const pagePromise = context.waitForEvent('page');

		await usersAndOrganizationsPage.impersonateUserMenuItem.click();

		const newPage = await pagePromise;
		accountsPage = new AccountsPage(newPage);

		await accountsPage.goto();
		await (await accountsPage.accountsTableRowLink(account.name)).click();
		await accountsPage.organizationsTab.click();

		await expect(accountsPage.newButton).toHaveCount(0);
	});
});
