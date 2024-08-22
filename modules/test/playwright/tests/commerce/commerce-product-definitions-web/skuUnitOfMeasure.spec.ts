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
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-33466 User can update pricing quantity of UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminProductDetailsPage,
	commerceAdminProductDetailsSkusPage,
	commerceAdminProductPage,
}) => {
	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Catalog',
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'Product',
		},
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(sku.id, {
		incrementalOrderQuantity: 1.22,
		name: {en_US: 'UOM'},
		precision: 2,
		pricingQuantity: 1.0,
		priority: 0,
	});

	await applicationsMenuPage.goToProducts();

	await commerceAdminProductPage.managementToolbarSearchInput.fill('Product');
	await commerceAdminProductPage.managementToolbarSearchInput.press('Enter');

	await commerceAdminProductPage.productsTableRowLink('Product').click();

	await commerceAdminProductDetailsPage.goToProductSkus();

	await commerceAdminProductDetailsSkusPage
		.skusTableRowLink(`${sku.sku}`)
		.click();

	await commerceAdminProductDetailsSkusPage.goToSkuUOM();

	await commerceAdminProductDetailsSkusPage.uomTableRowLink('UOM').click();

	await expect(
		commerceAdminProductDetailsSkusPage.pricinQuantity
	).toHaveValue('1');

	await commerceAdminProductDetailsSkusPage.pricinQuantity.fill('2');

	await commerceAdminProductDetailsSkusPage.skuUOMFrameSaveButton.click();

	await commerceAdminProductDetailsSkusPage.skuUOMFrameCancelButton.click();

	await commerceAdminProductDetailsSkusPage.uomTableRowLink('UOM').click();

	await expect(
		commerceAdminProductDetailsSkusPage.pricinQuantity
	).toHaveValue('2');
});
