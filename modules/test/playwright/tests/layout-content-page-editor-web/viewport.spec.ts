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
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

type NonDesktopPanels = Array<{
	name: ConfigurationTab;
	sections: Array<{name: ConfigurationSection; visible: boolean}>;
}>;

const VIEWPORTS: Viewport[] = [
	'Desktop',
	'Tablet',
	'Landscape Phone',
	'Portrait Phone',
];

const NON_DESKTOP_PANELS: NonDesktopPanels = [
	{
		name: 'General',
		sections: [
			{name: 'Frame', visible: true},
			{name: 'Options', visible: false},
		],
	},
	{
		name: 'Styles',
		sections: [
			{name: 'Background', visible: true},
			{name: 'Borders', visible: true},
			{name: 'Effects', visible: true},
			{name: 'Spacing', visible: true},
			{name: 'Text', visible: true},
		],
	},
	{
		name: 'Advanced',
		sections: [
			{name: 'Hide from Site Search Results', visible: false},
			{name: 'CSS', visible: true},
		],
	},
];

test('shows correct sections on each configuration panel when viewport is not Desktop', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading fragment and go to Edit Mode

	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: headingFragment,
		site,
	});

	// Switch to Tablet viewport and select the fragment

	await pageEditorPage.switchViewport('Tablet');
	await pageEditorPage.selectFragment(headingId, false);

	// Go to each panel and check correct sections are shown

	for (const {name, sections} of NON_DESKTOP_PANELS) {
		await pageEditorPage.goToConfigurationTab(name);

		for (const {name, visible} of sections) {
			const section = page.locator('.panel-title').getByText(name);

			if (visible) {
				await expect(section).toBeVisible();
			}
			else {
				await expect(section).not.toBeVisible();
			}
		}
	}
});

test('shows only Image Source field when the viewport is Desktop', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: headingFragment,
		site,
	});

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Styles');

	for (const viewport of VIEWPORTS) {
		await pageEditorPage.switchViewport(viewport as Viewport);

		const imageSourceField = page.getByLabel('Image Source', {exact: true});

		if (viewport === 'Desktop') {
			await expect(imageSourceField).toBeVisible();
		}
		else {
			await expect(imageSourceField).not.toBeVisible();
		}
	}
});

test('Background Image field is disabled for non-desktop viewports', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: headingFragment,
		site,
	});

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Styles');

	for (const viewport of VIEWPORTS) {
		await pageEditorPage.switchViewport(viewport as Viewport);

		const backgroundImageField = page.getByRole('textbox', {
			name: 'Background Image',
		});

		if (viewport === 'Desktop') {
			await expect(backgroundImageField).not.toBeDisabled();
		}
		else {
			await expect(backgroundImageField).toBeDisabled();
		}
	}
});

test('checks that the layout can be resized', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: headingFragment,
		site,
	});

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.switchViewport('Portrait Phone');

	// Get the handle for the resize

	const resizeHandle = page.locator('.page-editor__layout-viewport__handle');

	// Get the element to be resized

	const resizer = page.locator('.page-editor__layout-viewport__resizer');

	// Get the size and the position of the previous elements

	const originalSize = await resizeHandle.boundingBox();
	const originalSizeResizer = await resizer.boundingBox();

	// Check the original size of the resizer element

	await expect(originalSizeResizer.width).toBe(360);

	// Simulate mouse movement to resize the element

	await page.mouse.move(
		originalSize.x + originalSize.width / 2,
		originalSize.y
	);
	await page.mouse.down();
	await page.mouse.move(
		originalSize.x + originalSize.width / 2 + 50,
		originalSize.y
	);
	await page.mouse.up();

	// Get the new size of the resizer element and verify that it has increased

	const newSizeResizer = await resizer.boundingBox();

	await expect(newSizeResizer.width).toBe(460);
});

test('checks that the value of a field is propagated to smaller viewports', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: headingFragment,
		site,
	});

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('General');

	const hideFragmentInput = await page.getByLabel('Hide Fragment', {
		exact: true,
	});

	await hideFragmentInput.check();

	for (const viewport of VIEWPORTS) {
		await pageEditorPage.switchViewport(viewport as Viewport);

		await expect(hideFragmentInput).toBeChecked();
	}

	await pageEditorPage.switchViewport('Desktop');

	await hideFragmentInput.uncheck();

	for (const viewport of VIEWPORTS) {
		await pageEditorPage.switchViewport(viewport as Viewport);

		await expect(hideFragmentInput).not.toBeChecked();
	}
});

test('correct viewport configuration is set when adding a Grid', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition(),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Add a grid and change number of columns to 6

	await pageEditorPage.addFragment('Layout Elements', 'Grid');

	const topper = await page.locator('.page-editor__topper[data-name="Grid"]');

	const gridId = await topper.evaluate((element) =>
		Array.from(element.classList)
			.find((cssClass) => cssClass.includes('lfr-layout-structure-item'))
			.replace('lfr-layout-structure-item-topper-', '')
	);

	await pageEditorPage.changeFragmentConfiguration(
		gridId,
		'General',
		'Number of Modules',
		'6'
	);

	// Check columns have size 2 in desktop

	await expect(page.locator('.page-editor__col.col-2')).toHaveCount(6);

	// Change to Landscape Phone and check columns have size 12

	await pageEditorPage.switchViewport('Landscape Phone');

	const globalFrame = await page.frameLocator(
		'.page-editor__global-context-iframe'
	);

	await pageEditorPage.selectFragment(gridId, false);

	await expect(globalFrame.locator('.page-editor__col.col-12')).toHaveCount(
		6
	);

	// Change to 2 modules per row and check size is 6

	await pageEditorPage.changeFragmentConfiguration(
		gridId,
		'General',
		'Layout',
		'2 Modules per Row',
		false
	);

	await expect(globalFrame.locator('.page-editor__col.col-6')).toHaveCount(6);
});
