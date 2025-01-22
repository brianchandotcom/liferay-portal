/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	pageViewModePagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'heckout with single approval',
	{tag: '@LPD-3360'},
	async ({
		apiHelpers,
		commerceAdminChannelsPage,
		commerceMiniCartPage,
		site,
		widgetPagePage,
	}) => {
		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: `${site.name} Channel`,
				siteGroupId: site.id,
			});

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
				name: `${site.name} Catalog`,
			});

		const product1 =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: 'Product1'},
			});

		const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
			.getProduct(product1.productId)
			.then((product) => {
				return product.skus;
			});

		const sku1 = product1Skus[0];

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'business',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			['test@liferay.com']
		);

		await commerceAdminChannelsPage.changeCommerceChannelSiteType(
			channel.name,
			'B2B'
		);

		await commerceAdminChannelsPage.changeCommerceChannelBuyerOrderApprovalWorkflow(
			'Single Approver (Version 1)',
			channel.name
		);

		await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{
						options: '[]',
						quantity: 1,
						replacedSkuId: 0,
						skuId: sku1.id,
					},
				],
			},
			channel.id
		);

		const widgetLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: 'Commerce Cart Page',
		});

		await widgetPagePage.goto(widgetLayout, site.friendlyUrlPath);

		await widgetPagePage.addPortlet('Cart Summary');

		await commerceMiniCartPage.submitButton.waitFor({state: 'visible'});

		await expect(commerceMiniCartPage.submitButton).toBeVisible();
	}
);
