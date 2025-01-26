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

test(
	'LPD-47225 Can add and remove a user to an account',
	{tag: ['@LPS-139430', '@LPS-149125']},
	async ({
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

		await (
			await accountsPage.accountsTable.cellLink(account1.name)
		).click();

		await accountsPage.usersTab.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();

		await accountUserSelectorPage.assignUsers([user.name]);

		await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();

		await accountsPage.goto();

		await (
			await accountsPage.accountsTable.cellLink(account2.name)
		).click();

		await accountsPage.usersTab.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();

		await accountUserSelectorPage.assignUsers([user.name]);

		await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				user.alternateName
			)
		).click();
		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsAccountsTableRow(
					0,
					account1.name,
					true
				)
			).row
		).toBeVisible();
		await expect(
			(
				await editUserPage.membershipsAccountsTableRow(
					0,
					account2.name,
					true
				)
			).row
		).toBeVisible();

		await accountsPage.goto();

		await (
			await accountsPage.accountsTable.cellLink(account1.name)
		).click();

		await accountsPage.usersTab.click();

		await (await accountUsersPage.usersTable.rowActions(user.name)).click();
		await accountUsersPage.removeButton.click();

		await expect(accountUsersPage.usersTable.cell(user.name)).toHaveCount(
			0
		);
	}
);

test(
	'LPD-47225 Can add and remove users to an account in bulk',
	{tag: ['@LPS-139430']},
	async ({
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

		await accountUserSelectorPage.assignUsers(
			users.map((user) => user.name)
		);

		for (const user of users) {
			await expect(
				accountUsersPage.usersTable.cell(user.name)
			).toBeVisible();
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
	}
);

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

test('LPD-47225 Can remove user from personal account', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user.emailAddress]
	);

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await expect(editAccountPage.assignUserMessage).not.toBeVisible();
	await expect(editAccountPage.personAccountUserContainer).toBeVisible();
	await expect(
		editAccountPage.personAccountUserName(user.name)
	).toBeVisible();
	await expect(accountsPage.usersTab).not.toBeVisible();

	await editAccountPage.personAccountUserRemoveButton.click();

	await expect(editAccountPage.assignUserMessage).toBeVisible();
	await expect(editAccountPage.personAccountUserName(user.name)).toHaveCount(
		0
	);

	await editAccountPage.saveButton.click();

	await waitForAlert(page);

	await editAccountPage.backButton.click();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await expect(editAccountPage.assignUserMessage).toBeVisible();
	await expect(editAccountPage.personAccountUserName(user.name)).toHaveCount(
		0
	);
});

test('LPD-47225 Only one user can be assigned to a Person Account', async ({
	accountPersonUserSelectorPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user1.emailAddress]
	);

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account.name)).click();

	await expect(editAccountPage.assignUserMessage).not.toBeVisible();
	await expect(editAccountPage.personAccountUserContainer).toBeVisible();
	await expect(
		editAccountPage.personAccountUserName(user1.name)
	).toBeVisible();
	await expect(editAccountPage.personAccountUserName(user2.name)).toHaveCount(
		0
	);

	await editAccountPage.personAccountUserSelectButton.click();

	await accountPersonUserSelectorPage.chooseUser(user2.name);

	await expect(editAccountPage.assignUserMessage).not.toBeVisible();
	await expect(editAccountPage.personAccountUserContainer).toBeVisible();
	await expect(editAccountPage.personAccountUserName(user1.name)).toHaveCount(
		0
	);
	await expect(
		editAccountPage.personAccountUserName(user2.name)
	).toBeVisible();

	await editAccountPage.saveButton.click();

	await waitForAlert(page);

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink(user1.alternateName)
	).click();

	await editUserPage.membershipsLink.click();

	await expect(editUserPage.membershipsNoAccountsMessage).toBeVisible();

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink(user2.alternateName)
	).click();

	await editUserPage.membershipsLink.click();

	await expect(editUserPage.membershipsNoAccountsMessage).not.toBeVisible();
});

test(
	'LPD-47225 Can search in person account user selector modal',
	{tag: ['@LPS-117171']},
	async ({
		accountPersonUserSelectorPage,
		accountsPage,
		apiHelpers,
		editAccountPage,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			type: 'person',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await expect(editAccountPage.assignUserMessage).toBeVisible();

		await editAccountPage.personAccountUserSelectButton.click();

		await expect(
			accountPersonUserSelectorPage.usersTable.filterButton
		).toHaveCount(0);
		await expect(
			accountPersonUserSelectorPage.usersTable.orderButton
		).toBeVisible();

		await accountPersonUserSelectorPage.usersTable.search(
			getRandomString()
		);

		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user1.name)
		).toHaveCount(0);
		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user2.name)
		).toHaveCount(0);

		await accountPersonUserSelectorPage.usersTable.search(user1.name);

		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user1.name)
		).toBeVisible();
		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user2.name)
		).toHaveCount(0);

		await accountPersonUserSelectorPage.usersTable.search(user2.name);

		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user1.name)
		).toHaveCount(0);
		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user2.name)
		).toBeVisible();

		await accountPersonUserSelectorPage.usersTable.search('');

		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user1.name)
		).toBeVisible();
		await expect(
			accountPersonUserSelectorPage.usersTable.cell(user2.name)
		).toBeVisible();
	}
);

test('LPD-47225 Can search, filter and sort in account user selector modal', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
}) => {
	const account1 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account2.id, type: 'account'});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount({
		emailAddress: `A${getRandomString()}@liferay.com`,
		familyName: `Z${getRandomString()}`,
		givenName: `A${getRandomString()}`,
	});
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount({
		emailAddress: `Z${getRandomString()}@liferay.com`,
		familyName: `A${getRandomString()}`,
		givenName: `Z${getRandomString()}`,
	});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account2.id,
		[user2.emailAddress]
	);

	await accountsPage.goto();

	await (await accountsPage.accountsTable.cellLink(account1.name)).click();

	await accountsPage.usersTab.click();
	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();

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

	await accountUserSelectorPage.usersTable.orderButton.click();
	await accountUserSelectorPage.usersTable
		.orderMenuItem('First Name')
		.click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();
	await expect(
		await accountUserSelectorPage.usersTable.firstRow()
	).toContainText(user1.name);
	await expect(
		await accountUserSelectorPage.usersTable.lastRow()
	).toContainText(user2.name);

	await accountUserSelectorPage.usersTable.orderButton.click();
	await accountUserSelectorPage.usersTable.orderMenuItem('Last Name').click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();
	await expect(
		await accountUserSelectorPage.usersTable.firstRow()
	).toContainText(user2.name);
	await expect(
		await accountUserSelectorPage.usersTable.lastRow()
	).toContainText(user1.name);

	await accountUserSelectorPage.usersTable.orderButton.click();
	await accountUserSelectorPage.usersTable
		.orderMenuItem('Email Address')
		.click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();
	await expect(
		await accountUserSelectorPage.usersTable.firstRow()
	).toContainText(user1.name);
	await expect(
		await accountUserSelectorPage.usersTable.lastRow()
	).toContainText(user2.name);

	await accountUserSelectorPage.usersTable.orderButton.click();
	await accountUserSelectorPage.usersTable.orderMenuItem('Last Name').click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();

	await accountUserSelectorPage.usersTable.filterButton.click();
	await accountUserSelectorPage.usersTable
		.filterMenuItem('Account Users')
		.click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();
	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toHaveCount(0);
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toBeVisible();

	await accountUserSelectorPage.usersTable.filterButton.click();
	await accountUserSelectorPage.usersTable
		.filterMenuItem('No Assigned Account')
		.click();

	await expect(accountUserSelectorPage.usersTable.searchInput).toBeEditable();
	await expect(
		accountUserSelectorPage.usersTable.cell(user1.name)
	).toBeVisible();
	await expect(
		accountUserSelectorPage.usersTable.cell(user2.name)
	).toHaveCount(0);
});

test('LPD-47225 A user with a blocked domain cannot be added to an account', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	editUserPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
		true,
		'yahoo.com,blocked.com'
	);

	try {
		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();
		await accountUserSelectorPage.usersTable.newButton.click();

		const randomString = getRandomString();

		await editUserPage.emailAddressInput.fill(
			`${getRandomString()}@blocked.com`
		);
		await editUserPage.firstNameInput.fill(randomString);
		await editUserPage.lastNameInput.fill(randomString);
		await editUserPage.screenNameInput.fill(randomString);

		await expect(editUserPage.emailAddressError).toContainText(
			'is a blocked domain.'
		);

		await editUserPage.saveButton.click();

		await waitForAlert(page, 'Your request failed to complete', {
			type: 'danger',
		});

		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await editAccountPage.usersLink.click();

		await expect(
			accountUsersPage.usersTable.cell(randomString, false)
		).toHaveCount(0);
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false,
			''
		);
	}
});

test('LPD-47225 A user with an invalid domain cannot be added to an account', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	editUserPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	const account = {
		domains: ['liferay.com', 'google.com', 'si-na.com', '9teen.com'],
		name: getRandomString(),
		type: 'business',
	};

	const {domains, name, type} = account;

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {domains, name, type});

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();
		await accountUserSelectorPage.usersTable.newButton.click();

		const randomString = getRandomString();

		await editUserPage.emailAddressInput.fill(
			`${getRandomString()}@invalid.com`
		);
		await editUserPage.firstNameInput.fill(randomString);
		await editUserPage.lastNameInput.fill(randomString);
		await editUserPage.screenNameInput.fill(randomString);

		await expect(editUserPage.emailAddressError).toContainText(
			'is not a valid domain for the following accounts'
		);

		await editUserPage.saveButton.click();

		await waitForAlert(page, 'Your request failed to complete', {
			type: 'danger',
		});

		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await editAccountPage.usersLink.click();

		await expect(
			accountUsersPage.usersTable.cell(randomString, false)
		).toHaveCount(0);
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPD-47225 A user with a valid domain can be added to an account without warnings', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	editUserPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	const account = {
		domains: ['liferay.com', 'google.com', 'si-na.com', '9teen.com'],
		name: getRandomString(),
		type: 'business',
	};

	const {domains, name, type} = account;

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {domains, name, type});

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();
		await accountUserSelectorPage.usersTable.newButton.click();

		const randomString = getRandomString();

		await editUserPage.emailAddressInput.fill(
			`${randomString}@liferay.com`
		);
		await editUserPage.firstNameInput.fill(randomString);
		await editUserPage.lastNameInput.fill(randomString);
		await editUserPage.screenNameInput.fill(randomString);

		await expect(editUserPage.emailAddressError).toHaveCount(0);

		await editUserPage.saveButton.click();

		await waitForAlert(page);

		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await editAccountPage.usersLink.click();

		await expect(
			accountUsersPage.usersTable.cell(randomString, false)
		).toBeVisible();
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPD-47225 Can filter valid domain users and all users when assigning', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	const user1 = await apiHelpers.headlessAdminUser.postUserAccount({
		emailAddress: `${getRandomString()}@liferay.com`,
	});
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount({
		emailAddress: `${getRandomString()}@invalid.com`,
	});

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {
			name: getRandomString(),
		});

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();

		await expect(
			accountUserSelectorPage.usersTable.searchInput
		).toBeEditable();

		await accountUserSelectorPage.usersTable.filterButton.click();
		await accountUserSelectorPage.usersTable
			.filterMenuItem('Valid Domain Users')
			.click();

		await page.waitForTimeout(100);

		expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeNull();
		expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeNull();

		await accountUserSelectorPage.usersTable.filterButton.click();
		await accountUserSelectorPage.usersTable
			.filterMenuItem('All Users')
			.click();

		await page.waitForTimeout(100);

		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeVisible();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeEnabled();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeVisible();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeEnabled();

		await accountUserSelectorPage.usersTable.selectAllItemsCheckbox.check();
		await accountUserSelectorPage.assignButton.click();

		await waitForAlert(page);

		await expect(
			accountUsersPage.usersTable.cell(user1.name)
		).toBeVisible();
		await expect(
			accountUsersPage.usersTable.cell(user2.name)
		).toBeVisible();

		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {
			domains: ['liferay.com', 'google.com', 'si-na.com', '9teen.com'],
			name: getRandomString(),
		});

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();

		await expect(
			accountUserSelectorPage.usersTable.searchInput
		).toBeEditable();

		await accountUserSelectorPage.usersTable.filterButton.click();
		await accountUserSelectorPage.usersTable
			.filterMenuItem('Valid Domain Users')
			.click();

		await page.waitForTimeout(100);

		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeVisible();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeEnabled();
		expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeNull();

		await accountUserSelectorPage.usersTable.filterButton.click();
		await accountUserSelectorPage.usersTable
			.filterMenuItem('All Users')
			.click();

		await page.waitForTimeout(100);

		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeVisible();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user1.name)
		).toBeEnabled();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeVisible();
		await expect(
			await accountUserSelectorPage.usersTable.rowCheckBox(user2.name)
		).toBeDisabled();

		await accountUserSelectorPage.usersTable.selectAllItemsCheckbox.check();
		await accountUserSelectorPage.assignButton.click();

		await waitForAlert(page);

		await expect(
			accountUsersPage.usersTable.cell(user1.name)
		).toBeVisible();
		await expect(accountUsersPage.usersTable.cell(user2.name)).toHaveCount(
			0
		);

		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();

		await expect(
			accountUserSelectorPage.usersTable.searchInput
		).toBeEditable();

		await accountUserSelectorPage.usersTable.filterButton.click();
		await accountUserSelectorPage.usersTable
			.filterMenuItem('Valid Domain Users')
			.click();
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPD-47225 Cannot invite a user with a different domain', async ({
	accountUserInvitePage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {
			domains: ['liferay.com'],
			name: getRandomString(),
		});

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.inviteUserMenuItem.click();

		await accountUserInvitePage
			.emailAddressInput(accountUserInvitePage.firstEntry)
			.fill(`${getRandomString()}@invalid.com`);
		await accountUserInvitePage
			.emailAddressInput(accountUserInvitePage.firstEntry)
			.press('Enter');

		await expect(
			accountUserInvitePage.formError(
				accountUserInvitePage.firstEntry,
				'has an invalid email domain.'
			)
		).toBeVisible();

		await accountUserInvitePage.inviteButton.click();

		await expect(
			accountUserInvitePage.formError(
				accountUserInvitePage.firstEntry,
				'has an invalid email domain.'
			)
		).toBeVisible();

		await accountUserInvitePage
			.clearAllButton(accountUserInvitePage.firstEntry)
			.click();
		await accountUserInvitePage
			.emailAddressInput(accountUserInvitePage.firstEntry)
			.fill(`${getRandomString()}@liferay.com`);
		await accountUserInvitePage
			.emailAddressInput(accountUserInvitePage.firstEntry)
			.press('Enter');

		await expect(
			accountUserInvitePage.formError(
				accountUserInvitePage.firstEntry,
				'has an invalid email domain.'
			)
		).toHaveCount(0);

		await accountUserInvitePage.inviteButton.click();

		await waitForAlert(page);
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test(
	'LPD-47225 The user is able to add and remove entries when inviting users to an account',
	{tag: ['@LPS-189434']},
	async ({
		accountUserInvitePage,
		accountUsersPage,
		accountsPage,
		apiHelpers,
		editAccountPage,
		emailDomainsInstanceSettingsPage,
		page,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

		try {
			await accountsPage.goto();

			await accountsPage.accountsTable.newButton.click();

			await editAccountPage.createAccount(apiHelpers, {
				domains: ['liferay.com'],
				name: getRandomString(),
			});

			await editAccountPage.usersLink.click();
			await accountUsersPage.usersTable.newButton.click();
			await accountUsersPage.inviteUserMenuItem.click();

			await expect(accountUserInvitePage.entries).toHaveCount(1);

			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.firstEntry)
				.fill(`${getRandomString()}@liferay.com`);
			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.firstEntry)
				.press('Enter');

			await accountUserInvitePage.addEntryButton.click();

			await expect(accountUserInvitePage.entries).toHaveCount(2);

			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.lastEntry)
				.fill(`${getRandomString()}@invalid.com`);
			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.lastEntry)
				.press('Enter');

			await expect(
				accountUserInvitePage.formError(
					accountUserInvitePage.lastEntry,
					'has an invalid email domain.'
				)
			).toBeVisible();

			await accountUserInvitePage.inviteButton.click();

			await expect(
				accountUserInvitePage.formError(
					accountUserInvitePage.lastEntry,
					'has an invalid email domain.'
				)
			).toBeVisible();

			await accountUserInvitePage
				.removeEntryButton(accountUserInvitePage.lastEntry)
				.click();

			await expect(accountUserInvitePage.entries).toHaveCount(1);

			await accountUserInvitePage.inviteButton.click();

			await waitForAlert(page);
		}
		finally {
			await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
				false
			);
		}
	}
);

test('LPD-47225 Removes domain set to an account', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
	editUserPage,
	emailDomainsInstanceSettingsPage,
	page,
}) => {
	const account = {
		domains: ['liferay.com', 'google.com', 'si-na.com', '9teen.com'],
		name: getRandomString(),
		type: 'business',
	};

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, account);

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();
		await accountUserSelectorPage.usersTable.newButton.click();

		const randomString1 = getRandomString();

		await editUserPage.emailAddressInput.fill(
			`${getRandomString()}@liferay.com`
		);
		await editUserPage.firstNameInput.fill(randomString1);
		await editUserPage.lastNameInput.fill(randomString1);
		await editUserPage.screenNameInput.fill(randomString1);

		await editUserPage.saveButton.click();

		await waitForAlert(page);

		await expect(
			accountUsersPage.usersTable.cell(randomString1, false)
		).toHaveCount(1);

		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);

		await accountsPage.goto();

		await (await accountsPage.accountsTable.cellLink(account.name)).click();

		await editAccountPage.usersLink.click();
		await accountUsersPage.usersTable.newButton.click();
		await accountUsersPage.assignUserMenuItem.click();
		await accountUserSelectorPage.usersTable.newButton.click();

		const randomString2 = getRandomString();

		await editUserPage.emailAddressInput.fill(
			`${getRandomString()}@invalid.com`
		);
		await editUserPage.firstNameInput.fill(randomString2);
		await editUserPage.lastNameInput.fill(randomString2);
		await editUserPage.screenNameInput.fill(randomString2);

		await editUserPage.saveButton.click();

		await waitForAlert(page);

		await expect(
			accountUsersPage.usersTable.cell(randomString1, false)
		).toHaveCount(1);
		await expect(
			accountUsersPage.usersTable.cell(randomString2, false)
		).toHaveCount(1);
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test(
	'LPD-47225 An account can be removed from a user through the user page',
	{tag: ['@LPS-139430']},
	async ({apiHelpers, editUserPage, usersAndOrganizationsPage}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			type: 'business',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[user.emailAddress]
		);

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				user.alternateName
			)
		).click();
		await editUserPage.membershipsLink.click();

		await expect(async () => {
			await expect(editUserPage.membershipsNoAccountsMessage).toHaveCount(
				0
			);
			await expect(
				(
					await editUserPage.membershipsAccountsTableRow(
						0,
						account.name,
						true
					)
				).row
			).toBeVisible();
		}).toPass();

		await editUserPage
			.membershipsAccountsRemoveButton(account.name)
			.click();

		await expect(editUserPage.membershipsNoAccountsMessage).toBeVisible();
	}
);

test('LPD-47225 Can view account users from manage users link', async ({
	accountUserSelectorPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editUserPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[user.emailAddress]
	);

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.manageUsersButton.click();

	await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();

	await accountUsersPage.usersTable.newButton.click();
	await accountUsersPage.assignUserMenuItem.click();
	await accountUserSelectorPage.usersTable.newButton.click();

	const randomString = getRandomString();

	await editUserPage.emailAddressInput.fill(
		`${getRandomString()}@liferay.com`
	);
	await editUserPage.firstNameInput.fill(randomString);
	await editUserPage.lastNameInput.fill(randomString);
	await editUserPage.screenNameInput.fill(randomString);

	await editUserPage.saveButton.click();

	await waitForAlert(page);

	await expect(accountUsersPage.usersTable.cell(user.name)).toBeVisible();
	await expect(
		accountUsersPage.usersTable.cell(randomString, false)
	).toBeVisible();
});
