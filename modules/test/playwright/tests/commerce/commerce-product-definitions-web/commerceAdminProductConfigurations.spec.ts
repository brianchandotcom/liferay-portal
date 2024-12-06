/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	featureFlagsTest({
		'LPD-10889': true,
	}),
	loginTest()
);

test('LPD-42555 Verify configuration list table appears', async ({
	applicationsMenuPage,
	commerceAdminProductConfigurationListsPage,
}) => {
	await applicationsMenuPage.goToCommerceProductConfigurationLists(false);

	await expect(
		commerceAdminProductConfigurationListsPage.table
	).toBeVisible();
});

test('LPD-43390 Create child configuration list', async ({
	applicationsMenuPage,
	commerceAdminProductConfigurationListsPage,
}) => {
	await applicationsMenuPage.goToCommerceProductConfigurationLists(false);

	await expect(
		commerceAdminProductConfigurationListsPage.table
	).toBeVisible();

	await commerceAdminProductConfigurationListsPage.addConfigurationList.click();

	await commerceAdminProductConfigurationListsPage.addConfigurationListName.fill(
		'Test'
	);
	await commerceAdminProductConfigurationListsPage.addConfigurationListPriority.fill(
		'1'
	);
	await commerceAdminProductConfigurationListsPage.addConfigurationListCatalog.selectOption(
		{label: 'Master'}
	);
	await commerceAdminProductConfigurationListsPage.addConfigurationListParentList.click();
	await commerceAdminProductConfigurationListsPage.addConfigurationListParentListElement.click();
	await commerceAdminProductConfigurationListsPage.addConfigurationListSaveButton.click();

	await expect(
		commerceAdminProductConfigurationListsPage.newConfigurationListName
	).toHaveText('Test');
});
