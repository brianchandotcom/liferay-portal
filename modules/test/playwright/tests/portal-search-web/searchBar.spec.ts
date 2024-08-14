/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {searchPageTest} from '../../fixtures/searchPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {SearchPage} from '../../pages/portal-search-web/SearchPage';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	isolatedLayoutTest({type: 'portlet'}),
	loginTest(),
	productMenuPageTest,
	usersAndOrganizationsPagesTest,
	pagesPagesTest,
	searchPageTest
);

test.describe('Search Bar directs to correct page', () => {
	test('Retains impersonation parameter when suggestions is disabled @LPD-17509', async ({
		apiHelpers,
		layout,
		page,
		searchPage,
		usersAndOrganizationsPage,
	}) => {
		let impersonatePage: Page;
		let impersonateSearchPage: SearchPage;
		let testUser: any;

		await test.step('Create test user', async () => {
			testUser = await apiHelpers.headlessAdminUser.postUserAccount();
		});

		await test.step('Add search bar and results portlet to new page', async () => {
			await page.goto('/web/guest' + layout.friendlyURL);

			await searchPage.addPortlet('Search Bar', 'Search');

			await searchPage.addPortlet('Search Results', 'Search');
		});

		await test.step('Disable search suggestions for added search bar', async () => {
			await searchPage.searchBarPortletInMainContent
				.getByLabel('Options')
				.click();

			await searchPage.configurationMenuItem.click();

			await searchPage.selectPortletConfigurationsCheckbox([
				{
					label: 'Enable Suggestions',
					value: false,
				},
			]);

			await searchPage.savePortletConfiguration();
		});

		await test.step('Impersonate as the test user', async () => {
			await usersAndOrganizationsPage.goto();

			await usersAndOrganizationsPage.goToUsers();

			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					testUser.alternateName
				)
			).click();

			const popupPromise = page.waitForEvent('popup');

			await page
				.getByRole('menuitem', {
					name: 'Impersonate User (Opens a new window)',
				})
				.click();

			impersonatePage = await popupPromise;

			impersonateSearchPage = new SearchPage(impersonatePage);
		});

		await test.step('Check that impersonation parameter persists', async () => {
			await impersonatePage
				.getByRole('menuitem', {name: layout.nameCurrentValue})
				.click();

			await impersonateSearchPage.searchKeywordInMainContent('test');

			await expect(
				impersonateSearchPage.searchResultsTotalLabel
			).toHaveText(/Results for test/);

			await expect(impersonatePage).toHaveURL(/doAsUserId=.+/);
			await expect(impersonatePage).toHaveURL(/q=test/);
		});
	});
});
