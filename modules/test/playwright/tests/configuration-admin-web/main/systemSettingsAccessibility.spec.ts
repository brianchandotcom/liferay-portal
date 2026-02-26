/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test.beforeEach(async ({systemSettingsPage}) => {
	await systemSettingsPage.goToSystemSetting('Assets', 'Asset Auto Tagging');
});

test.afterEach(async ({systemSettingsPage}) => {
	await systemSettingsPage.resetToDefaultValues();
});

test(
	'System settings can be saved using the keyboard',
	{tag: '@LPS-97907'},
	async ({page}) => {
		const value = Math.floor(1 + 100 * Math.random()).toString();

		const input = page.getByRole('textbox', {
			name: 'Maximum Number of Tags',
		});

		await test.step('Change value and save using keyboard', async () => {
			await input.scrollIntoViewIfNeeded();

			await input.click();

			await expect(input).toBeFocused();

			await input.fill(value);

			await input.press('Enter');

			await waitForAlert(page);
		});

		await test.step('Assert value is persisted', async () => {
			await page.reload();

			await expect(input).toBeVisible();
			await expect(input).toHaveValue(value);
		});
	}
);
