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

test('Allows moving through layout content with keyboard', async ({
	apiHelpers,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading and a Card fragment

	const headingId = getRandomString();
	const headingDefinition = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const cardId = getRandomString();
	const cardDefinition = getFragmentDefinition({
		id: cardId,
		key: 'BASIC_COMPONENT-card',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([cardDefinition, headingDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check we can move with arrows

	const card = pageEditorPage.getTopper(cardId);
	const heading = pageEditorPage.getTopper(headingId);

	await card.focus();

	await card.press('ArrowDown');

	await expect(heading).toBeFocused();

	await heading.press('ArrowUp');

	await expect(card).toBeFocused();

	// Check we can select the fragment with Enter

	await card.press('Enter');

	await expect(card).toBeFocused();
	await expect(card).toHaveClass(/active/);

	// Check we can move to editables with Tab

	const firstEditable = card.locator('img');
	const secondEditable = card.getByText('Card Title example');

	await card.press('Tab');

	await expect(firstEditable).toBeFocused();

	await firstEditable.press('Tab');

	await expect(secondEditable).toBeFocused();

	// Check we can also select the editable with Enter

	await secondEditable.press('Enter');

	await expect(secondEditable).toBeFocused();
	await expect(secondEditable).toHaveClass(/page-editor__editable--active/);
});

test('Focus order is correct', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading fragment

	const headingId = getRandomString();
	const headingDefinition = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check focus order is correct:
	// Sidebar > Fragment > Editable > Breadcrumb > Config sidebar

	const resizer = page.getByLabel('Resize Sidebar');
	const heading = pageEditorPage.getTopper(headingId);
	const editable = heading.getByText('Heading Example');
	const breadcrumbItem = page.getByRole('link', {name: 'Heading'});
	const generalTab = page.getByTitle('General', {exact: true});

	// Sidebar to fragment

	await resizer.focus();
	await resizer.press('Tab');

	await expect(heading).toBeFocused();

	// Fragment to editable

	await heading.press('Enter');
	await heading.press('Tab');

	await expect(editable).toBeFocused();

	// Editable to breadcrumb item

	await editable.press('Tab');

	await expect(breadcrumbItem).toBeFocused();

	// Breadcrumb item to config sidebar

	await breadcrumbItem.press('Tab');

	await expect(generalTab).toBeFocused();
});

test('Check that it cannot be accessed by keyboard in disabled areas', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	for (const item of await page
		.locator('.page-editor__disabled-area .navbar-classic')
		.all()) {
		await expect(item).toHaveAttribute('inert', '');
		await expect(item).toHaveAttribute('aria-hidden', 'true');
	}
});

test('Checks the correct keyboard navigation in the experience selector', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Open the experience selector

	const experienceSelectorButton = page.getByLabel('Experience: Default');

	await experienceSelectorButton.press('Enter');

	// Go back and check that the experience selector button is focused and the dropdown is closed

	const newExperienceButton = page.getByLabel('New Experience', {
		exact: true,
	});

	await newExperienceButton.press('Shift+Tab');

	await expect(newExperienceButton).not.toBeVisible();
	await expect(experienceSelectorButton).toBeFocused();

	// Open the experience selector again

	await experienceSelectorButton.press('Enter');

	// Check the focus goes to New Experience button and continue navigating

	await expect(newExperienceButton).toBeFocused();

	await newExperienceButton.press('Tab');

	await page
		.getByRole('link', {name: 'Content Page Personalization.'})
		.press('Tab');

	// Check that the dropdown is closed

	await expect(newExperienceButton).not.toBeVisible();
});

test('Checks that a fragment is selected when it is added and the panel does not change when the fragment is selected', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Checks that Heading is selected when it is added to the page

	await pageEditorPage.addFragment('Basic Components', 'Heading');

	const headingId = await pageEditorPage.getFragmentId('Heading');

	expect(await pageEditorPage.isActive(headingId)).toBe(true);

	// Checks that Container is selected when it is added to the page

	await pageEditorPage.addFragment('Layout Elements', 'Container');

	const containerId = await pageEditorPage.getFragmentId('Container');

	expect(await pageEditorPage.isActive(containerId)).toBe(true);

	await pageEditorPage.deleteFragment(containerId);

	// Checks a fragment composition is selected when it is added to the page

	await pageEditorPage.addFragment('Featured Content', 'Banner Center');

	const bannerId = await pageEditorPage.getFragmentId('Container');

	expect(await pageEditorPage.isActive(bannerId)).toBe(true);

	// Checks that a Widget is selected when it is added to the page

	await pageEditorPage.addWidget('Commerce', 'Sort');

	const widgetId = await pageEditorPage.getFragmentId('Sort');

	expect(await pageEditorPage.isActive(widgetId)).toBe(true);

	// The panel does not change to the Browser panel when a fragment is selected

	await pageEditorPage.selectFragment(headingId);

	await expect(page.getByLabel('Fragments and Widgets Panel')).toBeVisible();
});

test(
	'Check that the range multiselection by keyboard from the page structure tree works correctly',
	{
		tag: '@LPD-36432',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const firstHeadingId = getRandomString();

		const firstHeading = getFragmentDefinition({
			id: firstHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const secondHeadingId = getRandomString();

		const secondHeading = getFragmentDefinition({
			id: secondHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const thirdHeadingId = getRandomString();

		const thirdHeading = getFragmentDefinition({
			id: thirdHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const fourthHeadingId = getRandomString();

		const fourthHeading = getFragmentDefinition({
			id: fourthHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const containerId = getRandomString();

		const container = getContainerDefinition({
			id: containerId,
			pageElements: [secondHeading, thirdHeading],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				firstHeading,
				container,
				fourthHeading,
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.goToSidebarTab('Browser');

		// Select the first heading inside the container by keyboard

		await page.locator('.page-editor__page-structure').press('Tab');
		await page.keyboard.press('Tab');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('Enter');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('Enter');

		// Activate the range multiselection

		await page.keyboard.down('Shift');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');

		// Check that the 3 last headings are selected

		expect(await pageEditorPage.isActive(secondHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(thirdHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(fourthHeadingId)).toBe(true);

		// Select the container, first and second heading without deactivate the range multiselection

		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');

		// Check that the container, first and second heading are selected

		expect(await pageEditorPage.isActive(containerId)).toBe(true);
		expect(await pageEditorPage.isActive(firstHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(secondHeadingId)).toBe(true);
	}
);

test(
	'Check that the range multiselection by keyboard from the layout works correctly',
	{
		tag: '@LPD-36432',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const firstHeadingId = getRandomString();

		const firstHeading = getFragmentDefinition({
			id: firstHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const secondHeadingId = getRandomString();

		const secondHeading = getFragmentDefinition({
			id: secondHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const thirdHeadingId = getRandomString();

		const thirdHeading = getFragmentDefinition({
			id: thirdHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const fourthHeadingId = getRandomString();

		const fourthHeading = getFragmentDefinition({
			id: fourthHeadingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const containerId = getRandomString();

		const container = getContainerDefinition({
			id: containerId,
			pageElements: [secondHeading, thirdHeading],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				firstHeading,
				container,
				fourthHeading,
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.goToSidebarTab('Browser');

		// Select the first heading inside the container by keyboard

		await page.locator('.page-editor__sidebar__resizer').focus();

		await page.keyboard.press('Tab');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('Enter');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('Enter');

		// Activate the range multiselection

		await page.keyboard.down('Shift');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');
		await page.keyboard.press('ArrowDown');

		// Check that the 3 last headings are selected

		expect(await pageEditorPage.isActive(secondHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(thirdHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(fourthHeadingId)).toBe(true);

		// Select the container, first and second heading without deactivate the range multiselection

		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');
		await page.keyboard.press('ArrowUp');

		// Check that the container, first and second heading are selected

		expect(await pageEditorPage.isActive(containerId)).toBe(true);
		expect(await pageEditorPage.isActive(firstHeadingId)).toBe(true);
		expect(await pageEditorPage.isActive(secondHeadingId)).toBe(true);
	}
);

test(
	'Check that Move Items action from the browser toolbar works correctly',
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

		await page.keyboard.down('Shift');

		await pageEditorPage.selectFragment(buttonId);

		await page.keyboard.up('Shift');

		// Move multiple fragments from the browser toolbar

		await pageEditorPage.goToSidebarTab('Browser');

		const actionsButton = page.getByLabel('Actions for Selected Items');

		await actionsButton.press('Enter');

		await page.getByText('Move 2 Items').press('Enter');

		// Check drag preview label

		expect(
			page.locator('.page-editor__keyboard-movement-preview__content')
		).toHaveText('2 Items');

		// Move fragments inside the container

		await page.keyboard.press('ArrowDown');

		await page.keyboard.press('ArrowDown');

		await page.keyboard.press('Enter');

		// Check that both elements are active when they have been moved

		expect(await pageEditorPage.isActive(buttonId)).toBe(true);

		expect(await pageEditorPage.isActive(headingId)).toBe(true);
	}
);
