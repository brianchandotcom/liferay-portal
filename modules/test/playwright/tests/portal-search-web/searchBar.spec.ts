/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {searchPageTest} from '../../fixtures/searchPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {SearchPage} from '../../pages/portal-search-web/SearchPage';
import {getRandomInt} from '../../utils/getRandomInt';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	productMenuPageTest,
	usersAndOrganizationsPagesTest,
	pagesPagesTest,
	searchPageTest
);

test.describe('Searchbar directs to correct page', () => {
	const TEST_PAGE = `Test${getRandomInt()}`;

	test.beforeEach(async ({page, productMenuPage, staticPagesPage}) => {
		await test.step('Create a new test page', async () => {
			await productMenuPage.openProductMenuIfClosed();

			await productMenuPage.goToPages();

			await page.getByRole('button', {name: 'New'}).click();

			await page
				.getByRole('menuitem', {exact: true, name: 'Page'})
				.click();

			await staticPagesPage.addWidgetPage(TEST_PAGE);
		});
	});

	test.afterEach(async ({page, productMenuPage}) => {
		await test.step('Delete test page', async () => {
			await page.goto('/');

			await productMenuPage.openProductMenuIfClosed();

			await productMenuPage.goToPages();

			await page
				.locator('li')
				.filter({hasText: new RegExp(TEST_PAGE, 'i')})
				.getByTitle('Open Page Options Menu')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await page
				.locator('.modal-dialog')
				.getByRole('button', {name: 'Delete'})
				.click();
		});
	});

	test('retains impersonation parameter when suggestions is disabled @LPD-17509', async ({
		apiHelpers,
		page,
		productMenuPage,
		searchPage,
		usersAndOrganizationsPage,
	}) => {
		let impersonatePage: Page;
		let impersonateSearchPage: SearchPage;
		let testUser: any;

		await test.step('Create test user', async () => {
			testUser = await apiHelpers.headlessAdminUser.postUserAccount();
		});

		await test.step('Add searchbar and results portlet to new page', async () => {
			await page.goto('/');

			await productMenuPage.openProductMenuIfClosed();

			await productMenuPage.goToPages();

			await productMenuPage.clickSpecificPage(TEST_PAGE);

			await searchPage.addPortlet('Search Bar', 'Search');

			await searchPage.addPortlet('Search Results', 'Search');
		});

		await test.step('Disable search suggestions for added searchbar', async () => {
			await searchPage.selectSearchBarInMainContentConfigurations([
				{
					label: 'Enable Suggestions',
					value: false,
				},
			]);
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
				.getByRole('menuitem', {name: TEST_PAGE})
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
