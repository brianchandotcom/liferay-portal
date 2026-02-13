/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

export const test = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-75032': {enabled: true},
	}),
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

test('LPD-30822 Cookie Banner Accessibility', async ({page}) => {
	await test.step('Enable Third Party Cookies', async () => {
		await updateConsentManagerConfiguration(page, {
			enabled: true,
			forceReload: true,
		});
	});

	await test.step('Check aria-label, role, and paragraph', async () => {
		await page.goto('/');

		await page
			.locator(
				'#p_p_id_com_liferay_cookies_banner_web_portlet_CookiesBannerPortlet_'
			)
			.waitFor({state: 'visible'});

		const cookiesBannerContainer = page.locator(
			'//div[@role="dialog"][@aria-label="banner cookies"]'
		);

		await expect(cookiesBannerContainer).toBeVisible();

		const paragraph = cookiesBannerContainer.locator('p.mb-0');

		await expect(paragraph).toBeVisible();
	});
});
