/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(instanceSettingsPagesTest, loginTest());

test.beforeEach(async ({instanceSettingsPage, page}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Custom Dialogs',
		'Custom Dialogs'
	);

	await page.getByLabel('Enabled').check();

	await instanceSettingsPage.saveAndWaitForAlert();
});

test.afterEach(async ({instanceSettingsPage, page}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Custom Dialogs',
		'Custom Dialogs'
	);

	await page.getByLabel('Enabled').uncheck();

	await instanceSettingsPage.saveAndWaitForAlert();
});

test('Can render custom alert modal', {tag: '@LPS-165263'}, async ({page}) => {
	await page.evaluate(() => {

		// @ts-ignore

		Liferay.Util.openAlertModal({
			message: 'Test Alert Modal',
			onConfirm: () => {},
		});
	});

	await expect(page.locator('.modal-body')).toHaveText('Test Alert Modal');
});

test(
	'Can render custom confirm modal',
	{tag: '@LPS-165262'},
	async ({page}) => {
		await page.evaluate(() => {

			// @ts-ignore

			Liferay.Util.openConfirmModal({
				message: 'Test Confirm Modal',
				onConfirm: () => {},
			});
		});

		await expect(page.locator('.modal-body')).toContainText(
			'Test Confirm Modal'
		);
	}
);
