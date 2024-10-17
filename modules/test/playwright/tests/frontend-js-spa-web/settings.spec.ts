/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../utils/waitForAlert';
import isSPAEnabled from './utils/isSPAEnabled';

export const test = mergeTests(
	isolatedLayoutTest(),
	loginTest(),
	systemSettingsPageTest
);

test(
	'Exclude path in SPA Settings',
	{
		tag: '@LPS-108376',
	},
	async ({layout, page, systemSettingsPage}) => {
		await test.step('Navigate to an isolated page', async () => {
			await page.goto(layout.friendlyURL);
		});

		await test.step('Check if SPA is enabled', async () => {
			expect(await isSPAEnabled({page})).toBeTruthy();
		});

		await test.step('Exclude path in SPA Settings', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Infrastructure',
				'Frontend SPA Infrastructure'
			);

			const customExcludedPathsInput = page.getByLabel(
				'Custom Excluded Paths',
				{
					exact: true,
				}
			);

			await customExcludedPathsInput.waitFor({state: 'visible'});
			await customExcludedPathsInput.click();
			await customExcludedPathsInput.fill(layout.friendlyURL);

			const updateButton = page.getByRole('button', {
				name: 'Update',
			});

			const saveButton = page.getByRole('button', {
				name: 'Save',
			});

			if (await saveButton.isVisible()) {
				await saveButton.click();
			}
			else if (await updateButton.isVisible()) {
				await updateButton.click();
			}

			await waitForAlert(page);
		});

		await test.step('Go back to isolated page and check SPA', async () => {
			await page.goto(layout.friendlyURL);

			expect(await isSPAEnabled({page})).toBeFalsy();
		});
	}
);
