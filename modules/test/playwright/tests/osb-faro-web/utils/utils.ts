/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';
import {waitForLoading} from './loading';

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
}

export async function expectNotToBeVisible({
	itemNames,
	page,
}: {
	itemNames: string[];
	page: Page;
}) {
	for (const itemName of itemNames) {
		await expect(page.getByRole('cell', {name: itemName})).toBeHidden({
			timeout: 100 * 1000,
		});
	}
}

export async function expectToBeVisible({
	itemNames,
	page,
}: {
	itemNames: string[];
	page: Page;
}) {
	for (const itemName of itemNames) {
		await expect(page.getByRole('cell', {name: itemName})).toBeVisible({
			timeout: 100 * 1000,
		});
	}
}
