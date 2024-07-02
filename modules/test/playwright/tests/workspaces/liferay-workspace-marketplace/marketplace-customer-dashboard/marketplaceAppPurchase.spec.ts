/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {backendPageTest} from '../../../../fixtures/backendPageTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../utils/getRandomInt';
import {getTempDir} from '../../../../utils/temp';
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

export const test = mergeTests(
	backendPageTest,
	dataApiHelpersTest,
	marketplacePagesTest,
	marketplaceSiteFixture
);

export const ORDER_ITEMS = {
	DECIMAL_QUANTITY: 1,
	QUANTITY: 1,
	UNIT_PRICE: 1,
};

export const CUSTOMER_ACCOUNT_NAME = `Customer${getRandomInt()}`;
export const PRODUCT_NAME = `Product${getRandomInt()}`;

test.describe('Can Purchase and Manage Apps', () => {
	let _catalog;
	let _customerAccount;
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

		_customerAccount = account;
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
						en_US: 'paid',
					},
				},
				{
					specificationKey: 'latest-version',
					value: {
						en_US: '1.0.1',
					},
				},
			],
			productStatus: PRODUCT_WORKFLOW_STATUS_CODE.APPROVED,
			productType: 'virtual',
			productVirtualSettings: {
				productVirtualSettingsFileEntries: [
					{
						attachment: btoa('liferay'),
						version: 'Liferay Portal 7.4 GA110',
					},
				],
			},
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

		await apiHelpers.headlessAdminUser.deleteAccount(_customerAccount.id);
	});

	test('LPD-21740 The customer can download by using the kebab', async ({
		customerDashboardAppDetailsPage,
		customerDashboardPage,
		marketplace,
	}) => {
		await customerDashboardPage.goto(
			`web${marketplace.friendlyUrlPath}/customer-dashboard`
		);

		await customerDashboardPage.selectAccount(CUSTOMER_ACCOUNT_NAME);

		await expect(
			customerDashboardPage.purchasedApp(PRODUCT_NAME)
		).toBeVisible();

		await expect(
			customerDashboardPage.tableKebabButton(PRODUCT_NAME)
		).toBeVisible();

		await customerDashboardPage
			.tableKebabButton(PRODUCT_NAME)
			.waitFor({state: 'visible'});

		await clickAndExpectToBeVisible({
			target: customerDashboardPage.page.getByText('Download App'),
			trigger: customerDashboardPage.tableKebabButton(PRODUCT_NAME),
		});

		await customerDashboardPage.dropdownDownloadButton.click();

		await expect(customerDashboardAppDetailsPage.downloadTab).toBeVisible();

		await expect(customerDashboardAppDetailsPage.downloadTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardPage.downloadButton).toBeVisible();

		const downloadPromise =
			customerDashboardPage.page.waitForEvent('download');

		await customerDashboardPage.downloadButton.click();

		const download = await downloadPromise;

		const filePath = getTempDir() + download.suggestedFilename();

		await download.saveAs(filePath);

		await expect(filePath).toBeTruthy();
	});

	test('LPD-21740 Customer can download the app through the table', async ({
		customerDashboardAppDetailsPage,
		customerDashboardPage,
		marketplace,
	}) => {
		await customerDashboardPage.goto(
			`web${marketplace.friendlyUrlPath}/customer-dashboard`
		);

		await customerDashboardPage.selectAccount(CUSTOMER_ACCOUNT_NAME);

		await expect(
			customerDashboardPage.purchasedApp(PRODUCT_NAME)
		).toBeVisible();
		await customerDashboardPage.purchasedApp(PRODUCT_NAME).click();

		await expect(customerDashboardAppDetailsPage.detailTab).toBeVisible();

		await expect(customerDashboardAppDetailsPage.detailTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardAppDetailsPage.downloadTab).toBeVisible();
		await customerDashboardAppDetailsPage.downloadTab.click();

		await expect(customerDashboardAppDetailsPage.downloadTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardPage.downloadButton).toBeVisible();

		const downloadPromise =
			customerDashboardPage.page.waitForEvent('download');

		await customerDashboardPage.downloadButton.click();

		const download = await downloadPromise;

		const filePath = getTempDir() + download.suggestedFilename();

		await download.saveAs(filePath);

		await expect(filePath).toBeTruthy();
	});
});
