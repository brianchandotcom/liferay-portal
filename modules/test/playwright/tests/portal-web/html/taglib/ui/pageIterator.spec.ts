/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {samplePageTest} from '../../../../frontend-taglib/fixtures/samplePageTest';

const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	samplePageTest
);

const linkName = 'Search Paginator';

test(
	'Check various accessibility in pagination',
	{tag: ['@LPD-38101', '@LPD-38653', '@LPD-38653']},
	async ({page}) => {
		await test.step('Use searchbar to go to search page', async () => {
			await page.goto('/');

			const searchBar = page.getByPlaceholder('Search...');

			await searchBar.waitFor({state: 'visible'});

			await searchBar.fill('png');

			await searchBar.press('Enter');

			await page
				.getByRole('heading', {name: 'Search Results'})
				.waitFor({state: 'visible'});
		});

		await test.step('Check pagination button is selected and contains option role', async () => {
			await page.getByLabel('Items per Page').click();

			const paginationFourSelection = page.getByRole('option', {
				name: '4  Entries per Page',
			});

			await paginationFourSelection.click();

			const pagination = page.getByLabel('Items per Page');

			await pagination.waitFor({state: 'visible'});

			const paginationLinkSelected = page.locator(
				'a[aria-selected="true"][role="option"][id="4"]'
			);

			await expect(paginationLinkSelected).toBeHidden();
		});

		await test.step('Check pagination list has aria-labelledby', async () => {
			const element = page.locator('.dropdown-menu.dropdown-menu-top');

			await expect(element).toHaveAttribute('aria-labelledby');
		});

		await test.step('Check aria-label is being translated', async () => {
			await page.goto('/es/web/guest/search?q=png');

			await page
				.getByRole('heading', {name: 'Barra de búsqueda'})
				.waitFor({state: 'visible'});

			const paginationTranslated = page.getByLabel('Paginación');

			await expect(paginationTranslated).toBeVisible();
		});
	}
);

test(
	'Intermediate pages button and dropdown accesibility issues',
	{tag: '@LPD-42610'},
	async ({page, samplePage, site}) => {
		await test.step('Add taglib sample to page', async () => {
			await samplePage.setupSampleWidget({
				site,
			});

			await samplePage.selectLink(linkName);
		});

		await test.step('Check intermediate pages button has a tooltip', async () => {
			const intermediatePagesButton = await page.getByRole('button', {
				name: 'Intermediate Pages Use TAB to',
			});

			await expect(intermediatePagesButton).toHaveAttribute('title');
		});

		await test.step('Check intermediate pages dropdown items has a role', async () => {
			const intermediatePagesDropdown = await page.locator(
				'ul.pagination div.dropdown-menu'
			);

			const pageLink = intermediatePagesDropdown
				.locator('a.dropdown-item')
				.first();

			await expect(pageLink).toHaveRole('menuitem');
		});
	}
);
