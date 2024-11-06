import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {gotoObjectEntries} from './gotoObjectEntries';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function deleteObjectEntries({
	entityName,
	page,
	siteUrl,
}: {
	entityName: 'All Fields' | 'Lemons' | 'Lemon Baskets' | 'Potatoes';
	page: Page;
	siteUrl: Site['friendlyUrlPath'];
}) {

	// Go to Entity

	await gotoObjectEntries({
		entityName,
		page,
		siteUrl,
	});

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

		await page.waitForEvent('load');

		await expect(page.locator('.dnd-tbody .item-actions')).toHaveCount(
			count - 1
		);

		count--;
	}
}
