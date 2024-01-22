/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';

export const test = mergeTests(loginTest, usersAndOrganizationsPagesTest);

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
