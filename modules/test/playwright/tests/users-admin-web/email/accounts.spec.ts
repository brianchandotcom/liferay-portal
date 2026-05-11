/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../fixtures/accountsPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {MockMockPage} from '../../../pages/smtp-server/MockMockPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	performUserSwitch,
} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	accountsPagesTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'Can invite new user to account',
	{tag: ['@LPD-58118']},
	async ({
		accountUserInvitePage,
		accountUsersPage,
		accountsPage,
		apiHelpers,
		editAccountPage,
		editUserPage,
		emailDomainsInstanceSettingsPage,
		page,
	}) => {
		await emailDomainsInstanceSettingsPage.goto();

		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

		try {
			await accountsPage.goto();

			await expect(accountsPage.accountsTable.searchInput).toBeEditable();

			await accountsPage.accountsTable.newButton.click();

			const domain = 'liferay.com';
			const name = 'Account' + getRandomInt();

			await editAccountPage.createAccount(apiHelpers, {
				domains: [domain],
				name,
			});

			await expect(editAccountPage.domainCell(domain)).toBeVisible();

			const randomString = getRandomString();
			const emailAddress = `${randomString}@liferay.com`;

			await editAccountPage.usersLink.click();
			await accountUsersPage.usersTable.newButton.click();
			await accountUsersPage.inviteUserMenuItem.click();
			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.firstEntry)
				.fill(emailAddress);
			await accountUserInvitePage
				.emailAddressInput(accountUserInvitePage.firstEntry)
				.press('Enter');
			await accountUserInvitePage.inviteButton.click();

			await waitForAlert(page);

			await performLogout(page);

			const mockMockPage = new MockMockPage(page);

			await mockMockPage.assertEmail({
				body: `Test Test has invited you to join ${name}.`,
				subject: name,
			});

			await page.getByRole('link', {name: 'Create Account'}).click();

			await editUserPage.firstNameInput.fill(randomString);
			await editUserPage.lastNameInput.fill(randomString);
			await editUserPage.screenNameInput.fill(randomString);
			await editUserPage.passwordInput.fill(randomString);
			await editUserPage.passwordReenterInput.fill(randomString);
			await editUserPage.saveButton.click();

			await expect(
				editAccountPage.accountCreationSuccessMessage
			).toBeVisible();

			await performLoginViaApi({page, screenName: 'test'});

			await accountsPage.goto();

			await expect(accountsPage.accountsTable.searchInput).toBeEditable();

			await (await accountsPage.accountsTable.cellLink(name)).click();
			await editAccountPage.usersLink.click();

			await expect(
				accountUsersPage.usersTable.cell(
					`${randomString} ${randomString}`
				)
			).toBeVisible();
			await expect(
				accountUsersPage.usersTable.cell(emailAddress)
			).toBeVisible();
		}
		finally {
			await performUserSwitch(page, 'test');

			await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
				false
			);
		}
	}
);
