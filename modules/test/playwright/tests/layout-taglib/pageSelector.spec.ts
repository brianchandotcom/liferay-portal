/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import {navigationMenusPagesTest} from '../site-navigation-admin-web/fixtures/navigationMenusPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
		'LPS-196847': true,
	}),
	isolatedSiteTest,
	navigationMenusPagesTest
);

test('load more works properly in search results', async ({
	apiHelpers,
	navigationMenusPage,
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

	const modal = await navigationMenusPage.openAddPageModal();

	const loadMoreButton = modal.locator('.load-more-btn');

	// Search for another string and check empty state

	await modal.getByPlaceholder('Search').fill('Orange');

	await expect(modal.getByText('No Results Found')).toBeVisible();

	// Search for Lemon pages, check it shows all results and does not show Load More button

	await modal.getByPlaceholder('Search').fill('Lem');

	await modal.locator('.loading-animation').waitFor();
	await modal.locator('.loading-animation').waitFor({state: 'hidden'});

	await expect(
		modal.locator('.search-result').getByText('Lemon')
	).toHaveCount(15);

	await expect(loadMoreButton).not.toBeVisible();

	// Check only Lem substring is marked

	const firstResult = await modal
		.locator('.search-result')
		.getByText('Lemon')
		.first();

	await expect(firstResult.locator('mark')).toHaveText('Lem');

	// Search for Apple pages, check it initially shows 20 items

	await modal.getByPlaceholder('Search').fill('App');

	await modal.locator('.loading-animation').waitFor();
	await modal.locator('.loading-animation').waitFor({state: 'hidden'});

	await expect(
		modal.locator('.search-result').getByText('Apple')
	).toHaveCount(20);

	// Load more items and check it loads all results and button disappears

	await loadMoreButton.click();

	await loadMoreButton.locator('.loading-animation').waitFor();
	await loadMoreButton
		.locator('.loading-animation')
		.waitFor({state: 'hidden'});

	await expect(
		modal.locator('.search-result').getByText('Apple')
	).toHaveCount(30);

	await expect(loadMoreButton).not.toBeVisible();
});

test('checks the correct label for restricted page in the layout tree', async ({
	apiHelpers,
	navigationMenusPage,
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

	const modal = await navigationMenusPage.openAddPageModal();

	// Check the correct label for restricted page

	await expect(
		modal
			.locator('div', {
				hasText: pageName,
			})
			.getByLabel('Restricted Page')
	).toBeVisible();
});
