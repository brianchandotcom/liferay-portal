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
import createUserWithPermissions from '../../utils/createUserWithPermissions';
import getRandomString from '../../utils/getRandomString';
import {performUserSwitch} from '../../utils/performLogin';
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

		// Create unpublished fragment inside Page Management fragment set

		await fragmentsPage.goto(pageManagementSite.friendlyUrlPath);

		const unpublishedFragmentName = getRandomString();

		await fragmentsPage.createFragment(
			'Page Management Fragments',
			unpublishedFragmentName
		);

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
				name: 'Page Management Fragments',
			})
			.click();

		await expect(page.getByText('Apple')).toBeVisible();

		await expect(page.getByText(unpublishedFragmentName)).not.toBeVisible();

		// Check that the new set appears in the last position

		const lastFragmentSet = page
			.getByLabel('Fragments', {exact: true})
			.locator('.panel-header')
			.last();

		await expect(lastFragmentSet).toContainText(
			'Page Management Fragments'
		);

		// Delete unpublished fragment

		await fragmentsPage.goto(pageManagementSite.friendlyUrlPath);

		await fragmentsPage.gotoFragmentSet('Page Management Fragments');

		await fragmentsPage.deleteFragment(unpublishedFragmentName);
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

	test('Favorite section is empty when there are no favorites', async ({
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

	test(
		'A widget marked as favorite in a content page is also marked in a widget page',
		{tag: '@LPS-161732'},
		async ({apiHelpers, page, pageEditorPage, site}) => {

			// Create content page and go to edit mode

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Go to the Widget tab a select one widget as favorite

			await pageEditorPage.goToSidebarTab('Fragments and Widgets');

			await page.getByRole('tab', {exact: true, name: 'Widgets'}).click();

			let highlightedSet = page
				.locator('.page-editor__collapse')
				.filter({hasText: 'Highlighted'});

			await highlightedSet.waitFor();

			const favoriteButton = page.getByTitle(
				'Mark Reports Display as Favorite'
			);

			// If the widget is already marked as favorite, unmark it

			if ((await favoriteButton.count()) > 1) {
				await favoriteButton.first().click();

				expect(favoriteButton).toHaveCount(1);
			}

			await favoriteButton.click();

			// Check that the widget is inside Highlighted set

			await expect(highlightedSet).toContainText('Reports Display');

			// Check that the widget is also inside Highlighted set in a widget page

			await page.goto(`/search`);

			await page.getByLabel('Add').click();

			highlightedSet = page.locator('.panel', {hasText: 'Highlighted'});

			await expect(highlightedSet).toContainText('Reports Display');

			// Check that a new user with update permissions cannot see the changes

			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const user = await createUserWithPermissions({
				apiHelpers,
				rolePermissions: [
					{
						actionIds: ['UPDATE'],
						primaryKey: company.companyId,
						resourceName: 'com.liferay.portal.kernel.model.Layout',
						scope: 1,
					},
				],
			});

			await performUserSwitch(page, user.alternateName);

			await page.goto(`/search`);

			await page.getByLabel('Add').click();

			await expect(highlightedSet).not.toContainText('Reports Display');
		}
	);

	test('Fragment and widget sets are reordered', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const moveSet = async (setTitle: string) => {

			// Move the set 2 positions down

			await page.getByLabel(setTitle, {exact: true}).press('Enter');

			await page.keyboard.press('ArrowDown');

			await page.keyboard.press('ArrowDown');

			await page.keyboard.press('ArrowDown');

			await page.keyboard.press('Enter');
		};

		// Create content page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Open the "Fragments and Widgets" and get the first set of fragments

		await pageEditorPage.goToSidebarTab('Fragments and Widgets');

		const tabpanel = page
			.locator('.page-editor__sidebar__fragments-widgets-panel')
			.getByRole('tabpanel');

		const fragmentSets = tabpanel.first().locator('.panel-header');

		const firstFragmentSet = await fragmentSets.first().textContent();

		// Got to the Widgets tab and get the first set of widgets

		await page.getByRole('tab', {exact: true, name: 'Widgets'}).click();

		await page.getByText('Highlighted').waitFor();

		let widgetSets = tabpanel
			.last()
			.locator('.panel-header', {hasNotText: 'Highlighted'});

		const firstWidgetSet = await widgetSets.first().textContent();

		// Open "Reorder Sets" modal and reorder the first set of fragments

		await page.getByTitle('Reorder Sets', {exact: true}).click();

		const modal = page.locator('.modal-body');

		await modal
			.getByText('Fragments and widgets sets can be ordered')
			.waitFor();

		await moveSet(firstFragmentSet);

		// Go to the Widget tab and reorder the first set

		await modal.getByRole('tab', {exact: true, name: 'Widgets'}).click();

		await modal.getByText(firstWidgetSet).waitFor();

		await moveSet(firstWidgetSet);

		// Save

		await page.getByText('Save', {exact: true}).click();

		await pageEditorPage.waitForChangesSaved();

		// Check that the position of the first widget set has changed

		await expect(widgetSets.nth(2)).toContainText(firstWidgetSet);

		// Go back to the Fragments tab and check that the position of the first fragment has changed

		await page.getByRole('tab', {exact: true, name: 'Fragments'}).click();

		await expect(fragmentSets.nth(2)).toContainText(firstFragmentSet);

		// Refresh the page and check that order is maintained

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(fragmentSets.nth(2)).toContainText(firstFragmentSet);

		await page.getByRole('tab', {exact: true, name: 'Widgets'}).click();

		await expect(widgetSets.nth(2)).toContainText(firstWidgetSet);

		// Check that the order is maintained on the widget page

		await page.goto(`/search`);

		await page.getByLabel('Add').click();

		widgetSets = page.locator('.sidebar-body__add-panel .panel-header', {
			hasNotText: 'Highlighted',
		});

		await expect(widgetSets.nth(2)).toContainText(firstWidgetSet);

		// Check that a new user with update permissions cannot see the changes

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['UPDATE'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Layout',
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await page.goto(`/search`);

		await page.getByLabel('Add').click();

		[
			'Accounts',
			'Business Intelligence & Reporting',
			'Collaboration',
			'Commerce',
			'Community',
			'Content Management',
			'News',
			'Object',
			'Sample',
			'Search',
		].forEach(async (set, index) => {
			await expect(widgetSets.nth(index)).toContainText(set);
		});
	});

	test(
		'Save interactions with the panel when the page is refresh',
		{tag: '@LPS-76741'},
		async ({apiHelpers, page, pageEditorPage, site}) => {

			// Create content page and go to edit mode

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Change the view of the fragments to Cards

			await pageEditorPage.goToSidebarTab('Fragments and Widgets');

			const firstSetList = page
				.locator('.page-editor__collapse ul')
				.first();

			await expect(firstSetList).toHaveClass(
				/page-editor__fragments-widgets__tab-collection-list/
			);

			await page.getByTitle('Switch to Card View').click();

			// Open Cookie Banner collapse

			const menuDisplayFragmentSet = page.getByRole('menuitem', {
				exact: true,
				name: 'Cookie Banner',
			});

			await expect(menuDisplayFragmentSet).toHaveClass(/collapsed/);

			await menuDisplayFragmentSet.click();

			// Refresh the page and check that everything remains the same

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.goToSidebarTab('Fragments and Widgets');

			await expect(firstSetList).toHaveClass(
				/page-editor__fragments-widgets__tab-collection-cards/
			);

			await expect(menuDisplayFragmentSet).not.toHaveClass(/collapsed/);

			// Reset the panel

			await page.getByTitle('Switch to List View').click();

			await menuDisplayFragmentSet.click();
		}
	);

	test(
		'List fragment is disabled when dragging',
		{tag: '@LPS-130964'},
		async ({apiHelpers, page, pageEditorPage, site}) => {

			// Create content page and go to edit mode

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Check that the list fragment is disabled when dragging

			await pageEditorPage.goToSidebarTab('Fragments and Widgets');

			const fragment = page
				.locator('.page-editor__fragments-widgets__tab-list-item')
				.filter({hasText: 'External Video'});

			await fragment.hover();

			await page.mouse.down();

			await page
				.getByText('Drag and drop fragments or widgets here.')
				.hover();

			expect(fragment).toHaveClass(/disabled/);
		}
	);
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
