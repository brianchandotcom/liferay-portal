/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForLoading} from './loading';
import {CardSelectors} from './selectors';

export async function changeTimeFilter({
	cardSelector,
	page,
	timeFilterPeriod,
}: {
	cardSelector?: CardSelectors;
	page: Page;
	timeFilterPeriod: string;
}) {
	let element: Page | Locator = page;

	if (cardSelector) {
		const card = page.locator(cardSelector);

		element = card;
	}

	const timeFilterButton = element.locator('.dropdown-range-key-root button');

	await timeFilterButton.click();

	await page
		.getByRole('menuitem', {
			name: timeFilterPeriod,
		})
		.click();

	await waitForLoading(page);
}
