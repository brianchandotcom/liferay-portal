/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {captchaConfigPageTest} from '../../fixtures/captchaConfigPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {passwordPoliciesAdminPageTest} from '../../fixtures/passwordPoliciesAdminConfigPageTest';
import {TPasswordPolicy} from '../../helpers/PasswordPolicyApiHelper';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {performLoginViaApi, performLogout} from '../../utils/performLogin';

export const test = mergeTests(
	applicationsMenuPageTest,
	captchaConfigPageTest,
	loginTest(),
	passwordPoliciesAdminPageTest
);

test.beforeEach(
	'Disable create account CAPTCHA',
	async ({captchaConfigPage, page}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		if (await page.getByRole('button', {name: 'Sign In'}).isVisible()) {
			await performLoginViaApi(page, 'test');
		}

		await captchaConfigPage.goTo();

		await captchaConfigPage.disableCreateAccountCaptcha();
	}
);

test.afterEach(
	'Reset CAPTCHA configuration',
	async ({captchaConfigPage, page, passwordPoliciesAdminConfigPage}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		if (await page.getByRole('button', {name: 'Sign In'}).isVisible()) {
			await performLoginViaApi(page, 'test');
		}

		await captchaConfigPage.goTo();

		await captchaConfigPage.resetCaptchaConfiguration();

		await page.goto(liferayConfig.environment.baseUrl);

		await passwordPoliciesAdminConfigPage.goTo();
		await passwordPoliciesAdminConfigPage.resetDefaultPasswordPolicy();
	}
);

test(
	'Edit default password policy with syntax checking and 1 lowercase and check that it shows an error for Minimum Lower Case error',
	{tag: '@LPD-48268'},
	async ({page, passwordPoliciesAdminConfigPage}) => {
		const passwordPolicy: TPasswordPolicy = {
			checkSyntaxToggle: true,
			minLowerCase: 1,
			name: getRandomString()
		};
		await passwordPoliciesAdminConfigPage.goTo();
		await passwordPoliciesAdminConfigPage.editDefaultPasswordPolicy(
			passwordPolicy
		);

		performLogout(page);

		await page.getByRole('button', {name: 'Sign In'}).click();

		await page.getByText('Create Account').click();

		await page.getByLabel('Screen Name').fill(getRandomString());

		await page
			.getByLabel('Email Address')
			.fill(getRandomString() + '@liferay.com');

		await page.getByLabel('First Name').fill(getRandomString());

		await page.getByLabel('Last Name').fill(getRandomString());

		const password = 'ABC123';

		await page
			.getByLabel('Password Required', {exact: true})
			.fill(password);

		await page.getByLabel('Reenter Password Required').fill(password);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText(
				'Close Error: That password must contain at least 1 lowercase characters. User'
			)
		).toBeVisible();
	}
);
