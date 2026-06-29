/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {KBServiceConfigurationPage} from '../../../pages/knowledge-base-web/KBServiceConfigurationPage';

const test = loginTest();

let originalCheckInterval: string;
let originalExpirationDateNotificationDateWeeks: string;

test.beforeEach(async ({page}) => {
	const kbServiceConfigurationPage = new KBServiceConfigurationPage(page);

	await kbServiceConfigurationPage.goTo();

	originalCheckInterval =
		await kbServiceConfigurationPage.checkIntervalInput.inputValue();
	originalExpirationDateNotificationDateWeeks =
		await kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput.inputValue();
});

test.afterEach(async ({page}) => {
	if (originalCheckInterval === undefined) {
		return;
	}

	const kbServiceConfigurationPage = new KBServiceConfigurationPage(page);

	await kbServiceConfigurationPage.goTo();

	await kbServiceConfigurationPage.checkIntervalInput.fill(
		originalCheckInterval
	);
	await kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput.fill(
		originalExpirationDateNotificationDateWeeks
	);

	await kbServiceConfigurationPage.save();
});

test(
	'Persists Knowledge Base service configuration changes',
	{tag: '@LPD-92881'},
	async ({page}) => {
		const kbServiceConfigurationPage = new KBServiceConfigurationPage(page);

		const newCheckInterval = String(Number(originalCheckInterval) + 1);
		const newExpirationDateNotificationDateWeeks = String(
			Number(originalExpirationDateNotificationDateWeeks) + 1
		);

		await test.step('Change the configuration values', async () => {
			await kbServiceConfigurationPage.checkIntervalInput.fill(
				newCheckInterval
			);
			await kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput.fill(
				newExpirationDateNotificationDateWeeks
			);

			await kbServiceConfigurationPage.save();
		});

		await test.step('Assert the values persisted', async () => {
			await kbServiceConfigurationPage.goTo();

			await expect(
				kbServiceConfigurationPage.checkIntervalInput
			).toHaveValue(newCheckInterval);
			await expect(
				kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput
			).toHaveValue(newExpirationDateNotificationDateWeeks);
		});
	}
);

test(
	'Resets Knowledge Base service configuration to default values',
	{tag: '@LPD-92881'},
	async ({page}) => {
		const kbServiceConfigurationPage = new KBServiceConfigurationPage(page);

		await test.step('Change and save non-default values', async () => {
			await kbServiceConfigurationPage.checkIntervalInput.fill('99');
			await kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput.fill(
				'9'
			);

			await kbServiceConfigurationPage.save();
		});

		await test.step('Reset to the default values', async () => {
			await kbServiceConfigurationPage.resetToDefaultValues();
		});

		await test.step('Assert the default values were restored', async () => {
			await kbServiceConfigurationPage.goTo();

			await expect(
				kbServiceConfigurationPage.checkIntervalInput
			).toHaveValue('15');
			await expect(
				kbServiceConfigurationPage.expirationDateNotificationDateWeeksInput
			).toHaveValue('1');
		});
	}
);
