import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {openProductMenu} from '../../../../utils/productMenu';
import {JournalPage} from '../../../journal-web/pages/JournalPage';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function deleteObjectEntries({
	entityName,
	page,
	siteUrl,
}: {
	entityName: 'Lemons' | 'Lemon Baskets' | 'Potatoes';
	page: Page;
	siteUrl: Site['friendlyUrlPath'];
}) {

	// Go to Web Content admin

	const journalPage = new JournalPage(page);
	await journalPage.goto(siteUrl);

	// Go to entity

	await openProductMenu(page);

	await page.getByRole('menuitem', {name: entityName}).click();

	await page.locator('.dnd-tbody').waitFor();

	// Remove all entries one by one

	let count = await page
		.locator('.dnd-tbody .item-actions')
		.getByRole('button')
		.count();

	while (count > 0) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				exact: true,
				name: 'Delete',
			}),
			trigger: page.locator('.dnd-tbody .item-actions').first(),
		});

		await page.getByRole('button', {exact: true, name: 'Delete'}).waitFor();

		await page.getByRole('button', {exact: true, name: 'Delete'}).click();

		await expect(page.locator('.dnd-tbody .item-actions')).toHaveCount(
			count - 1
		);

		count--;
	}
}
