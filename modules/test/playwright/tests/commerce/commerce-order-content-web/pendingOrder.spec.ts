/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	loginTest(),
	notificationPagesTest
);

const data = [];

test.afterEach(async ({apiHelpers}) => {
	for await (const item of data.reverse()) {
		switch (item.type) {
			case 'account':
				await apiHelpers.headlessAdminUser.deleteAccount(item.id);

				break;
			case 'catalog':
				await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
					item.id
				);

				break;
			case 'channel':
				await apiHelpers.headlessCommerceAdminChannel.deleteChannel(
					item.id
				);

				break;
			case 'product':
				await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
					item.id
				);

				break;
			case 'site':
				await apiHelpers.headlessSite.deleteSite(item.id);

				break;
			case 'warehouse':
				await apiHelpers.headlessCommerceAdminInventoryApiHelper.deleteWarehouse(
					item.id
				);

				break;
			default:
				break;
		}
	}
});

test('Edit pending order item with UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	pendingOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Edit pending order',
	});

	data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Edit pending order Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Edit pending order Catalog',
	});

	data.push({id: catalog.id, type: 'catalog'});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	data.push({id: product1.productId, type: 'product'});

	const sku1 = product1.skus[0];

	const uom1 =
		await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
			sku1.id,
			{
				incrementalOrderQuantity: 3,
				name: {en_US: 'Box'},
				primary: true,
				priority: 1,
				rate: 1,
			}
		);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Edit pending order account',
		type: 'person',
	});

	data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
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
					skuUnitOfMeasure: {key: uom1.key},
				},
			],
		},
		channel.id
	);

	await applicationsMenuPage.goToSite('Edit pending order');

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Pending Orders Page');
	await commerceLayoutsPage.goToPages(false);

	await commerceLayoutsPage.siteHomePageLink.click();

	await pendingOrdersPage.addPendingOrdersWidget();

	await pendingOrdersPage.viewButton.click();

	await pendingOrdersPage.orderItemActionsButton.click();

	await expect(pendingOrdersPage.orderItemActionsButtonEdit).toBeVisible();
});

test('Edit pending order item without UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	pendingOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Edit pending order',
	});

	data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Edit pending order Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Edit pending order Catalog',
	});

	data.push({id: catalog.id, type: 'catalog'});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	data.push({id: product1.productId, type: 'product'});

	const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = product1Skus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Edit pending order account',
		type: 'person',
	});

	data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
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

	await applicationsMenuPage.goToSite('Edit pending order');

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Pending Orders Page');
	await commerceLayoutsPage.goToPages(false);

	await commerceLayoutsPage.siteHomePageLink.click();

	await pendingOrdersPage.addPendingOrdersWidget();

	await pendingOrdersPage.viewButton.click();

	await pendingOrdersPage.orderItemActionsButton.click();

	await expect(pendingOrdersPage.orderItemActionsButtonEdit).toHaveCount(0);
});

test('LPD-4174 Sales agent can recieve email notifications for new orders placed to their accounts', async ({
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
	commerceMiniCartPage,
	page,
	queuePage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Sales agent can recieve email notifications Site',
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	data.push({id: site.id, type: 'site'});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Sales agent can recieve email notifications account',
		type: 'business',
	});

	data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'test@liferay.com'
		);

	const roles = await apiHelpers.headlessAdminUser.getRoles('Sales Agent');

	await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
		roles.items[0].id,
		user.id
	);

	const notificationTemplate =
		await apiHelpers.notification.postNotificationTemplate({
			editorType: 'richText',
			name: 'Sales agent can recieve email notifications Template',
			recipientType: 'email',
			recipients: [
				{
					from: 'do-not-reply@liferay.com',
					fromName: {
						en_US: 'do-not-replay@liferay.com',
					},
					to: {
						en_US: '[%SALES_AGENT%]',
					},
				},
			],
			subject: {
				en_US: 'Sales agent can recieve email notifications',
			},
			type: 'email',
		});

	const objectAction =
		await apiHelpers.objectAdmin.postObjectDefinitionByExternalRefernceCodeObjectAction(
			'L_COMMERCE_ORDER',
			{
				active: true,
				label: {
					en_US: 'commerceOrderStatusOnChange',
				},
				name: 'commerceOrderStatusOnChange',
				objectActionExecutorKey: 'notification',
				objectActionTriggerKey: 'liferay/commerce_order_status',
				parameters: {
					notificationTemplateId: notificationTemplate.id,
				},
			}
		);

	await applicationsMenuPage.goToSite(
		'Sales agent can recieve email notifications Site'
	);

	await commerceMiniCartPage.miniCartButton.click();
	await commerceMiniCartPage.searchProductsInput.fill('MIN55861');
	await commerceMiniCartPage.quickAddToCartSku('MIN55861').click();
	await commerceMiniCartPage.quickAddToCartButton.click();
	await commerceMiniCartPage.submitButton.click();

	await checkoutPage.nameInput.fill('name');
	await checkoutPage.addressInput.fill('address');
	await checkoutPage.zipInput.fill('1234');
	await checkoutPage.phoneNumberInput.fill('1234');
	await checkoutPage.cityInput.fill('city');
	await checkoutPage.countryInput.selectOption({label: 'Italy'});
	await checkoutPage.continueButton.click();
	await checkoutPage.continueButton.click();
	await checkoutPage.continueButton.click();

	await expect(checkoutPage.orderSuccessMessage).toBeVisible();

	await applicationsMenuPage.goToQueue();

	await expect(queuePage.pageTitle).toBeVisible();
	await expect(
		page.getByText('Sales agent can recieve email notifications')
	).toHaveCount(1);

	const notificationQueueEntry =
		await apiHelpers.notification.getNotificationQueueEntriesPage(
			'Sales agent can recieve email notifications'
		);

	await apiHelpers.notification.deleteNotificationQueueEntry(
		notificationQueueEntry.items[0].id
	);

	await apiHelpers.objectAdmin.deleteObjectAction(objectAction.id);
	await apiHelpers.notification.deleteNotificationTemplate(
		notificationTemplate.id
	);

	const channels =
		await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
			'Sales agent can recieve email notifications'
		);

	data.push({id: channels.items[0].id, type: 'channel'});

	const catalogs =
		await apiHelpers.headlessCommerceAdminCatalog.getCatalogsPage(
			'Sales agent can recieve email notifications'
		);

	data.push({id: catalogs.items[0].id, type: 'catalog'});

	const products =
		await apiHelpers.headlessCommerceAdminCatalog.getProductsPage(50, '');

	for (let i = 0; i < products.totalCount; i++) {
		if (products.items[i].catalogId === catalogs.items[0].id) {
			data.push({id: products.items[i].productId, type: 'product'});
		}
	}

	const warehouses =
		await apiHelpers.headlessCommerceAdminInventoryApiHelper.getWarehousesPage();

	for (let i = 0; i < warehouses.totalCount; i++) {
		data.push({id: warehouses.items[i].id, type: 'warehouse'});
	}

	await apiHelpers.headlessAdminUser.deleteRoleUserAccountAssociation(
		roles.items[0].id,
		user.id
	);
});
