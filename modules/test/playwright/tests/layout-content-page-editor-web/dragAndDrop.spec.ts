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
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	isolatedSiteTest
);

test('Checks that a widget can be added and dragged to another part of the page', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a content page with a grid

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getGridDefinition({
				columns: [{pageElements: [], size: 4}, {size: 4}, {size: 4}],
				id: getRandomString(),
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode, add the Sort widget in the first column of the grid and publish the page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const gridColumn = page.locator('.page-editor__col__border');

	await pageEditorPage.addWidget('Commerce', 'Sort', gridColumn.first());

	// Check that the Sort widget is selected

	const widgetId = await pageEditorPage.getFragmentId('Sort');

	await expect(await pageEditorPage.isActive(widgetId)).toBe(true);

	await pageEditorPage.publishPage();

	// Edit the page and move the widget to the third column of the grid

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.dragAndDropElement(
		page.locator('[data-name="Sort"]'),
		gridColumn.nth(2)
	);

	expect(gridColumn.nth(2).locator('[data-name="Sort"]')).toBeVisible();
});

test('checks that the drag target and drop target have the correct classes when dragged', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const containerDefinition = getContainerDefinition({
		id: getRandomString(),
	});

	const headingDefinition = getFragmentDefinition({
		id: getRandomString(),
		key: 'BASIC_COMPONENT-heading',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			containerDefinition,
			headingDefinition,
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode and drag the heading fragment into the container fragment

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const dragTarget = page.locator('[data-name="Heading"]');

	const dropTarget = page.locator('.page-editor__container');

	// Drag and drop starts

	await dragTarget.hover();

	await page.mouse.down();

	await dropTarget.hover();

	await expect(dragTarget).toHaveClass(/dragged/);

	const boundingClientRect = await dropTarget.evaluate((element) =>
		element.getBoundingClientRect()
	);

	await dropTarget.hover({
		position: {
			x: boundingClientRect.width / 2,
			y: boundingClientRect.height / 2,
		},
	});

	const dropTargetTopper = page.locator('[data-name]', {
		has: dropTarget,
	});

	await expect(dropTargetTopper).toHaveClass(/highlighted/);

	await page.mouse.up();

	// Check if the drag and drop is done

	expect(dropTarget.locator('[data-name="Heading"]')).toBeVisible();
});
