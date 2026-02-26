/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {userPersonalBarPagesTest} from '../../../fixtures/userPersonalBarPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	userPersonalBarPagesTest
);

test(
	'Notification badge configuration enables and disables notification badge in personal menu',
	{tag: ['@LPD-15423']},
	async ({apiHelpers, page, userPersonalBarPage}) => {
		test.setTimeout(90000);

		await userPersonalBarPage.goToProcessBuilderConfigurationTab();
		await userPersonalBarPage.enableSingleApproverWorkflowProduct();
		await userPersonalBarPage.disableNotificationBadgeInPersonalMenu();

		await page.goto('/');

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		const response =
			await apiHelpers.headlessCommerceAdminCatalog.patchProduct(
				String(product.productId)
			);

		expect(response.ok()).toBe(true);

		await expect(userPersonalBarPage.notificationBadge).not.toBeVisible();

		await userPersonalBarPage.enableNotificationBadgeInPersonalMenu();

		await page.goto('/');

		await expect(userPersonalBarPage.notificationBadge).toBeVisible();

		await userPersonalBarPage.disableSingleApproverWorkflowProduct();
	}
);
