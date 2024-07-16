/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import getRandomString from '../../utils/getRandomString';
import getContainerDefinition from './utils/getContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getGridDefinition from './utils/getGridDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

test('Grid background image can be customized', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a grid

	const gridId = getRandomString();

	const grid = getGridDefinition({
		columns: [{pageElements: [], size: 4}, {size: 4}, {size: 4}],
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Select background image

	await pageEditorPage.selectFragment(gridId);

	await pageEditorPage.goToConfigurationTab('Styles');

	await page.getByLabel('Select Image').click();

	const card = page
		.frameLocator('iframe[title="Select"]')
		.locator('[data-title="echo-logo.png"]');

	await clickAndExpectToBeHidden({
		target: page.locator('.modal-dialog'),
		trigger: card,
	});

	// Check correct image is used for background

	expect(
		await pageEditorPage
			.getFragment(gridId)
			.evaluate((element) => getComputedStyle(element).background)
	).toEqual(expect.stringContaining('echo-logo-png'));
});

test('Grid content is also duplicated', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a grid with a Heading in the first column

	const heading = getFragmentDefinition({
		id: getRandomString(),
		key: 'BASIC_COMPONENT-heading',
	});

	const gridId = getRandomString();

	const grid = getGridDefinition({
		columns: [{pageElements: [heading], size: 4}, {size: 4}, {size: 4}],
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Check there's one heading

	await expect(page.getByText('Heading Example')).toHaveCount(1);

	// Duplicate row and check there are two headings

	await pageEditorPage.duplicateFragment(gridId);

	await expect(page.getByText('Heading Example')).toHaveCount(2);
});

test('Can nest grids', async ({apiHelpers, pageEditorPage, wemSite}) => {

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
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Check nested grid is rendered properly

	const parentGridTopper = pageEditorPage.getTopper(parentGridId);

	const firstColumn = parentGridTopper.locator('.page-editor__col').first();

	await expect(firstColumn.locator('.page-editor__col')).toHaveCount(3);
});

test('Can configure grid', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a grid

	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Change grid config and check it's applied

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Number of Modules',
		fragmentId: gridId,
		tab: 'General',
		value: '2',
	});

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Layout',
		fragmentId: gridId,
		tab: 'General',
		value: '1 Module per Row',
	});

	await expect(page.locator('.page-editor__col.col-12')).toHaveCount(2);
});

test('Can duplicate a grid inside a container', async ({
	apiHelpers,
	pageEditorPage,
	wemSite,
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
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Duplicate grid and check the copy is added properly inside the container

	await pageEditorPage.duplicateFragment(gridId);

	const containerTopper = pageEditorPage.getTopper(containerId);

	await expect(containerTopper.locator('.page-editor__row')).toHaveCount(2);
});

test('Can resize a grid', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a container with a grid inside

	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

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
