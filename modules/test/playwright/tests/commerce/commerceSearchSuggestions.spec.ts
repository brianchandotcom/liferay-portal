/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from '../layout-content-page-editor-web/fixtures/pageEditorPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest
);

test('LPD-18809 search suggestions should filter by product visibility with the Commerce Contributor', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	searchBarPortletPage,
}) => {

	// setup

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Search Suggestions Catalog',
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'product' + getRandomInt(),
		},
		productChannelFilter: true,
	});

	// this is to verify that the search suggestions are still showing,
	// channel filter is off for this so it will always show in the suggestions

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'product' + getRandomInt(),
		},
	});

	try {

		// create our page with a search bar portlet in it

		await applicationsMenuPage.goToSite(site.name);

		await commerceLayoutsPage.goToPages(false);
		await commerceLayoutsPage.createWidgetPage('Search Suggestions Page');
		await commerceLayoutsPage.goToPages(false);

		await commerceLayoutsPage.siteHomePageLink.click();

		await searchBarPortletPage.addSearchBarWidget();
		await searchBarPortletPage.openSearchBarConfiguration();
		await searchBarPortletPage.replaceSuggestionsContributorWithCommerceContributor();

		// now test the search bar suggestions results

		await searchBarPortletPage.searchBarInput.click();
		await searchBarPortletPage.searchBarInput.fill('product');
		expect(
			searchBarPortletPage.searchSuggestionMenuItem(product2.name.en_US)
		).toBeVisible();
		expect(
			searchBarPortletPage.searchSuggestionMenuItem(product.name.en_US)
		).not.toBeVisible();
	}
	finally {
		await Promise.all([
			apiHelpers.headlessCommerceAdminChannel.deleteChannel(channel.id),
			apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
				product.productId
			),
			apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
				product2.productId
			),
		]);

		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(catalog.id),
			await apiHelpers.headlessSite.deleteSite(site.id);
	}
});
