/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest
);

test('Assert table columns', async ({dataSetsPage, page}) => {
	await dataSetsPage.goto();
	await dataSetsPage.createDataSet();

	const tableColumns = await page
		.locator('.dnd-thead > .dnd-tr')
		.first()
		.locator('.dnd-th')
		.count();

	await expect(tableColumns).toBe(6);
});
