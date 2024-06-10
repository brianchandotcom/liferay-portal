/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	accountsPagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-25948 Can search for account group', async ({
	accountAccountGroupsPage,
	accountsPage,
	apiHelpers,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const accountGroup1 = await apiHelpers.headlessAdminUser.postAccountGroup({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: accountGroup1.id, type: 'accountGroup'});

	const accountGroup2 = await apiHelpers.headlessAdminUser.postAccountGroup({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: accountGroup2.id, type: 'accountGroup'});

	await apiHelpers.headlessAdminUser.assignAccountToAccountGroup(
		account.externalReferenceCode,
		accountGroup1.externalReferenceCode
	);

	await apiHelpers.headlessAdminUser.assignAccountToAccountGroup(
		account.externalReferenceCode,
		accountGroup2.externalReferenceCode
	);

	await accountsPage.goto();
	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await accountsPage.accountGroupsTab.click();

	await accountAccountGroupsPage.searchInput.fill(accountGroup1.name);
	await accountAccountGroupsPage.searchButton.click();

	await expect(
		page.getByText(accountGroup1.name, {exact: true})
	).toBeVisible();

	await expect(page.getByText(accountGroup2.name, {exact: true})).toHaveCount(
		0
	);
});

test('LPD-28159 Account groups are displayed in account details page', async ({
	accountAccountGroupsPage,
	accountsPage,
	apiHelpers,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const accountGroup1 = await apiHelpers.headlessAdminUser.postAccountGroup({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: accountGroup1.id, type: 'accountGroup'});

	await apiHelpers.headlessAdminUser.assignAccountToAccountGroup(
		account.externalReferenceCode,
		accountGroup1.externalReferenceCode
	);

	const accountGroup2 = await apiHelpers.headlessAdminUser.postAccountGroup({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: accountGroup2.id, type: 'accountGroup'});

	await apiHelpers.headlessAdminUser.assignAccountToAccountGroup(
		account.externalReferenceCode,
		accountGroup2.externalReferenceCode
	);

	const accountGroup3 = await apiHelpers.headlessAdminUser.postAccountGroup({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: accountGroup3.id, type: 'accountGroup'});

	await apiHelpers.headlessAdminUser.assignAccountToAccountGroup(
		account.externalReferenceCode,
		accountGroup3.externalReferenceCode
	);

	await accountsPage.goto();
	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await accountsPage.accountGroupsTab.click();

	await expect(
		await accountAccountGroupsPage.accountGroupName(accountGroup1.name)
	).toBeVisible();
	await expect(
		await accountAccountGroupsPage.accountGroupName(accountGroup2.name)
	).toBeVisible();
	await expect(
		await accountAccountGroupsPage.accountGroupName(accountGroup3.name)
	).toBeVisible();
});
