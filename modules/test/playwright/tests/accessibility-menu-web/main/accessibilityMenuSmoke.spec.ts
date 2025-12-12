/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

const test = mergeTests(
	accessibilityMenuPagesTest,
	apiHelpersTest,
	isolatedSiteTest,
	siteSettingsPagesTest,
	systemSettingsPageTest,
	instanceSettingsPagesTest,
	loginTest()
);

test(
	'Verifies that the user can enable it by Instance Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, instanceSettingsPage, page}) => {
		await test.step('When Accessibility Menu is enabled at Instance Settings level', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				'Accessibility',
				'Accessibility Menu'
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await accessibilityMenuPage.openAccessibilityMenu();
			await accessibilityMenuPage.closeButton.click();
		});

		await test.step('And Accessibility Menu can be reached through the user profile menu', async () => {
			await page.locator('button[data-qa-id=userPersonalMenu]').click();

			await page
				.getByRole('menuitem', {name: 'Accessibility Menu'})
				.click();

			await expect(
				page.locator('.modal').getByLabel('Accessibility Menu')
			).toBeVisible();
		});
	}
);

test(
	'Verifies that the user can enable it by Site Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, site, siteSettingsPage}) => {
		await test.step('When navigate to Site Settings > Accessibility And turn on Enable Accessibility Menu', async () => {
			await siteSettingsPage.goToSiteSetting(
				'Accessibility',
				'Accessibility Menu',
				site.friendlyUrlPath
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await accessibilityMenuPage.openAccessibilityMenu();
			await accessibilityMenuPage.closeButton.click();
		});
	}
);

test(
	'Verifies that the user can enable it by System Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, systemSettingsPage}) => {
		await test.step('When navigate to Site Settings > Accessibility And turn on Enable Accessibility Menu', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Accessibility',
				'Accessibility Menu'
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await accessibilityMenuPage.openAccessibilityMenu();
			await accessibilityMenuPage.closeButton.click();
		});
	}
);
