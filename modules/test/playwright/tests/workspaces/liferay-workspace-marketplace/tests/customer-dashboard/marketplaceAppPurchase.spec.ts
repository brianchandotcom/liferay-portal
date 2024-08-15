/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../../utils/getRandomInt';
import {getTempDir} from '../../../../../utils/temp';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';
import {
	MARKETPLACE_CHANNEL,
	ORDER_ITEMS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../utils/constants';

export const test = mergeTests(
	marketplacePagesTest,
	marketplaceHelper,
	marketplaceSiteFixture
);

test.describe('Can Purchase and Manage Apps', () => {
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

	test('LPD-21740 The customer can download by using the kebab', async ({
		customerDashboardAppDetailsPage,
		customerDashboardPage,
		marketplace,
	}) => {
		await customerDashboardPage.goto(marketplace.friendlyUrlPath);

		await customerDashboardPage.selectAccount(accountName);

		await expect(
			customerDashboardPage.purchasedApp(productName)
		).toBeVisible();

		await expect(
			customerDashboardPage.tableKebabButton(productName)
		).toBeVisible();

		await customerDashboardPage
			.tableKebabButton(productName)
			.waitFor({state: 'visible'});

		await clickAndExpectToBeVisible({
			target: customerDashboardPage.page.getByText('Download App'),
			trigger: customerDashboardPage.tableKebabButton(productName),
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
		await customerDashboardPage.goto(marketplace.friendlyUrlPath);

		await customerDashboardPage.selectAccount(accountName);

		await expect(
			customerDashboardPage.purchasedApp(productName)
		).toBeVisible();
		await customerDashboardPage.purchasedApp(productName).click();

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
