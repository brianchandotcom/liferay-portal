/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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

	await accessibilityMenuPage.openAccessibilityMenu();
});

test.afterEach(async ({instanceSettingsPage, page}) => {
	await performLoginViaApi({page, screenName: 'test'});

	await instanceSettingsPage.goToInstanceSetting(
		'Accessibility',
		'Accessibility Menu'
	);

	await instanceSettingsPage.resetInstanceSetting();
});

test(
	'Accessibility menu options can be controlled via the accessibility menu',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		const options = [
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

		for (const {expectedClass, label} of options) {
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
