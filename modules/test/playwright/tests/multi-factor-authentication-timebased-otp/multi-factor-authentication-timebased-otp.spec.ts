/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../fixtures/accountSettingsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {multiFactorAuthenticationPagesTest} from '../../fixtures/multiFactorAuthenticationPagesTest';

export const test = mergeTests(
	accountSettingsPagesTest,
	loginTest(),
	multiFactorAuthenticationPagesTest
);

test('LPD-48214 verify that QR code is visible', async ({
	accountSettingsPage,
	multiFactorAuthenticationConfigurationPage,
	page,
	timeBasedOneTimePasswordConfigurationPage,
}) => {
	await multiFactorAuthenticationConfigurationPage.goto();

	await multiFactorAuthenticationConfigurationPage.enable();

	await timeBasedOneTimePasswordConfigurationPage.goto();

	await timeBasedOneTimePasswordConfigurationPage.enable();

	await accountSettingsPage.goToMultiFactorAuthenticationSettings();

	await expect(
		await page.getByAltText('otp-configuration-qrcode').getAttribute('src')
	).not.toBeNull();

	await timeBasedOneTimePasswordConfigurationPage.goto();

	await timeBasedOneTimePasswordConfigurationPage.resetConfiguration();

	await multiFactorAuthenticationConfigurationPage.goto();

	await multiFactorAuthenticationConfigurationPage.resetConfiguration();
});
