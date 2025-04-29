/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {Individuals, MetricType, RangeSelectors} from '../types';
import {waitForLoading} from './loading';

export async function changeGlobalFilters(
	page: Page,
	{
		individual,
		metricType = MetricType.Views,
		rangeSelector,
	}: {
		individual: Individuals;
		metricType?: MetricType;
		rangeSelector: RangeSelectors;
	}
) {
	await page.getByTestId('rangeSelectors').click();

	await page.getByTestId(`filter-item-${rangeSelector}`).click();

	await waitForLoading(page);

	await page.getByTestId('individuals').click();

	await page.getByTestId(`filter-item-${individual}`).click();

	await waitForLoading(page);

	await page
		.getByTestId(`overview__${metricType.toLowerCase()}-metric`)
		.click();

	await waitForLoading(page);
}
