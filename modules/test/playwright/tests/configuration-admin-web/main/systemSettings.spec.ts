/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(
	accessibilityMenuPagesTest,
	loginTest(),
	systemSettingsPageTest,
	instanceSettingsPagesTest,
	siteSettingsPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPS-155284': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

const SETTING_CATEGORY_KEY = 'Accessibility';
const SETTING_CONFIGURATION_NAME = 'Accessibility Menu';

test(
	'Assert that a system configuration is unchanged when the same configuration changes on the company and group scopes',
	{tag: '@LPS-79394'},
	async ({
		accessibilityMenuPage,
		instanceSettingsPage,
		siteSettingsPage,
		systemSettingsPage,
	}) => {
		await test.step('Navigate to System Settings and enable Accessibility Menu configuration', async () => {
			await systemSettingsPage.goToSystemSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Navigate to Instance Settings and disable Accessibility Menu configuration', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await accessibilityMenuPage.disableAccessibilityMenu();
		});

		await test.step('Navigate to Site Settings and disable Accessibility Menu configuration', async () => {
			await siteSettingsPage.goToSiteSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await accessibilityMenuPage.disableAccessibilityMenu();
		});

		await test.step('Check System Settings scope is corrected', async () => {
			await systemSettingsPage.goToSystemSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await expect(
				accessibilityMenuPage.enableAccessibilityMenuCheckbox
			).toBeChecked();
		});
	}
);
