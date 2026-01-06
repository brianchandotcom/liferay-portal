/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {doAndGoBack} from '../../../utils/doAndGoBack';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';

const test = mergeTests(
	accessibilityMenuPagesTest,
	instanceSettingsPagesTest,
	loginTest()
);

test.beforeEach(async ({accessibilityMenuPage, instanceSettingsPage, page}) => {
	await doAndGoBack(page, async () => {
		await instanceSettingsPage.goToInstanceSetting(
			'Accessibility',
			'Accessibility Menu'
		);

		await accessibilityMenuPage.enableAccessibilityMenu();
	});

	await performLogout(page);
});

test.afterEach(async ({instanceSettingsPage, page}) => {
	await performLoginViaApi({page, screenName: 'test'});

	await instanceSettingsPage.goToInstanceSetting(
		'Accessibility',
		'Accessibility Menu'
	);

	await instanceSettingsPage.resetInstanceSetting();
});

const OPTIONS = [
	{
		expectedClass: /c-prefers-link-underline/,
		label: 'Underlined Links',
	},
	{
		expectedClass: /c-prefers-letter-spacing-1/,
		label: 'Increased Text Spacing',
	},
	{
		expectedClass: /c-prefers-expanded-text/,
		label: 'Expanded Text',
	},
	{
		expectedClass: /c-prefers-reduced-motion/,
		label: 'Reduced Motion',
	},
];

test(
	'Accessibility menu is accessible using the keyboard',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		const {closeButton, menuTitle, openAccessibilityMenuButton} =
			accessibilityMenuPage;

		await test.step('Open menu with keyboard', async () => {
			await expect(menuTitle).toBeHidden();
			await expect(openAccessibilityMenuButton).not.toBeFocused();

			await page.keyboard.press('Tab');
			await page.keyboard.press('Tab');

			await expect(openAccessibilityMenuButton).toBeFocused();
			await expect(openAccessibilityMenuButton).toBeVisible();

			await page.keyboard.press('Enter');

			await expect(menuTitle).toBeVisible();
		});

		await test.step('Close menu with keyboard', async () => {
			await expect(closeButton).toBeVisible();

			await page.keyboard.press('Tab');

			await expect(closeButton).toBeFocused();

			await page.keyboard.press('Enter');

			await expect(menuTitle).toBeHidden();
		});
	}
);

test(
	'Accessibility menu options can be controlled via the accessibility menu',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		await test.step('Open the accessibility menu', async () => {
			await accessibilityMenuPage.openAccessibilityMenu();
		});

		for (const {expectedClass, label} of OPTIONS) {
			await test.step(`The "${label}" option can be configured via the accessibility menu`, async () => {
				const body = page.locator('body');
				const toggle = page.getByLabel(label);

				await expect(toggle).not.toBeChecked();

				await accessibilityMenuPage.toggle(toggle, true);

				await expect(body).toHaveClass(expectedClass);

				await accessibilityMenuPage.toggle(toggle, false);

				await expect(body).not.toHaveClass(expectedClass);
			});
		}
	}
);

test(
	'Accessibility menu options can be controlled via the keyboard',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		await test.step('Open accessibility menu and focus the close button', async () => {
			await accessibilityMenuPage.openAccessibilityMenu();

			await page.keyboard.press('Tab');

			await expect(accessibilityMenuPage.closeButton).toBeFocused();
		});

		for (const {expectedClass, label} of OPTIONS) {
			const toggle = page.getByLabel(label);

			await test.step(`Focus ${label} using the keyboard`, async () => {
				await page.keyboard.press('Tab');

				await expect(toggle).toBeFocused();
			});

			const body = page.locator('body');

			await test.step(`Toggle ${label} using the keyboard and verify it is still focused`, async () => {
				await page.keyboard.press('Enter');

				await expect(body).toHaveClass(expectedClass);
				await expect(toggle).toBeChecked();
				await expect(toggle).toBeFocused();
			});

			await test.step(`Toggle ${label} again using the keyboard and verify it is still focused`, async () => {
				await page.keyboard.press('Enter');

				await expect(body).not.toHaveClass(expectedClass);
				await expect(toggle).not.toBeChecked();
				await expect(toggle).toBeFocused();
			});
		}
	}
);
