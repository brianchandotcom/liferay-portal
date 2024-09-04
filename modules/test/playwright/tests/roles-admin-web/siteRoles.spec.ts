/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {rolesPagesTest} from '../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test('LPD-35066 Site role search should not persist after selecting an option', async ({
	apiHelpers,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const site1 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site1.id, type: 'site'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	const role =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		role.id,
		site1.id,
		user.id
	);

	const site2 = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site2.id, type: 'site'});

	await apiHelpers.headlessAdminUser.assignUserToSite(
		role.id,
		site2.id,
		user.id
	);

	await usersAndOrganizationsPage.goToUsers();
	await (
		await usersAndOrganizationsPage.usersTableRowLink(user.alternateName)
	).click();

	await editUserPage.rolesLink.waitFor({state: 'visible'});
	await editUserPage.rolesLink.click();
	await editUserPage.selectSiteRolesButton.click();
	await editUserPage.selectSiteRolesSearchBar.fill(site1.name);
	await editUserPage.selectSiteRolesSearchBarButton.click();

	await page.waitForTimeout(500);
	await editUserPage.selectSitesTable.waitFor({state: 'visible'});

	await expect(
		editUserPage.selectSiteRolesFrame.getByText(site1.name)
	).toBeVisible();

	await (await editUserPage.selectSitesTableRowButton(site1.name)).click();
	await editUserPage.selectSiteRolesTable.waitFor({state: 'visible'});

	await expect(
		(await editUserPage.selectSiteRolesTable.getByRole('row').all()).length
	).toBeGreaterThanOrEqual(1);
});
