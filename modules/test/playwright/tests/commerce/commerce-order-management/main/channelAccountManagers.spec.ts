/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../../fixtures/accountsPagesTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {
	createChannelAccountManagerUser,
	miniumSetUp,
} from '../../utils/commerce';

export const test = mergeTests(
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

let channel: {id: number; name: string};
let companyId: string;
let setupData: Array<{id: number | string; type: string}>;
let site: Site;

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	companyId = await page.evaluate(() => Liferay.ThemeDisplay.getCompanyId());

	const apiHelpers = new DataApiHelpers(page);

	const miniumResult = await miniumSetUp(apiHelpers);

	channel = miniumResult.channel;
	site = miniumResult.site;
	setupData = [...apiHelpers.data];

	await page.close();
});

test.afterAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	apiHelpers.setData(setupData);

	await apiHelpers.clearData();

	await page.close();
});

test(
	'Can add a Channel Account Managers entry',
	{tag: ['@COMMERCE-9988', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		await expect(
			editAccountChannelDefaultsPage.channelAccountManagerRow(
				channel.name,
				channelAccountManager.alternateName
			)
		).toBeVisible();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			expectDuplicateError: true,
			userScreenName: channelAccountManager.alternateName,
		});
	}
);

test(
	'Multiple users can be set as Channel Account Managers for the account',
	{tag: ['@COMMERCE-10000', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});

		const {user: channelAccountManager1} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});
		const {user: channelAccountManager2} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		for (const account of [account1, account2]) {
			await accountsPage.goto();

			await accountsPage.accountNameLink(account.name).click();
			await editAccountPage.channelDefaultsLink.click();

			for (const channelAccountManager of [
				channelAccountManager1,
				channelAccountManager2,
			]) {
				await editAccountChannelDefaultsPage.addChannelAccountManager({
					channelName: channel.name,
					userScreenName: channelAccountManager.alternateName,
				});
			}
		}

		for (const channelAccountManager of [
			channelAccountManager1,
			channelAccountManager2,
		]) {
			await performUserSwitch(page, channelAccountManager.alternateName);

			await page.goto(`/web${site.friendlyUrlPath}/account-management`);

			for (const account of [account1, account2]) {
				await expect(
					accountEntriesManagementPortletPage.accountNameLink(
						account.name
					)
				).toBeVisible();
			}
		}
	}
);

test(
	'A buyer added by a Channel Account Manager is visible to the admin',
	{tag: ['@COMMERCE-10004', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		const buyerEmail = `buyer-${getRandomString()}@liferay.com`;

		const buyer = await apiHelpers.headlessAdminUser.postUserAccount({
			emailAddress: buyerEmail,
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[buyerEmail]
		);

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.usersLink.click();

		await expect(page.getByText(buyer.alternateName).first()).toBeVisible();
	}
);

test(
	'Channel Account Manager can only edit accounts assigned to them',
	{tag: ['@COMMERCE-9993', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const account3 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});

		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		for (const account of [account1, account2]) {
			await accountsPage.goto();

			await accountsPage.accountNameLink(account.name).click();
			await editAccountPage.channelDefaultsLink.click();

			await editAccountChannelDefaultsPage.addChannelAccountManager({
				channelName: channel.name,
				userScreenName: channelAccountManager.alternateName,
			});
		}

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account1.name)
		).toBeVisible();
		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account2.name)
		).toBeVisible();
		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account3.name)
		).not.toBeVisible();
	}
);

test(
	'Accounts eligible via both permissions are not duplicated',
	{tag: ['@COMMERCE-9996', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const account3 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Test Organization ${getRandomString()}`,
			});

		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				organizationActionIds: ['MANAGE_AVAILABLE_ACCOUNTS'],
				siteId: site.id,
			});

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization.id,
			channelAccountManager.emailAddress
		);

		for (const account of [account1, account2]) {
			await accountsPage.goto();

			await accountsPage.accountNameLink(account.name).click();
			await editAccountPage.channelDefaultsLink.click();

			await editAccountChannelDefaultsPage.addChannelAccountManager({
				channelName: channel.name,
				userScreenName: channelAccountManager.alternateName,
			});
		}

		for (const account of [account2, account3]) {
			await apiHelpers.headlessAdminUser.assignAccountToOrganization(
				account.id,
				organization.id
			);
		}

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		for (const account of [account1, account2, account3]) {
			await expect(
				accountEntriesManagementPortletPage.accountNameLink(
					account.name
				)
			).toBeVisible();
		}

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account2.name)
		).toHaveCount(1);
	}
);

test(
	'Channel Account Manager without eligibility sees no accounts',
	{tag: ['@COMMERCE-9992', '@LPD-85008']},
	async ({accountEntriesManagementPortletPage, apiHelpers, page}) => {
		await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});

		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				companyId,
				siteId: site.id,
			});

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.noAccountsFoundMessage
		).toBeVisible();
	}
);

test(
	'Removing the MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL permission hides accounts from the Channel Account Manager',
	{tag: ['@COMMERCE-9995', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {role, user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		await apiHelpers.headlessAdminUser.deleteRole(role.id);

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.noAccountsFoundMessage
		).toBeVisible();
	}
);

test(
	'Channel Account Manager can edit account details',
	{tag: ['@COMMERCE-10005', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await accountEntriesManagementPortletPage
			.accountNameLink(account.name)
			.click();

		const editedName = `${account.name} Edited`;
		const editedDescription = 'Edited by CAM';
		const editedTaxId = '123456';

		await editAccountPage.accountNameInput.fill(editedName);
		await editAccountPage.descriptionInput.fill(editedDescription);
		await editAccountPage.taxIDInput.fill(editedTaxId);
		await editAccountPage.saveButton.click();

		await performUserSwitch(page, 'test');

		await accountsPage.goto();

		await expect(accountsPage.accountNameLink(editedName)).toBeVisible();

		await accountsPage.accountNameLink(editedName).click();

		await expect(editAccountPage.accountNameInput).toHaveValue(editedName);
		await expect(editAccountPage.descriptionInput).toHaveValue(
			editedDescription
		);
		await expect(editAccountPage.taxIDInput).toHaveValue(editedTaxId);
	}
);

test(
	'Channel Account Manager entry can be edited; old user loses visibility, new user gains it',
	{tag: ['@COMMERCE-9989', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {user: channelAccountManager1} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});
		const {user: channelAccountManager2} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager1.alternateName,
		});

		await performUserSwitch(page, channelAccountManager1.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).toBeVisible();

		await performUserSwitch(page, channelAccountManager2.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).not.toBeVisible();

		await performUserSwitch(page, 'test');

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.editChannelAccountManager({
			channelName: channel.name,
			currentUserScreenName: channelAccountManager1.alternateName,
			newUserScreenName: channelAccountManager2.alternateName,
		});

		await performUserSwitch(page, channelAccountManager1.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).not.toBeVisible();

		await performUserSwitch(page, channelAccountManager2.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).toBeVisible();
	}
);

test(
	'Channel Account Manager entry can be removed; user loses visibility',
	{tag: ['@COMMERCE-9990', '@LPD-85008']},
	async ({
		accountEntriesManagementPortletPage,
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});
		const {user: channelAccountManager} =
			await createChannelAccountManagerUser(apiHelpers, {
				accountEntryActionIds: [
					'MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL',
				],
				companyId,
				siteId: site.id,
			});

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.addChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).toBeVisible();

		await performUserSwitch(page, 'test');

		await accountsPage.goto();

		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await editAccountChannelDefaultsPage.deleteChannelAccountManager({
			channelName: channel.name,
			userScreenName: channelAccountManager.alternateName,
		});

		await expect(
			editAccountChannelDefaultsPage.channelAccountManagerRow(
				channel.name,
				channelAccountManager.alternateName
			)
		).not.toBeVisible();

		await performUserSwitch(page, channelAccountManager.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}/account-management`);

		await expect(
			accountEntriesManagementPortletPage.accountNameLink(account.name)
		).not.toBeVisible();
	}
);
