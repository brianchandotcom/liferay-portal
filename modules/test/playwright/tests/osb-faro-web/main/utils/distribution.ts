/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../../utils/fillAndClickOutside';
import getRandomString from '../../../../utils/getRandomString';
import {waitForLoading} from './loading';
import {navigateTo} from './navigation';

export async function addBreakdownByAttribute({
	attributeName,
	attributeValue,
	page,
}: {
	attributeName: String;
	attributeValue?: String;
	page: Page;
}) {
	await clickAndExpectToBeVisible({
		target: page.getByPlaceholder('Select Field'),
		trigger: page.getByRole('button', {name: 'Add'}),
	});

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.locator(
			`div.dropdown-menu span:has-text("${attributeName}")`
		),
		trigger: page.getByPlaceholder('Select Field'),
	});

	await fillAndClickOutside(
		page,
		page.getByLabel('Breakdown Name'),
		String(attributeValue || getRandomString())
	);

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForLoading(page);
}

export async function goToDistributionTabAndSelectAttribute({
	attributeName,
	page,
}: {
	attributeName: string;
	page: Page;
}) {
	await navigateTo({page, pageName: 'Distribution'});

	await waitForLoading(page);

	await page.locator('.selected-item-container').click();

	await page
		.locator(`div.dropdown-menu span:has-text("${attributeName}")`)
		.click();

	await waitForLoading(page);
}

export async function viewBreakdownRechartsData({
	attributeValue,
	maxCount,
	page,
}: {
	attributeValue: string;
	maxCount: string;
	page: Page;
}) {
	if ((await page.$('.distribution-card-root')) !== null) {

		// If it is on the Breakdown by Individual Attribute card

		const card = page.locator('.distribution-card-root');
		const ticks = card.locator(
			'.recharts-cartesian-axis.recharts-xAxis .recharts-layer.recharts-cartesian-axis-tick'
		);

		const ticksCount = await ticks.count();
		const lastTick = ticks.nth(ticksCount - 1);

		const lastTickValue = await lastTick.textContent();

		expect(card.getByText(attributeValue).first()).toBeVisible();
		expect(lastTickValue).toEqual(maxCount);
	}
	else {

		// If it is on the Distribution by Attribute in the Distribution tab

		const ticks = page.locator(
			'.recharts-cartesian-axis.recharts-xAxis .recharts-layer.recharts-cartesian-axis-tick'
		);

		const ticksCount = await ticks.count();
		const lastTick = ticks.nth(ticksCount - 1);

		const lastTickValue = await lastTick.textContent();

		expect(page.getByText(attributeValue).first()).toBeVisible();
		expect(lastTickValue).toEqual(maxCount);
	}
}
