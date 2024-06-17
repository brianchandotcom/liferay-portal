/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page } from "@playwright/test";

export async function createDynamicSegment(page: Page) {
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Dynamic Segment'}).click();
}

export async function createStaticSegment(page: Page) {
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Static Segment'}).click();
}

export async function addSegmentField(page: Page, segmentType: string, segmentCriterion: string) {
	await page.locator('button.dropdown-toggle.btn-outline-secondary').click();
	await page.getByRole('menuitem', { name: segmentType }).click();

	await dragAndDropCriteriaItem(page, segmentCriterion);
}

export async function setSegmentName(page: Page, segmentName: string) {
	await page.getByRole('button', { name: 'Unnamed Segment' }).click();
	await page.getByPlaceholder('Unnamed Segment').fill(segmentName);
}

export async function editCriteriaAttributeValue(page: Page, attributeValue: string) {
	await page.locator('input[data-testid="attribute-value-string-input"]').click();
	await page
		.locator('input[data-testid="attribute-value-string-input"]')
		.fill(attributeValue);
}
	const target = page.locator('div.drop-zone-target').last();

	return await source.dragTo(target);
}

export async function saveSegment(page: Page) {
	await page.getByRole('button', {name: 'Save Segment'}).click();
	await page.waitForSelector('div.alert-success', { state: 'visible' });
}

export async function editSegment(page: Page) {
	await page.getByRole('link', {name: 'Edit Segment'}).click();
	await page.waitForSelector('text=Edit Individuals Segment');
}