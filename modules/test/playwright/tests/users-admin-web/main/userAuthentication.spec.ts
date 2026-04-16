/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {performLogout, userData} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

const adminLogin = 'test@liferay.com';
const {password: adminPassword} = userData['test'];

let adminUserId: string;

test.afterEach(async ({page, userAuthenticationGeneralPage, userLoginPage}) => {
	await page.goto('/');
	await page.waitForLoadState('networkidle');

	if (await userLoginPage.signInNavButton.isVisible()) {
		await userLoginPage.signInNavButton.click();

		await expect(userLoginPage.loginInput).toBeVisible({timeout: 5000});

		if (await userLoginPage.idInput.isVisible().catch(() => false)) {
			await userLoginPage.loginInput.fill(adminUserId);
		}
		else {
			await userLoginPage.loginInput.fill(adminLogin);
		}

		await userLoginPage.passwordInput.fill(adminPassword);
		await userLoginPage.signInButton.click();

		await expect(userLoginPage.userProfileMenuButton).toBeVisible({
			timeout: 30000,
		});
	}

	await userAuthenticationGeneralPage.resetFields();
});

test(
	'Can change user general authentication',
	{tag: '@LPD-81993'},
	async ({
		page,
		userAuthenticationGeneralPage,
		userLoginPage,
		userRegistrationPage,
	}) => {
		adminUserId = await page.evaluate(() =>
			Liferay.ThemeDisplay.getUserId()
		);

		await test.step('Change authentication to By User ID and disable company email accounts', async () => {
			await userAuthenticationGeneralPage.goto();

			await userAuthenticationGeneralPage.authenticationMethodSelect.selectOption(
				'By User ID'
			);
			await userAuthenticationGeneralPage.allowStrangersWithCompanyEmailCheckbox.uncheck();
			await userAuthenticationGeneralPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Verify login form shows ID field', async () => {
			await performLogout(page);

			await userLoginPage.signInNavButton.click();

			await expect(userLoginPage.idInput).toBeVisible();
		});

		await test.step('Verify creating account with company email shows error', async () => {
			await userLoginPage.createAccountLink.click();

			await page.waitForLoadState('networkidle');

			await userRegistrationPage.screenNameInput.fill('ScreenName');
			await userRegistrationPage.emailAddressInput.fill(
				'userEmailAddress@liferay.com'
			);
			await userRegistrationPage.firstNameInput.fill('FirstName');
			await userRegistrationPage.lastNameInput.fill('LastName');
			await userRegistrationPage.passwordInput.fill('Password1');
			await userRegistrationPage.reenterPasswordInput.fill('Password1');

			await userRegistrationPage.saveButton.click();

			await expect(
				page
					.getByRole('alert')
					.getByText(
						'The email address you requested is not valid because its domain is reserved.'
					)
			).toBeVisible();
		});

		await test.step('Login with User ID', async () => {
			await page.goto('/');

			await userLoginPage.signInNavButton.click();

			await userLoginPage.idInput.fill(adminUserId);
			await userLoginPage.passwordInput.fill(adminPassword);
			await userLoginPage.signInButton.click();

			await expect(userLoginPage.userProfileMenuButton).toBeVisible({
				timeout: 30000,
			});
		});

		await test.step('Change to Email Address auth and disable auto-login, forgot password, create accounts', async () => {
			await userAuthenticationGeneralPage.goto();

			await userAuthenticationGeneralPage.authenticationMethodSelect.selectOption(
				'By Email Address'
			);
			await userAuthenticationGeneralPage.allowUsersAutoLoginCheckbox.uncheck();
			await userAuthenticationGeneralPage.allowUsersRequestPasswordResetCheckbox.uncheck();
			await userAuthenticationGeneralPage.allowStrangersCheckbox.uncheck();
			await userAuthenticationGeneralPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Verify login form hides remember me, forgot password, and create account', async () => {
			await performLogout(page);

			await userLoginPage.signInNavButton.click();

			await expect(userLoginPage.emailAddressInput).toBeVisible();
			await expect(userLoginPage.createAccountLink).not.toBeVisible();
			await expect(userLoginPage.forgotPasswordLink).not.toBeVisible();
			await expect(userLoginPage.rememberMeCheckbox).not.toBeVisible();
		});
	}
);
