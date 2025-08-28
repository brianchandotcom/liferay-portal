/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPD-52212': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('Advanced');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

test(
	'Check the empty state',
	{
		tag: ['@LPD-56880'],
	},
	async ({fdsSamplePage, page}) => {
		await test.step('Check the empty state with a filter applied', async () => {
			await test.step('Apply the status "Pending" filter', async () => {
				await fdsSamplePage.managementToolbar.container
					.getByRole('button', {name: 'Filter'})
					.click();

				await page
					.locator('.dropdown-menu')
					.getByRole('menuitem', {name: 'Status'})
					.click();

				await page
					.locator('.dropdown-menu')
					.getByRole('checkbox', {name: 'Pending'})
					.check();

				await page
					.locator('.dropdown-menu')
					.getByRole('button', {name: 'Add Filter'})
					.click();
			});

			await test.step('Check that the text "Review your filters and try again." is displayed', async () => {
				await expect(
					page.getByText('Review your filters and try again.')
				).toBeVisible();
			});

			await test.step('Check that the button "Clear Filters" is displayed', async () => {
				await expect(
					fdsSamplePage.emptyStateContainer.getByRole('button', {
						name: 'Clear Filters',
					})
				).toBeVisible();
			});

			await test.step('Check that when clicking the button "Clear Filters", the filters are removed', async () => {
				await fdsSamplePage.emptyStateContainer
					.getByRole('button', {name: 'Clear Filters'})
					.click();

				await waitForFDS({
					page,
					visualizationMode: EFDSVisualizationMode.TABLE,
				});

				await expect(
					fdsSamplePage.activeFiltersToolbar
				).not.toBeVisible();
			});
		});

		await test.step('Check the empty state with a search applied', async () => {
			await test.step('Search using a keyword that will return no results, "no results"', async () => {
				await fdsSamplePage.managementToolbar.searchInput.fill(
					'no results'
				);

				await fdsSamplePage.managementToolbar.container
					.getByRole('button', {name: 'Search'})
					.click();
			});

			await test.step('Check that the text "Review your search and try again." is displayed', async () => {
				await expect(
					page.getByText('Review your search and try again.')
				).toBeVisible();
			});

			await test.step('Check that the button "Clear Search" is displayed', async () => {
				await expect(
					fdsSamplePage.emptyStateContainer.getByRole('button', {
						name: 'Clear Search',
					})
				).toBeVisible();
			});

			await test.step('Check that when clicking the button "Clear Search", the search is removed', async () => {
				await fdsSamplePage.emptyStateContainer
					.getByRole('button', {name: 'Clear Search'})
					.click();

				await waitForFDS({
					page,
					visualizationMode: EFDSVisualizationMode.TABLE,
				});

				await expect(
					fdsSamplePage.managementToolbar.searchInput
				).toBeEmpty();
			});
		});

		await test.step('Check the empty state with a search and filter applied', async () => {
			await test.step('Apply the status "Pending" filter', async () => {
				await fdsSamplePage.managementToolbar.container
					.getByRole('button', {name: 'Filter'})
					.click();

				await page
					.locator('.dropdown-menu')
					.getByRole('checkbox', {name: 'Pending'})
					.check();

				await page
					.locator('.dropdown-menu')
					.getByRole('button', {name: 'Add Filter'})
					.click();
			});

			await test.step('Search using a keyword that will return no results, "no results"', async () => {
				await fdsSamplePage.managementToolbar.searchInput.fill(
					'no results'
				);

				await fdsSamplePage.managementToolbar.container
					.getByRole('button', {name: 'Search'})
					.click();
			});

			await test.step('Check that the text "Review your filters or search and try again." is displayed', async () => {
				await expect(
					page.getByText(
						'Review your filters or search and try again.'
					)
				).toBeVisible();
			});

			await test.step('Check that the button "Clear Search And Filters" is displayed', async () => {
				await expect(
					fdsSamplePage.emptyStateContainer.getByRole('button', {
						name: 'Clear Search And Filters',
					})
				).toBeVisible();
			});

			await test.step('Check that when clicking the button "Clear Search And Filters", the search and filters are removed', async () => {
				await fdsSamplePage.emptyStateContainer
					.getByRole('button', {
						name: 'Clear Search And Filters',
					})
					.click();

				await waitForFDS({
					page,
					visualizationMode: EFDSVisualizationMode.TABLE,
				});

				await expect(
					fdsSamplePage.managementToolbar.searchInput
				).toBeEmpty();

				await expect(
					fdsSamplePage.activeFiltersToolbar
				).not.toBeVisible();
			});
		});
	}
);
