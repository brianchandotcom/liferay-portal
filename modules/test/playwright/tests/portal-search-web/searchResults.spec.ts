/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {searchPageTest} from '../../fixtures/searchPageTest';
export const test = mergeTests(
	isolatedLayoutTest({type: 'portlet'}),
	loginTest(),
	searchPageTest
);

test.describe('Use two Search Results widgets in the same page', () => {
	test('Change the pagination delta of a search results widget on a page with two search results widgets @LPD-36512', async ({
		layout,
		page,
		searchPage,
	}) => {
		await test.step('Add Search Results and Search Options portlets', async () => {
			await page.goto('/web/guest' + layout.friendlyURL);

			await searchPage.addPortlet('Search Results', 'Search');

			await searchPage.addPortlet('Search Options', 'Search');

			await searchPage.addPortlet('Search Results', 'Search');

			await searchPage.addPortlet('Search Options', 'Search');
		});

		await test.step('Configure Search Results portlets', async () => {
			await searchPage.openSearchPortletConfiguration('Search Results');

			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Pagination Delta Parameter',
					value: 'delta1',
				},
				{
					label: 'Pagination Start Parameter',
					value: 'start1',
				},
				{
					label: 'Federated Search Key Enter',
					value: 'federatedSearchKey1',
				},
			]);

			await searchPage.savePortletConfiguration();

			await searchPage.openSearchPortletConfiguration(
				'Search Results',
				1
			);

			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Federated Search Key Enter',
					value: 'federatedSearchKey2',
				},
			]);

			await searchPage.savePortletConfiguration();
		});

		await test.step('Configure Search Options Configuration portlets', async () => {
			await searchPage.openSearchPortletConfiguration('Search Options');

			await searchPage.selectPortletConfigurationsCheckbox([
				{
					label: 'Allow Empty Searches',
					value: true,
				},
			]);
			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Federated Search Key Enter',
					value: 'federatedSearchKey1',
				},
			]);

			await searchPage.savePortletConfiguration();

			await searchPage.openSearchPortletConfiguration(
				'Search Options',
				1
			);

			await searchPage.selectPortletConfigurationsCheckbox([
				{
					label: 'Allow Empty Searches',
					value: true,
				},
			]);
			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Federated Search Key Enter',
					value: 'federatedSearchKey2',
				},
			]);

			await searchPage.savePortletConfiguration();
		});

		await test.step('Check if the pagination delta of a Search Results widget persists when changing that of another widget', async () => {
			await page.reload();

			await searchPage.selectPaginationItemsPerPage(4);

			await expect(
				searchPage.searchResultsPaginationItemsPerPageToggle.nth(1)
			).toHaveText('20 Entries');

			await searchPage.selectPaginationItemsPerPage(8, 1);

			await expect(
				searchPage.searchResultsPaginationItemsPerPageToggle.nth(0)
			).toHaveText('4 Entries');
		});

		await test.step('Remove the federatedSearchKey of one Search Results and Search Options widget', async () => {
			await searchPage.openSearchPortletConfiguration('Search Results');

			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Federated Search Key Enter',
					value: '',
				},
			]);

			await searchPage.savePortletConfiguration();

			await searchPage.openSearchPortletConfiguration('Search Options');

			await searchPage.fillPortletConfigurationsInput([
				{
					label: 'Federated Search Key Enter',
					value: '',
				},
			]);

			await searchPage.savePortletConfiguration();
		});

		await test.step('Check if the pagination delta of a search results widget persists when changing that of another widget', async () => {
			await page.goto('/web/guest' + layout.friendlyURL);

			await searchPage.selectPaginationItemsPerPage(40);

			await expect(
				searchPage.searchResultsPaginationItemsPerPageToggle.nth(1)
			).toHaveText('20 Entries');

			await searchPage.selectPaginationItemsPerPage(4, 1);

			await expect(
				searchPage.searchResultsPaginationItemsPerPageToggle.nth(0)
			).toHaveText('40 Entries');
		});
	});
});
