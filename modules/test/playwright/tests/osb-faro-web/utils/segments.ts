/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {segmentConditions} from './selectors';
import {searchByTerm} from './utils';

export async function addSegmentField({
	criterionName,
	criterionType,
	page,
}: {
	criterionName: string;
	criterionType?: string;
	page: Page;
}) {
	await page.locator('button.dropdown-toggle.btn-outline-secondary').click();
	await page.getByRole('menuitem', {name: criterionType}).click();

	await dragAndDropCriteriaItem({
		page,
		segmentField: criterionName,
	});
}

export async function addStaticMember({
	memberNames,
	page,
}: {
	memberNames: string[] | string;
	page: Page;
}) {
	await page.getByRole('button', {name: 'Add Members'}).click();

	const namesArray = Array.isArray(memberNames) ? memberNames : [memberNames];

	for (const memberName of namesArray) {
		await searchByTerm({
			page,
			searchTerm: memberName,
		});

		await page.locator('.clickable').getByText(memberName).first().click();
	}

	await page.getByRole('button', {name: 'Add', exact: true}).click();
}

export async function createDynamicSegment(page: Page) {
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Dynamic Segment'}).click();
}

export async function createStaticSegment(page: Page) {
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Static Segment'}).click();
}

export async function deleteSegment({
	page,
	segmentName,
}: {
	page: Page;
	segmentName: string;
}) {
	await searchByTerm({
		page,
		searchTerm: segmentName,
	});

	await page.locator('.dropdown-action').click();
	await page.locator('button.dropdown-item:has-text("Delete")').click();
	await page.getByRole('button', {name: 'Delete'}).click();
}

export async function dragAndDropCriteriaItem({
	page,
	segmentField,
}: {
	page: Page;
	segmentField: string;
}) {
	const source = page.getByText(segmentField);
	const target = page.locator('div.drop-zone-target').last();

	return await source.dragTo(target);
}

export async function editCriteriaAttributeValue({
	attributeValue,
	page,
}: {
	attributeValue: string;
	page: Page;
}) {
	await page
		.locator('input[data-testid="attribute-value-string-input"]')
		.click();
	await page
		.locator('input[data-testid="attribute-value-string-input"]')
		.fill(attributeValue);
}

export async function editSegment(page: Page) {
	await page.getByRole('link', {name: 'Edit Segment'}).click();
	await page.waitForSelector('text=Edit Individuals Segment');
}

export async function saveSegment(page: Page) {
	await page.locator('button[type="submit"]').click();
	await page.waitForSelector('div.alert-success', {state: 'visible'});
}

export async function selectOperator({
	index = 0,
	operator,
	operatorField,
	page,
}: {
	index?: number;
	operator: string;
	operatorField: segmentConditions;
	page: Page;
}) {
	await page.locator(operatorField).nth(index).click();
	await page.getByRole('option', {name: operator}).click();
}

export async function setSegmentName({
	page,
	segmentName,
}: {
	page: Page;
	segmentName: string;
}) {
	const editDynamicSegmentName = page.getByText('Unnamed Segment');

	if (await editDynamicSegmentName.isVisible()) {
		await editDynamicSegmentName.click();
	}

	await page.getByPlaceholder('Segment').fill(segmentName);
}
