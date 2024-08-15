/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../../utils/getRandomInt';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';
import {
	MARKETPLACE_CHANNEL,
	ORDER_ITEMS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../utils/constants';

export const test = mergeTests(
	marketplaceSiteFixture,
	marketplacePagesTest,
	marketplaceHelper
);

test.describe('Custumers Can View Marketplace App Details', () => {
	let _catalog;
	let _account;
	let _product;
	let _order;
	const accountName = `Customer Account${getRandomInt()}`;
	const productName = `Product${getRandomInt()}`;

	test.beforeEach(async ({apiHelpers, marketplaceHelper}) => {
		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`name eq ${MARKETPLACE_CHANNEL}`
			);

		const {account, catalog} =
			await marketplaceHelper.createAccountUserCatalog({
				accountName,
				accountType: 'person',
			});

		_account = account;
		_catalog = catalog;

		await marketplaceHelper.assignUserToAccountRole({
			accountId: account.id,
			accountRole: 'Account Buyer',
		});

		const productBody = {
			active: true,
			catalogId: catalog.id,
			name: {
				en_US: productName,
			},
			productChannels: [
				{
					channelId: channel.items[0].id,
					currencyCode: 'USD',
					id: channel.items[0].id,
					name: MARKETPLACE_CHANNEL,
					type: 'site',
				},
			],
			productSpecifications: [
				{
					specificationKey: 'type',
					value: {
						en_US: 'DXP',
					},
				},
				{
					specificationKey: 'price-model',
					value: {
						en_US: 'free',
					},
				},
			],
			productStatus: PRODUCT_WORKFLOW_STATUS_CODE.APPROVED,
			productType: 'virtual',
		};

		const {order, product} = await marketplaceHelper.createTestProductOrder(
			{
				accountId: account.id,
				channelId: channel.items[0].id,
				orderItems: ORDER_ITEMS,
				productBody,
			}
		);

		_order = order;
		_product = product;
	});

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessCommerceAdminOrder.deleteOrder(_order.id);

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
			_product.productId
		);

		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
			_catalog.id
		);

		await apiHelpers.headlessAdminUser.deleteAccount(_account.id);
	});

	test('LPD-30131 The customer can view the details tab and info', async ({
		customerDashboardAppDetailsPage,
		marketplace,
	}) => {
		await customerDashboardAppDetailsPage.goto(marketplace.friendlyUrlPath);

		await customerDashboardAppDetailsPage.selectAccount(accountName);

		await expect(
			customerDashboardAppDetailsPage.purchasedApp(productName)
		).toBeVisible();

		await clickAndExpectToBeVisible({
			target: customerDashboardAppDetailsPage.detailTab,
			trigger: customerDashboardAppDetailsPage.purchasedApp(productName),
		});

		await expect(
			customerDashboardAppDetailsPage.productTitle(productName)
		).toBeVisible();

		await expect(
			customerDashboardAppDetailsPage.catalogTitle(_catalog.name)
		).toBeVisible();

		await expect(customerDashboardAppDetailsPage.summaryTab).toBeVisible();
	});
});
