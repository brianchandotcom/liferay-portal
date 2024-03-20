/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, commercePagesTest, loginTest());

test('COMMERCE-11835 Account Supplier role user can upload diagram file/image', async ({
	apiHelpers,
	commerceAdminProductDetailsDiagramPage,
	commerceAdminProductDetailsPage,
	commerceAdminProductPage,
	page,
}) => {
	await page.goto('/');

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Supplier account',
		type: 'supplier',
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		accountId: account.id,
	});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'Product',
		},
		productType: 'diagram',
	});

	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountSupplierRole = rolesResponse?.items?.filter((role) => {
		return role.name === 'Account Supplier';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		accountSupplierRole[0].id,
		'test@liferay.com'
	);

	try {
		await commerceAdminProductPage.gotoProduct(product.name['en_US']);

		await commerceAdminProductDetailsPage.goToProductDiagram();

		await commerceAdminProductDetailsDiagramPage.goToDragAndDropImages();

		await expect(
			commerceAdminProductDetailsDiagramPage.dragAndDropImages
		).toBeVisible({
			timeout: 2000,
		});
	} finally {
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);
		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
			product.productId
		);
		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(catalog.id);
	}
});
