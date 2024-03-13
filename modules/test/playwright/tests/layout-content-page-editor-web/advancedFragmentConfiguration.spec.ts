/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
import getContainerDefinition from './utils/getContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest(),
	isolatedSiteTest,
	pageEditorPagesTest
);

test('checks that the corresponding message appears when a parent fragment is hidden from search and the link redirects correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const containerId = getRandomString();
	const headingId = getRandomString();

	// Create a container with a heading fragment inside

	const containerDefinition = getContainerDefinition(containerId, [
		getFragmentDefinition(headingId, 'BASIC_COMPONENT-heading'),
	]);

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: containerDefinition,
		site,
	});

	await page.getByTitle('Browser').click();

	await page.getByLabel('Select Container').click();

	await pageEditorPage.goToConfigurationTab('Advanced');

	const hideFromSiteSearchResultsInput = await page.getByLabel(
		'Hide from Site Search Results'
	);

	await hideFromSiteSearchResultsInput.check();

	await expect(hideFromSiteSearchResultsInput).toBeChecked();

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Advanced');

	await expect(
		page.getByText('This configuration is inherited')
	).toBeVisible();

	await page.getByText('Go to parent fragment to edit.').click();

	const containerIsActive = await pageEditorPage.isActive(containerId);

	await expect(containerIsActive).toBe(true);
});

test('checks that the fragment is hidden from Site Search Results', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const layouts = {fragment: null, searchBar: null};

	// Create a page with the Search Bar widget

	const widgetLayoutId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: getRandomString(),
		widgetName:
			'com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet',
	});

	layouts.searchBar = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		widgetLayoutId,
		getPageDefinition([widgetDefinition])
	);

	// Create a page with a fragment, change the text of the fragment and publish it

	const headingId = getRandomString();

	const headingFragmentDefinition = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	layouts.fragment = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomString(),
		getPageDefinition([headingFragmentDefinition])
	);

	await pageEditorPage.goToEditMode(layouts.fragment, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(headingId);

	const headingFragment = await pageEditorPage.getFragment(headingId);

	for (let i = 0; i < 2; i++) {
		await headingFragment.click();
		await page.waitForTimeout(1000);
	}

	await page.getByText('Publish').click();

	// Go to the Search Bar page and search for the fragment text

	await page.goto(
		`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
	);

	const searchBar = page.getByPlaceholder('Search...');

	await searchBar.click();
	await searchBar.fill('Heading');

	await page.waitForTimeout(3000);

	// Check that there are results

	const searchResults = await page.getByText('Suggestions');

	await expect(searchResults).toBeVisible();

	// Go back to the fragment page and hide the fragment from the search results

	await pageEditorPage.goToEditMode(layouts.fragment, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Advanced');

	const hideFromSiteSearchResultsInput = await page.getByLabel(
		'Hide from Site Search Results'
	);

	await hideFromSiteSearchResultsInput.check();

	await expect(hideFromSiteSearchResultsInput).toBeChecked();

	await page.getByText('Publish').click();

	// Go to the Search Bar page and search for the fragment text

	await page.goto(
		`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
	);

	await searchBar.click();
	await searchBar.fill('Heading');

	await page.waitForTimeout(3000);

	// Check that there are no results

	await expect(searchResults).not.toBeVisible();
});
