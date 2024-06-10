/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {backendPageTest} from '../../../fixtures/backendPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import {getTempDir} from '../../../utils/temp';
import {marketplacePagesTest} from './fixtures/marketplacePages';
import {marketplaceSiteFixture} from './fixtures/marketplaceSite';
import {PRODUCT_WORKFLOW_STATUS_CODE} from './utils/constants';

export const test = mergeTests(
	backendPageTest,
	dataApiHelpersTest,
	marketplacePagesTest,
	marketplaceSiteFixture
);

const customerAccountName = `Customer${getRandomInt()}`;
const productName = `Product${getRandomInt()}`;

test.describe('Customer Dashboard', () => {
	let _customer;
	let _product;
	let _order;

	test.beforeEach(async ({apiHelpers}) => {
		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		const customer = await apiHelpers.headlessAdminUser.postAccount({
			name: customerAccountName,
			type: 'person',
		});

		_customer = customer;

		const rolesResponse =
			await apiHelpers.headlessAdminUser.getAccountRoles(customer.id);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			customer.id,
			['test@liferay.com']
		);

		const customerAccountRole = rolesResponse?.items?.filter((role) => {
			return role.name === 'Account Buyer';
		});

		await apiHelpers.headlessAdminUser.assingUserToAccountRole(
			customer.id,
			customerAccountRole[0].id,
			user.id
		);

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.getCatalogByErc(
				'MKT-CATALOG-1'
			);

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				"name eq 'Marketplace Channel'"
			);

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {
					en_US: productName,
				},
				productChannels: [
					{
						channelId: channel.items[0].id,
						currencyCode: 'USD',
						externalReferenceCode:
							'fa803fad-6d86-32dd-aa04-7e59dff9d0cb',
						id: channel.items[0].id,
						name: 'Marketplace Channel',
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
							attachment:
								'YXV0aG9yOiB3ZWxsaW5ndG9uIGRvcyBzYW50b3MgYmFyYm9zYQ0KZW1haWw6IHdlbGl0b25wZUBnbWFpbC5jb20=',
							version: 'Liferay Portal 7.4 GA110',
						},
					],
				},
			});

		_product = product;

		const odrder = await apiHelpers.headlessCommerceAdminOrder.postOrder({
			accountId: customer.id,
			channelId: channel.items[0].id,
			orderItems: [
				{
					decimalQuantity: 10,
					quantity: 2,

					skuId: product.skus[0].id as unknown as string,
					unitPrice: 1,
				},
			],
			orderTypeExternalReferenceCode: 'DXPAPP',
		});

		_order = odrder;

		await apiHelpers.headlessCommerceAdminOrder.patchOrder({
			id: odrder.id,
			order: {
				paymentStatus: '0',
			},
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder({
			id: odrder.id,
			order: {
				orderStatus: '10',
			},
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder({
			id: odrder.id,
			order: {
				orderStatus: '0',
			},
		});
	});

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessCommerceAdminOrder.deleteOrder(_order.id);

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
			_product.id + 1
		);

		await apiHelpers.headlessAdminUser.deleteAccount(_customer.id);
	});

	test('LPD-21740 The customer can download by using the kebab', async ({
		customerDashboardpage,
		marketplace,
	}) => {
		await customerDashboardpage.goto(
			`web${marketplace.friendlyUrlPath}/customer-dashboard`
		);

		await customerDashboardpage.selectAccount(customerAccountName);

		await expect(
			customerDashboardpage.purchasedApp(productName)
		).toBeVisible();

		await expect(
			customerDashboardpage.tableKebabButton(productName)
		).toBeVisible();

		customerDashboardpage
			.tableKebabButton(productName)
			.waitFor({state: 'visible'});

		await clickAndExpectToBeVisible({
			target: customerDashboardpage.page.getByText('Download App'),
			trigger: customerDashboardpage.tableKebabButton(productName),
		});

		await customerDashboardpage.dropDownDownloadbutton.click();

		await expect(customerDashboardpage.downloadDashboardTab).toBeVisible();

		await expect(customerDashboardpage.downloadDashboardTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardpage.downloadButton).toBeVisible();

		const downloadPromise =
			customerDashboardpage.page.waitForEvent('download');

		customerDashboardpage.downloadButton.click();

		const download = await downloadPromise;

		const filePath = getTempDir() + download.suggestedFilename();

		await download.saveAs(filePath);

		expect(filePath).toBeTruthy();
	});

	test('LPD-21740 The customer would go to the download page and download the application through the table', async ({
		customerDashboardpage,
		marketplace,
	}) => {
		await customerDashboardpage.goto(
			`web${marketplace.friendlyUrlPath}/customer-dashboard`
		);

		await customerDashboardpage.selectAccount(customerAccountName);

		await expect(
			customerDashboardpage.purchasedApp(productName)
		).toBeVisible();
		await customerDashboardpage.purchasedApp(productName).click();

		await expect(customerDashboardpage.detailDashboardTab).toBeVisible();

		await expect(customerDashboardpage.detailDashboardTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardpage.downloadDashboardTab).toBeVisible();
		await customerDashboardpage.downloadDashboardTab.click();

		await expect(customerDashboardpage.downloadDashboardTab).toHaveClass(
			'nav-link active'
		);

		await expect(customerDashboardpage.downloadButton).toBeVisible();

		const downloadPromise =
			customerDashboardpage.page.waitForEvent('download');

		customerDashboardpage.downloadButton.click();

		const download = await downloadPromise;

		const filePath = getTempDir() + download.suggestedFilename();

		await download.saveAs(filePath);

		expect(filePath).toBeTruthy();
	});
});
