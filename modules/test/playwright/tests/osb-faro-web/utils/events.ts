/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {selectAndExpectToHaveValue} from '../../../utils/selectAndExpectToHaveValue';

export async function addBreakdown({
	breakdownName,
	page,
	tab,
}: {
	breakdownName: string;
	page: Page;
	tab: string;
}) {
	await page
		.locator('.attribute-breakdown-section-root')
		.getByRole('button')
		.click();

	await page.locator('.card-tab').filter({hasText: tab}).first().click();

	await page
		.getByRole('menuitem', {exact: true, name: breakdownName})
		.click();

	await expect(
		page
			.locator('.attribute-breakdown-section-root')
			.filter({hasText: breakdownName})
	).toBeVisible();
}

export async function addCustomEvent({
	customEventName,
	page,
}: {
	customEventName: string;
	page: Page;
}) {
	await page.getByLabel('Add').click();

	await page.locator('.card-tab').filter({hasText: 'Custom'}).click();

	await page
		.getByRole('menuitem', {exact: true, name: customEventName})
		.click();

	await expect(
		page.locator('.event-container').filter({hasText: customEventName})
	).toBeVisible();
}

export async function addFilter({
	filterName,
	operator,
	page,
	pageTitle,
}: {
	filterName: string;
	operator: string;
	page: Page;
	pageTitle: string;
}) {
	await page
		.locator('.attribute-filter-section-root')
		.getByRole('button')
		.click();

	await page.getByRole('menuitem', {exact: true, name: filterName}).click();

	await page.locator('select.select-root').click();

	await selectAndExpectToHaveValue({
		optionLabel: operator,
		select: page.getByLabel('Condition'),
	});

	await page.getByTestId('attribute-value-string-input').fill(pageTitle);

	await page.keyboard.press('Enter');

	await expect(
		page
			.locator('.attribute-filter-section-root')
			.filter({hasText: filterName})
	).toBeVisible();
}

export async function removeAttribute({
	page,
	section,
}: {
	page: Page;
	section: string;
}) {
	if (section === 'Event') {
		await page
			.locator('.event-section-root')
			.getByRole('button', {name: 'Close'})
			.click();
	}

	if (section === 'Breakdown') {
		await page
			.locator('.attribute-breakdown-section-root')
			.getByRole('button', {name: 'Close'})
			.click();
	}

	if (section === 'Filter') {
		await page
			.locator('.attribute-filter-section-root')
			.getByRole('button', {name: 'Close'})
			.click();
	}
}

export async function setEventAnalysisName({
	eventAnalysisName,
	page,
}: {
	eventAnalysisName: string;
	page: Page;
}) {
	const editEventAnalysisName = page.getByText('Unnamed Analysis');

	if (await editEventAnalysisName.isVisible()) {
		await editEventAnalysisName.click();
	}

	await page.getByPlaceholder('Analysis').fill(eventAnalysisName);
}
