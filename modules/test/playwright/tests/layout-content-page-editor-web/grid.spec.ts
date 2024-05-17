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

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('grid content is also duplicated', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a grid with a Heading in the first column

	const heading = getFragmentDefinition(
		getRandomString(),
		'BASIC_COMPONENT-heading'
	);

	const gridId = getRandomString();

	const grid = getGridDefinition({
		columns: [{pageElements: [heading], size: 4}, {size: 4}, {size: 4}],
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check there's one heading

	await expect(page.getByText('Heading Example')).toHaveCount(1);

	// Duplicate row and check there are two headings

	await pageEditorPage.duplicateFragment(gridId);

	await expect(page.getByText('Heading Example')).toHaveCount(2);
});

test('can nest grids', async ({apiHelpers, pageEditorPage, site}) => {

	// Create a grid with another grid inside

	const childGrid = getGridDefinition();

	const parentGridId = getRandomString();

	const parentGrid = getGridDefinition({
		columns: [{pageElements: [childGrid], size: 4}, {size: 4}, {size: 4}],
		id: parentGridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([parentGrid]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check nested grid is rendered properly

	const parentGridTopper = await pageEditorPage.getTopper(parentGridId);

	const firstColumn = await parentGridTopper
		.locator('.page-editor__col')
		.first();

	await expect(firstColumn.locator('.page-editor__col')).toHaveCount(3);
});

test('can configure grid', async ({apiHelpers, page, pageEditorPage, site}) => {

	// Create a grid

	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Change grid config and check it's applied

	await pageEditorPage.changeFragmentConfiguration(
		gridId,
		'General',
		'Number of Modules',
		'2'
	);

	await pageEditorPage.changeFragmentConfiguration(
		gridId,
		'General',
		'Layout',
		'1 Module per Row'
	);

	await expect(page.locator('.page-editor__col.col-12')).toHaveCount(2);
});

test('can duplicate a grid inside a container', async ({
	apiHelpers,
	pageEditorPage,
	site,
}) => {

	// Create a container with a grid inside

	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	const containerId = getRandomString();

	const container = getContainerDefinition({
		id: containerId,
		pageElements: [grid],
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([container]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Duplicate grid and check the copy is added properly inside the container

	await pageEditorPage.duplicateFragment(gridId);

	const containerTopper = await pageEditorPage.getTopper(containerId);

	await expect(containerTopper.locator('.page-editor__row')).toHaveCount(2);
});

test('can resize a grid', async ({apiHelpers, page, pageEditorPage, site}) => {

	// Create a container with a grid inside

	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Select grid and resize last column

	await pageEditorPage.selectFragment(gridId);

	const resizer = page.locator('.page-editor__col__resizer').last();

	const targetY = await resizer.evaluate(
		(element) => element.getBoundingClientRect().y
	);

	const targetX = await pageEditorPage
		.getTopper(gridId)
		.evaluate((element) => element.getBoundingClientRect().right);

	await resizer.hover();

	await page.mouse.down();

	await page.mouse.move(targetX, targetY);

	await page.mouse.up();

	await pageEditorPage.waitForChangesSaved();

	// Check correct size is applied

	await expect(page.locator('.page-editor__col.col-12')).toBeVisible();
});
