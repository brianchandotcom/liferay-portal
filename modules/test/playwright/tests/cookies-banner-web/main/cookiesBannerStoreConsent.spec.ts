/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
} from './utils/consentManagerAfterEach';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-75032': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset Consent Manager Configuration', async () => {
		await resetConsentManagerConfiguration(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage);
	});
});

test.beforeEach(async ({page, systemSettingsPage}) => {
	await test.step('Enable Consent Manager', async () => {
		await systemSettingsPage.goToSystemSetting(
			'Privacy',
			'Consent Manager'
		);

		await systemSettingsPage.page.waitForTimeout(1000);

		const enabledButton = page.getByLabel('Enabled');

		await enabledButton.waitFor({state: 'visible'});

		await page.waitForLoadState();

		await enabledButton.setChecked(true);

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);
	});

	await test.step('Verify Cookies Banner appears, then Accept All cookies', async () => {
		const cookiesBanner = await page.locator(
			'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
		);

		await expect(cookiesBanner).toBeVisible();

		await page.getByRole('button', {name: 'Accept All'}).click();
	});
});

test(
	'Store Consent configuration field validation',
	{tag: '@LPD-78076'},
	async ({page, systemSettingsPage}) => {
		await systemSettingsPage.goToSystemSetting(
			'Privacy',
			'Consent Manager'
		);

		const storeConsentField = page.getByLabel('Store Consent');

		await test.step('Validate Store Consent field is not enabled by default', async () => {
			await expect(storeConsentField).not.toBeChecked();
		});

		await test.step('Verify Store Consent field can be saved', async () => {
			page.once('dialog', async (dialogWindow) => {
				await dialogWindow.dismiss();
			});

			await storeConsentField.dispatchEvent('click');

			await page
				.getByRole('button', {name: 'Update'})
				.dispatchEvent('click');

			await page.waitForLoadState();

			await expect(storeConsentField).toBeChecked();
		});
	}
);
