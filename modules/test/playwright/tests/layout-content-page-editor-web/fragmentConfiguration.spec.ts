/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

const STYLES = [
	{defaultValue: 'Align Left', label: 'Text Align', type: 'button'},

	{defaultValue: '#00000000', label: 'Background Color', type: 'color'},
	{defaultValue: '#1C1C24', label: 'Border Color', type: 'color'},

	{defaultValue: 'Inherited', label: 'Font Family', type: 'select'},
	{defaultValue: 'Inherited', label: 'Font Size', type: 'select'},
	{defaultValue: 'Inherited', label: 'Font Weight', type: 'select'},

	{defaultValue: '0', label: 'Border Radius', type: 'text'},
	{defaultValue: '0', label: 'Border Width', type: 'text'},
	{defaultValue: '100', label: 'Opacity', type: 'text'},
	{defaultValue: 'none', label: 'Shadow', type: 'text'},
];

const COLOR_PICKER_PALETTES = [
	{sections: ['Brand Colors', 'Gray', 'Theme Colors'], title: 'Color System'},
	{sections: ['Body'], title: 'General'},
	{sections: ['Other'], title: 'Typography'},
	{
		sections: [
			'Button Primary',
			'Button Outline Primary',
			'Button Secondary',
			'Button Outline Secondary',
			'Button Link',
		],
		title: 'Buttons',
	},
];

test.describe('Editable Configuration', () => {
	test('Can set a link to a link editable', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a container

		const buttonId1 = getRandomString();
		const buttonId2 = getRandomString();
		const buttonId3 = getRandomString();

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: buttonId1,
					key: 'BASIC_COMPONENT-button',
				}),
				getFragmentDefinition({
					id: buttonId2,
					key: 'BASIC_COMPONENT-button',
				}),
				getFragmentDefinition({
					id: buttonId3,
					key: 'BASIC_COMPONENT-button',
				}),
			]),
			siteId: pageManagementSite.id,
			title: layoutTitle,
		});

		// Navigate to the page editor

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Change the link of the containers

		await pageEditorPage.selectEditable(buttonId1, 'link');

		await page.getByRole('tab', {exact: true, name: 'Link'}).click();

		await pageEditorPage.setLinkConfiguration({
			type: 'URL',
			url: 'https://liferay.com',
		});

		await expect(
			page.locator(`.lfr-layout-structure-item-${buttonId1} a`)
		).toHaveAttribute('href', 'https://liferay.com');

		await pageEditorPage.selectEditable(buttonId2, 'link');

		await page.getByRole('tab', {exact: true, name: 'Link'}).click();

		await pageEditorPage.setLinkConfiguration({
			layoutTitle,
			type: 'Page',
		});

		await expect(
			page.locator(`.lfr-layout-structure-item-${buttonId2} a`)
		).toHaveAttribute(
			'href',
			`/web${pageManagementSite.friendlyUrlPath}/${layoutTitle}`
		);

		await pageEditorPage.selectEditable(buttonId3, 'link');

		await page.getByRole('tab', {exact: true, name: 'Link'}).click();

		await pageEditorPage.setLinkConfiguration({
			mappingConfiguration: {
				mapping: {
					entity: 'Documents and Media',
					entry: 'poodle.jpg',
					entryLocator: page
						.frameLocator('iframe[title="Select"]')
						.getByText('poodle.jpg', {exact: false}),
					field: 'Download URL',
				},
			},
			type: 'Mapped URL',
		});

		await expect(
			page.locator(`.lfr-layout-structure-item-${buttonId3} a`)
		).toHaveAttribute('href', /poodle\.jpg/);

		await pageEditorPage.publishPage();

		// Check that the links are correct in view mode

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const buttons = page.locator('.component-button a');

		const firstButtonHref = await buttons
			.first()
			.evaluate((element) => element.getAttribute('href'));

		expect(firstButtonHref).toContain('https://liferay.com');

		const secondButtonHref = await buttons
			.nth(1)
			.evaluate((element) => element.getAttribute('href'));

		expect(secondButtonHref).toContain(
			`/web${pageManagementSite.friendlyUrlPath}/${layoutTitle}`
		);

		const thirdButtonHref = await buttons
			.last()
			.evaluate((element) => element.getAttribute('href'));

		expect(thirdButtonHref).toContain('poodle.jpg');
	});
});

test.describe('Advanced Configuration', () => {
	test('Add multiple css classes to fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a content page with a Heading fragment

		const fragmentId = getRandomString();

		const fragmentDefinition = getFragmentDefinition({
			id: fragmentId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragmentDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Adds css classes and assert that added to the page

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'CSS Classes',
			fragmentId,
			tab: 'Advanced',
			value: 'background-color',
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'CSS Classes',
			fragmentId,
			tab: 'Advanced',
			value: 'border-color',
		});

		const fragmentContent = page.locator('.page-editor__fragment-content');

		await expect(fragmentContent).toHaveClass(/background-color/);
		await expect(fragmentContent).toHaveClass(/border-color/);
	});

	test('Checks that the fragment is hidden from Site Search Results', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const layouts = {fragment: null, searchBar: null};

		// Create a page with the Search Bar widget

		const widgetLayoutId = getRandomString();

		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet',
		});

		layouts.searchBar = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: widgetLayoutId,
		});

		// Create a page with a fragment and publish it

		const headingId = getRandomString();

		const headingFragmentDefinition = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		layouts.fragment = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingFragmentDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layouts.fragment, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(headingId);

		await pageEditorPage.publishPage();

		// Go to the Search Bar page and search for the fragment text

		await page.goto(
			`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
		);

		const searchBar = page.getByPlaceholder('Search...');

		await searchBar.click();
		await searchBar.fill('Heading');

		await page
			.locator('.search-bar-suggestions .loading-animation')
			.waitFor();
		await page
			.locator('.search-bar-suggestions .loading-animation')
			.waitFor({state: 'hidden'});

		// Check that there are results

		const searchResults = page.getByText('Suggestions');

		await expect(searchResults).toBeVisible();

		// Go back to the fragment page and hide the fragment from the search results

		await pageEditorPage.goto(layouts.fragment, site.friendlyUrlPath);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Hide from Site Search Results',
			fragmentId: headingId,
			tab: 'Advanced',
			value: true,
		});

		await pageEditorPage.publishPage();

		// Go to the Search Bar page and search for the fragment text

		await page.goto(
			`/web${site.friendlyUrlPath}${layouts.searchBar.friendlyUrlPath}`
		);

		await searchBar.click();
		await searchBar.fill('Heading');

		await page
			.locator('.search-bar-suggestions .loading-animation')
			.waitFor();
		await page
			.locator('.search-bar-suggestions .loading-animation')
			.waitFor({state: 'hidden'});

		// Check that there are no results

		await expect(searchResults).not.toBeVisible();
	});

	test('Checks that the advanced configuration of a fragment appears in its corresponding tab', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a content page with Page Management Site's Apple fragment

		const fragmentDefinition = getFragmentDefinition({
			fragmentConfig: {
				color: 'red',
			},
			id: getRandomString(),
			key: 'apple',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragmentDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Check advanced configuration appears where it should

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await page.getByTitle('Browser').click();

		await page.getByLabel('Select Apple').click();

		await pageEditorPage.goToConfigurationTab('Advanced');

		await expect(page.getByLabel('Color', {exact: true})).toBeVisible();
	});
});

test.describe('Styles Configuration', () => {
	test('Allows selecting a color palette color', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a page with a Separator fragment

		const separatorId = getRandomString();

		const separatorFragment = getFragmentDefinition({
			id: separatorId,
			key: 'BASIC_COMPONENT-separator',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([separatorFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to the created page

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Select the Separator fragment

		await pageEditorPage.selectFragment(separatorId);

		// Select a color in the color palette

		await pageEditorPage.goToConfigurationTab('Styles');

		await page.getByTitle('success', {exact: true}).click();

		await pageEditorPage.waitForChangesSaved();

		// Check that the color is applied

		expect(
			page
				.locator('.component-separator hr')
				.evaluate((element) =>
					element.classList.contains('border-success')
				)
		).toBeTruthy();

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		expect(
			page
				.locator('.component-separator hr')
				.evaluate((element) =>
					element.classList.contains('border-success')
				)
		).toBeTruthy();
	});

	test('Allows changing and resetting spacing', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		await page.goto('/');

		// Create a page with a Heading fragment

		const headingId = getRandomString();

		const headingFragment = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Check Saved icon is not visible at the beggining

		await expect(page.getByLabel('Saved')).not.toBeVisible();

		// Change Margin Top with custom value and check change is applied

		await pageEditorPage.changeFragmentSpacing(
			headingId,
			'Margin Top',
			'5',
			'px'
		);
		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: headingId,
				isTopperStyle: true,
				style: 'marginTop',
			})
		).toBe('5px');

		// Change Margin Top with token value and check change is applied

		await pageEditorPage.changeFragmentSpacing(
			headingId,
			'Margin Top',
			'2'
		);
		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: headingId,
				isTopperStyle: true,
				style: 'marginTop',
			})
		).toBe('8px');

		// Reset to initial value and check change is applied

		await pageEditorPage.resetSpacing(headingId, 'Margin Top');

		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: headingId,
				isTopperStyle: true,
				style: 'marginTop',
			})
		).toBe('0px');
	});

	test(
		'Renders all selectors with correct default values',
		{
			tag: '@LPS-136412',
		},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			await page.goto('/');

			// Create a page with a Heading fragment

			const headingId = getRandomString();

			const headingFragment = getFragmentDefinition({
				id: headingId,
				key: 'BASIC_COMPONENT-heading',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([headingFragment]),
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.selectFragment(headingId);

			await pageEditorPage.goToConfigurationTab('Styles');

			// Check correct default values are rendered

			for (const {defaultValue, label, type} of STYLES) {
				if (type === 'button') {
					await expect(
						page.getByRole('button', {
							exact: true,
							name: defaultValue,
						})
					).toHaveAttribute('aria-pressed', 'true');
				}
				else if (type === 'color') {
					await expect(
						page
							.getByLabel(label, {exact: true})
							.getByLabel('Color', {exact: true})
					).toHaveValue(defaultValue);
				}
				else if (type === 'select') {
					expect(
						await page
							.getByLabel(label, {exact: true})
							.evaluate(
								(node: HTMLSelectElement) =>
									node.options[node.selectedIndex].text
							)
					).toBe(defaultValue);
				}
				else {
					await expect(
						page.getByLabel(label, {exact: true})
					).toHaveValue(defaultValue);
				}
			}
		}
	);

	test('Renders correct sections in color picker', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		await page.goto('/');

		// Create a page with a Heading fragment and go to edit mode

		const headingId = getRandomString();

		const headingFragment = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Check correct sections are displayed

		await pageEditorPage.selectFragment(headingId);

		await pageEditorPage.goToConfigurationTab('Styles');

		await page.locator('.layout__dropdown-color-picker__selector').click();

		for (const palette of COLOR_PICKER_PALETTES) {
			await expect(
				page
					.locator('.layout__dropdown-color-picker__color-palette')
					.getByText(palette.title)
			).toBeAttached();

			for (const section of palette.sections) {
				await expect(
					page
						.locator(
							'.layout__dropdown-color-picker__color-palette'
						)
						.getByText(section)
				).toBeAttached();
			}
		}
	});

	test('Changes the value in the Color Picker when the reset button is clicked', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		await page.goto('/');

		// Create page with heading fragment and go to edit mode

		const headingId = getRandomString();

		const headingFragment = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Select fragment and go to Styles configuration panel

		await pageEditorPage.selectFragment(headingId);

		await pageEditorPage.goToConfigurationTab('Styles');

		const backgroundColorInput = page
			.getByLabel('Background Color')
			.locator('.layout__color-picker__input');

		await fillAndClickOutside(page, backgroundColorInput, '#AAA');

		await page.getByLabel('Reset to Initial Value').click();

		// Check the value gets at least six characters

		await backgroundColorInput.click();

		await fillAndClickOutside(page, backgroundColorInput, '#000');

		await expect(backgroundColorInput).toHaveValue('#000000');
	});
});
