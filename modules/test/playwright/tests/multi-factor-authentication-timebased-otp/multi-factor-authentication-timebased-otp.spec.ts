/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {MultiFactorAuthenticationConfigurationPage} from '../../pages/multi-factor-authentication/MultiFactorAuthenticationConfigurationPage';
import {TimeBasedOneTimePasswordConfigurationPage} from '../../pages/multi-factor-authentication/TimeBasedOneTimePasswordConfigurationPage';
import {HomePage} from '../../pages/portal-web/HomePage';
import {AccountSettingsPage} from '../../pages/users-admin-web/AccountSettingsPage';

export const test = mergeTests(loginTest());

test('LPD-48214 verify that qr code is visible', async ({page}) => {
	const multiFactorAuthPage = new MultiFactorAuthenticationConfigurationPage(
		page
	);

	const timeBasedOTPPage = new TimeBasedOneTimePasswordConfigurationPage(
		page
	);

	const accountSettingsPage = new AccountSettingsPage(page);

	const homePage = new HomePage(page);

	await multiFactorAuthPage.goTo();

	await multiFactorAuthPage.enable();

	await timeBasedOTPPage.goTo();

	await timeBasedOTPPage.enable();

	await homePage.goto();

	await accountSettingsPage.goToMultiFactorAuthenticationSettings();

	await expect(
		await page.getByAltText('otp-configuration-qrcode').getAttribute('src')
	).not.toBeNull();

	await timeBasedOTPPage.goTo();

	await timeBasedOTPPage.disable();

	await multiFactorAuthPage.goTo();

	await multiFactorAuthPage.disable();
});
