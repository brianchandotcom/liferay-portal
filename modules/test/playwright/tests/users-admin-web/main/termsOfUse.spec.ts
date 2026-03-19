/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test.afterEach(
	async ({page, termsOfUseInstanceSettingsPage, userLoginPage}) => {
		await performLoginViaApi({page, screenName: 'test'});

		await page.waitForLoadState('networkidle');

		if (await userLoginPage.iAgreeButton.isVisible()) {
			await userLoginPage.iAgreeButton.click();
			await page.waitForLoadState('networkidle');

			await termsOfUseInstanceSettingsPage.goto();

			await termsOfUseInstanceSettingsPage.termsOfUseRequiredCheckbox.uncheck();
			await termsOfUseInstanceSettingsPage.saveButton.click();

			await waitForAlert(page);
		}
	}
);

test(
	'Can disable terms of use',
	{tag: '@LPD-81993'},
	async ({
		apiHelpers,
		page,
		termsOfUseInstanceSettingsPage,
		userLoginPage,
	}) => {
		await test.step('Enable terms of use', async () => {
			if (await userLoginPage.iAgreeButton.isVisible()) {
				await userLoginPage.iAgreeButton.click();
				await page.waitForLoadState('networkidle');
			}

			await termsOfUseInstanceSettingsPage.goto();

			await termsOfUseInstanceSettingsPage.termsOfUseRequiredCheckbox.check();
			await termsOfUseInstanceSettingsPage.saveButton.click();

			await page.waitForLoadState('networkidle');

			await expect(
				termsOfUseInstanceSettingsPage.termsOfUseRequiredCheckbox
			).toBeChecked();
		});

		await test.step('Disable terms of use', async () => {
			await termsOfUseInstanceSettingsPage.termsOfUseRequiredCheckbox.uncheck();
			await termsOfUseInstanceSettingsPage.saveButton.click();

			await page.waitForLoadState('networkidle');

			await expect(
				termsOfUseInstanceSettingsPage.termsOfUseRequiredCheckbox
			).not.toBeChecked();
		});

		await test.step('Verify new user can login without terms of use', async () => {
			const userAccount =
				await apiHelpers.headlessAdminUser.postUserAccount();

			await performLogout(page);

			await userLoginPage.goto();
			await userLoginPage.emailAddressInput.fill(
				userAccount.emailAddress
			);
			await userLoginPage.passwordInput.fill('test');
			await userLoginPage.signInButton.click();

			await expect(userLoginPage.iAgreeButton).not.toBeVisible();
			await expect(userLoginPage.userProfileMenuButton).toBeVisible({
				timeout: 30000,
			});
		});
	}
);
