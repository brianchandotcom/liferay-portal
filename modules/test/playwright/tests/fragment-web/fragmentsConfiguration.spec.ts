/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../utils/waitForAlert';

const test = mergeTests(loginTest(), systemSettingsPageTest);

test(
	'Admin user can enable and disable the propagation of contributed fragments on instance startup',
	{
		tag: '@LPS-177286',
	},
	async ({page, systemSettingsPage}) => {

		// Go to system settings

		await systemSettingsPage.goToSystemSetting(
			'Page Fragments',
			'Page Fragments'
		);

		// Disable propagate default fragments

		const checkBox = page.getByLabel(
			'Propagate Default Fragments Changes During Deploy Process Automatically'
		);

		await checkBox.uncheck({trial: true});

		await checkBox.uncheck();

		const propagateChangesButton = page.getByRole('button', {
			name: 'Propagate Changes',
		});

		await expect(propagateChangesButton).toBeVisible();

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);

		// Propagate changes manually

		await propagateChangesButton.click();

		await page.getByRole('button', {name: 'Continue'}).click();

		await waitForAlert(
			page,
			'Success:The changes in the contributed fragments have been propagated successfully.'
		);

		await expect(
			page.getByText('All changes were propagated.')
		).toBeVisible();

		await expect(propagateChangesButton).toBeDisabled();

		// Enable propagate default fragments

		await checkBox.check();

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(async () => {
			await page.getByRole('button', {name: 'Save'}).click();

			await expect(
				page.getByText('Success:Your request completed successfully.')
			).toBeVisible({timeout: 100});
		}).toPass();
	}
);
