/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {captchaConfigPageTest} from '../../../fixtures/captchaConfigPageTest';
import {loginTest} from '../../../fixtures/loginTest';
import {liferayConfig} from '../../../liferay.config';
import {performLogout} from '../../../utils/performLogin';

export const test = mergeTests(loginTest(), captchaConfigPageTest);

test('LPD-44395: Test sample captcha works', async ({
	captchaConfigPage,
	page,
}) => {
	await captchaConfigPage.goTo();

	const captchaEngine = page.getByRole('combobox');
	await expect(captchaEngine).toBeVisible();
	await captchaEngine.focus();

	for (let i = 0; i < 4; i++) {
		const currentActiveId = await captchaEngine.getAttribute(
			'aria-activedescendant'
		);

		if (currentActiveId.includes('FunctionCaptchaImpl')) {
			await captchaEngine.press('Enter');
			break;
		}

		await captchaEngine.press('ArrowDown');
	}

	await captchaConfigPage.saveConfiguration();

	await performLogout(page);
	await page.goto(liferayConfig.environment.baseUrl);
	await page.getByRole('button', {name: 'Sign In'}).click();
	await page.getByText('Forgot Password').click();
	await page.getByRole('button', {name: 'Send New Password'}).waitFor();
	await page.getByLabel('Email Address').fill('able@test.com');

	await page
		.frameLocator("iframe[title='reCAPTCHA']")
		.getByText("I'm not a robot")
		.click();
	await page.waitForTimeout(1000);

	await page.getByRole('button', {name: 'Send New Password'}).click();

	await expect(
		await page.getByText('Your request completed successfully')
	).toBeVisible();
});
