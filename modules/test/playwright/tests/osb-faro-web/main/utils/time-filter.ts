/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
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

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: timeFilterPeriod}),
		trigger: element.locator('.dropdown-range-key-root button'),
	});

	await waitForLoading(page);
}
