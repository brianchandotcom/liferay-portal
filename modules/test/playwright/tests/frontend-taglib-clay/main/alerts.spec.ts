/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {claySamplePageTest} from './fixtures/claySamplePageTest';

const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	claySamplePageTest
);

const ALERT_CONTAINER_ID = '#ToastAlertContainer';

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

async function closeAllDefaultAlerts(page: Page) {
	const closeButtons = page
		.locator(ALERT_CONTAINER_ID)
		.locator('[aria-label="Close"]');

	const count = await closeButtons.count();

	if (count > 0) {
		for (let i = 0; i < count; i++) {
			await closeButtons.first().click();
		}

		await expect(closeButtons).toHaveCount(0);
	}
}

test('Testing ClayAlert, Testing ClayAlertStripe,Testing ClayAlertFeedback, Testing ClayAlertToast', async ({
	claySamplePage,
	page,
}) => {
	test.setTimeout(90000);

	const alertEmbeddedSuccess = claySamplePage.alert(
		'Success:This is a success message.'
	);

	const [
		alertFailSubmit,
		alertDisappearsAfterFiveSeconds,
		alertDisappearsAfterTenSeconds,
		alertSuccessSubmit,
	] = TRIGGERED_ALERTS_CONFIG.map((config) =>
		claySamplePage.alert(config.message, config.trigger)
	);

	const allToastAlertsLocator = page
		.locator(ALERT_CONTAINER_ID)
		.getByRole('alert');

	await test.step('ClayAlert: Verify alert contains all required attributes: icon, lead text, and description.', async () => {
		await expect(alertEmbeddedSuccess.icon.first()).toBeVisible();
		await expect(alertEmbeddedSuccess.lead.first()).toBeVisible();
		await expect(alertEmbeddedSuccess.locator.first()).toBeVisible();
	});

	await test.step('ClayAlert: Verify the keyword is semi-bold when alert contains status icon and keyword.', async () => {
		const successAlert = alertEmbeddedSuccess.locator.first();

		await expect(successAlert).toHaveCSS('font-weight', '400');
		await expect(successAlert.locator('.lead')).toHaveCSS(
			'font-weight',
			'600'
		);
	});

	await test.step('ClayAlert: Verify toast message popup can be closed manually.', async () => {
		await alertSuccessSubmit.trigger.click();

		await clickAndExpectToBeHidden({
			target: alertSuccessSubmit.locator,
			trigger: alertSuccessSubmit.close,
		});
	});

	await test.step('ClayAlert: Verify toast message popup will close automatically.', async () => {
		await alertDisappearsAfterFiveSeconds.trigger.click();

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeVisible();

		await page.waitForTimeout(5000);

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeHidden();
	});

	await test.step('ClayAlertStripe: Check if the stripe alert-success displays close button', async () => {
		await expect(alertEmbeddedSuccess.close).toBeVisible();
	});

	await test.step('ClayAlertFeedback: Check if a tooltip message is displayed when have an alert icon without text and the cursor hovers over it.', async () => {
		const alertHover = claySamplePage.alert('Tooltip Content', 'Hover');

		await expect(alertHover.trigger).toBeVisible();

		await alertHover.trigger.hover();

		await expect(alertHover.tooltip).toBeVisible();
	});

	await test.step('ClayAlertToast: Check if alert toast message does disapear.', async () => {
		await alertFailSubmit.trigger.click();

		await expect(alertFailSubmit.locator).toBeVisible();

		await page.waitForTimeout(20000);

		await expect(alertFailSubmit.locator).toBeHidden();
	});

	await test.step('Closing all Alerts', async () => {
		await closeAllDefaultAlerts(page);
	});

	await test.step('ClayAlertToast: Check if an alert in the middle of stack is closed, the others fill the stack.', async () => {
		await alertSuccessSubmit.trigger.click();
		await alertFailSubmit.trigger.click();
		await alertSuccessSubmit.trigger.click();

		await expect(allToastAlertsLocator).toHaveCount(3);

		await expect(allToastAlertsLocator.nth(0)).toHaveText(
			SUCCESS_SUBMIT_MESSAGE
		);
		await expect(allToastAlertsLocator.nth(1)).toHaveText(
			FAIL_SUBMIT_MESSAGE
		);
		await expect(allToastAlertsLocator.nth(2)).toHaveText(
			SUCCESS_SUBMIT_MESSAGE
		);

		await alertFailSubmit.close.click();

		await expect(allToastAlertsLocator).toHaveCount(2);
		await expect(allToastAlertsLocator.nth(0)).toHaveText(
			SUCCESS_SUBMIT_MESSAGE
		);
		await expect(allToastAlertsLocator.nth(1)).toHaveText(
			SUCCESS_SUBMIT_MESSAGE
		);
	});

	await test.step('Closing all Alerts', async () => {
		await closeAllDefaultAlerts(page);
	});

	await test.step('ClayAlertToast: Check if after clicking in a link present on a toast alert, the navigation goes to the link.', async () => {
		await alertDisappearsAfterTenSeconds.trigger.click();

		await expect(alertDisappearsAfterTenSeconds.locator).toBeVisible();

		const linkTextRegex = new RegExp(
			DISAPPEARS_AFTER_TEN_SECONDS_MESSAGE.split(':')[1],

			'i'
		);

		await alertDisappearsAfterTenSeconds.locator
			.getByRole('link', {
				name: linkTextRegex,
			})
			.click();

		await expect(page).toHaveURL(/\/web\/guest$/);
	});

	await test.step('Back to claySamplePage.', async () => {
		await claySamplePage.goto();
	});

	await test.step('ClayAlertToast: Check if toast alert with no actions disappears after 5 seconds.', async () => {
		await alertDisappearsAfterFiveSeconds.trigger.click();

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeVisible();

		await page.waitForTimeout(5000);

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeHidden();
	});

	await test.step('ClayAlertToast: Check if toast alert with no actions disappears after 10 seconds.', async () => {
		await alertDisappearsAfterTenSeconds.trigger.click();

		await expect(alertDisappearsAfterTenSeconds.locator).toBeVisible();

		await page.waitForTimeout(10000);

		await expect(alertDisappearsAfterTenSeconds.locator).toBeHidden();
	});

	await test.step('ClayAlertToast: Check if alert toast message does not disapear when mouse hovers over it.', async () => {
		await alertDisappearsAfterFiveSeconds.trigger.click();

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeVisible();

		await alertDisappearsAfterFiveSeconds.locator.hover();

		await page.waitForTimeout(6000);

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeVisible();

		await page.mouse.move(0, 0);

		await page.waitForTimeout(5000);

		await expect(alertDisappearsAfterFiveSeconds.locator).toBeHidden();
	});

	await test.step('Closing all Alerts', async () => {
		await closeAllDefaultAlerts(page);
	});

	await test.step('ClayAlertToast: Check if multiple alert messages can be displayed.', async () => {
		await alertDisappearsAfterFiveSeconds.trigger.click();

		await alertDisappearsAfterFiveSeconds.locator.hover();

		await alertFailSubmit.trigger.click();
		await alertSuccessSubmit.trigger.click();

		await expect(allToastAlertsLocator).toHaveCount(3);

		await expect(allToastAlertsLocator.nth(0)).toHaveText(
			DISAPPEARS_AFTER_FIVE_SECONDS_MESSAGE
		);
		await expect(allToastAlertsLocator.nth(1)).toHaveText(
			FAIL_SUBMIT_MESSAGE
		);
		await expect(allToastAlertsLocator.nth(2)).toHaveText(
			SUCCESS_SUBMIT_MESSAGE
		);
	});
});
