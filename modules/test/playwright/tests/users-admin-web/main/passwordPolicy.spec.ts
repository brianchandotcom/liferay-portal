/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {PasswordPoliciesAdminPage} from '../../../pages/password-policies-admin-web/PasswordPoliciesAdminPage';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test.afterEach(async ({page}) => {
	const passwordPoliciesPage = new PasswordPoliciesAdminPage(page);

	await passwordPoliciesPage.goTo();
	await passwordPoliciesPage.resetDefaultPasswordPolicy();
});

test(
	'Validate regex password policy',
	{tag: '@LPD-81993'},
	async ({apiHelpers, editUserPage, page, usersAndOrganizationsPage}) => {
		const passwordPoliciesPage = new PasswordPoliciesAdminPage(page);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		async function navigateToUserPassword() {
			await usersAndOrganizationsPage.goto();

			await expect(async () => {
				await usersAndOrganizationsPage.usersSearchBar.fill(
					user.alternateName
				);
				await usersAndOrganizationsPage.usersSearchBar.press('Enter');

				await (
					await usersAndOrganizationsPage.usersTableRowLink(
						user.alternateName
					)
				).click();

				await expect(editUserPage.passwordLink).toBeVisible({
					timeout: 1000,
				});
			}).toPass({timeout: 10000});

			await editUserPage.passwordLink.click();
		}

		async function setPasswordAndExpectSuccess(password: string) {
			await navigateToUserPassword();

			await editUserPage.passwordInput.fill(password);
			await editUserPage.passwordReenterInput.fill(password);
			await editUserPage.saveButton.click();

			await waitForAlert(page);
		}

		async function setPasswordAndExpectError(password: string) {
			await navigateToUserPassword();

			await editUserPage.passwordInput.fill(password);
			await editUserPage.passwordReenterInput.fill(password);
			await editUserPage.saveButton.click();

			await expect(
				page.getByRole('alert').getByText('Error')
			).toBeVisible();
		}

		await test.step('Enable syntax checking with letters-only regex', async () => {
			await passwordPoliciesPage.goTo();

			await page
				.getByRole('link', {name: 'Default Password Policy'})
				.click();

			await page
				.getByRole('button', {name: 'Password Syntax Checking'})
				.click();

			await passwordPoliciesPage.checkSyntaxToggle.check();
			await passwordPoliciesPage.allowDictionaryWordsToggle.uncheck();
			await passwordPoliciesPage.minAlphanumeric.fill('0');
			await passwordPoliciesPage.minLength.fill('0');
			await passwordPoliciesPage.minLowerCase.fill('0');
			await passwordPoliciesPage.minNumbers.fill('0');
			await passwordPoliciesPage.minSymbols.fill('0');
			await passwordPoliciesPage.minUpperCase.fill('0');
			await page.locator('[id$="regex"]').fill('(?=.{4})(?:[a-zA-Z]*)');
			await passwordPoliciesPage.saveButton.click();

			await expect(passwordPoliciesPage.successMessage).toBeVisible();
		});

		await test.step('Verify "NO1else" fails (contains number)', async () => {
			await setPasswordAndExpectError('NO1else');
		});

		await test.step('Verify "tryingHARD" passes (all letters)', async () => {
			await setPasswordAndExpectSuccess('tryingHARD');
		});

		await test.step('Change to alphanumeric regex', async () => {
			await passwordPoliciesPage.goTo();

			await page
				.getByRole('link', {name: 'Default Password Policy'})
				.click();

			const regexInput = page.locator('[id$="regex"]');

			await expect(async () => {
				await page
					.getByRole('button', {name: 'Password Syntax Checking'})
					.click({timeout: 500});

				await expect(regexInput).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await regexInput.fill('(?=.{6})(?:[a-zA-Z0-9]*)');
			await passwordPoliciesPage.saveButton.click();

			await expect(passwordPoliciesPage.successMessage).toBeVisible();
		});

		await test.step('Verify "abcABC" passes', async () => {
			await setPasswordAndExpectSuccess('abcABC');
		});

		await test.step('Verify "123ABC" passes', async () => {
			await setPasswordAndExpectSuccess('123ABC');
		});

		await test.step('Verify "1-2-3ABC" fails (contains hyphens)', async () => {
			await setPasswordAndExpectError('1-2-3ABC');
		});

		await test.step('Set minimumNumbers=2 and verify', async () => {
			await passwordPoliciesPage.goTo();

			await page
				.getByRole('link', {name: 'Default Password Policy'})
				.click();

			await expect(async () => {
				await page
					.getByRole('button', {name: 'Password Syntax Checking'})
					.click({timeout: 500});

				await expect(passwordPoliciesPage.minNumbers).toBeVisible({
					timeout: 500,
				});
			}).toPass({timeout: 5000});

			await passwordPoliciesPage.minNumbers.fill('2');
			await passwordPoliciesPage.saveButton.click();

			await expect(passwordPoliciesPage.successMessage).toBeVisible();
		});

		await test.step('Verify "abcABC" fails (no numbers)', async () => {
			await setPasswordAndExpectError('abcABC');
		});

		await test.step('Verify "12AB" fails (too short)', async () => {
			await setPasswordAndExpectError('12AB');
		});

		await test.step('Verify "12ABcd" passes', async () => {
			await setPasswordAndExpectSuccess('12ABcd');
		});
	}
);
