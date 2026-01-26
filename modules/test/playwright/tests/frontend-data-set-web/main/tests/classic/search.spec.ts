/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import getRandomString from '../../../../../utils/getRandomString';
import {waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPD-22473': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({
		fragmentKeys: ['classic-search-fds-sample'],
		site,
	});

	await fdsSamplePage.selectTab('Classic');

	await waitForFDS({page});
});

test(
	'Global FDS state integration',
	{
		tag: ['@LPD-54150'],
	},
	async ({fdsSamplePage, page}) => {
		const searchInput = fdsSamplePage.managementToolbar.searchInput;

		const fragmentInput = page.getByPlaceholder('Search in Classic tab of');
		const fragmentButton = page.getByTestId('classicSearchFDSSampleButton');

		await test.step('Search in fragment results with search in FDS', async () => {
			const sampleSearchText = getRandomString();

			await fragmentInput.fill(sampleSearchText);

			await fragmentButton.click();

			await expect(searchInput).toHaveValue(sampleSearchText);
		});

		await test.step('Search in FDS UI reflects in fragment', async () => {
			const sampleSearchText = getRandomString();

			await searchInput.fill(sampleSearchText);

			await fdsSamplePage.managementToolbar.searchButton.click();

			await expect(fragmentInput).toHaveValue(sampleSearchText);
		});

		await test.step('Clear search in FDS UI reflects in fragment', async () => {
			const sampleSearchText = getRandomString();

			await searchInput.fill(sampleSearchText);

			await fdsSamplePage.managementToolbar.searchButton.click();

			await waitForFDS({
				empty: true,
				page,
			});

			await expect(fragmentInput).toHaveValue(sampleSearchText);

			await fdsSamplePage.activeFiltersToolbar.clearSearchButton.click();

			await expect(fragmentInput).toHaveValue('');
		});

		await test.step('Clear all in FDS UI reflects in fragment', async () => {
			const sampleSearchText = getRandomString();

			await searchInput.fill(sampleSearchText);

			await fdsSamplePage.managementToolbar.searchButton.click();

			await waitForFDS({
				empty: true,
				page,
			});

			await expect(fragmentInput).toHaveValue(sampleSearchText);

			await fdsSamplePage.activeFiltersToolbar.clearButton.click();

			await expect(fragmentInput).toHaveValue('');
		});

		await test.step('Search in FDS URL config reflects in fragment', async () => {
			const sampleSearchText = getRandomString();

			await searchInput.fill(sampleSearchText);

			await fdsSamplePage.managementToolbar.searchButton.click();

			await waitForFDS({
				empty: true,
				page,
			});

			// FDS URL config will be set after page reload

			await page.reload();

			await page.waitForResponse((resp) => resp.status() === 200);

			await waitForFDS({
				empty: true,
				page,
			});

			await expect(fragmentInput).toHaveValue(sampleSearchText);
		});
	}
);
