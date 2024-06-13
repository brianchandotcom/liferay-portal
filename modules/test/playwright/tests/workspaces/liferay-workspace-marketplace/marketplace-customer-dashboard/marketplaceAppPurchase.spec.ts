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
	MARKETPLACE_CHANNEL,
	ORDER_TYPES,
	ORDER_WORKFLOW_STATUS_CODE,
	PAYMENT_STATUS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../utils/constants';

export const test = mergeTests(
	backendPageTest,
	dataApiHelpersTest,
	marketplacePagesTest,
	marketplaceSiteFixture
);

const ORDER_ITEM_DECIMAL_QUANTITY = 1;
const ORDER_ITEM_QUANTITY = 1;
const ORDER_ITEM_UNIT_PRICE = 1;

const CUSTOMER_ACCOUNT_NAME = `Customer${getRandomInt()}`;
const PRODUCT_NAME = `Product${getRandomInt()}`;

test.describe('Can Purchase and Manage Apps', () => {
	let _catalog;
	let _customerAccount;
	let _product;
	let _order;

	test.beforeEach(async ({apiHelpers}) => {
		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		const customer = await apiHelpers.headlessAdminUser.postAccount({
			name: CUSTOMER_ACCOUNT_NAME,
			type: 'person',
		});

		_customerAccount = customer;

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		_catalog = catalog;

		const rolesResponse =
			await apiHelpers.headlessAdminUser.getAccountRoles(customer.id);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			customer.id,
			['test@liferay.com']
		);

		const customerAccountRole = rolesResponse?.items?.filter(
			(role) => role.name === 'Account Buyer'
		);

		await apiHelpers.headlessAdminUser.assignUserToAccountRole(
			customer.id,
			customerAccountRole[0].id,
			user.id
		);

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`name eq ${MARKETPLACE_CHANNEL}`
			);

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
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
			});

		_product = product;

		const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
			accountId: customer.id,
			channelId: channel.items[0].id,
			orderItems: [
				{
					decimalQuantity: ORDER_ITEM_DECIMAL_QUANTITY,
					quantity: ORDER_ITEM_QUANTITY,
					skuId: product.skus[0].id as unknown as string,
					unitPrice: ORDER_ITEM_UNIT_PRICE,
				},
			],
			orderTypeExternalReferenceCode: ORDER_TYPES.DXPAPP,
		});

		_order = order;

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			paymentStatus: PAYMENT_STATUS.COMPLETED,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			orderStatus: ORDER_WORKFLOW_STATUS_CODE.COMPLETED,
		});
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

		await expect(customerDashboardPage.downloadDashboardTab).toBeVisible();

		await expect(customerDashboardPage.downloadDashboardTab).toHaveClass(
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

		await expect(customerDashboardPage.detailDashboardTab).toBeVisible();

		await expect(customerDashboardPage.detailDashboardTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardPage.downloadDashboardTab).toBeVisible();
		await customerDashboardPage.downloadDashboardTab.click();

		await expect(customerDashboardPage.downloadDashboardTab).toHaveClass(
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
