/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {AccountsPage} from '../../pages/account-admin-web/AccountsPage';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	accountsPagesTest,
	applicationsMenuPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

async function postRoleWithAccountAdminPermissions(
	apiHelpers: any,
	companyId: string
) {
	return await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		rolePermissions: [
			{
				actionIds: [
					'ASSIGN_USERS',
					'MANAGE_ADDRESSES',
					'MANAGE_CHANNEL_DEFAULTS',
					'MANAGE_ORGANIZATIONS',
					'MANAGE_USERS',
					'UPDATE',
					'VIEW',
					'VIEW_ACCOUNT_ROLES',
					'VIEW_ADDRESSES',
					'VIEW_CHANNEL_DEFAULTS',
					'VIEW_ORGANIZATIONS',
					'VIEW_USERS',
				],
				primaryKey: '0',
				resourceName: 'com.liferay.account.model.AccountEntry',
				scope: 3,
			},
			{
				actionIds: ['VIEW'],
				primaryKey: '0',
				resourceName: 'com.liferay.account.model.AccountRole',
				scope: 3,
			},
			{
				actionIds: ['VIEW'],
				primaryKey: companyId,
				resourceName: 'com.liferay.commerce.model.CommerceOrderType',
				scope: 1,
			},
			{
				actionIds: [
					'ADD_COMMERCE_ORDER',
					'APPROVE_OPEN_COMMERCE_ORDERS',
					'CHECKOUT_OPEN_COMMERCE_ORDERS',
					'DELETE_COMMERCE_ORDERS',
					'MANAGE_COMMERCE_ORDERS',
					'MANAGE_COMMERCE_ORDER_DELIVERY_TERMS',
					'MANAGE_COMMERCE_ORDER_PAYMENT_METHODS',
					'MANAGE_COMMERCE_ORDER_PAYMENT_STATUSES',
					'MANAGE_COMMERCE_ORDER_PAYMENT_TERMS',
					'MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS',
					'VIEW_BILLING_ADDRESS',
					'VIEW_COMMERCE_ORDERS',
					'VIEW_OPEN_COMMERCE_ORDERS',
				],
				primaryKey: '0',
				resourceName: 'com.liferay.commerce.order',
				scope: 3,
			},
		],
		roleType: 'account',
	});
}

test.describe('Test for Organization Account visibility depending on Permissions', () => {
	test('LPD-28116 Update Organizations permission visibility', async ({
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
						'MANAGE_USERS',
						'UPDATE',
						'UPDATE_ORGANIZATIONS',
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

	test('LPD-28116 No Update or Manage Organizations permission', async ({
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

		await expect(accountsPage.newButton).toHaveCount(0);
	});
});

test('LPD-30009 Account admin can unassign organization from account', async ({
	accountManagementWidgetPage,
	accountOrganizationsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[userAccount.alternateName] = {
		name: userAccount.givenName,
		password: 'test',
		surname: userAccount.familyName,
	};

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const roleWithAccountAdminPermissions =
		await postRoleWithAccountAdminPermissions(apiHelpers, companyId);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Account' + getRandomInt(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[userAccount.emailAddress]
	);

	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const role = rolesResponse?.items?.filter(
		(role) => role.name === roleWithAccountAdminPermissions.name
	);

	await apiHelpers.headlessAdminUser.assignUserToAccountRole(
		account.id,
		role[0].id,
		userAccount.id
	);

	const organization = await apiHelpers.headlessAdminUser.postOrganization();

	await apiHelpers.headlessAdminUser.assignAccountToOrganization(
		account.id,
		organization.id
	);

	await performLogout(page);
	await performLogin(page, userAccount.alternateName);

	try {
		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await (
			await accountManagementWidgetPage.accountsTableRowLink(account.name)
		).click();
		await accountManagementWidgetPage.organizationsTab.click();

		await accountOrganizationsPage.selectAllItemsCheckbox.click();
		await accountOrganizationsPage.removeButton.click();

		await accountManagementWidgetPage.searchInput.waitFor();

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);
		await expect(
			accountOrganizationsPage.organizationName(organization.name)
		).toHaveCount(0);
	}
	finally {
		await performLogout(page);
		await performLogin(page, 'test');
	}
});

test('LPD-30004 Account admin can unassign organizations in bulk', async ({
	accountManagementWidgetPage,
	accountOrganizationsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[userAccount.alternateName] = {
		name: userAccount.givenName,
		password: 'test',
		surname: userAccount.familyName,
	};

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const roleWithAccountAdminPermissions =
		await postRoleWithAccountAdminPermissions(apiHelpers, companyId);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Account' + getRandomInt(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[userAccount.emailAddress]
	);

	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const role = rolesResponse?.items?.filter(
		(role) => role.name === roleWithAccountAdminPermissions.name
	);

	await apiHelpers.headlessAdminUser.assignUserToAccountRole(
		account.id,
		role[0].id,
		userAccount.id
	);

	const organization1 = await apiHelpers.headlessAdminUser.postOrganization();
	const organization2 = await apiHelpers.headlessAdminUser.postOrganization();
	const organization3 = await apiHelpers.headlessAdminUser.postOrganization();
	const organization4 = await apiHelpers.headlessAdminUser.postOrganization();

	await apiHelpers.headlessAdminUser.assignAccountToOrganization(
		account.id,
		organization1.id
	);
	await apiHelpers.headlessAdminUser.assignAccountToOrganization(
		account.id,
		organization2.id
	);
	await apiHelpers.headlessAdminUser.assignAccountToOrganization(
		account.id,
		organization3.id
	);
	await apiHelpers.headlessAdminUser.assignAccountToOrganization(
		account.id,
		organization4.id
	);

	await performLogout(page);
	await performLogin(page, userAccount.alternateName);

	try {
		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await (
			await accountManagementWidgetPage.accountsTableRowLink(account.name)
		).click();
		await accountManagementWidgetPage.organizationsTab.click();

		await accountOrganizationsPage.selectAllItemsCheckbox.click();
		await accountOrganizationsPage.removeButton.click();

		await accountManagementWidgetPage.searchInput.waitFor();

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(
			accountOrganizationsPage.organizationName(organization1.name)
		).toHaveCount(0);
		await expect(
			accountOrganizationsPage.organizationName(organization2.name)
		).toHaveCount(0);
		await expect(
			accountOrganizationsPage.organizationName(organization3.name)
		).toHaveCount(0);
		await expect(
			accountOrganizationsPage.organizationName(organization4.name)
		).toHaveCount(0);
	}
	finally {
		await performLogout(page);
		await performLogin(page, 'test');
	}
});
