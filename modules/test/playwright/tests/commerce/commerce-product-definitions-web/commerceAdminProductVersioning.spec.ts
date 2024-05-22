/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-3272 Enable product versioning and verify a new product version is created after updating the sku', async ({
	apiHelpers,
	commerceCatalogSystemSettingsPage,
}) => {
	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
	});

	const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const product1Sku = product1Skus[0];

	await commerceCatalogSystemSettingsPage.toggleProductVersioning();

	await apiHelpers.headlessCommerceAdminCatalog.patchSku(product1Sku.id, {
		cost: product1Sku.cost,
		price: product1Sku.price,
		published: true,
		purchasable: product1Sku.purchaseable,
		sku: 'updatedSku',
	});

	const product2 =
		await apiHelpers.headlessCommerceAdminCatalog.getProductByVersion(
			product1.productId,
			2
		);

	expect(product2.skuFormatted).not.toEqual(product1Sku.sku);
	expect(product2.skuFormatted).toEqual('updatedSku');

	await apiHelpers.headlessCommerceAdminCatalog.deleteProductByVersion(
		product2.productId,
		2
	);

	await apiHelpers.headlessCommerceAdminCatalog.deleteProductByVersion(
		product1.productId,
		1
	);

	await commerceCatalogSystemSettingsPage.toggleProductVersioning();
});
