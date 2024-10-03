/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getContainerDefinition from './utils/getContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getGridDefinition from './utils/getGridDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-18221': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Check that the breadcrumbs maintain the hierarchy and work correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a container with a heading inside a grid

	const headingId = getRandomString();

	const headingDefinition = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const containerDefinition = getContainerDefinition({
		id: getRandomString(),
		pageElements: [headingDefinition],
	});

	const gridDefinition = getGridDefinition({
		columns: [{pageElements: [containerDefinition], size: 12}],
		id: getRandomString(),
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([gridDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Select the heading fragment

	await pageEditorPage.selectFragment(headingId);

	// Check that only 2 breadcrumb items are shown and the active item is "Heading"

	const breadcrumbs = page.getByLabel('Breadcrumb', {exact: true});
	const breadcrumbItems = breadcrumbs.locator('.breadcrumb-item');

	await expect(breadcrumbs.locator('a[aria-current="page"]')).toHaveText(
		'Heading'
	);

	await expect(breadcrumbItems).toHaveCount(2);

	await expect(breadcrumbItems.nth(0)).toHaveText('Container');

	await expect(breadcrumbItems.nth(1)).toHaveText('Heading');

	// Expand the breadcrumbs and check that all breadcrumb items are shown

	await breadcrumbs.locator('.breadcrumb-toggle').click();

	await expect(breadcrumbItems).toHaveCount(4);

	await expect(breadcrumbItems.nth(0)).toHaveText('Grid');

	await expect(breadcrumbItems.nth(1)).toHaveText('Module');

	await expect(breadcrumbItems.nth(2)).toHaveText('Container');

	await expect(breadcrumbItems.nth(3)).toHaveText('Heading');

	// Select the breadcrumb "Grid" and check that the active breadcrumb item is the grid

	await breadcrumbs.getByText('Grid').click();

	await expect(breadcrumbs.locator('a[aria-current="page"]')).toHaveText(
		'Grid'
	);
});
