/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {portletConfigurationPermissionsPageTest} from '../../fixtures/portletConfigurationPermissionsPagesTest';

export const test = mergeTests(
	loginTest(),
	portletConfigurationPermissionsPageTest
);

test('LPD-25265 search results should stay when form submitted', async ({
	portletConfigurationPermissionsPage,
}) => {
	await portletConfigurationPermissionsPage.goto();
	await expect(portletConfigurationPermissionsPage.searchBar).toBeVisible();

	await portletConfigurationPermissionsPage.searchBar.click();
	await portletConfigurationPermissionsPage.searchBar.fill('r');
	await portletConfigurationPermissionsPage.searchBar.press('Enter');

	await expect(
		portletConfigurationPermissionsPage.resultsBanner
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.ownerRoleCell
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.siteMemberRoleCell
	).toBeVisible();

	await portletConfigurationPermissionsPage.changePagination(20, 4);

	await expect(
		portletConfigurationPermissionsPage.ownerRoleCell
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.siteMemberRoleCell
	).toHaveCount(0);

	await portletConfigurationPermissionsPage.saveButton.click();

	await expect(
		portletConfigurationPermissionsPage.successMessage
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.resultsBanner
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.ownerRoleCell
	).toBeVisible();
	await expect(
		portletConfigurationPermissionsPage.siteMemberRoleCell
	).toHaveCount(0);

	await portletConfigurationPermissionsPage.clearLink.click();

	await expect(
		portletConfigurationPermissionsPage.siteMemberRoleCell
	).toBeVisible();
});
