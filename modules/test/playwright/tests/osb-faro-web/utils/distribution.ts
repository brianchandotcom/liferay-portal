/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {waitForLoading} from './loading';

export async function addBreakdownByAttribute({
	attributeName,
	page,
}: {
	attributeName: String;
	page: Page;
}) {
	await page.getByLabel('Add').click();
	await page.getByPlaceholder('Select Field').click();

	await page
		.locator(`div.dropdown-menu span:has-text("${attributeName}")`)
		.click();

	await page.getByLabel('Breakdown Name').click();
	await page.getByLabel('Breakdown Name').fill(getRandomString());
	await page.getByRole('button', {name: 'Save'}).click();

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
	const card = page.locator('.distribution-card-root');
	const ticks = card.locator(
		'.recharts-cartesian-axis.recharts-xAxis .recharts-layer.recharts-cartesian-axis-tick'
	);

	const ticksCount = await ticks.count();
	const lastTick = ticks.nth(ticksCount - 1);

	const lastTickValue = await lastTick.textContent();

	expect(card.getByText(attributeValue)).toBeVisible();
	expect(lastTickValue).toEqual(maxCount);
}
