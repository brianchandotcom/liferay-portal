/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {waitForLoading} from './loading';

export async function viewNameNotPresentOnTableList({
	itemNames,
	page,
}: {
	itemNames: string[] | string;
	page: Page;
}) {
	const itemNamesArray = Array.isArray(itemNames) ? itemNames : [itemNames];

	for (const itemName of itemNamesArray) {
		await expect(
			page.locator('.table-title').getByText(itemName)
		).toBeHidden({
			timeout: 100 * 1000,
		});
	}
}

export async function viewNameOnTableList({
	itemNames,
	page,
}: {
	itemNames: string[] | string;
	page: Page;
}) {
	const itemNamesArray = Array.isArray(itemNames) ? itemNames : [itemNames];

	for (const itemName of itemNamesArray) {
		await expect(
			page.locator('.table-title').getByText(itemName)
		).toBeVisible({
			timeout: 100 * 1000,
		});
	}
}

export async function searchByTerm({
	page,
	searchTerm,
}: {
	page: Page;
	searchTerm: string;
}) {
	await waitForLoading(page);

	await page.getByPlaceholder('Search').first().click();
	await page.getByPlaceholder('Search').first().fill(searchTerm);
	await page.getByPlaceholder('Search').first().press('Enter');

	await waitForLoading(page);
}

export async function viewPaginationResults({
	page,
	paginationResults,
}: {
	page: Page;
	paginationResults: string;
}) {
	await waitForLoading(page);

	await expect(page.locator('.pagination-results')).toContainText(
		paginationResults
	);

	await waitForLoading(page);
}

export async function selectPaginationItemsPerPage({
	itemsPerPage,
	page,
}: {
	itemsPerPage: string;
	page: Page;
}) {
	await waitForLoading(page);

	await page.locator('.pagination-items-per-page').click();

	await page.getByRole('menuitem', {exact: true, name: itemsPerPage}).click();

	await waitForLoading(page);
}

export async function selectPaginationPageNumber({
	page,
	paginationPageNumber,
}: {
	page: Page;
	paginationPageNumber: string;
}) {
	await waitForLoading(page);

	await page
		.locator(`xpath=//*[contains(@href,'page=${paginationPageNumber}')]`)
		.first().click();

	await waitForLoading(page);
}
