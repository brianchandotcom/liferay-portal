/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page } from "@playwright/test";

export async function createDynamicSegment(page: Page) {
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Dynamic Segment'}).click();
}

export async function dragAndDropCriteriaItem(page: Page, itemName: string) {
	const source = page.getByTestId(`criteria-item-${itemName}`);

	const target = page.locator('div.drop-zone-target').last();

	return await source.dragTo(target);
}

export async function saveSegment(page: Page) {
	await page.getByRole('button', {name: 'Save Segment'}).click();
	await page.waitForSelector('div.alert-success', { state: 'visible' });
}
