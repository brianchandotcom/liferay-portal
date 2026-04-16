/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {getExportedConfiguration} from '../../../utils/getExportedConfiguration';

export const test = mergeTests(
	accessibilityMenuPagesTest,
	loginTest(),
	systemSettingsPageTest,
	featureFlagsTest({
		'LPS-155284': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

const SETTING_CATEGORY_KEY = 'Accessibility';
const SETTING_CONFIGURATION_NAME = 'Accessibility Menu';

test('Should persist and apply system configuration changes made via the User Interface', async ({
	accessibilityMenuPage,
	page,
	systemSettingsPage,
}) => {
	async function expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
		expectedStatus: 'visible' | 'hidden' = 'visible'
	) {
		const userProfileTrigger = page.getByTestId('userPersonalMenu');
		const accessibilityMenuItem = page.getByRole('menuitem', {
			name: SETTING_CONFIGURATION_NAME,
		});

		await expect(userProfileTrigger).toBeVisible();

		await userProfileTrigger.click();

		if (expectedStatus === 'visible') {
			await expect(accessibilityMenuItem).toBeVisible();

			await accessibilityMenuItem.click();

			const accessibilityModal = page
				.locator('.modal')
				.getByLabel(SETTING_CONFIGURATION_NAME);

			await expect(accessibilityModal).toBeVisible();

			await accessibilityModal.getByLabel('Close').click();

			await expect(accessibilityModal).toBeHidden();
		}
		else if (expectedStatus === 'hidden') {
			await expect(accessibilityMenuItem).toBeHidden();

			await page.keyboard.press('Escape');
		}
	}

	await test.step('Check that Enable Accessibility Menu is checked in System Settings', async () => {
		await systemSettingsPage.goToSystemSetting(
			SETTING_CATEGORY_KEY,
			SETTING_CONFIGURATION_NAME
		);

		await expect(
			accessibilityMenuPage.enableAccessibilityMenuCheckbox
		).toBeChecked();
	});

	await test.step('Verify that the Accessibility Menu is available', async () => {
		await expectAccessibilityMenuToBeAvailableFromUserProfileMenu();
	});

	await test.step('Navigate to System Settings and disable Accessibility Menu in System Settings', async () => {
		await systemSettingsPage.goToSystemSetting(
			SETTING_CATEGORY_KEY,
			SETTING_CONFIGURATION_NAME
		);

		await accessibilityMenuPage.disableAccessibilityMenu();
	});

	await test.step('Verify that the Accessibility Menu is no longer available', async () => {
		await expectAccessibilityMenuToBeAvailableFromUserProfileMenu('hidden');
	});

	await test.step('Verify exported configuration file reflects the change', async () => {
		await systemSettingsPage.goToSystemSetting(
			SETTING_CATEGORY_KEY,
			SETTING_CONFIGURATION_NAME
		);

		expect(await getExportedConfiguration(page)).toContain(
			`enableAccessibilityMenu=B"false"`
		);
	});
});
