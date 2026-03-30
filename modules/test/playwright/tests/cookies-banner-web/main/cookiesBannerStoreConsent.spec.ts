/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {consentManagerConfigurationPageTest} from '../../../fixtures/consentManagerConfigurationPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

export const test = mergeTests(
	consentManagerConfigurationPageTest,
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
		await clearConsentCookies(systemSettingsPage.page);
	});
});

test.beforeEach(async ({page}) => {
	await test.step('Enable Consent Manager', async () => {
		await updateConsentManagerConfiguration(page, {
			enabled: true,
			forceReload: true,
		});
	});

	await test.step('Verify Cookies Banner appears, then Accept All cookies', async () => {
		const cookiesBanner = page.getByRole('dialog', {
			name: 'banner cookies',
		});

		await expect(cookiesBanner).toBeVisible();

		await page.getByRole('button', {name: 'Accept All'}).click();
	});
});

test(
	'Store Consent configuration field validation',
	{tag: '@LPD-78076'},
	async ({consentManagerConfigurationPage}) => {
		await test.step('Validate Store Consent field is not enabled by default', async () => {
			await expect(
				consentManagerConfigurationPage.storeConsentCheckbox
			).not.toBeChecked();
		});

		await test.step('Verify Store Consent field can be saved', async () => {
			await updateConsentManagerConfiguration(
				consentManagerConfigurationPage.page,
				{
					enabled: true,
					storeConsent: true,
				}
			);

			await expect(
				consentManagerConfigurationPage.storeConsentCheckbox
			).toBeChecked();
		});
	}
);
