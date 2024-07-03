/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {marketplacePagesTest} from '../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../fixtures/marketplaceSite';
import {
	assignMarketplaceUserToAccountRole,
	createMarketplaceAccountUserCatalog,
	createMarketplaceTestProductOrder,
	getMarketplaceVocabularyAndCategory,
} from '../helpers/marketplaceHelpers';
import {
	ORDER_ITEMS,
	PRODUCT_NAME,
} from '../marketplace-customer-dashboard/marketplaceAppPurchase.spec';
import {
	MARKETPLACE_CHANNEL,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../utils/constants';
import {
	ACCOUNT_NAME,
	SOLUTION_PUBLISHER_ROLE,
} from './marketplaceSolutionPublisher.spec';

export const test = mergeTests(marketplaceSiteFixture, marketplacePagesTest);

test.describe('Publishers Can View Marketplace Solution Details', () => {
	let _catalog;
	let _account;
	let _product;
	let _order;

	test.beforeEach(async ({apiHelpers, marketplace}) => {
		console;

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`name eq ${MARKETPLACE_CHANNEL}`
			);

		const {account, catalog} = await createMarketplaceAccountUserCatalog({
			accountName: ACCOUNT_NAME.SUPPLIER,
			accountType: 'supplier',
			apiHelpers,
		});

		_account = account;
		_catalog = catalog;

		await assignMarketplaceUserToAccountRole({
			accountId: account.id,
			accountRole: SOLUTION_PUBLISHER_ROLE,
			apiHelpers,
		});

		const solutionCategory = await getMarketplaceVocabularyAndCategory({
			apiHelpers,
			categoryName: 'Solution',
			siteId: marketplace.id,
			vocabularyName: 'Marketplace Product Type',
		});

		const productBody = {
			active: true,
			catalogId: catalog.id,
			categories: [
				{
					id: solutionCategory.id,
					name: 'Solution',
				},
			],
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
			productSpecifications: [],
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

	test('LPD-30131 The publisher can view the details tab and info', async ({
		marketplace,
		publisherDashboardSolutionDetailsPage,
	}) => {
		await publisherDashboardSolutionDetailsPage.goto(
			marketplace.friendlyUrlPath
		);

		await publisherDashboardSolutionDetailsPage.selectAccount(
			ACCOUNT_NAME.SUPPLIER
		);

		await clickAndExpectToBeVisible({
			target: publisherDashboardSolutionDetailsPage.publishedApp(
				PRODUCT_NAME
			),
			trigger: publisherDashboardSolutionDetailsPage.solutionsTab,
		});

		await clickAndExpectToBeVisible({
			target: publisherDashboardSolutionDetailsPage.detailTab,
			trigger:
				publisherDashboardSolutionDetailsPage.publishedApp(
					PRODUCT_NAME
				),
		});
	});
});
