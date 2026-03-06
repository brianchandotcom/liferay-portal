/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {designLibrariesPageTest} from './fixtures/designLibrariesPageTest';

const test = mergeTests(
	apiHelpersTest,
	designLibrariesPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPD-57283': {enabled: true},
	}),
	loginTest()
);

test('Check if Design Library is working correctly', async ({
	designLibrariesPage,
	page,
}) => {
	await test.step('Can navigate to Design Libraries page', async () => {
		await designLibrariesPage.goto();

		await expect(page.getByTestId('header')).toHaveText('Design Libraries');
	});

	await test.step('Check that the empty state labels are correct', async () => {
		await expect(page.getByText('No Design Libraries Yet')).toBeVisible();

		await expect(
			page.getByText('Click "New" to create your first Design Library.')
		).toBeVisible();

		await expect(
			page.getByRole('button', {name: 'New Design Library'})
		).toBeVisible();
	});

	await test.step('Check that the "/states/design_library_empty_state.svg" image is displayed', async () => {
		await expect(
			designLibrariesPage.emptyStateContainer.locator(
				'img[src$="/states/design_library_empty_state.svg"]'
			)
		).toBeVisible();
	});
});

test('Can navigate to a Design Library dashboard', async ({
	apiHelpers,
	designLibrariesPage,
	page,
}) => {
	const designLibraryName = getRandomString();

	const depot = await apiHelpers.jsonWebServicesDepot.addDepotEntry(
		designLibraryName,
		{type: apiHelpers.jsonWebServicesDepot.depotType.DESIGN_LIBRARY}
	);

	await test.step('Navigate to a Design Library dashboard', async () => {
		await designLibrariesPage.goto();

		await expect(page.getByTestId('header')).toHaveText('Design Libraries');

		const designLibraryLink = page.getByRole('link', {
			name: designLibraryName,
		});

		await designLibraryLink.click();
	});

	await test.step('Check dashboard elements', async () => {
		const breadcrumb = page.getByRole('navigation', {name: 'Breadcrumb'});

		await expect(breadcrumb).toBeVisible();

		const links = breadcrumb.getByRole('link');

		expect(await links.count()).toEqual(2);

		expect(links.first()).toHaveText('Design Libraries');

		expect(links.last()).toHaveText(designLibraryName);

		const moreActionsButton = page.getByRole('button', {
			name: 'More Actions',
		});

		await moreActionsButton.click();

		await expect(page.getByRole('menu')).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Settings'})
		).toBeVisible();

		await expect(
			page
				.getByRole('menu')
				.getByRole('menuitem', {name: 'Connected Sites'})
		).toBeVisible();

		await expect(
			page
				.getByRole('menu')
				.getByRole('menuitem', {name: 'Manage Members'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Import'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Export'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Delete'})
		).toBeVisible();
	});

	await apiHelpers.jsonWebServicesDepot.deleteDepotEntry(depot.depotEntryId);
});
