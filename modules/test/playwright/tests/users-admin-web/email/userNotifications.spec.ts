/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {EmailNotificationPage} from '../../../pages/users-admin-web/EmailNotificationPage';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

let emailNotificationPage: EmailNotificationPage;

test.beforeEach(
	async ({emailInstanceSettingsPage, instanceSettingsPage, page}) => {
		await emailInstanceSettingsPage.goToEmailSender();

		await emailInstanceSettingsPage.senderAddressInput.fill(
			'joe@liferay.com'
		);
		await emailInstanceSettingsPage.senderNameInput.fill('Joe Bloggs');

		await instanceSettingsPage.saveAndWaitForAlert();

		await page.waitForLoadState('networkidle');

		const mockMockPage = await page.context().newPage();

		emailNotificationPage = new EmailNotificationPage(mockMockPage);

		await emailNotificationPage.goto();

		if (await emailNotificationPage.deleteAllLink.isVisible()) {
			await emailNotificationPage.deleteAllLink.click();
			await expect(
				emailNotificationPage.noEmailsInQueueMessage
			).toBeVisible();
		}
	}
);

test.afterEach(
	async ({emailInstanceSettingsPage, instanceSettingsPage, page}) => {
		await emailNotificationPage.page.close();

		await performLoginViaApi({page, screenName: 'test'});

		await emailInstanceSettingsPage.goToEmailSender();

		await emailInstanceSettingsPage.senderAddressInput.fill(
			'test@liferay.com'
		);
		await emailInstanceSettingsPage.senderNameInput.fill('Test Test');

		await instanceSettingsPage.saveAndWaitForAlert();
	}
);

test(
	'Can disable account created notification',
	{tag: '@LPD-81993'},
	async ({
		emailInstanceSettingsPage,
		instanceSettingsPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		await emailInstanceSettingsPage.goToAccountCreatedNotification();

		await emailInstanceSettingsPage.accountCreatedNotificationEnabledCheckbox.uncheck();

		await instanceSettingsPage.saveAndWaitForAlert();

		const userName = `user${getRandomString()}`;

		try {
			await usersAndOrganizationsPage.goToUsers();

			await usersAndOrganizationsPage.addUserButton.click();

			await usersAndOrganizationsPage.emailAddressInput.fill(
				`${userName}@liferay.com`
			);
			await usersAndOrganizationsPage.firstNameInput.fill(userName);
			await usersAndOrganizationsPage.lastNameInput.fill(userName);
			await usersAndOrganizationsPage.screenNameInput.fill(userName);
			await usersAndOrganizationsPage.saveUserButton.click();

			await waitForAlert(page, 'The user was created successfully.');

			await emailNotificationPage.page.reload();

			await expect(
				emailNotificationPage.noEmailsInQueueMessage
			).toBeVisible();
		}
		finally {
			await emailInstanceSettingsPage.goToAccountCreatedNotification();

			await emailInstanceSettingsPage.accountCreatedNotificationEnabledCheckbox.check();

			await instanceSettingsPage.saveAndWaitForAlert();
		}
	}
);

test(
	'Can view account created email with password setup link',
	{tag: ['@LPD-81993', '@LPS-176074', '@LPS-85534']},
	async ({page, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await emailNotificationPage.page.reload();

		await expect(
			emailNotificationPage.emailSubjectLink('Your New Account')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage
			.emailSubjectLink('Your New Account')
			.click();

		await expect(
			emailNotificationPage.emailBodyText(userName)
		).toBeVisible();
	}
);

test(
	'Can view password changed notification email',
	{tag: ['@LPD-81993', '@LPS-176074', '@LRQA-68315']},
	async ({editUserPage, page, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await usersAndOrganizationsPage.goto();

		await expect(async () => {
			await usersAndOrganizationsPage.usersSearchBar.fill(userName);
			await usersAndOrganizationsPage.usersSearchBar.press('Enter');

			await (
				await usersAndOrganizationsPage.usersTableRowLink(userName)
			).click();

			await expect(editUserPage.passwordLink).toBeVisible({
				timeout: 1000,
			});
		}).toPass({timeout: 10000});

		await editUserPage.passwordLink.click();

		await editUserPage.passwordInput.fill('newPassword1');
		await editUserPage.passwordReenterInput.fill('newPassword1');
		await editUserPage.saveButton.click();

		await waitForAlert(page);

		await emailNotificationPage.page.reload();

		await expect(
			emailNotificationPage.emailSubjectLink('Password Has Been Changed')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage
			.emailSubjectLink('Password Has Been Changed')
			.click();

		await expect(
			emailNotificationPage.emailBodyText(userName)
		).toBeVisible();
	}
);

test(
	'Can view password reset notification email',
	{tag: ['@LPD-81993', '@LPS-176074', '@LRQA-68316']},
	async ({page, userLoginPage, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await performLogout(page);

		await userLoginPage.goto();

		await userLoginPage.forgotPasswordLink.click();

		await expect(userLoginPage.sendNewPasswordButton).toBeVisible();

		await userLoginPage.emailAddressInput.fill(`${userName}@liferay.com`);
		await userLoginPage.sendNewPasswordButton.click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage.page.reload();

		await expect(
			emailNotificationPage.emailSubjectLink('Reset Your Password')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage
			.emailSubjectLink('Reset Your Password')
			.click();

		await expect(
			emailNotificationPage.emailBodyText(userName)
		).toBeVisible();
	}
);

test(
	'Can change password via password reset link',
	{tag: '@LPD-81993'},
	async ({browser, page, userLoginPage, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await performLogout(page);

		await userLoginPage.goto();

		await userLoginPage.forgotPasswordLink.click();

		await expect(userLoginPage.sendNewPasswordButton).toBeVisible();

		await userLoginPage.emailAddressInput.fill(`${userName}@liferay.com`);
		await userLoginPage.sendNewPasswordButton.click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage.page.reload();

		await emailNotificationPage
			.emailSubjectLink('Reset Your Password')
			.click();

		const resetLinkHref =
			await emailNotificationPage.getEmailBodyLinkHref('update_password');

		expect(resetLinkHref).toContain('update_password');

		const resetContext = await browser.newContext();
		const resetPage = await resetContext.newPage();

		try {
			await resetPage.goto(resetLinkHref);

			await resetPage.getByLabel('Password').first().fill('Password1');
			await resetPage.getByLabel('Reenter Password').fill('Password1');
			await resetPage.getByRole('button', {name: 'Save'}).click();

			await resetPage.waitForURL('**/home**');
		}
		finally {
			await resetContext.close();
		}

		await performLoginViaApi({page, screenName: 'test'});
	}
);

test(
	'Error message displays when passwords do not match in reset link',
	{tag: ['@LPD-81993', '@LPS-60180']},
	async ({browser, page, userLoginPage, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await performLogout(page);

		await userLoginPage.goto();

		await userLoginPage.forgotPasswordLink.click();

		await expect(userLoginPage.sendNewPasswordButton).toBeVisible();

		await userLoginPage.emailAddressInput.fill(`${userName}@liferay.com`);
		await userLoginPage.sendNewPasswordButton.click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage.page.reload();

		await emailNotificationPage
			.emailSubjectLink('Reset Your Password')
			.click();

		const resetLinkHref =
			await emailNotificationPage.getEmailBodyLinkHref('update_password');

		const resetContext = await browser.newContext();
		const resetPage = await resetContext.newPage();

		try {
			await resetPage.goto(resetLinkHref);

			await resetPage.getByLabel('Password').first().fill('Password1');
			await resetPage.getByLabel('Reenter Password').fill('Password2');
			await resetPage.getByRole('button', {name: 'Save'}).click();

			await expect(
				resetPage.getByText('Please enter the same value again.')
			).toBeVisible();
		}
		finally {
			await resetContext.close();
		}

		await performLoginViaApi({page, screenName: 'test'});
	}
);

test(
	'Language changed without redirect on password reset page',
	{tag: ['@LPD-81993', '@LPS-145025']},
	async ({browser, page, userLoginPage, usersAndOrganizationsPage}) => {
		const userName = `user${getRandomString()}`;

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.addUserButton.click();

		await usersAndOrganizationsPage.emailAddressInput.fill(
			`${userName}@liferay.com`
		);
		await usersAndOrganizationsPage.firstNameInput.fill(userName);
		await usersAndOrganizationsPage.lastNameInput.fill(userName);
		await usersAndOrganizationsPage.screenNameInput.fill(userName);
		await usersAndOrganizationsPage.saveUserButton.click();

		await waitForAlert(page, 'The user was created successfully.');

		await performLogout(page);

		await userLoginPage.goto();

		await userLoginPage.forgotPasswordLink.click();

		await expect(userLoginPage.sendNewPasswordButton).toBeVisible();

		await userLoginPage.emailAddressInput.fill(`${userName}@liferay.com`);
		await userLoginPage.sendNewPasswordButton.click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible({timeout: 10000});

		await emailNotificationPage.page.reload();

		await emailNotificationPage
			.emailSubjectLink('Reset Your Password')
			.click();

		const resetLinkHref =
			await emailNotificationPage.getEmailBodyLinkHref('update_password');

		const resetContext = await browser.newContext();
		const resetPage = await resetContext.newPage();

		try {
			await resetPage.goto(resetLinkHref);

			await expect(
				resetPage.getByRole('heading', {name: 'Change Password'})
			).toBeVisible();

			await expect(async () => {
				await resetPage.locator('button:has-text("en-US")').click();

				await expect(
					resetPage.getByRole('menuitem', {name: /中文/})
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await resetPage.getByRole('menuitem', {name: /中文/}).click();

			await expect(
				resetPage.getByRole('heading', {name: '更改密码'})
			).toBeVisible();
		}
		finally {
			await resetContext.close();
		}

		await performLoginViaApi({page, screenName: 'test'});
	}
);
