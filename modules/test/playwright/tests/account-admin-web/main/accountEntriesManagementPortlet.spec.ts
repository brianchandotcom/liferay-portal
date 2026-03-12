/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../fixtures/accountsPagesTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	accountsPagesTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'User can change selected accounts',
	{tag: ['@LPD-28846']},
	async ({
		accountEntriesManagementPortletPage,
		apiHelpers,
		commerceAdminChannelsPage,
		page,
		site,
	}) => {
		test.setTimeout(120000);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				siteGroupId: site.id,
			});
		await commerceAdminChannelsPage.changeCommerceChannelSiteType(
			channel.name,
			'B2B'
		);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'account' + getRandomInt(),
			type: 'business',
		});
		await apiHelpers.headlessAdminUser.postAccount({
			name: 'account' + getRandomInt(),
			type: 'business',
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'role' + getRandomInt(),
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await expect(async () => {
			await page.goto(
				`/web/${site.name}/${layout.friendlyUrlPath}?doAsUserId=${user.id}`
			);

			await expect(
				accountEntriesManagementPortletPage.searchInput
			).toBeEnabled({timeout: 2000});
		}).toPass();

		await accountEntriesManagementPortletPage.selectAccount(account1.name);

		await page.reload();

		await expect(
			await accountEntriesManagementPortletPage.accountEntriesTableRowSelectedCheck(
				account1.name
			)
		).toBeVisible();
	}
);

test(
	'User can search for account',
	{tag: ['@LPD-81993']},
	async ({accountManagementWidgetPage, apiHelpers, page, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		const acmeIncName = `Acme Inc ${getRandomInt()}`;
		const liferayName = `Liferay ${getRandomInt()}`;
		const southBayName = `South Bay Auto Parts ${getRandomInt()}`;

		for (const accountName of [acmeIncName, liferayName, southBayName]) {
			await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'business',
			});
		}

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await accountManagementWidgetPage.accountsTable.search(southBayName);

		await expect(
			accountManagementWidgetPage.accountsTable.cell(acmeIncName)
		).not.toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(liferayName)
		).not.toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(southBayName)
		).toBeVisible();

		await accountManagementWidgetPage.accountsTable.search(acmeIncName);

		await expect(
			accountManagementWidgetPage.accountsTable.cell(acmeIncName)
		).toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(liferayName)
		).not.toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(southBayName)
		).not.toBeVisible();

		await accountManagementWidgetPage.accountsTable.search('');

		await expect(
			accountManagementWidgetPage.accountsTable.cell(acmeIncName)
		).toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(liferayName)
		).toBeVisible();
		await expect(
			accountManagementWidgetPage.accountsTable.cell(southBayName)
		).toBeVisible();
	}
);

test(
	'User can set active account',
	{tag: ['@LPD-81993']},
	async ({accountEntriesManagementPortletPage, apiHelpers, page, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		const account1Name = `Account Name 1 ${getRandomInt()}`;
		const account2Name = `Account Name 2 ${getRandomInt()}`;

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: account1Name,
			type: 'business',
		});

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: account2Name,
			type: 'business',
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account1.id,
			[user.emailAddress]
		);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account2.id,
			[user.emailAddress]
		);

		await expect(async () => {
			await page.goto(
				`/web/${site.name}/${layout.friendlyUrlPath}?doAsUserId=${user.id}`
			);

			await expect(
				accountEntriesManagementPortletPage.searchInput
			).toBeEnabled({timeout: 2000});
		}).toPass();

		await expect(
			await accountEntriesManagementPortletPage.accountEntriesTableRowSelectedCheck(
				account1Name
			)
		).toBeVisible();

		await accountEntriesManagementPortletPage.selectAccount(account2Name);

		await page.reload();

		await expect(
			await accountEntriesManagementPortletPage.accountEntriesTableRowSelectedCheck(
				account2Name
			)
		).toBeVisible();
	}
);
