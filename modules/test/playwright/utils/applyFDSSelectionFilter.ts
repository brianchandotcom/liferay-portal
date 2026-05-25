/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export async function applyFDSSelectionFilter(
	page: Page,
	{
		exclude = false,
		filter,
		value,
	}: {exclude?: boolean; filter: string; value: string}
) {
	await page.getByRole('button', {exact: true, name: 'Filter'}).click();

	await page.getByRole('menuitem', {exact: true, name: filter}).click();

	await page.getByRole('checkbox', {exact: true, name: value}).check();

	if (exclude) {
		await page.getByRole('switch', {exact: true, name: 'Exclude'}).click();
	}

	await page.getByRole('button', {exact: true, name: 'Add Filter'}).click();

	const chipName = exclude
		? `${filter}: (Exclude) ${value}`
		: `${filter}: ${value}`;

	await expect(page.getByRole('button', {name: chipName})).toBeVisible();
}
