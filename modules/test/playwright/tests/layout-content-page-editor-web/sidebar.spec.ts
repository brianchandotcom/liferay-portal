/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';
import {closeProductMenu, openProductMenu} from '../../utils/productMenu';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-169837': true,
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

const PANELS: SidebarTab[] = [
	'Fragments and Widgets',
	'Browser',
	'Page Design Options',
	'Page Rules',
	'Page Content',
	'Comments',
];

test('Renders all panel buttons in the vertical bar', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check all panel buttons are rendered

	for (const panel of PANELS) {
		const panelButton = page.getByLabel(panel, {exact: true});

		await expect(panelButton).toBeVisible();
		await expect(panelButton).toHaveAttribute(
			'aria-selected',
			panel === PANELS[0] ? 'true' : 'false'
		);
	}
});

test('Renders sidebars visible at desktop size and sidebars not visible at small resolutions', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const panel = page.getByLabel('Fragments and Widgets Panel');
	const configurationPanel = page.getByLabel('Configuration Panel', {
		exact: true,
	});

	await expect(panel).toBeVisible();

	await expect(configurationPanel).toBeVisible();

	// Set small resolution and check panels are not visible

	await page.setViewportSize({height: 600, width: 600});

	await panel.waitFor({state: 'hidden'});

	await expect(panel).not.toBeVisible();

	await expect(configurationPanel).not.toBeVisible();
});

test('Checks if sidebars are open or closed depending on Product Menu', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check panels are visible

	const panel = page.getByLabel('Fragments and Widgets Panel');
	const configurationPanel = page.getByLabel('Configuration Panel', {
		exact: true,
	});

	// Check if sidebars are not visible when Product Menu is open

	await openProductMenu(page);

	await expect(panel).not.toBeVisible();

	await expect(configurationPanel).not.toBeVisible();

	// Check if sidebars are visible when Product Menu is closed

	await closeProductMenu(page);

	await expect(panel).toBeVisible();

	await expect(configurationPanel).toBeVisible();
});

test('Checks sidebar accessibility', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check where the focus goes when the sidebar is closed

	await page.getByRole('button', {name: 'Close'}).press('Enter');

	await expect(
		page.getByLabel('Fragments and Widgets', {exact: true})
	).toBeFocused();

	// Check with axe

	await checkAccessibility({
		page,
		selectors: ['.page-editor__sidebar'],
	});
});

test.describe('Fragments Panel', () => {
	test('Only published fragments are shown in the Fragments Sidebar', async ({
		apiHelpers,
		fragmentsPage,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create unpublished fragment inside Imported fragment set

		await fragmentsPage.goto(pageManagementSite.friendlyUrlPath);

		const unpublishedFragmentName = getRandomString();
		await fragmentsPage.createFragment('Imported', unpublishedFragmentName);

		// Create content page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Check only published fragment is displayed

		await pageEditorPage.goToSidebarTab('Fragments and Widgets');

		await page
			.getByRole('menuitem', {
				exact: true,
				name: 'Imported',
			})
			.click();

		await expect(page.getByText('Apple')).toBeVisible();

		await expect(page.getByText(unpublishedFragmentName)).not.toBeVisible();
	});

	test('Can remove search text when pressing backspace', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create content page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Open the "Fragments and Widgets" panel

		await pageEditorPage.goToSidebarTab('Fragments and Widgets');

		// Find the search input and type some text

		const searchInput = page.getByPlaceholder('Search...');

		await searchInput.fill('Heading');

		// Verify the search text is present

		await expect(searchInput).toHaveValue('Heading');

		// Press Backspace to remove the text

		await searchInput.press('Backspace');

		// Verify the search text has been removed

		await expect(searchInput).toHaveValue('Headin');
	});

	test('Favorite section', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create content page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Open the "Fragments and Widgets" panel

		await pageEditorPage.goToSidebarTab('Fragments and Widgets');

		// Assert favorite section is empty

		await expect(
			page.getByRole('menuitem', {name: 'Favorites'})
		).not.toBeVisible();
	});
});

test.describe('Page Contents Panel', () => {
	test('Allows editing inline text from Page Content Panel', async ({
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

		// Go to Page Contents panel

		await pageEditorPage.goToSidebarTab('Page Content');

		// Hover the content and check that the fragment is hovered

		const content = page.getByLabel('Edit Text Heading Example');

		await content.hover();

		const headingFragment = page.locator('.component-heading');

		await expect(headingFragment).toHaveClass(
			/page-editor__editable--content-hovered/
		);

		// Edit inline text

		await content.click();

		const editable = pageEditorPage.getEditable({
			editableId: 'element-text',
			fragmentId: headingId,
		});

		await editable.locator('.cke_editable_inline').waitFor();

		// Clear current content text and fill with new one

		await page.keyboard.press('Control+KeyA');
		await page.keyboard.press('Backspace');

		await page.keyboard.type('New Content');
		await page.locator('body').click();

		await pageEditorPage.waitForChangesSaved();

		await expect(
			page.locator('.page-editor__page-contents__page-content')
		).toContainText('New Content');
	});
});

test.describe('Page Design Options', () => {
	test(
		'Allows editing inline text from Page Content Panel',
		{
			tag: '@LPS-146373',
		},
		async ({apiHelpers, page, pageEditorPage, site}) => {

			// Create a page

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			// Go to edit mode of page

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Go to Page Contents panel

			await pageEditorPage.goToSidebarTab('Page Design Options');

			// Go to look and feel

			await page
				.getByTitle('More Page Design Options', {exact: true})
				.click();

			// Assert sections

			await expect(
				page.getByRole('heading', {name: 'Theme'})
			).toBeAttached();

			await expect(
				page.getByRole('heading', {name: 'Basic Settings'})
			).toBeAttached();

			await expect(
				page.getByRole('heading', {name: 'Customization'})
			).toBeAttached();
		}
	);
});

test.describe('Rules Panel', () => {
	test('Checks the accessibility of the rule modal by filling out a condition and an action', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create content page with a Heading fragment and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Add rule and check accessibility of modal

		await pageEditorPage.goToSidebarTab('Page Rules');

		await page.getByRole('button', {name: 'New Rule'}).click();

		await pageEditorPage.addRuleCondition();

		await pageEditorPage.addRuleAction();

		await checkAccessibility({
			page,
			selectors: ['.modal-body'],
		});
	});
});
