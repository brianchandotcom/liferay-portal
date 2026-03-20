/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test.afterEach(async ({userFieldsInstanceSettingsPage}) => {
	await userFieldsInstanceSettingsPage.resetFields();
});

test(
	'User fields can be disabled',
	{tag: '@LPD-81993'},
	async ({
		editUserPage,
		page,
		userFieldsInstanceSettingsPage,
		usersAndOrganizationsPage,
	}) => {
		await test.step('Verify default user form fields', async () => {
			await usersAndOrganizationsPage.goToUsers();

			await usersAndOrganizationsPage.addUserButton.click();

			await expect(editUserPage.screenNameInput).toBeVisible();
			await expect(page.getByLabel('Gender')).not.toBeVisible();
			await expect(page.getByLabel('Birthday')).toBeVisible();
		});

		await test.step('Toggle user field settings', async () => {
			await userFieldsInstanceSettingsPage.goto();

			await userFieldsInstanceSettingsPage.autogenerateCheckbox.check();
			await userFieldsInstanceSettingsPage.birthdayCheckbox.uncheck();
			await userFieldsInstanceSettingsPage.genderCheckbox.check();
			await userFieldsInstanceSettingsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Verify updated user form fields', async () => {
			await usersAndOrganizationsPage.goToUsers();

			await usersAndOrganizationsPage.addUserButton.click();

			await expect(editUserPage.screenNameInput).not.toBeVisible();
			await expect(page.getByLabel('Birthday')).not.toBeVisible();
			await expect(page.getByLabel('Gender')).toBeVisible();
		});

		await test.step('Create user and verify edit form', async () => {
			const userName = getRandomString();

			await usersAndOrganizationsPage.firstNameInput.fill(userName);
			await usersAndOrganizationsPage.lastNameInput.fill(userName);
			await usersAndOrganizationsPage.emailAddressInput.fill(
				`${userName}@liferay.com`
			);
			await usersAndOrganizationsPage.saveUserButton.click();

			await waitForAlert(page, 'The user was created successfully.');

			await usersAndOrganizationsPage.goToUsers();

			await (
				await usersAndOrganizationsPage.usersTableRowLink(userName)
			).click();

			await expect(editUserPage.screenNameInput).toBeVisible();
			await expect(editUserPage.screenNameInput).toBeDisabled();
			await expect(page.getByLabel('Birthday')).not.toBeVisible();
			await expect(page.getByLabel('Gender')).toBeVisible();
		});
	}
);
