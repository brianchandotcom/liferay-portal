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

	// Checks that a Widget is selected when it is added to the page

	await pageEditorPage.addWidget('Commerce', 'Sort');

	const widgetId = await pageEditorPage.getFragmentId('Sort');

	expect(await pageEditorPage.isActive(widgetId)).toBe(true);

	// The panel does not change to the Browser panel when a fragment is selected

	await pageEditorPage.selectFragment(headingId);

	await expect(page.getByLabel('Fragments and Widgets Panel')).toBeVisible();
});
