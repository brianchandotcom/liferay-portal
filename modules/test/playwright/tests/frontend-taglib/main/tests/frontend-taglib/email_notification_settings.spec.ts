/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import getRandomString from '../../../../../utils/getRandomString';
import {waitForEditor} from '../../../../../utils/waitFor';
import {waitForAlert} from '../../../../../utils/waitForAlert';

const test = mergeTests(
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-36105': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest()
);

test(
	'Email verification notification can be edited and saved',
	{tag: '@LPD-52952'},
	async ({instanceSettingsPage, page}) => {
		await test.step('Go to Email Verification Notification page', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				'Email',
				'Email Verification Notification'
			);

			await waitForEditor({page});
		});

		const sampleEmailBodyText = getRandomString();
		let originalEmailBodyText: string;

		const editable = page.locator('.lfr-ck .ck-editor__editable');

		await test.step('Edit Body content and save', async () => {
			originalEmailBodyText = await page
				.locator('input[data-field-name=adminEmailVerificationBody]')
				.inputValue();

			await editable.fill(sampleEmailBodyText);

			await instanceSettingsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Go back to Email Verification Notification page', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				'Email',
				'Email Verification Notification'
			);

			await waitForEditor({page});
		});

		await test.step('Check if modified Body content was saved', async () => {
			await expect(editable).toHaveText(sampleEmailBodyText);
		});

		await test.step('Restore original Body content', async () => {
			await editable.fill(originalEmailBodyText);

			await instanceSettingsPage.saveButton.click();

			await waitForAlert(page);
		});
	}
);
