/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-15231 Escape account name on admin order details page', async ({
	apiHelpers,
	commerceAdminOrderDetailsPage,
	commerceAdminOrdersPage,
	page,
}) => {
	await page.goto('/');

	const site =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath('guest');

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: '<img src="x" onError="alert(document.location)">',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
		},
		channel.id
	);

	await commerceAdminOrdersPage.goto();

	await (
		await commerceAdminOrdersPage.tableRowLink({
			colIndex: 1,
			rowValue: cart.id,
		})
	).click();

	await expect(
		commerceAdminOrderDetailsPage.headerDetailsTitle
	).toBeVisible();

	await expect(
		commerceAdminOrderDetailsPage.commerceOrderAccountEntryName
	).toHaveText(account.name);
});

test('LPD-26244 Split order items are shown on admin order details page when show separate order items toggle is enabled', async ({
	apiHelpers,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	commerceAdminOrderDetailsPage,
	commerceAdminOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	await commerceAdminChannelsPage.goto();

	await (
		await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
	).click();

	await (
		await commerceAdminChannelDetailsPage.showSeparateOrderItemsToggle
	).check();

	await expect(
		await commerceAdminChannelDetailsPage.showSeparateOrderItemsToggle
	).toBeChecked();

	await (await commerceAdminChannelDetailsPage.saveButton).click();

	await expect(
		await commerceAdminChannelDetailsPage.showSeparateOrderItemsToggle
	).toBeChecked();

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: getRandomString(),
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: getRandomString()},
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

	const cartItem1 = {
		options: '[]',
		quantity: 1,
		replacedSkuId: 0,
		skuId: sku.id,
	};

	const cartItem2 = {
		options: '[]',
		quantity: 10,
		replacedSkuId: 0,
		skuId: sku.id,
	};

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
			cartItems: [cartItem1, cartItem2],
		},
		channel.id
	);

	await commerceAdminOrdersPage.goto();

	await (
		await commerceAdminOrdersPage.tableRowLink({
			colIndex: 1,
			rowValue: cart.id,
		})
	).click();

	await expect(
		commerceAdminOrderDetailsPage.headerDetailsTitle
	).toBeVisible();

	await expect(
		(
			await commerceAdminOrderDetailsPage.tableRow(
				2,
				product.name['en_US'],
				true
			)
		).row
	).toBeVisible();

	await expect(
		(
			await commerceAdminOrderDetailsPage.tableRow(
				8,
				cartItem1.quantity,
				true
			)
		).row
	).toBeVisible();

	await expect(
		(
			await commerceAdminOrderDetailsPage.tableRow(
				8,
				cartItem2.quantity,
				true
			)
		).row
	).toBeVisible();
});
