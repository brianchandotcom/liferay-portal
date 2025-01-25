/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import {nextPage, setItemsPerPage} from '../../utils/pagination';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	accountsPagesTest,
	apiHelpersTest,
	applicationsMenuPageTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest,
	serverAdministrationPageTest
);

test('LPS-139430 Can add and remove a user to an account', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const account1 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account2.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account1.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await accountUserSelectorPage.assignUsers([user.name]);

	await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account2.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await accountUserSelectorPage.assignUsers([user.name]);

	await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink(user.alternateName)
	).click();
	await editUserPage.membershipsLink.click();

	await expect(
		(await editUserPage.membershipsAccountsTableRow(0, account1.name, true))
			.row
	).toBeVisible();
	await expect(
		(await editUserPage.membershipsAccountsTableRow(0, account2.name, true))
			.row
	).toBeVisible();

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account1.name)).click();

	await accountsPage.usersTab.click();

	await (await accountUsersPage.usersTable.rowActions(user.name)).click();
	await accountUsersPage.removeButton.click();

	await expect(accountUsersPage.usersTable.cell(user.name)).toHaveCount(0);
});

test('LPS-139430 Can add and remove users to an account in bulk', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const users = [];

	for (let i = 0; i < 5; i++) {
		users.push(await apiHelpers.headlessAdminUser.postUserAccount());
	}

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await accountUserSelectorPage.assignUsers(users.map((user) => user.name));

	for (const user of users) {
		await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();
	}

	for (const index of [1, 3]) {
		await (
			await accountUsersPage.usersTable.rowCheckBox(users[index].name)
		).check();
	}

	await accountUsersPage.removeButton.click();

	await waitForAlert(page);

	for (let i = 1; i < 5; i++) {
		if (i % 2 === 0) {
			await expect(
				accountUsersPage.usersTable.cell(users[i].name)
			).toBeVisible();
		}
		else {
			await expect(
				accountUsersPage.usersTable.cell(users[i].name)
			).toHaveCount(0);
		}
	}
});

test('LPD-47225 Can search assigned users', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await accountUserSelectorPage.assignUsers([user1.name, user2.name]);

	await expect(accountUsersPage.usersTable.cell(user1.name)).toBeVisible();
	await expect(accountUsersPage.usersTable.cell(user2.name)).toBeVisible();

	await accountUsersPage.usersTable.search(getRandomString());

	await expect(accountUsersPage.usersTable.cell(user1.name)).toHaveCount(0);
	await expect(accountUsersPage.usersTable.cell(user2.name)).toHaveCount(0);

	await accountUsersPage.usersTable.search(user1.name);

	await expect(accountUsersPage.usersTable.cell(user1.name)).toBeVisible();
	await expect(accountUsersPage.usersTable.cell(user2.name)).toHaveCount(0);

	await accountUsersPage.usersTable.search(user2.name);

	await expect(accountUsersPage.usersTable.cell(user1.name)).toHaveCount(0);
	await expect(accountUsersPage.usersTable.cell(user2.name)).toBeVisible();

	await accountUsersPage.usersTable.search('');

	await expect(accountUsersPage.usersTable.cell(user1.name)).toBeVisible();
	await expect(accountUsersPage.usersTable.cell(user2.name)).toBeVisible();
});

test('LPD-47225 Can search users during assignment', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();

	await accountUserSelectorPage.usersTable.search(getRandomString());

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(user1.name);

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);

	await accountUserSelectorPage.usersTable.search(user2.name);

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();

	await accountUserSelectorPage.usersTable.search('');

	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();
});

test('LPD-47225 Can paginate users during assignment', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	const users = [];

	for (let i = 1; i <= 5; i++) {
		users.push(
			await apiHelpers.headlessAdminUser.postUserAccount({
				familyName: `A User ${i}`,
			})
		);
	}

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

	await setItemsPerPage(accountUserSelectorPage.frame, 4);

	for (const [index, user] of users.entries()) {
		if (index < 4) {
			await expect(
				accountUserSelectorPage.usersTable.cell(user.name)
			).toBeVisible();
		}
		else {
			await expect(
				accountUserSelectorPage.usersTable.cell(user.name)
			).toHaveCount(0);
		}
	}

	await nextPage(accountUserSelectorPage.frame);

	for (const [index, user] of users.entries()) {
		if (index < 4) {
			await expect(
				accountUserSelectorPage.usersTable.cell(user.name)
			).toHaveCount(0);
		}
		else {
			await expect(
				accountUserSelectorPage.usersTable.cell(user.name)
			).toBeVisible();
		}
	}
});
