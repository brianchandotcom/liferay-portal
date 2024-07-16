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
import fillAndClickOutside from '../../utils/fillAndClickOutside';
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

	await pageEditorPage.changeFragmentSpacing(headingId, 'Margin Top', '2');
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

test('Renders all selectors with correct default values', async ({
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

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Styles');

	// Check correct default values are rendered

	for (const {defaultValue, label, type} of STYLES) {
		if (type === 'button') {
			await expect(
				page.getByRole('button', {exact: true, name: defaultValue})
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
			await expect(page.getByLabel(label, {exact: true})).toHaveValue(
				defaultValue
			);
		}
	}
});

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
					.locator('.layout__dropdown-color-picker__color-palette')
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
