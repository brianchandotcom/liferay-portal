/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from '../layout-content-page-editor-web/fixtures/pageEditorPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest
);

test('LPD-18809 Search suggestions should filter by product visibility with the Commerce Contributor', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	searchBarPortletPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Search Suggestions Catalog',
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'tprod' + getRandomInt(),
		},
		productChannelFilter: true,
	});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'tprod' + getRandomInt(),
		},
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Search Suggestions Page');

	await page.goto(`/web/${site.name}`);

	await searchBarPortletPage.addSearchBarWidget();
	await searchBarPortletPage.openSearchBarConfiguration();
	await searchBarPortletPage.replaceSuggestionsContributorWithCommerceContributor();

	await page.reload();

	await searchBarPortletPage.searchBarInput.click();
	await searchBarPortletPage.searchBarInput.fill('tprod');

	await expect(
		searchBarPortletPage.searchSuggestionMenuItem(product2.name.en_US)
	).toBeVisible();
	await expect(
		searchBarPortletPage.searchSuggestionMenuItem(product.name.en_US)
	).not.toBeVisible();
});
