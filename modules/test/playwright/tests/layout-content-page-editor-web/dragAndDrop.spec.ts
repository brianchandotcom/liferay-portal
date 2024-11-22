/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import dragAndDropElement from '../../utils/dragAndDropElement';
import getRandomString from '../../utils/getRandomString';
import {ANIMALS_COLLECTION_NAME} from '../setup/page-management-site/constants/animals';
import {getObjectERC} from '../setup/page-management-site/utils/getObjectERC';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getContainerDefinition from './utils/getContainerDefinition';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getGridDefinition from './utils/getGridDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
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

test(
	'Check drag and drop from Page Structure Tree',
	{tag: ['@LPS-118271', '@LPS-106776']},
	async ({
		apiHelpers,
		collectionsPage,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Add a content page with a fragment, a collection display and a widget

		const heading = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const animalsClassPK = await collectionsPage.getCollectionClassPK(
			ANIMALS_COLLECTION_NAME,
			pageManagementSite.friendlyUrlPath
		);

		const collectionId = getRandomString();

		const collection = getCollectionDefinition({
			classPK: animalsClassPK,
			id: collectionId,
		});

		const assetPublisher = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				heading,
				collection,
				assetPublisher,
			]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode and to Browser panel

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await pageEditorPage.goToSidebarTab('Browser');

		// Reorder fragments and check it works

		await pageEditorPage.dragTreeNode({
			position: 'top',
			source: {label: 'Asset Publisher'},
			target: {label: 'Heading'},
		});

		await expect(
			page.locator('.treeview > .treeview-item').nth(0)
		).toContainText('Asset Publisher');

		await pageEditorPage.dragTreeNode({
			position: 'bottom',
			source: {label: 'Asset Publisher'},
			target: {label: 'Heading'},
		});

		await expect(
			page.locator('.treeview > .treeview-item').nth(1)
		).toContainText('Asset Publisher');

		// Drag the asset publisher inside the collection item

		await pageEditorPage.selectFragment(collectionId);

		await page
			.locator('.page-editor__page-structure__tree-node')
			.filter({hasText: 'Collection Item'})
			.waitFor();

		await pageEditorPage.dragTreeNode({
			source: {label: 'Asset Publisher'},
			target: {label: 'Collection Item'},
		});

		expect(
			page
				.locator('[data-name="Collection Display"]')
				.locator('[data-name="Asset Publisher"]')
				.first()
		).toBeVisible();
	}
);

test(
	'Drag multiple form elements from one step to another',
	{tag: '@LPD-37828'},
	async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

		// Get the id of Potato object from the site initializer

		const objectDefinitionApiClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {className: objectDefinitionClassName} = (
			await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
				getObjectERC('Potato')
			)
		).body;

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
			objectDefinitionClassName,
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

		await pageEditorPage.dragTreeNode({
			source: {label: 'Text'},
			target: {label: 'Step 2'},
		});

		await pageEditorPage.waitForChangesSaved();

		// Check inputs have been moved to second step

		const firstStep = page.locator('.page-editor__form-step').nth(0);
		const secondStep = page.locator('.page-editor__form-step').nth(1);

		await expect(firstStep.locator('[data-name="Text"]')).toHaveCount(0);
		await expect(secondStep.locator('[data-name="Text"]')).toHaveCount(2);
	}
);
