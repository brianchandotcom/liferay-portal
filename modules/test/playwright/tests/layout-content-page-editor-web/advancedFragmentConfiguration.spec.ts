/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Checks that the fragment is hidden from Site Search Results', async ({
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

	layouts.searchBar = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: widgetLayoutId,
	});

	// Create a page with a fragment and publish it

	const headingId = getRandomString();

	const headingFragmentDefinition = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	layouts.fragment = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingFragmentDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layouts.fragment, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.publishPage();

	// Go to the Search Bar page and search for the fragment text

	await page.goto(
		`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
	);

	const searchBar = page.getByPlaceholder('Search...');

	await searchBar.click();
	await searchBar.fill('Heading');

	await page.locator('.search-bar-suggestions .loading-animation').waitFor();
	await page
		.locator('.search-bar-suggestions .loading-animation')
		.waitFor({state: 'hidden'});

	// Check that there are results

	const searchResults = page.getByText('Suggestions');

	await expect(searchResults).toBeVisible();

	// Go back to the fragment page and hide the fragment from the search results

	await pageEditorPage.goto(layouts.fragment, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Advanced');

	const hideFromSiteSearchResultsInput = page.getByLabel(
		'Hide from Site Search Results'
	);

	await hideFromSiteSearchResultsInput.check();

	await expect(hideFromSiteSearchResultsInput).toBeChecked();

	await pageEditorPage.publishPage();

	// Go to the Search Bar page and search for the fragment text

	await page.goto(
		`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
	);

	await searchBar.click();
	await searchBar.fill('Heading');

	await page.locator('.search-bar-suggestions .loading-animation').waitFor();
	await page
		.locator('.search-bar-suggestions .loading-animation')
		.waitFor({state: 'hidden'});

	// Check that there are no results

	await expect(searchResults).not.toBeVisible();
});

test('Checks that the advanced configuration of a fragment appears in its corresponding tab', async ({
	apiHelpers,
	fragmentEditorPage,
	fragmentsPage,
	page,
	pageEditorPage,
	site,
}) => {

	// Go to fragment editor

	await fragmentsPage.goto(site.friendlyUrlPath);

	// Add a new fragment set and a fragment inside it

	const setName = getRandomString();

	await fragmentsPage.createFragmentSet(setName);

	await fragmentsPage.goto(site.friendlyUrlPath);

	await fragmentsPage.createFragment(setName, 'My Fragment');

	// Add a configuration for the fragment

	await fragmentEditorPage.addConfiguration(
		JSON.stringify({
			fieldSets: [
				{
					configurationRole: 'advanced',
					fields: [
						{
							dataType: 'string',
							defaultValue: '1',
							label: 'Advanced Config Field',
							name: 'advancedConfigField',
							type: 'select',
							typeOptions: {
								validValues: [{label: '1', value: '1'}],
							},
						},
					],
					label: 'Advanced Config Fieldset',
				},
			],
		})
	);

	await fragmentEditorPage.publish();

	// Create a content page with the fragment previously created

	const fragmentDefinition = getFragmentDefinition({
		fragmentConfig: {
			advancedConfigField: '1',
		},
		id: getRandomString(),
		key: 'my-fragment',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([fragmentDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Check advanced configuration appears where it should

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await page.getByTitle('Browser').click();

	await page.getByLabel('Select My Fragment').click();

	await pageEditorPage.goToConfigurationTab('Advanced');

	await expect(
		page.getByLabel('Advanced Config Field', {exact: true})
	).toBeVisible();
});
