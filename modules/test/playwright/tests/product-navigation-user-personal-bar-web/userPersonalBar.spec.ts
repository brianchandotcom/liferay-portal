/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {userPersonalBarPagesTest} from '../../fixtures/userPersonalBarPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	userPersonalBarPagesTest,
	loginTest
);

test('LPD-15423 notification badge configuration enables and disables notification badge in personal menu', async ({
	apiHelpers,
	page,
	userPersonalBarPage,
}) => {
	await userPersonalBarPage.goToProcessBuilderConfigurationTab();
	await userPersonalBarPage.enableSingleApproverWorkflowProduct();
	await userPersonalBarPage.disableNotificationBadgeInPersonalMenu();

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog(
		'Test Catalog'
	);

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct(
		catalog.id,
		'Test Product'
	);

	const response = await apiHelpers.headlessCommerceAdminCatalog.patchProduct(
		product.productId
	);

	expect(response.ok()).toBe(true);

	await page.goto('/');

	await expect(userPersonalBarPage.notificationBadge).not.toBeVisible();

	await userPersonalBarPage.enableNotificationBadgeInPersonalMenu();

	await page.goto('/');

	await expect(userPersonalBarPage.notificationBadge).toBeVisible();

	await userPersonalBarPage.disableSingleApproverWorkflowProduct();

	await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
		product.productId
	);

	await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(catalog.id);
});
