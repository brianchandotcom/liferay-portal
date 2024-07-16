/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageSelectorPagesTest} from '../../fixtures/pageSelectorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {navigationMenusPagesTest} from './fixtures/navigationMenusPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest,
	pageSelectorPagesTest
);

test.describe('Add pages to Navigation Menu', () => {
	test('Load more works properly in search results', async ({
		apiHelpers,
		navigationMenusPage,
		pageSelectorPage,
		site,
	}) => {

		// Create 15 Lemon pages

		for (let i = 1; i <= 15; i++) {
			await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: `Lemon ${i}`,
			});
		}

		// Create 30 Apple pages

		for (let i = 1; i <= 30; i++) {
			await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: `Apple ${i}`,
			});
		}

		// Create a navigation menu and open pages selector

		await navigationMenusPage.goto(site.friendlyUrlPath);

		await navigationMenusPage.createNavigationMenu(getRandomString());

		await navigationMenusPage.openAddPageModal();

		// Store modal instance in variable so we can search for things inside it

		const modal = await pageSelectorPage.getModal();

		// Search for another string and check empty state

		await pageSelectorPage.search('Orange');

		await expect(modal.getByText('No Results Found')).toBeVisible();

		// Search for Lemon pages, check it shows all results and does not show Load More button

		await pageSelectorPage.search('Lem');

		await expect(modal.locator('.search-result')).toHaveCount(15);

		await expect(modal.getByText('Load More Results')).not.toBeVisible();

		// Check only Lem substring is marked

		const firstResult = modal.locator('.search-result').first();

		await expect(firstResult.locator('mark')).toHaveText('Lem');

		// Search for Apple pages, check it initially shows 20 items

		await pageSelectorPage.search('App');

		await expect(modal.locator('.search-result')).toHaveCount(20);

		// Load more items and check it loads all results and button disappears

		await pageSelectorPage.loadMore();

		await expect(modal.locator('.search-result')).toHaveCount(30);

		await expect(modal.getByText('Load More Results')).not.toBeVisible();
	});

	test('Checks the correct label for restricted page in the layout tree', async ({
		apiHelpers,
		navigationMenusPage,
		pageSelectorPage,
		site,
	}) => {

		// Create a page with only one permission

		const pageName = getRandomString();

		await apiHelpers.headlessDelivery.createSitePage({
			pagePermissions: [
				{
					actionKeys: ['VIEW'],
					roleKey: 'Owner',
				},
			],
			siteId: site.id,
			title: pageName,
		});

		// Create a navigation menu and open pages selector

		await navigationMenusPage.goto(site.friendlyUrlPath);

		await navigationMenusPage.createNavigationMenu(getRandomString());

		await navigationMenusPage.openAddPageModal();

		const modal = await pageSelectorPage.getModal();

		// Check the correct label for restricted page

		await expect(
			modal
				.locator('div', {
					hasText: pageName,
				})
				.getByLabel('Restricted Page')
		).toBeVisible();
	});
});
