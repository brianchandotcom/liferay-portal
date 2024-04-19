/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest(),
	notificationPagesTest
);

test('LPD-13627 Edit pending order item with UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	pendingOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Edit pending order',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Edit pending order Channel',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Edit pending order Catalog',
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

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
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

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

	await page.goto(`/web/${site.name}`);

	await pendingOrdersPage.addPendingOrdersWidget();

	await pendingOrdersPage.viewButton.click();

	await pendingOrdersPage.orderItemActionsButton.click();

	await expect(pendingOrdersPage.orderItemActionsButtonEdit).toBeVisible();
});

test('LPD-13627 Edit pending order item without UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	pendingOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Edit pending order',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Edit pending order Channel',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Edit pending order Catalog',
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = product1Skus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

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

	await page.goto(`/web/${site.name}`);

	await pendingOrdersPage.addPendingOrdersWidget();

	await pendingOrdersPage.viewButton.click();

	await pendingOrdersPage.orderItemActionsButton.click();

	await expect(pendingOrdersPage.orderItemActionsButtonEdit).toHaveCount(0);
});

test('LPD-4174 Sales agent can receive email notifications for new orders placed to their accounts', async ({
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
	commerceMiniCartPage,
	page,
	queuePage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Sales agent can receive email notifications Site',
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Sales agent can receive email notifications account',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

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
			name: 'Sales agent can receive email notifications Template',
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
				en_US: 'Sales agent can receive email notifications',
			},
			type: 'email',
		});

	const objectAction =
		await apiHelpers.objectAdmin.postObjectActionByExternalReferenceCode(
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
		'Sales agent can receive email notifications Site'
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

	try {
		await expect(queuePage.pageTitle).toBeVisible();
		await expect(
			page.getByText('Sales agent can receive email notifications')
		).toHaveCount(1);
	}
	finally {
		const orders =
			await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();

		apiHelpers.data.push({id: orders.items[0].id, type: 'order'});

		const notificationQueueEntry =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				'Sales agent can receive email notifications'
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
				'Sales agent can receive email notifications'
			);

		apiHelpers.data.push({id: channels.items[0].id, type: 'channel'});

		const catalogs =
			await apiHelpers.headlessCommerceAdminCatalog.getCatalogsPage(
				'Sales agent can receive email notifications'
			);

		apiHelpers.data.push({id: catalogs.items[0].id, type: 'catalog'});

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProductsPage(
				50,
				''
			);

		for (let i = 0; i < products.totalCount; i++) {
			if (products.items[i].catalogId === catalogs.items[0].id) {
				apiHelpers.data.push({
					id: products.items[i].productId,
					type: 'product',
				});
			}
		}

		const options =
			await apiHelpers.headlessCommerceAdminCatalog.getOptions();

		for (let i = 0; i < options.totalCount; i++) {
			apiHelpers.data.push({
				id: options.items[i].id,
				type: 'option',
			});
		}

		const optionCategories =
			await apiHelpers.headlessCommerceAdminCatalog.getOptionCategories();

		for (let i = 0; i < optionCategories.totalCount; i++) {
			apiHelpers.data.push({
				id: optionCategories.items[i].id,
				type: 'optionCategory',
			});
		}

		const specifications =
			await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

		for (let i = 0; i < specifications.totalCount; i++) {
			apiHelpers.data.push({
				id: specifications.items[i].id,
				type: 'specification',
			});
		}

		const warehouses =
			await apiHelpers.headlessCommerceAdminInventoryApiHelper.getWarehousesPage();

		for (let i = 0; i < warehouses.totalCount; i++) {
			apiHelpers.data.push({
				id: warehouses.items[i].id,
				type: 'warehouse',
			});
		}

		await apiHelpers.headlessAdminUser.deleteRoleUserAccountAssociation(
			roles.items[0].id,
			user.id
		);
	}
});
