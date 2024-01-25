/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest,
	usersAndOrganizationsPagesTest
);

test('LPS-204541 check export/import menu visibility', async ({
	usersAndOrganizationsPage,
}) => {
	await usersAndOrganizationsPage.goToUsers();
	await usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toHaveCount(0);
	await expect(
		usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toBeVisible();

	await usersAndOrganizationsPage.goToOrganizations();
	await usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toHaveCount(0);
});

test('LPD-15224 check escape of memberships account name', async ({
	apiHelpers,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	await page.goto('/');

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: '<img src="x" onError="alert(document.location)">',
	});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	try {
		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowLink('test')
		).click();
		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsAccountsTableRow(
					0,
					account.name,
					true
				)
			).row
		).toBeVisible();
	}
	finally {
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);
	}
});
