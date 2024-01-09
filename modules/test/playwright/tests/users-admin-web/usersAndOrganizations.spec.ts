/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPages.fixture';

export const test = mergeTests(usersAndOrganizationsPagesTest);

test('LPS-204541 check export/import menu visibility', async ({
	_usersAndOrganizationsPage,
}) => {
	await _usersAndOrganizationsPage.goToUsers();
	await _usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		_usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toHaveCount(0);
	await expect(
		_usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		_usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toBeVisible();

	await _usersAndOrganizationsPage.goToOrganizations();
	await _usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		_usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toBeVisible();
	await expect(
		_usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		_usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toHaveCount(0);
});
