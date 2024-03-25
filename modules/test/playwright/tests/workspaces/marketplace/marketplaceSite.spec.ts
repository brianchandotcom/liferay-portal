/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {marketplaceSiteFixture} from './fixtures/marketplaceSite';

export const test = mergeTests(marketplaceSiteFixture);

test('Open Marketplace Homepage', async ({page}) => {
	const appsDescription = await page.getByText(
		"Expand Liferay's portfolio through apps from our partner and developer ecosystem"
	);
	const appsTitle = await page.getByText('App Marketplace');
	const searchInput = await page.getByTestId('searchInput');

	expect(appsDescription).toBeTruthy();
	expect(appsTitle).toBeTruthy();
	expect(searchInput).toBeVisible();
});
