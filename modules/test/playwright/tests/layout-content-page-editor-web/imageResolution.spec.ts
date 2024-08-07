/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

function getImageWidth(page: Page) {
	return page
		.locator('[data-lfr-editable-id="image-square"]')
		.evaluate((element: HTMLImageElement) => element.naturalWidth);
}

test('Allow changing image resolution with direct selection', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create a page with a image fragment

	const imageId = getRandomString();

	const imageFragment = getFragmentDefinition({
		id: imageId,
		key: 'BASIC_COMPONENT-image',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([imageFragment]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Select the image directly

	await pageEditorPage.selectEditable(imageId, 'image-square');

	await page.getByTitle('Select Image').click();

	const articleCard = page
		.frameLocator('iframe[title="Select"]')
		.getByText('high_resolution_photo.jpg');

	await clickAndExpectToBeHidden({
		target: page.locator('.modal-dialog'),
		trigger: articleCard,
	});

	await pageEditorPage.waitForChangesSaved();

	// Check that all the options are available

	expect(page.getByText('Auto', {exact: true})).toBeAttached();
	expect(page.getByText('Preview-1000x0')).toBeAttached();
	expect(page.getByText('Thumbnail-300x300')).toBeAttached();

	// Change the resolution in another viewport

	const autoWidth = await getImageWidth(page);

	await page.getByLabel('Resolution').selectOption('Thumbnail-300x300');

	await pageEditorPage.waitForChangesSaved();

	const nextWidth = await getImageWidth(page);

	// Check that the width is lower in smaller viewports and that it is the size of the thumbnail

	expect(autoWidth).toBeGreaterThan(nextWidth);
	expect(nextWidth).toBe(300);
});

test('Allow changing image resolution with mapping selection', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create a page with a image fragment

	const imageId = getRandomString();

	const imageFragment = getFragmentDefinition({
		id: imageId,
		key: 'BASIC_COMPONENT-image',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([imageFragment]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Map the editable to a document

	await pageEditorPage.selectEditable(imageId, 'image-square');

	await page.getByLabel('Source Selection').selectOption('Mapping');

	await pageEditorPage.setMappingConfiguration({
		mapping: {
			entity: 'Documents and Media',
			entry: 'high_resolution_photo.jpg',
			entryLocator: page
				.frameLocator('iframe[title="Select"]')
				.getByText('high_resolution_photo.jpg', {exact: false}),
			field: 'File URL',
		},
	});

	// Check that all the options are available

	expect(page.getByText('Auto', {exact: true})).toBeAttached();
	expect(page.getByText('Preview-1000x0')).toBeAttached();
	expect(page.getByText('Thumbnail-300x300')).toBeAttached();
});

test('Allow changing image resolution in other viewports', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create a page with a image fragment

	const imageId = getRandomString();

	const imageFragment = getFragmentDefinition({
		id: imageId,
		key: 'BASIC_COMPONENT-image',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([imageFragment]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Select the image directly

	await pageEditorPage.selectEditable(imageId, 'image-square');

	await page.getByTitle('Select Image').click();

	const articleCard = page
		.frameLocator('iframe[title="Select"]')
		.getByText('high_resolution_photo.jpg', {exact: false});

	await clickAndExpectToBeHidden({
		target: page.locator('.modal-dialog'),
		trigger: articleCard,
	});

	await pageEditorPage.waitForChangesSaved();

	// Check that the resolution can be changed independenly in each viewport

	await pageEditorPage.switchViewport('Tablet');

	await page.getByLabel('Resolution').selectOption('Preview-1000x0');

	await pageEditorPage.waitForChangesSaved();

	expect(page.getByLabel('Resolution')).toHaveValue('Preview-1000x0');

	await pageEditorPage.switchViewport('Desktop');

	expect(page.getByLabel('Resolution')).toHaveValue('auto');
});
