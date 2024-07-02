/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {marketplacePagesTest} from '../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../fixtures/marketplaceSite';
import {
	assignMarketplaceUserToAccountRole,
	createMarketplaceAccountUserCatalog,
	createMarketplaceTestProductOrder,
} from '../helpers/marketplaceHelpers';
import {
	MARKETPLACE_CHANNEL,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../utils/constants';
import {
	CUSTOMER_ACCOUNT_NAME,
	ORDER_ITEMS,
	PRODUCT_NAME,
} from './marketplaceAppPurchase.spec';

export const test = mergeTests(marketplaceSiteFixture, marketplacePagesTest);

test.describe('Custumers Can View Marketplace App Details', () => {
	let _catalog;
	let _account;
	let _product;
	let _order;

	test.beforeEach(async ({apiHelpers}) => {
		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`name eq ${MARKETPLACE_CHANNEL}`
			);

		const {account, catalog} = await createMarketplaceAccountUserCatalog({
			accountName: CUSTOMER_ACCOUNT_NAME,
			accountType: 'person',
			apiHelpers,
		});

		_account = account;
		_catalog = catalog;

		await assignMarketplaceUserToAccountRole({
			accountId: account.id,
			accountRole: 'Account Buyer',
			apiHelpers,
		});

		const productBody = {
			active: true,
			catalogId: catalog.id,
			name: {
				en_US: PRODUCT_NAME,
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

		const {order, product} = await createMarketplaceTestProductOrder({
			accountId: account.id,
			apiHelpers,
			orderItems: ORDER_ITEMS,
			productBody,
		});

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
		await customerDashboardAppDetailsPage.goto(
			`web${marketplace.friendlyUrlPath}/customer-dashboard`
		);

		await customerDashboardAppDetailsPage.selectAccount(
			CUSTOMER_ACCOUNT_NAME
		);

		await expect(
			customerDashboardAppDetailsPage.purchasedApp(PRODUCT_NAME)
		).toBeVisible();

		await clickAndExpectToBeVisible({
			target: customerDashboardAppDetailsPage.detailTab,
			trigger: customerDashboardAppDetailsPage.purchasedApp(PRODUCT_NAME),
		});

		await expect(
			customerDashboardAppDetailsPage.productTitle(PRODUCT_NAME)
		).toBeVisible();

		await expect(
			customerDashboardAppDetailsPage.catalogTitle(_catalog.name)
		).toBeVisible();

		await expect(customerDashboardAppDetailsPage.summaryTab).toBeVisible();
	});
});
