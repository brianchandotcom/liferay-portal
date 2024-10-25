/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-20556': true,
	}),
	apiHelpersTest,
	changeTrackingPagesTest,
	isolatedSiteTest
);

test.beforeEach(async ({changeTrackingPage}) => {
	await changeTrackingPage.toggleSandboxConfiguration(true);
});

test.afterEach(async ({changeTrackingPage}) => {
	await changeTrackingPage.toggleSandboxConfiguration(false);
});

test('LPD-34602 Add view-only mode for production when using Publications sandbox', async ({
	apiHelpers,
	changeTrackingPage,
	page,
}) => {
	const user = await changeTrackingPage.addUserWithPublicationsUserRole();

	await performLogout(page);

	await performLogin(page, user.alternateName);

	const changeTrackingIndicatorButton = page.locator(
		'.change-tracking-indicator-button'
	);

	await changeTrackingIndicatorButton.click();

	const viewInProductionMenuItem = page.getByRole('menuitem', {
		name: 'View On Production',
	});

	await expect(viewInProductionMenuItem).toBeVisible();

	await viewInProductionMenuItem.click();

	const viewingProductionMenuItem = page.getByText('Viewing Production');

	await expect(viewingProductionMenuItem).toBeVisible();

	await viewingProductionMenuItem.click();

	const workOnUserMenuItem = page.getByText('Work on user');

	await expect(workOnUserMenuItem).toBeVisible();

	await workOnUserMenuItem.click();

	await performLogout(page);

	await performLogin(page, 'test');

	await changeTrackingPage.workOnProduction();

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));
});

test('LPD-39341 Sandbox mode allows users to work on production without permissions', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	page,
	site,
}) => {
	await changeTrackingPage.workOnPublication(ctCollection);

	const basicWebContentStructureId =
		await getBasicWebContentStructureId(apiHelpers);

	const title = getRandomString();

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: basicWebContentStructureId,
		groupId: site.id,
		titleMap: {en_US: title},
	});

	const user = await changeTrackingPage.addUserWithPublicationsUserRole();

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await changeTrackingPage.workOnPublication(ctCollection);

	await apiHelpers.headlessChangeTracking.publishCTCollection(
		ctCollection.id
	);

	await page.reload();

	const sandboxPublication = await page.getByText(user.alternateName + ' - ');

	await expect(sandboxPublication).toBeVisible();

	await performLogout(page);

	await performLogin(page, 'test');

	await changeTrackingPage.workOnProduction();

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));
});
