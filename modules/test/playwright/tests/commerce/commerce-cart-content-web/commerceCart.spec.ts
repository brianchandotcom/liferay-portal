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

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	loginTest()
);

test('LPD-27036 Cart shows decimal quantities', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceCartPage,
	commerceLayoutsPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Cart Site',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Cart Channel',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Cart Catalog',
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
		productConfiguration: {
			minOrderQuantity: 1.22,
			multipleOrderQuantity: 1.22,
		},
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const uom =
		await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
			sku.id,
			{
				incrementalOrderQuantity: 1.22,
				name: {en_US: 'UOM'},
				precision: 2,
				priority: 0,
			}
		);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Cart Account',
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
					quantity: 1.22,
					skuId: sku.id,
					skuUnitOfMeasure: {key: uom.key},
				},
			],
			currencyCode: 'USD',
		},
		channel.id
	);

	await applicationsMenuPage.goToSite('Cart Site');

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Cart Page');

	await applicationsMenuPage.goToSite('Cart Site');

	await commerceCartPage.addCartWidget();
	expect(
		await commerceCartPage.commerceOrderItemsTableRowQuantityInput(
			product.name['en_US']
		)
	).toHaveValue('1.22');
});
