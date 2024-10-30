/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../../../fixtures/loginTest';

const test = mergeTests(loginTest());

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
