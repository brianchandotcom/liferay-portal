/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {consentManagerConfigurationPageTest} from '../../../fixtures/consentManagerConfigurationPageTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {
	clearConsentCookies,
	resetAllConsentManagerConfigurations,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

export const test = mergeTests(
	consentManagerConfigurationPageTest,
	loginTest(),
	systemSettingsPageTest
);

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset All Consent Manager Configurations', async () => {
		await resetAllConsentManagerConfigurations(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage.page);
	});
});

test(
	'Pressing Preview on the Cookie Banner page renders the banner inside the preview modal',
	{tag: '@LPD-86660'},
	async ({page, systemSettingsPage}) => {
		await test.step('Enable Consent Manager so the Cookie Banner sub-tab is reachable', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: true,
				forceReload: true,
			});
		});

		await test.step('Go to Privacy → Cookie Banner', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Privacy',
				'Cookie Banner'
			);
		});

		const previewButton = page.getByRole('button', {
			exact: true,
			name: 'Preview',
		});

		await test.step('Verify a Preview button is present on the page', async () => {
			await expect(previewButton).toBeVisible();
		});

		await test.step('Click the Preview button and verify the cookie banner is rendered inside the modal', async () => {
			await previewButton.click();

			const previewFrame = page.frameLocator('iframe[title="Preview"]');

			await expect(
				previewFrame.getByRole('dialog', {name: 'banner cookies'})
			).toBeVisible();
		});
	}
);

test(
	'Pressing Preview on the Cookie Panel page renders the banner inside the preview modal',
	{tag: '@LPD-86661'},
	async ({page, systemSettingsPage}) => {
		await test.step('Enable Consent Manager so the Cookie Panel sub-tab is reachable', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: true,
				forceReload: true,
			});
		});

		await test.step('Go to Privacy → Cookie Panel', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Privacy',
				'Cookie Panel'
			);
		});

		const previewButton = page.getByRole('button', {
			exact: true,
			name: 'Preview',
		});

		await test.step('Verify a Preview button is present on the page', async () => {
			await expect(previewButton).toBeVisible();
		});

		await test.step('Click the Preview button and verify the cookie banner is rendered inside the modal', async () => {
			await previewButton.click();

			const previewFrame = page.frameLocator('iframe[title="Preview"]');

			await expect(
				previewFrame.getByRole('dialog', {name: 'banner cookies'})
			).toBeVisible();
		});
	}
);

test(
	'Preview banner buttons are inert and the privacy policy link does not navigate',
	{tag: ['@LPD-86660', '@LPD-86661']},
	async ({page, systemSettingsPage}) => {
		await test.step('Enable Consent Manager so the Cookie Banner sub-tab is reachable', async () => {
			await updateConsentManagerConfiguration(page, {
				enabled: true,
				forceReload: true,
			});
		});

		await test.step('Open the Preview modal from the Cookie Banner page', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Privacy',
				'Cookie Banner'
			);

			await page
				.getByRole('button', {exact: true, name: 'Preview'})
				.click();
		});

		const previewFrame = page.frameLocator('iframe[title="Preview"]');
		const banner = previewFrame.getByRole('dialog', {
			name: 'banner cookies',
		});

		await expect(banner).toBeVisible();

		await test.step('Clicking Accept All keeps the banner visible (no action persisted)', async () => {
			await banner.getByRole('button', {name: 'Accept All'}).click();

			await expect(banner).toBeVisible();
		});

		await test.step('Clicking the privacy policy link does not navigate away from the preview', async () => {
			const policyLink = banner.locator('p.mb-0 a');

			if (await policyLink.isVisible()) {
				await policyLink.click();

				await page.waitForTimeout(500);

				await expect(banner).toBeVisible();
			}
		});

		await test.step('Configuration button still opens the cookie configuration panel', async () => {
			await banner.getByRole('button', {name: 'Configuration'}).click();

			const cookieConfigurationFrame = previewFrame.frameLocator(
				'iframe[title="Cookie Configuration"]'
			);

			await expect(
				cookieConfigurationFrame.getByRole('heading', {
					name: 'Strictly Necessary Cookies',
				})
			).toBeVisible();
		});
	}
);
