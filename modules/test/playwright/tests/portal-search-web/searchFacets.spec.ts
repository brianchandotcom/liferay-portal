/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {searchPageTest} from '../../fixtures/searchPageTest';

export const test = mergeTests(loginTest(), searchPageTest);

test.describe('Clear and retain facet selections', () => {
	let typeDocumentFacetCheckbox: Locator;
	let folderLiferayFacetCheckbox: Locator;
	let userTestTestFacetCheckbox: Locator;
	let lastModifiedPastYearFacetLink: Locator;

	test.beforeEach(async ({searchPage}) => {
		await searchPage.goto();

		// Perform a search

		await searchPage.searchKeywordInNavBar('test');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for test/
		);

		// Select facet terms and assert checked

		folderLiferayFacetCheckbox = await searchPage.getSearchFacetCheckbox(
			'Provided by Liferay',
			'Folder'
		);
		typeDocumentFacetCheckbox = await searchPage.getSearchFacetCheckbox(
			/Document\s/,
			'Type'
		);
		userTestTestFacetCheckbox = await searchPage.getSearchFacetCheckbox(
			'Test Test',
			'User'
		);
		lastModifiedPastYearFacetLink = await searchPage.getSearchFacetLink(
			'Past Year',
			'Last Modified'
		);

		await searchPage.selectSearchFacetCheckbox(folderLiferayFacetCheckbox);
		await searchPage.selectSearchFacetCheckbox(typeDocumentFacetCheckbox);
		await searchPage.selectSearchFacetCheckbox(userTestTestFacetCheckbox);
		await searchPage.selectSearchFacetLink(lastModifiedPastYearFacetLink);
	});

	test.afterEach(async ({searchPage}) => {

		// Teardown by resetting search options

		await searchPage.selectSearchOptionCheckboxConfigurations([
			{
				label: 'Allow Empty Searches',
				value: false,
			},
			{
				label: 'Retain Facet Selections Across Searches',
				value: false,
			},
		]);
	});

	test('clears facet terms after new keyword search @LPD-19994', async ({
		searchPage,
	}) => {

		// Perform new search with different keyword

		await searchPage.searchKeywordInNavBar('png');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for png/
		);

		// Verify that facet selections are cleared

		await expect(folderLiferayFacetCheckbox).not.toBeChecked();
		await expect(typeDocumentFacetCheckbox).not.toBeChecked();
		await expect(userTestTestFacetCheckbox).not.toBeChecked();
		await expect(lastModifiedPastYearFacetLink).not.toHaveClass(
			/facet-term-selected/
		);
	});

	test('retains facet terms if search keyword has not changed @LPD-19994', async ({
		searchPage,
	}) => {

		// Perform new search with same keyword

		await searchPage.searchKeywordInNavBar('test');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for test/
		);

		// Verify that facet selections are retained

		await expect(folderLiferayFacetCheckbox).toBeChecked();
		await expect(typeDocumentFacetCheckbox).toBeChecked();
		await expect(userTestTestFacetCheckbox).toBeChecked();
		await expect(lastModifiedPastYearFacetLink).toHaveClass(
			/facet-term-selected/
		);
	});

	test('retains items per page after new keyword search @LPD-19994', async ({
		searchPage,
	}) => {

		// Change pagination items per page and page number

		await searchPage.selectPaginationItemsPerPage(4);

		await searchPage.selectPaginationPageNumber(2);

		// Perform new search with different keyword

		await searchPage.searchKeywordInNavBar('png');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for png/
		);

		// Verify that page number is reset but items per page is not

		await expect(
			searchPage.searchResultsPaginationItemsPerPageToggle
		).toHaveText('4 Entries Per Page');

		await expect(
			searchPage.searchResultsPaginationBar.getByText('1').first()
		).toHaveAttribute('aria-current', 'page');

		await expect(searchPage.searchResultsPaginationDescription).toHaveText(
			/Showing 1 to 4 of \d+ entries./
		);
	});

	test('clears facet terms if performing an empty search @LPD-19994', async ({
		page,
		searchPage,
	}) => {

		// Configure search options to retain facet selections

		await searchPage.selectSearchOptionCheckboxConfigurations([
			{
				label: 'Allow Empty Searches',
				value: true,
			},
		]);

		await page.reload();

		// Perform new search with empty keyword

		await searchPage.searchKeywordInNavBar('');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for\s+/
		);

		// Verify that facet selections are cleared

		await expect(folderLiferayFacetCheckbox).not.toBeChecked();
		await expect(typeDocumentFacetCheckbox).not.toBeChecked();
		await expect(userTestTestFacetCheckbox).not.toBeChecked();
		await expect(lastModifiedPastYearFacetLink).not.toHaveClass(
			/facet-term-selected/
		);
	});

	test('retains facet terms if configured under search options @LPD-19994', async ({
		page,
		searchPage,
	}) => {

		// Configure search options to retain facet selections

		await searchPage.selectSearchOptionCheckboxConfigurations([
			{
				label: 'Retain Facet Selections Across Searches',
				value: true,
			},
		]);

		await page.reload();

		// Perform new search with different keyword

		await searchPage.searchKeywordInNavBar('png');

		await expect(searchPage.searchResultsTotalLabel).toHaveText(
			/\d+ Results for png/
		);

		// Verify that facet selections are retained

		await expect(folderLiferayFacetCheckbox).toBeChecked();
		await expect(typeDocumentFacetCheckbox).toBeChecked();
		await expect(userTestTestFacetCheckbox).toBeChecked();
		await expect(lastModifiedPastYearFacetLink).toHaveClass(
			/facet-term-selected/
		);
	});
});
