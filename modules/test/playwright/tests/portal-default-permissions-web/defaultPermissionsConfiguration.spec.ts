/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {portalDefaultPermissionsPagesTest} from '../../fixtures/portalDefaultPermissionsPagesTest';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export const test = mergeTests(
	portalDefaultPermissionsPagesTest,
	featureFlagsTest({
		'LPD-21265': true,
	}),
	loginTest()
);

test('LPD-21645 set up the default permissions for pages', async ({
	defaultPermissionsConfigurationPage,
	page,
}) => {
	await defaultPermissionsConfigurationPage.goto();

	await expect(
		defaultPermissionsConfigurationPage.portalDefaultPermissionsSearchContainer
	).toBeVisible();
	await page.waitForTimeout(500);

	await defaultPermissionsConfigurationPage.editPageButton.click();

	await defaultPermissionsConfigurationPage.frameSaveButton.waitFor({
		state: 'attached',
	});
	await page.waitForTimeout(300);

	await defaultPermissionsConfigurationPage.analyticsAdministratorUpdateDiscussionCheckbox.setChecked(
		true
	);
	await defaultPermissionsConfigurationPage.ownerUpdateDiscussionCheckbox.setChecked(
		true
	);

	await defaultPermissionsConfigurationPage.frameSaveButton.click();

	await defaultPermissionsConfigurationPage.frameSaveButton.waitFor({
		state: 'detached',
	});

	await waitForSuccessAlert(page);

	await defaultPermissionsConfigurationPage.editPageButton.click();

	await defaultPermissionsConfigurationPage.frameSaveButton.waitFor({
		state: 'attached',
	});

	await expect(
		defaultPermissionsConfigurationPage.analyticsAdministratorUpdateDiscussionCheckbox
	).toBeChecked();

	await expect(
		defaultPermissionsConfigurationPage.ownerUpdateDiscussionCheckbox
	).toBeChecked();

	await defaultPermissionsConfigurationPage.analyticsAdministratorUpdateDiscussionCheckbox.setChecked(
		false
	);
	await defaultPermissionsConfigurationPage.ownerUpdateDiscussionCheckbox.setChecked(
		false
	);

	await defaultPermissionsConfigurationPage.frameSaveButton.click();

	await defaultPermissionsConfigurationPage.frameSaveButton.waitFor({
		state: 'detached',
	});

	await waitForSuccessAlert(page);
});
