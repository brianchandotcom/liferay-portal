/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	claySamplePageTest
);

const FAIL_SUBMIT_MESSAGE = 'Error:An unexpected error occurred.';
const DISAPPEARS_AFTER_FIVE_SECONDS_MESSAGE =
	'Info:Your request completed successfully.';
const DISAPPEARS_AFTER_TEN_SECONDS_MESSAGE =
	'Info:This toast alert with action button should disappear after 10 seconds';
const SUCCESS_SUBMIT_MESSAGE = 'Success:Your request completed successfully.';

const TRIGGERED_ALERTS_CONFIG = [
	{message: FAIL_SUBMIT_MESSAGE, trigger: 'Fail Submit'},
	{
		message: DISAPPEARS_AFTER_FIVE_SECONDS_MESSAGE,
		trigger: 'Disappears After 5 Seconds',
	},
	{
		message: DISAPPEARS_AFTER_TEN_SECONDS_MESSAGE,
		trigger: 'Disappears After 10 Seconds',
	},
	{message: SUCCESS_SUBMIT_MESSAGE, trigger: 'Success Submit'},
];

test.beforeEach('Select Alerts tab', async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.ALERTS);
});

test.describe('Testing Alert Scenarios', () => {
	test('Testing ClayAlert', async ({claySamplePage, page}) => {
		const alertEmbeddedSuccess = claySamplePage.alert(
			'Success:This is a success message.'
		);

		const [, alertDisappearsAfterFiveSeconds, , alertSuccessSubmit] =
			TRIGGERED_ALERTS_CONFIG.map((config) =>
				claySamplePage.alert(config.message, config.trigger)
			);

		await test.step('Verify alert contains all required attributes: icon, lead text, and description.', async () => {
			await expect(alertEmbeddedSuccess.icon.first()).toBeVisible();

			await expect(alertEmbeddedSuccess.lead.first()).toBeVisible();

			await expect(alertEmbeddedSuccess.locator.first()).toBeVisible();
		});

		await test.step('Verify the keyword is semi-bold when alert contains status icon and keyword.', async () => {
			const successAlert = alertEmbeddedSuccess.locator.first();

			await expect(successAlert).toHaveCSS('font-weight', '400');

			await expect(successAlert.locator('.lead')).toHaveCSS(
				'font-weight',
				'600'
			);
		});

		await test.step('Verify toast message popup can be closed manually.', async () => {
			await alertSuccessSubmit.trigger.click();

			await clickAndExpectToBeHidden({
				target: alertSuccessSubmit.locator,
				trigger: alertSuccessSubmit.close,
			});
		});

		await test.step('Verify toast message popup will close automatically.', async () => {
			await alertDisappearsAfterFiveSeconds.trigger.click();

			await expect(alertDisappearsAfterFiveSeconds.locator).toBeVisible();

			await page.waitForTimeout(5000);

			await expect(alertDisappearsAfterFiveSeconds.locator).toBeHidden();
		});
	});

	test('Testing ClayAlertStripe', async ({claySamplePage}) => {
		const alertEmbeddedSuccess = claySamplePage.alert(
			'Success:This is a success message.'
		);

		await test.step('Check if the stripe alert-success displays close button.', async () => {
			await expect(alertEmbeddedSuccess.close).toBeVisible();
		});
	});
});
