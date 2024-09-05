/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import performLogin, {performLogout} from '../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagsTest({
		'LPD-20556': true,
	}),
	loginTest()
);

test('LPD-34602 Add view-only mode for production when using Publications sandbox', async ({
	apiHelpers,
	changeTrackingPage,
	page,
}) => {
	const user = await changeTrackingPage.addUserWithPublicationsUserRole();

	await changeTrackingPage.toggleSandboxConfiguration(true);

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

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

	await changeTrackingPage.toggleSandboxConfiguration(false);
});
