/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {doAndGoBack} from '../../../utils/doAndGoBack';

const test = mergeTests(
	accessibilityMenuPagesTest,
	instanceSettingsPagesTest,
	loginTest()
);

test(
	'A user can enable and disable the accessibility menu from the instance settings',
	{tag: '@LPD-73812'},
	async ({accessibilityMenuPage, instanceSettingsPage, page}) => {
		const accessibilityMenuItem = page.getByRole('menuitem', {
			name: 'Accessibility Menu',
		});

		async function goToAccessibilityMenuSetting() {
			await instanceSettingsPage.goToInstanceSetting(
				'Accessibility',
				'Accessibility Menu'
			);
		}

		async function openUserProfileMenu() {
			await clickAndExpectToBeVisible({
				target: page.getByRole('menu'),
				trigger: page.getByTitle('User Profile Menu'),
			});
		}

		await test.step('Enable the Accessibility Menu', async () => {
			await doAndGoBack(page, async () => {
				await goToAccessibilityMenuSetting();

				await accessibilityMenuPage.enableAccessibilityMenu();
			});
		});

		await test.step('Verify that the Accessibility Menu is visible', async () => {
			await expect(
				accessibilityMenuPage.openAccessibilityMenuButton
			).toBeAttached();

			await openUserProfileMenu();

			await expect(accessibilityMenuItem).toBeVisible();
		});

		await test.step('Disable the Accessibility Menu', async () => {
			await doAndGoBack(page, async () => {
				await goToAccessibilityMenuSetting();

				await instanceSettingsPage.resetInstanceSetting();
			});
		});

		await test.step('Verify that the Accessibility Menu is not visible', async () => {
			await expect(
				accessibilityMenuPage.openAccessibilityMenuButton
			).not.toBeAttached();

			await openUserProfileMenu();

			await expect(accessibilityMenuItem).toBeHidden();
		});
	}
);
