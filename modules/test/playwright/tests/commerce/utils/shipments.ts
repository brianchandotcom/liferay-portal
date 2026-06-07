/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {CommerceAdminOrderDetailsPage} from '../../../pages/commerce/commerce-order-web/commerceAdminOrderDetailsPage';
import {CommerceAdminOrdersPage} from '../../../pages/commerce/commerce-order-web/commerceAdminOrdersPage';
import {CommerceAdminShipmentsPage} from '../../../pages/commerce/commerce-shipment-web/commerceAdminShipmentsPage';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export async function acceptOrderAndCreateShipment(
	{
		commerceAdminOrderDetailsPage,
		commerceAdminOrdersPage,
		page,
	}: {
		commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
		commerceAdminOrdersPage: CommerceAdminOrdersPage;
		page: Page;
	},
	{orderId}: {orderId: number}
) {
	await commerceAdminOrdersPage.goto();

	await commerceAdminOrdersPage.tableRowOrderIdLink(orderId).click();

	await expect(
		commerceAdminOrderDetailsPage.headerDetailsTitle
	).toBeVisible();

	await commerceAdminOrderDetailsPage.acceptOrderButton.click();

	await waitForAlert(page);

	await commerceAdminOrderDetailsPage.createShipmentButton.click();

	await expect(
		commerceAdminOrderDetailsPage.orderStatusProcessing
	).toBeVisible();
}

export async function acceptOrderAndCreateShipmentWithItem(
	pages: {
		commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
		commerceAdminOrdersPage: CommerceAdminOrdersPage;
		commerceAdminShipmentsPage: CommerceAdminShipmentsPage;
		page: Page;
	},
	{
		orderId,
		quantity = '1',
		sku,
		warehouseName,
	}: {
		orderId: number;
		quantity?: string;
		sku: {sku: string};
		warehouseName: string;
	}
) {
	await acceptOrderAndCreateShipment(pages, {orderId});

	await addShipmentItems(
		pages.commerceAdminShipmentsPage,
		[sku],
		warehouseName,
		quantity
	);
}

export async function addShipmentItems(
	commerceAdminShipmentsPage: CommerceAdminShipmentsPage,
	skus: Array<{sku: string}>,
	warehouseName: string,
	quantity = '1'
) {
	await commerceAdminShipmentsPage.addProductsToShipment.click();

	for (const sku of skus) {
		await (
			await commerceAdminShipmentsPage.shipmentItemsTableRowAction(
				1,
				sku.sku
			)
		).check();
	}

	await commerceAdminShipmentsPage.shipmentsItemSubmitButton.click();

	for (const sku of skus) {
		await expect(async () => {
			await commerceAdminShipmentsPage.page.reload();

			await commerceAdminShipmentsPage.productsSkuLink(sku.sku).click();

			const quantityField = commerceAdminShipmentsPage.editProductFrame
				.locator('tr', {hasText: warehouseName})
				.getByRole('spinbutton');

			await quantityField.waitFor({state: 'visible', timeout: 10000});

			await quantityField.fill(quantity);

			await expect(quantityField).toHaveValue(quantity, {timeout: 2000});
		}).toPass({timeout: 60000});

		await commerceAdminShipmentsPage.editProductSaveButton.click();

		await waitForAlert(
			commerceAdminShipmentsPage.editProductFrame,
			undefined,
			{
				autoClose: false,
			}
		);

		await commerceAdminShipmentsPage.page.reload();
	}
}

export async function finishProcessingAndShip(
	commerceAdminShipmentsPage: CommerceAdminShipmentsPage,
	page: Page
) {
	await commerceAdminShipmentsPage
		.shipmentStatusLink('Finish Processing')
		.click();

	await waitForAlert(page);

	await commerceAdminShipmentsPage.shipmentStatusLink('Ship').click();

	await waitForAlert(page);
}

export async function setUpShipmentOrder(
	apiHelpers: DataApiHelpers,
	{
		catalog,
		channel,
		skuCount = 1,
	}: {
		catalog: {id: number; name: string};
		channel: {id: number; name: string};
		skuCount?: number;
	}
) {
	const randomSuffix = getRandomString().slice(0, 8);

	const warehouseName = `Test Warehouse ${randomSuffix}`;

	const skus: Array<{id: number; sku: string}> = [];

	for (let i = 0; i < skuCount; i++) {
		const productStub =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: `Shipment Product ${randomSuffix}-${i}`},
			});

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.getProduct(
				productStub.productId
			);

		skus.push(product.skus[0]);
	}

	const warehouse =
		await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehouses(
			{
				active: true,
				latitude: 10,
				longitude: 0,
				name: {en_US: warehouseName},
				warehouseItems: skus.map((sku) => ({
					quantity: 100,
					sku: sku.sku,
				})),
			}
		);

	await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehousesChannels(
		warehouse.id,
		channel.id
	);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: `Commerce Account ${randomSuffix}`,
		type: 'business',
	});

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{
			city: 'Test City',
			countryISOCode: 'US',
			defaultBilling: true,
			defaultShipping: true,
			name: 'Test Address',
			regionISOCode: 'CA',
			street1: 'Test Street',
			zip: '12345',
		}
	);

	const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
		accountId: account.id,
		billingAddressId: address.id,
		channelId: channel.id,
		orderItems: skus.map((sku) => ({quantity: 1, skuId: sku.id})),
		orderStatus: '1',
		paymentMethod: 'money-order',
		paymentStatus: '2',
		shippingAddressId: address.id,
		shippingMethod: 'by-weight',
		shippingOption: 'standard-option',
	} as any);

	return {account, order, skus, warehouse, warehouseName};
}
