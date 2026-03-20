/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {EmailNotificationPage} from '../../../pages/users-admin-web/EmailNotificationPage';
import {UserRegistrationPage} from '../../../pages/users-admin-web/UserRegistrationPage';
import {SiteSettingsPage} from '../../../pages/users-admin-web/site-admin-web/SiteSettingsPage';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'Can view email verification notification with user first name',
	{tag: ['@LPD-81993', '@LPS-176074']},
	async ({
		apiHelpers,
		emailInstanceSettingsPage,
		instanceSettingsPage,
		page,
		userLoginPage,
	}) => {
		await instanceSettingsPage.goToInstanceSetting(
			'User Authentication',
			'General'
		);

		const strangersVerifyCheckbox = page.getByLabel(
			'Require strangers to verify their email address'
		);

		await expect(strangersVerifyCheckbox).toBeVisible();

		await strangersVerifyCheckbox.check();

		await instanceSettingsPage.saveAndWaitForAlert();

		await emailInstanceSettingsPage.goToEmailSender();

		await emailInstanceSettingsPage.senderAddressInput.fill(
			'joe@liferay.com'
		);
		await emailInstanceSettingsPage.senderNameInput.fill('Joe Bloggs');

		await instanceSettingsPage.saveAndWaitForAlert();

		await page.waitForLoadState('networkidle');

		const mockMockPage = await page.context().newPage();
		const emailNotificationPage = new EmailNotificationPage(mockMockPage);

		await emailNotificationPage.goto();

		if (await emailNotificationPage.deleteAllLink.isVisible()) {
			await emailNotificationPage.deleteAllLink.click();
			await expect(
				emailNotificationPage.noEmailsInQueueMessage
			).toBeVisible();
		}

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		try {
			await performLogout(page);

			await userLoginPage.goto();

			await userLoginPage.emailAddressInput.fill(user.emailAddress);
			await userLoginPage.passwordInput.fill(userData['test'].password);
			await userLoginPage.signInButton.click();

			await page.waitForLoadState('networkidle');

			await expect(
				userLoginPage.sendNewVerificationCodeButton
			).toBeVisible({timeout: 10000});

			await userLoginPage.sendNewVerificationCodeButton.click();

			await page.waitForLoadState('networkidle');

			await emailNotificationPage.page.reload();

			await expect(
				emailNotificationPage
					.emailSubjectLink('Email Address Verification')
					.first()
			).toBeVisible({timeout: 10000});

			await emailNotificationPage
				.emailSubjectLink('Email Address Verification')
				.first()
				.click();

			await expect(
				emailNotificationPage.emailBodyText(user.givenName)
			).toBeVisible();
		}
		finally {
			await mockMockPage.close();

			await page.context().clearCookies();
			await performLoginViaApi({page, screenName: 'test'});

			await emailInstanceSettingsPage.goToEmailSender();

			await emailInstanceSettingsPage.senderAddressInput.fill(
				'test@liferay.com'
			);
			await emailInstanceSettingsPage.senderNameInput.fill('Test Test');

			await instanceSettingsPage.saveAndWaitForAlert();

			await instanceSettingsPage.goToInstanceSetting(
				'User Authentication',
				'General'
			);

			await page
				.getByLabel('Require strangers to verify their email address')
				.uncheck();

			await instanceSettingsPage.saveAndWaitForAlert();
		}
	}
);

test(
	'Welcome email contains virtual host URL',
	{tag: ['@LPD-81993', '@LPS-111136']},
	async ({
		apiHelpers,
		emailInstanceSettingsPage,
		instanceSettingsPage,
		page,
	}) => {
		await instanceSettingsPage.goToInstanceSetting(
			'User Authentication',
			'General'
		);

		await page
			.getByLabel('Require strangers to verify their email address')
			.uncheck();

		await instanceSettingsPage.saveAndWaitForAlert();

		await emailInstanceSettingsPage.goToEmailSender();

		await emailInstanceSettingsPage.senderAddressInput.fill(
			'joe@liferay.com'
		);
		await emailInstanceSettingsPage.senderNameInput.fill('Joe Bloggs');

		await instanceSettingsPage.saveAndWaitForAlert();

		await page.waitForLoadState('networkidle');

		const mockMockPage = await page.context().newPage();
		const emailNotificationPage = new EmailNotificationPage(mockMockPage);

		await emailNotificationPage.goto();

		if (await emailNotificationPage.deleteAllLink.isVisible()) {
			await emailNotificationPage.deleteAllLink.click();
			await expect(
				emailNotificationPage.noEmailsInQueueMessage
			).toBeVisible();
		}

		const site = await apiHelpers.headlessSite.createSite({
			name: `Site${getRandomString()}`,
		});

		await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'Home',
		});

		const siteSettingsPage = new SiteSettingsPage(page);
		const siteFriendlyUrl = site.friendlyUrlPath || '';

		try {
			await siteSettingsPage.setVirtualHost(
				siteFriendlyUrl,
				'www.able.com'
			);

			const registrationContext = await page
				.context()
				.browser()!
				.newContext();

			const registrationPage = await registrationContext.newPage();
			const userRegistrationPage = new UserRegistrationPage(
				registrationPage
			);

			try {
				await registrationPage.goto('http://www.able.com:8081');

				await userRegistrationPage.signInButton.click();
				await userRegistrationPage.createAccountLink.click();

				const userName = `user${getRandomString()}`;

				await userRegistrationPage.emailAddressInput.fill(
					`${userName}@liferay.com`
				);
				await userRegistrationPage.firstNameInput.fill(userName);
				await userRegistrationPage.lastNameInput.fill(userName);
				await userRegistrationPage.passwordInput.fill('Password1');
				await userRegistrationPage.reenterPasswordInput.fill(
					'Password1'
				);
				await userRegistrationPage.screenNameInput.fill(userName);

				await userRegistrationPage.saveButton.click();

				await registrationPage.waitForLoadState('networkidle');

				// Wait for the email to be sent asynchronously

				await registrationPage.waitForTimeout(3000);
			}
			finally {
				await registrationContext.close();
			}

			await emailNotificationPage.page.reload();

			await expect(
				emailNotificationPage.emailSubjectLink('www.able.com').first()
			).toBeVisible({timeout: 10000});

			await emailNotificationPage
				.emailSubjectLink('www.able.com')
				.first()
				.click();

			await expect(
				emailNotificationPage.emailBodyText('www.able.com')
			).toBeVisible();
		}
		finally {
			await mockMockPage.close();

			await apiHelpers.headlessSite.deleteSite(site.id);

			await emailInstanceSettingsPage.goToEmailSender();

			await emailInstanceSettingsPage.senderAddressInput.fill(
				'test@liferay.com'
			);
			await emailInstanceSettingsPage.senderNameInput.fill('Test Test');

			await instanceSettingsPage.saveAndWaitForAlert();
		}
	}
);
