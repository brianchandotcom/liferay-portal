/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';

test(
	'Check captcha input aria-describedby holds the evaluated namespace',
	{tag: ['@LPD-93576']},
	async ({page}) => {
		await page.goto('/');

		await page.getByRole('button', {name: 'Sign In'}).click();

		await page.getByText('Forgot Password').click();

		const namespace =
			'_com_liferay_login_web_portlet_ForgotPasswordPortlet_';

		const captchaText = page.locator(`[id="${namespace}captchaText"]`);

		await expect(captchaText).toBeVisible();

		// The namespace must be evaluated, not rendered as a literal JSP tag

		await expect(captchaText).toHaveAttribute(
			'aria-describedby',
			`${namespace}captchaError`
		);

		// The field is named by its label

		await expect(page.getByLabel('Text Verification')).toBeVisible();
	}
);

test(
	'Check captcha input requires a value before submitting',
	{tag: ['@LPD-24714']},
	async ({page}) => {
		await page.goto('/');

		await page.getByRole('button', {name: 'Sign In'}).click();

		await page.getByText('Forgot Password').click();

		await page.getByLabel('Email Address').fill('test@liferay.com');

		// Submitting with an empty captcha must trigger client-side validation

		await page.getByRole('button', {name: 'Send new password'}).click();

		await expect(
			page.getByText('The Text Verification field is required.')
		).toBeVisible();
	}
);

test('LPD-32888 Check captcha verification text is cleared after a wrong captcha', async ({
	page,
}) => {
	await page.goto(liferayConfig.environment.baseUrl);
	await page.getByRole('button', {name: 'Sign In'}).click();
	await page.getByText('Forgot Password').click();
	await page.waitForTimeout(1000);
	await page.getByLabel('Email Address').fill('test@liferay.com');
	await page
		.locator(
			'[id="_com_liferay_login_web_portlet_ForgotPasswordPortlet_captchaText"]'
		)
		.fill('abcd');
	await page.getByRole('button', {name: 'Send new password'}).click();
	await expect(
		page.locator(
			'[id="_com_liferay_login_web_portlet_ForgotPasswordPortlet_captchaText"]'
		)
	).toBeEmpty();
});
