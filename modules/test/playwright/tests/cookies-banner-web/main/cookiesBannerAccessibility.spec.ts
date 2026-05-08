/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {checkAccessibility} from '../../../utils/checkAccessibility';
import {
	clearConsentCookies,
	resetConsentManagerConfiguration,
	updateConsentManagerConfiguration,
} from './utils/consentManagerConfigurationHelper';

export const test = mergeTests(loginTest(), systemSettingsPageTest);

test.afterEach(async ({systemSettingsPage}) => {
	await test.step('Reset Consent Manager Configuration', async () => {
		await resetConsentManagerConfiguration(systemSettingsPage);
	});

	await test.step('Clear Consent Cookies if present', async () => {
		await clearConsentCookies(systemSettingsPage.page);
	});
});

test('LPD-30822 Cookie Banner Accessibility', async ({page}) => {
	await test.step('Enable Third Party Cookies', async () => {
		await updateConsentManagerConfiguration(page, {
			enabled: true,
			forceReload: true,
		});
	});

	await test.step('Check role, aria-modal, aria-labelledby, heading and paragraph', async () => {
		await page.goto('/');

		const cookiesBannerContainer = page.locator(
			'div[role="dialog"][aria-modal="true"]'
		);

		await cookiesBannerContainer.waitFor({state: 'visible'});

		await expect(cookiesBannerContainer).toBeVisible();

		await expect(cookiesBannerContainer).toHaveAttribute(
			'aria-modal',
			'true'
		);
		await expect(cookiesBannerContainer).toHaveAttribute('aria-labelledby');
		await expect(cookiesBannerContainer).not.toHaveAttribute('aria-label');

		const bannerTitle = cookiesBannerContainer.locator('h2');

		await expect(bannerTitle).toBeVisible();

		const paragraph = cookiesBannerContainer.locator('p.mb-0');

		await expect(paragraph).toBeVisible();
	});

	await test.step('Run axe accessibility check on banner', async () => {
		await checkAccessibility({
			page,
			selectors: ['div[role="dialog"][aria-modal="true"]'],
			soft: false,
		});
	});
});

test('LPD-67788 Cookie Panel toggle switches have aria-labels', async ({
	page,
}) => {
	await test.step('Enable Third Party Cookies', async () => {
		await updateConsentManagerConfiguration(page, {
			enabled: true,
			forceReload: true,
		});
	});

	await test.step('Open Configuration panel', async () => {
		await page.goto('/');

		await page
			.locator('div[role="dialog"][aria-modal="true"]')
			.waitFor({state: 'visible'});

		const configButton = page.getByRole('button', {name: 'Configuration'});

		await expect(configButton).toHaveAttribute(
			'aria-label',
			/configuration/i
		);

		await configButton.click();
	});

	await test.step('Verify toggle switches have non-empty aria-labels', async () => {
		await page
			.locator('[id="cookiesBannerConfiguration"]')
			.waitFor({state: 'visible'});

		const cookiesPanel = page.frameLocator(
			'#cookiesBannerConfiguration iframe'
		);

		await cookiesPanel
			.locator('.toggle-switch-check')
			.first()
			.waitFor({state: 'attached'});

		const toggles = await cookiesPanel
			.locator('.toggle-switch-check')
			.all();

		expect(toggles.length).toBeGreaterThan(0);

		for (const toggle of toggles) {
			const ariaLabel = await toggle.getAttribute('aria-label');

			expect(ariaLabel).toBeTruthy();
		}
	});

	await test.step('Run axe accessibility check on cookie panel', async () => {
		await checkAccessibility({
			page,
			selectors: ['[id="cookiesBannerConfiguration"]'],
			soft: false,
		});
	});
});
