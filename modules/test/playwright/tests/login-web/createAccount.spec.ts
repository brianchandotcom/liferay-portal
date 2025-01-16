/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {captchaConfigPageTest} from '../../fixtures/captchaConfigPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {performLogout} from '../../utils/performLogin';

export const test = mergeTests(loginTest(), captchaConfigPageTest);

test.describe('Duplicate account creation', () => {
	test('Using already existing email address does not throw failure alert', async ({
		captchaConfigPage,
		page,
	}) => {
		await captchaConfigPage.goTo();

		await captchaConfigPage.disableCreateAccountCaptcha();

		await captchaConfigPage.saveConfiguration();

		await performLogout(page);

		await page.goto(liferayConfig.environment.baseUrl);

		await page.getByRole('button', {name: 'Sign In'}).click();

		await page.getByText('Create Account').click();

		await page.getByLabel('Screen Name').fill(getRandomString());

		await page.getByLabel('Email Address').fill('test@liferay.com');

		await page.getByLabel('First Name').fill(getRandomString());

		await page.getByLabel('Last Name').fill(getRandomString());

		const password = getRandomString();

		await page
			.getByLabel('Password Required', {exact: true})
			.fill(password);

		await page.getByLabel('Reenter Password Required').fill(password);

		await page.getByRole('button', {name: 'Save'}).click();

		await page.waitForTimeout(1000);

		await expect(
			page.getByText('Error:Your request failed to complete.')
		).toBeHidden({timeout: 500});
	});
});
