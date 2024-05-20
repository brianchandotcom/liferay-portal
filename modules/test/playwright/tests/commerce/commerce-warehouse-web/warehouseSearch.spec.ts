/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-25885 Can search warehouse by name', async ({
	apiHelpers,
	commerceAdminWarehousesPage,
	page,
}) => {
	const warehouse1 =
		await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehouse();
	const warehouse2 =
		await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehouse();

	await commerceAdminWarehousesPage.goto();
	await commerceAdminWarehousesPage.managementToolbarSearchInput.fill(
		warehouse1.name['en_US']
	);
	await commerceAdminWarehousesPage.managementToolbarSearchInput.press(
		'Enter'
	);

	await expect(page.getByText(warehouse1.name['en_US'])).toBeVisible();
	await expect(page.getByText(warehouse2.name['en_US'])).toBeHidden();
});
