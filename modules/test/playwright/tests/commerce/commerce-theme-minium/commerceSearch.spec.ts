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
import getRandomString from '../../../utils/getRandomString';
import {miniumSetUp} from '../utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-29997 Search for products by typing different specification values in global search', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceCatalogPage,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search('Plastic');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Timing Chain Tensioner'
		)
	).toBeVisible();

	await commerceCatalogPage.clearSearchButton.click();
	await commerceCatalogPage.search('Plastic, Ceramic');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Timing Chain Tensioner'
		)
	).toBeVisible();

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Premium Brake Pads'
		)
	).toBeVisible();
});

test('LPD-30191 Search for products by typing different SKUs in global search', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceCatalogPage,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search('MIN93015');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).toBeVisible();

	await commerceCatalogPage.clearSearchButton.click();
	await commerceCatalogPage.search('MIN93015 MIN55861');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).toBeVisible();

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'U-Joint Product designed'
		)
	).toBeVisible();
});

test('LPD-30370 Search for all orders by typing user email in global search', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceCatalogPage,
}) => {
	const {channel, site} = await miniumSetUp(apiHelpers);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const openOrder = await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
		},
		channel.id
	);

	const product = (
		await apiHelpers.headlessCommerceAdminCatalog.getProducts(
			new URLSearchParams({
				filter: `name eq 'ABS Sensor'`,
			})
		)
	).items[0];

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{phoneNumber: '12345', regionISOCode: 'AL'}
	);

	const placedOrder = await apiHelpers.headlessCommerceAdminOrder.postOrder({
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
		orderStatus: '0',
		paymentMethod: 'paypal',
		paymentStatus: '0',
		shippingAddressId: address.id,
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search('test@liferay.com');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			String(openOrder.id)
		)
	).toBeVisible();

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			String(placedOrder.id)
		)
	).toBeVisible();
});
