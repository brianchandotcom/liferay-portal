/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {
	clickAndExpectToBeVisible
} from "../../../utils/clickAndExpectToBeVisible";

export const test = mergeTests(loginTest(), systemSettingsPageTest);

test.afterEach(async ({systemSettingsPage}) => {
	await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Manager');

	await systemSettingsPage.page.waitForLoadState();

	if (
		await systemSettingsPage.page
			.getByRole('button', {name: 'Actions'})
			.isVisible()
	) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: systemSettingsPage.page.getByRole('menuitem', {
				name: 'Reset Default Values',
			}),
			trigger: systemSettingsPage.page.getByRole('button', {
				name: 'Actions',
			}),
		});
	}

	await test.step('Clear Consent Cookies if present', async () => {
		await systemSettingsPage.page
			.context()
			.clearCookies({name: /^CONSENT_TYPE_/});
		await systemSettingsPage.page
			.context()
			.clearCookies({name: /^USER_CONSENT_CONFIGURED/});
	});
});

test('LPD-30822 Cookie Banner Accessibility', async ({
	page,
	systemSettingsPage,
}) => {
	await test.step('Enable Third Party Cookies', async () => {
		await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Manager');

		await systemSettingsPage.page.waitForTimeout(1000);

		const enabledButton = page.getByLabel('Enabled');

		await enabledButton.waitFor({state: 'visible'});

		const isChecked = await enabledButton.isChecked();

		if (!isChecked) {
			await enabledButton.click();
		}

		await expect(enabledButton).toBeChecked();

		const updateButton = page.getByRole('button', {
			name: 'Update',
		});

		const saveButton = page.getByRole('button', {
			name: 'Save',
		});

		if (await saveButton.isVisible()) {
			await saveButton.click();
		}
		else if (await updateButton.isVisible()) {
			await updateButton.click();
		}

		await waitForAlert(page);
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
