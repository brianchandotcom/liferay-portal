/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

const test = mergeTests(loginTest(), systemSettingsPageTest);

const FORM_SELECTOR =
	'[id="_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_fm"]';

let originalCheckInterval: string;
let originalNotificationDateWeeks: string;

test.afterEach(async ({systemSettingsPage}) => {
	if (originalCheckInterval === undefined) {
		return;
	}

	await systemSettingsPage.goToSystemSetting('Knowledge Base', 'Service');

	const form = systemSettingsPage.page.locator(FORM_SELECTOR);

	await form
		.locator('input[name$="checkInterval"]')
		.fill(originalCheckInterval);

	await form
		.locator('input[name$="expirationDateNotificationDateWeeks"]')
		.fill(originalNotificationDateWeeks);

	await systemSettingsPage.saveAndWaitForAlert();
});

test(
	'Persists Knowledge Base service configuration changes',
	{tag: '@LPD-92881'},
	async ({systemSettingsPage}) => {
		const {page} = systemSettingsPage;

		const form = page.locator(FORM_SELECTOR);
		const checkIntervalInput = form.locator('input[name$="checkInterval"]');
		const notificationDateWeeksInput = form.locator(
			'input[name$="expirationDateNotificationDateWeeks"]'
		);

		let newCheckInterval = '';
		let newNotificationDateWeeks = '';

		await test.step('Change the configuration values', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Knowledge Base',
				'Service'
			);

			originalCheckInterval = await checkIntervalInput.inputValue();
			originalNotificationDateWeeks =
				await notificationDateWeeksInput.inputValue();

			newCheckInterval = String(Number(originalCheckInterval) + 1);
			newNotificationDateWeeks = String(
				Number(originalNotificationDateWeeks) + 1
			);

			await checkIntervalInput.fill(newCheckInterval);
			await notificationDateWeeksInput.fill(newNotificationDateWeeks);

			await systemSettingsPage.saveAndWaitForAlert();
		});

		await test.step('Assert the values persisted', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Knowledge Base',
				'Service'
			);

			await expect(checkIntervalInput).toHaveValue(newCheckInterval);
			await expect(notificationDateWeeksInput).toHaveValue(
				newNotificationDateWeeks
			);
		});
	}
);
