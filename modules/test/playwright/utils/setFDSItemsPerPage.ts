/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export async function setFDSItemsPerPage(
	page: Page,
	itemsPerPage: 10 | 20 | 30 | 50
) {
	const itemsPerPageButton = page
		.locator('.data-set-pagination-wrapper')
		.getByLabel('Items Per Page');

	await itemsPerPageButton.click();

	const dropdownId = await itemsPerPageButton.getAttribute('aria-controls');

	await page
		.locator(`#${dropdownId}`)
		.getByRole('option', {exact: true, name: `${itemsPerPage} Items`})
		.click();

	await expect(itemsPerPageButton).toHaveText(`${itemsPerPage} Items`);
}
