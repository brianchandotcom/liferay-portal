/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../workspaces/liferay-workspace-marketplace/utils/constants';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-32515 Returns widget displays amount fields with correct currency pattern', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const featureFlag = await apiHelpers.featureFlag
		.isFeatureFlagEnabled('LPD-10562')
		.then((result) => {
			return result.featureFlag;
		});

	test.skip(!featureFlag.enabled, 'Feature flag LPD-10592 is disabled');

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		productType: 'virtual',
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{phoneNumber: '1234567890', regionISOCode: 'AL'}
	);

	const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
		accountId: account.id,
		billingAddressId: address.id,
		channelId: channel.id,
		orderItems: [
			{
				decimalQuantity: 10,
				quantity: 2,
				skuId: sku.id,
			},
		],
		shippingAddressId: address.id,
	});

	const payment =
		await apiHelpers.headlessCommerceAdminPaymentApiHelper.postPayment({
			amount: 10,
			relatedItemId: order.id,
		});

	await apiHelpers.headlessCommerceAdminPaymentApiHelper.patchPayment(
		{
			paymentStatus: 0,
			relatedItemId: payment.relatedItemId,
		},
		payment.id
	);

	await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
		orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
	});

	await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
		orderStatus: ORDER_WORKFLOW_STATUS_CODE.COMPLETED,
	});

	const orderItem = order.orderItems[0];

	const commerceReturn =
		await apiHelpers.headlessCommerceReturn.postCommerceReturn({
			channelId: channel.id,
			commerceReturnToCommerceReturnItems: [
				{
					amount: 10,
					authorized: 1,
					quantity: 1,
					r_accountToCommerceReturnItems_accountEntryId: account.id,
					r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId:
						orderItem.id,
					r_commerceOrderToCommerceReturns_commerceOrderId: order.id,
					received: 1,
					returnItemStatus: {
						key: 'toBeProccessed',
					},
					returnReason: {
						key: 'changeOfMind',
					},
					returnResolutionMethod: {
						key: 'refund',
					},
				},
			],
			r_accountToCommerceReturns_accountEntryId: account.id,
			r_commerceOrderToCommerceReturns_commerceOrderId: order.id,
			returnStatus: {
				key: 'processing',
			},
		});

	await apiHelpers.headlessCommerceAdminPaymentApiHelper.postPayment({
		amount: 10,
		relatedItemId: payment.id,
		relatedItemName:
			'com.liferay.commerce.payment.model.CommercePaymentEntry',
		type: 1,
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await expect(
		(await returnsPage.tableRow(1, '$ 0.00', true)).row
	).toBeVisible();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(
		(await returnDetailsPage.tableRow(0, sku.sku, true)).row
	).toBeVisible();

	for await (const currencyField of await returnDetailsPage.page
		.getByText('0.00')
		.all()) {
		await expect(currencyField.getByText('$')).toBeVisible();
	}
});

test('LPD-32522 Returns widget displays status field on return items table when return is submitted', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const featureFlag = await apiHelpers.featureFlag
		.isFeatureFlagEnabled('LPD-10562')
		.then((result) => {
			return result.featureFlag;
		});

	test.skip(!featureFlag.enabled, 'Feature flag LPD-10592 is disabled');

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		productType: 'virtual',
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{phoneNumber: '1234567890', regionISOCode: 'AL'}
	);

	const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
		accountId: account.id,
		billingAddressId: address.id,
		channelId: channel.id,
		orderItems: [
			{
				decimalQuantity: 10,
				quantity: 2,
				skuId: sku.id,
			},
		],
		shippingAddressId: address.id,
	});

	const payment =
		await apiHelpers.headlessCommerceAdminPaymentApiHelper.postPayment({
			amount: 10,
			relatedItemId: order.id,
		});

	await apiHelpers.headlessCommerceAdminPaymentApiHelper.patchPayment(
		{
			paymentStatus: 0,
			relatedItemId: payment.relatedItemId,
		},
		payment.id
	);

	await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
		orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
	});

	await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
		orderStatus: ORDER_WORKFLOW_STATUS_CODE.COMPLETED,
	});

	const orderItem = order.orderItems[0];

	const commerceReturn =
		await apiHelpers.headlessCommerceReturn.postCommerceReturn({
			channelId: channel.id,
			commerceReturnToCommerceReturnItems: [
				{
					amount: 0,
					authorized: 0,
					quantity: 1,
					r_accountToCommerceReturnItems_accountEntryId: account.id,
					r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId:
						orderItem.id,
					r_commerceOrderToCommerceReturns_commerceOrderId: order.id,
					received: 0,
					returnReason: {
						key: 'changeOfMind',
					},
					returnResolutionMethod: {
						key: 'refund',
					},
				},
			],
			r_accountToCommerceReturns_accountEntryId: account.id,
			r_commerceOrderToCommerceReturns_commerceOrderId: order.id,
			returnStatus: {
				key: 'draft',
			},
		});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(await returnDetailsPage.table).toBeVisible();

	await expect(
		await returnDetailsPage.table.getByText('Status')
	).toBeHidden();

	await returnDetailsPage.submitReturnRequestButton.click();

	await expect(
		await returnDetailsPage.table.getByText('Status')
	).toBeVisible();
});
