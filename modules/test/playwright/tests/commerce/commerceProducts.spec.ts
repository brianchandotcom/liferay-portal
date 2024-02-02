/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-5780 Modal title and product name appear properly in product menu', async ({
	apiHelpers,
	commerceAdminProductDetailsProductRelationsPage,
	commerceAdminProductPage,
}) => {
	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Product Catalog',
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: '"Product' + getRandomInt(),
		},
	});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
	});

	await commerceAdminProductPage.gotoProduct(product1.name.en_US);

	await commerceAdminProductDetailsProductRelationsPage.addSpareProductRelation();

	await expect(
		await commerceAdminProductDetailsProductRelationsPage.addProductRelationHeading(
			product1.name.en_US
		)
	).toBeVisible();

	await commerceAdminProductPage.modalCancelButton.click();

	await commerceAdminProductPage.gotoProduct(product2.name.en_US);

	await commerceAdminProductDetailsProductRelationsPage.addSpareProductRelation();

	await (
		await commerceAdminProductPage.validProductCheckbox(product1.name.en_US)
	).check();

	await commerceAdminProductPage.modalAddButton.click();

	await expect(
		await commerceAdminProductPage.specificProductMenuLink(
			product1.name.en_US
		)
	).toBeVisible();
});
