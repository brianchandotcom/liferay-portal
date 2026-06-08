/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {selectAndExpectToHaveValue} from '../../../../utils/selectAndExpectToHaveValue';
import {ACPage, navigateToACPageViaURL} from './navigation';

export async function addAttributeFilter({
	attributeName,
	condition,
	page,
	value,
}: {
	attributeName: string;
	condition: string;
	page: Page;
	value: string;
}) {
	await page
		.locator('.attribute-filter-section-root')
		.getByLabel('Add')
		.click();

	await page
		.getByRole('menuitem', {exact: true, name: attributeName})
		.click();

	await page.getByLabel('Condition').click();

	await selectAndExpectToHaveValue({
		optionLabel: condition,
		select: page.getByLabel('Condition'),
	});

	const valueInput = page
		.locator(
			"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
		)
		.first();

	await valueInput.click();

	// Type character by character so the autocomplete suggestions are fetched

	await valueInput.pressSequentially(value);

	await page.getByRole('option', {exact: true, name: value}).click();

	await page.getByRole('button', {name: 'Apply'}).click();

	await expect(
		page
			.locator('.attribute-filter-section-root')
			.filter({hasText: attributeName})
	).toBeVisible();
}

export async function addBooleanFilter({
	attributeName,
	page,
	value,
}: {
	attributeName: string;
	page: Page;
	value: 'false' | 'true';
}) {
	await page
		.locator('.attribute-filter-section-root')
		.getByLabel('Add')
		.click();

	await page
		.getByRole('menuitem', {exact: true, name: attributeName})
		.click();

	await selectAndExpectToHaveValue({
		optionValue: value,
		select: page.getByLabel('Condition'),
	});

	await page.getByRole('button', {name: 'Apply'}).click();

	await expect(
		page
			.locator('.attribute-filter-section-root')
			.filter({hasText: attributeName})
	).toBeVisible();
}

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
		.getByLabel('Add')
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

export async function addDateFilter({
	attributeName,
	operator,
	page,
	value,
}: {
	attributeName: string;
	operator: string;
	page: Page;
	value: string;
}) {
	await page
		.locator('.attribute-filter-section-root')
		.getByLabel('Add')
		.click();

	await page
		.getByRole('menuitem', {exact: true, name: attributeName})
		.click();

	await selectAndExpectToHaveValue({
		optionLabel: operator,
		select: page.getByLabel('Condition'),
	});

	await page.getByTestId('date-input').fill(value);

	await page.getByRole('button', {name: 'Apply'}).click();

	await expect(
		page
			.locator('.attribute-filter-section-root')
			.filter({hasText: attributeName})
	).toBeVisible();
}

export async function addFilter({
	filterName,
	input,
	operator,
	page,
}: {
	filterName: string;
	input: any;
	operator: string;
	page: Page;
}) {
	await page
		.locator('.attribute-filter-section-root')
		.getByRole('button')
		.click();

	await page.getByRole('menuitem', {exact: true, name: filterName}).click();

	await page.getByLabel('Condition').click();

	await selectAndExpectToHaveValue({
		optionLabel: operator,
		select: page.getByLabel('Condition'),
	});

	await page
		.locator(
			"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
		)
		.first()
		.fill(input);

	await page.keyboard.press('Enter');

	await expect(
		page
			.locator('.attribute-filter-section-root')
			.filter({hasText: filterName})
	).toBeVisible();
}

export async function createAndSaveEventAnalysis({
	channelId,
	eventName,
	name,
	page,
	projectId,
}: {
	channelId: string;
	eventName: string;
	name: string;
	page: Page;
	projectId: string;
}) {
	await navigateToACPageViaURL({
		acPage: ACPage.eventAnalysisPage,
		channelID: channelId,
		page,
		projectID: projectId,
	});

	await page.getByRole('link', {name: 'Create Analysis'}).click();

	await setEventAnalysisName({eventAnalysisName: name, page});

	await addCustomEvent({customEventName: eventName, page});

	await page
		.locator('.event-analysis-toolbar-right-content')
		.getByRole('button', {name: 'Save Analysis'})
		.click();

	await expect(page.getByText(name, {exact: true})).toBeVisible();
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
	const editEventAnalysisName = await page.locator(
		'.event-analysis-toolbar-left-content button'
	);

	await editEventAnalysisName.click();

	await page.keyboard.type(eventAnalysisName);
}
