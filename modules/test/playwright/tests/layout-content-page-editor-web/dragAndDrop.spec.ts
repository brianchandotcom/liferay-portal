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
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import dragAndDropElement from '../../utils/dragAndDropElement';
import getRandomString from '../../utils/getRandomString';
import {POTATO_OBJECT_ERC} from '../setup/page-management-site/constants';
import getContainerDefinition from './utils/getContainerDefinition';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getGridDefinition from './utils/getGridDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-18221': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
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

	expect(await pageEditorPage.isActive(widgetId)).toBe(true);

	await pageEditorPage.publishPage();

	// Edit the page and move the widget to the third column of the grid

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await dragAndDropElement({
		dragTarget: page.locator('[data-name="Sort"]'),
		dropTarget: gridColumn.nth(2),
		page,
	});

	expect(gridColumn.nth(2).locator('[data-name="Sort"]')).toBeVisible();
});

test('Checks that the drag target and drop target have the correct classes when dragged', async ({
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

test(
	'Check that multiple items can be dragged at once',
	{
		tag: '@LPD-30901',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const headingId = getRandomString();
		const headingDefinition = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const buttonId = getRandomString();
		const buttonDefinition = getFragmentDefinition({
			id: buttonId,
			key: 'BASIC_COMPONENT-button',
		});

		const containerDefinition = getContainerDefinition({
			id: getRandomString(),
			pageElements: [],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				buttonDefinition,
				headingDefinition,
				containerDefinition,
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode of page and select multiple fragments

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(headingId);

		await page.keyboard.down('Control');

		await pageEditorPage.selectFragment(buttonId);

		await page.keyboard.up('Control');

		// Move multiple fragments into the container

		const dropTarget = page.locator('[data-name="Container"]');

		await dragAndDropElement({
			dragTarget: page.locator('[data-name="Button"]'),
			dropTarget,
			page,
		});

		// Check that the fragment have been moved

		expect(dropTarget.locator('[data-name="Heading"]')).toBeVisible();
		expect(dropTarget.locator('[data-name="Button"]')).toBeVisible();
	}
);

test('Check drag and drop from Page Structure Tree', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const gridId = getRandomString();

	const grid = getGridDefinition({
		id: gridId,
	});

	const assetPublisherId = getRandomString();

	const assetPublisher = getWidgetDefinition({
		id: assetPublisherId,
		widgetName:
			'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([grid, assetPublisher]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Go to the page structure tree and drag the Asset Publisher into the first column of the Grid

	await pageEditorPage.goToSidebarTab('Browser');

	await pageEditorPage.selectFragment(gridId);

	const assetPublisherNode = page.locator(
		`.treeview-link[data-id$="${assetPublisherId}"]`
	);

	const moduleNode = page
		.locator('.treeview-link')
		.filter({hasText: 'Module'})
		.first();

	await dragAndDropElement({
		dragTarget: assetPublisherNode,
		dropTarget: moduleNode,
		page,
	});

	expect(
		page
			.locator('[data-name="Grid"]')
			.locator('[data-name="Asset Publisher"]')
	).toBeVisible();
});

test(
	'Drag multiple form elements from one step to another',
	{tag: '@LPD-37828'},
	async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

		// Get the id of Potato object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				POTATO_OBJECT_ERC
			);

		// Create a form with two steps and a stepper

		const stepperId = getRandomString();

		const stepperFragment = getFragmentDefinition({
			fragmentConfig: {
				numberOfSteps: 2,
			},
			id: stepperId,
			key: 'INPUTS-stepper',
		});

		const firstInputId = getRandomString();

		const firstInputDefinition = getFragmentDefinition({
			id: firstInputId,
			key: 'INPUTS-text-input',
		});

		const secondInputId = getRandomString();

		const secondInputDefintion = getFragmentDefinition({
			id: secondInputId,
			key: 'INPUTS-text-input',
		});

		const formDefinition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [stepperFragment],
			steps: [[firstInputDefinition, secondInputDefintion], []],
		});

		// Create page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Move multiple fragments into Step 2

		await pageEditorPage.goToSidebarTab('Browser');

		await pageEditorPage.selectFragment(firstInputId);

		await page.keyboard.down('Control');

		await pageEditorPage.selectFragment(secondInputId);

		await page.keyboard.up('Control');

		await dragAndDropElement({
			dragTarget: page.getByLabel('Select Text').first(),
			dropTarget: page.getByLabel('Select Step 2'),
			page,
		});

		const secondStep = page
			.locator('.page-editor__form-step-topper')
			.last();

		expect(secondStep.locator('[data-name="Text"]').first()).toBeAttached();
		expect(secondStep.locator('[data-name="Text"]').last()).toBeAttached();
	}
);
