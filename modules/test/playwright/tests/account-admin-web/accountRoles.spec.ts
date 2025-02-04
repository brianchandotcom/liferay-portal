/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {rolesPagesTest} from '../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	accountsPagesTest,
	dataApiHelpersTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test('LPD-47225 Can create an owned account role', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
}) => {
	const roleName = getRandomString();

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roleName});
	await editAccountRolePage.backButton.click();

	await expect(await accountRolesPage.roleName(roleName)).toBeVisible();
	await expect(async () => {
		await expect(
			await accountRolesPage.rolesTable.rowCheckbox(roleName)
		).toBeEnabled();
		await expect(
			(await accountRolesPage.rolesTable.row(1, roleName)).row.getByText(
				'Owned'
			)
		).toBeVisible();
	}).toPass({timeout: 5000});
});

test('LPD-47225 An owned account role is not shared between accounts', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
}) => {
	const roleName = getRandomString();

	const account1 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account2.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account1.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roleName});
	await editAccountRolePage.backButton.click();

	await expect(
		await accountRolesPage.roleName('Account Administrator')
	).toBeVisible();
	await expect(await accountRolesPage.roleName(roleName)).toBeVisible();

	await accountsPage.goto();

	await accountsPage.accountNameLink(account2.name).click();
	await accountsPage.accountRolesTab.click();

	await expect(
		await accountRolesPage.roleName('Account Administrator')
	).toBeVisible();
	await expect(await accountRolesPage.roleName(roleName)).toHaveCount(0);
});

test('LPD-47225 A shared account role is visible in all the accounts', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
}) => {
	const account1 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account2.id, type: 'account'});

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		roleType: 'account',
	});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account1.name).click();
	await accountsPage.accountRolesTab.click();

	await expect(
		await accountRolesPage.roleName('Account Administrator')
	).toBeVisible();
	await expect(await accountRolesPage.roleName(role.name)).toBeVisible();

	await accountsPage.goto();

	await accountsPage.accountNameLink(account2.name).click();
	await accountsPage.accountRolesTab.click();

	await expect(
		await accountRolesPage.roleName('Account Administrator')
	).toBeVisible();
	await expect(await accountRolesPage.roleName(role.name)).toBeVisible();
});

test('LPD-47225 Can assign / unassing a shared and an owned role to an account user', async ({
	accountRoleSelectorPage,
	accountRolesPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
	page,
}) => {
	const roles = ['Account Administrator', getRandomString()];

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user.emailAddress]
	);

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roles[1]});
	await editAccountRolePage.backButton.click();

	await accountsPage.usersTab.click();
	await (await accountUsersPage.usersTable.rowActions(user.name)).click();
	await accountUsersPage.assignRolesMenuItem.click();

	await accountRoleSelectorPage.selectRoles(roles);

	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[0])
	).toBeVisible();
	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[1])
	).toBeVisible();

	await page.reload();

	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[0])
	).toBeVisible();
	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[1])
	).toBeVisible();

	await (await accountUsersPage.usersTable.rowActions(user.name)).click();
	await accountUsersPage.assignRolesMenuItem.click();

	await accountRoleSelectorPage.selectRoles(roles, false);

	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[0])
	).toHaveCount(0);
	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[1])
	).toHaveCount(0);

	await page.reload();

	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[0])
	).toHaveCount(0);
	await expect(
		(await accountUsersPage.usersTable.firstRow()).getByText(roles[1])
	).toHaveCount(0);
});

test('LPD-47225 Can assign / unassing a shared and an owned role to an account user from the role', async ({
	accountRolesPage,
	accountUserSelectorPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const roles = ['Account Administrator', getRandomString()];

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user.emailAddress]
	);

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roles[1]});
	await editAccountRolePage.backButton.click();
	await accountsPage.accountRolesTab.click();

	for (const role of roles) {
		await expect(async () => {
			await expect(accountRolesPage.rolesTable.cell(role)).toBeVisible();

			await (await accountRolesPage.rolesTable.rowActions(role)).click();

			await expect(accountRolesPage.assignUsersButton).toBeVisible({
				timeout: 100,
			});
		}).toPass();

		await accountRolesPage.assignUsersButton.click();

		await expect(accountRolesPage.roleNameHeading(role)).toBeVisible();

		await accountRolesPage.assignUsersTable.newButton.click();

		await expect(
			accountUserSelectorPage.usersTable.searchInput
		).toBeEditable();

		await (
			await accountUserSelectorPage.usersTable.rowCheckbox(user.name)
		).check();

		await accountUserSelectorPage.assignButton.click();

		await waitForAlert(page);

		await expect(
			accountRolesPage.assignUsersTable.cell(user.name)
		).toBeVisible();

		await page.reload();

		await expect(
			accountRolesPage.assignUsersTable.cell(user.name)
		).toBeVisible();

		await (
			await accountRolesPage.assignUsersTable.rowCheckbox(user.name)
		).check();

		await accountRolesPage.removeButton.click();

		await expect(
			accountRolesPage.assignUsersTable.cell(user.name)
		).toHaveCount(0);

		await accountRolesPage.backButton.click();
	}
});

test('LPD-47225 The account role is also visible on the user page', async ({
	accountRoleSelectorPage,
	accountRolesPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
	editUserPage,
	usersAndOrganizationsPage,
}) => {
	const roles = ['Account Administrator', getRandomString()];

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user.emailAddress]
	);

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roles[1]});
	await editAccountRolePage.backButton.click();

	await accountsPage.usersTab.click();
	await (await accountUsersPage.usersTable.rowActions(user.name)).click();
	await accountUsersPage.assignRolesMenuItem.click();

	await accountRoleSelectorPage.selectRoles(roles);

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink(user.alternateName)
	).click();

	await editUserPage.membershipsLink.click();

	await expect(
		(await editUserPage.membershipsAccountsTableRow(0, account.name, true))
			.row
	).toBeVisible();
	await expect(
		(await editUserPage.membershipsAccountsTableRow(0, account.name, true))
			.row
	).toContainText(roles[0]);
	await expect(
		(await editUserPage.membershipsAccountsTableRow(0, account.name, true))
			.row
	).toContainText(roles[1]);
});

test('LPS-47225 Can search an account role', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
}) => {
	const roles = ['Account Administrator', getRandomString()];

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roles[1]});
	await editAccountRolePage.backButton.click();

	await accountRolesPage.rolesTable.search(getRandomString());

	await expect(accountRolesPage.rolesTable.cell(roles[0])).toHaveCount(0);
	await expect(accountsPage.accountsTable.cell(roles[1])).toHaveCount(0);

	await accountsPage.accountsTable.search(roles[0]);

	await expect(accountsPage.accountsTable.cell(roles[0])).toBeVisible();
	await expect(accountsPage.accountsTable.cell(roles[1])).toHaveCount(0);

	await accountsPage.accountsTable.search(roles[1]);

	await expect(accountsPage.accountsTable.cell(roles[0])).toHaveCount(0);
	await expect(accountsPage.accountsTable.cell(roles[1])).toBeVisible();

	await accountsPage.accountsTable.search('');

	await expect(accountsPage.accountsTable.cell(roles[0])).toBeVisible();
	await expect(accountsPage.accountsTable.cell(roles[1])).toBeVisible();
	await expect(accountsPage.accountsTable.cell(roles[2])).toBeVisible();
});

test('LPS-47225 Can search a user assigning account role', async ({
	accountRolesPage,
	accountUserSelectorPage,
	accountsPage,
	apiHelpers,
}) => {
	const roleName = 'Account Administrator';

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user3 = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user1.emailAddress, user2.emailAddress]
	);

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();

	await expect(async () => {
		await expect(accountRolesPage.rolesTable.cell(roleName)).toBeVisible();
	}).toPass();

	await (await accountRolesPage.rolesTable.rowActions(roleName)).click();
	await accountRolesPage.assignUsersButton.click();

	await expect(accountRolesPage.roleNameHeading(roleName)).toBeVisible();

	await accountRolesPage.assignUsersTable.newButton.click();

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(getRandomString());

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(user1.name);

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(user2.name);

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(user3.name);

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search('');

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user3.name)
	).toHaveCount(0);
});

test('LPS-47225 The default account roles are present in Roles Admin', async ({
	rolesPage,
}) => {
	await rolesPage.goto();

	await rolesPage.accountRolesLink.click();
	await rolesPage.rolesTable.changeView('Table');

	await expect(
		rolesPage.rolesTable.cell('Account Administrator')
	).toBeVisible();
	await expect(
		await rolesPage.rolesTable.rowCheckbox('Account Administrator')
	).toBeDisabled();
	await expect(rolesPage.rolesTable.cell('Account Member')).toBeVisible();
	await expect(
		await rolesPage.rolesTable.rowCheckbox('Account Member')
	).toBeDisabled();
});

test('LPS-47225 Group scope permissions can be defined for owned account roles', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
	page,
	roleDefinePermissionsPage,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const roleName = getRandomString();

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roleName});
	await editAccountRolePage.defineGroupScopePermissionsLink.click();
	await roleDefinePermissionsPage.menuItem('Site and Asset Library').click();
	await roleDefinePermissionsPage.menuItem('Applications').click();
	await roleDefinePermissionsPage.menuItem('Account Management').click();
	await roleDefinePermissionsPage.permissionCheckbox('Add to Page').check();
	await roleDefinePermissionsPage.permissionCheckbox('Configuration').check();
	await roleDefinePermissionsPage.saveButton.click();

	await waitForAlert(page, 'The role permissions were updated');

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await roleDefinePermissionsPage
		.resourceRemoveLink('Account Management: Add to Page')
		.click();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await page.reload();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();
});

test('LPS-47225 Group scope permissions can be defined for account roles from role pages', async ({
	apiHelpers,
	editAccountRolePage,
	page,
	roleDefinePermissionsPage,
	rolesPage,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		roleType: 'account',
	});

	await rolesPage.goto();

	await rolesPage.accountRolesLink.click();
	await rolesPage.rolesTable.changeView('Table');

	await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();

	await (await rolesPage.rolesTable.cellLink(role.name)).click();
	await editAccountRolePage.defineGroupScopePermissionsLink.click();
	await roleDefinePermissionsPage.menuItem('Site and Asset Library').click();
	await roleDefinePermissionsPage.menuItem('Applications').click();
	await roleDefinePermissionsPage.menuItem('Account Management').click();
	await roleDefinePermissionsPage.permissionCheckbox('Add to Page').check();
	await roleDefinePermissionsPage.permissionCheckbox('Configuration').check();
	await roleDefinePermissionsPage.saveButton.click();

	await waitForAlert(page, 'The role permissions were updated');

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await roleDefinePermissionsPage
		.resourceRemoveLink('Account Management: Add to Page')
		.click();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await page.reload();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();
});

test('LPS-47225 Permissions can be defined for owned account roles', async ({
	accountRolesPage,
	accountsPage,
	apiHelpers,
	editAccountRolePage,
	page,
	roleDefinePermissionsPage,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const roleName = getRandomString();

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();
	await accountsPage.accountRolesTab.click();
	await accountRolesPage.rolesTable.newButton.click();
	await editAccountRolePage.addRole({name: roleName});
	await editAccountRolePage.definePermissionsLink.click();
	await roleDefinePermissionsPage.menuItem('Site and Asset Library').click();
	await roleDefinePermissionsPage.menuItem('Applications').click();
	await roleDefinePermissionsPage.menuItem('Account Management').click();
	await roleDefinePermissionsPage.permissionCheckbox('Add to Page').check();
	await roleDefinePermissionsPage.permissionCheckbox('Configuration').check();
	await roleDefinePermissionsPage.saveButton.click();

	await waitForAlert(page, 'The role permissions were updated');

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await roleDefinePermissionsPage
		.resourceRemoveLink('Account Management: Add to Page')
		.click();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await page.reload();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();
});

test('LPS-47225 Permissions can be defined for account roles from role pages', async ({
	apiHelpers,
	editAccountRolePage,
	page,
	roleDefinePermissionsPage,
	rolesPage,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		roleType: 'account',
	});

	await rolesPage.goto();

	await rolesPage.accountRolesLink.click();
	await rolesPage.rolesTable.changeView('Table');

	await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();

	await (await rolesPage.rolesTable.cellLink(role.name)).click();
	await editAccountRolePage.definePermissionsLink.click();
	await roleDefinePermissionsPage.menuItem('Site and Asset Library').click();
	await roleDefinePermissionsPage.menuItem('Applications').click();
	await roleDefinePermissionsPage.menuItem('Account Management').click();
	await roleDefinePermissionsPage.permissionCheckbox('Add to Page').check();
	await roleDefinePermissionsPage.permissionCheckbox('Configuration').check();
	await roleDefinePermissionsPage.saveButton.click();

	await waitForAlert(page, 'The role permissions were updated');

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toBeVisible();
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await roleDefinePermissionsPage
		.resourceRemoveLink('Account Management: Add to Page')
		.click();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();

	await page.reload();

	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Add to Page'
		)
	).toHaveCount(0);
	await expect(
		roleDefinePermissionsPage.resourceName(
			'Account Management: Configuration'
		)
	).toBeVisible();
});
