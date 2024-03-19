/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
import getFragmentDefinition from './utils/getFragmentDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
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

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

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

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

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

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

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

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

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

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

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
