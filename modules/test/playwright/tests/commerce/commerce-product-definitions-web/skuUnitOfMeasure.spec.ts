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
import performLogin, {performLogout} from '../../../utils/performLogin';
import {miniumSetUp} from '../utils/commerce';

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

test('LPD-36797 Quantity selector starting quantity in catalog page and minicart is correct when UOM is set with decimal base unit quantity and decimal multiple order quantity', async ({
	apiHelpers,
	commerceMiniCartPage,
	commerceThemeMiniumCatalogPage,
	page,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);
	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountRoleBuyer = rolesResponse?.items?.filter((role) => {
		return role.name === 'Buyer';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		accountRoleBuyer[0].id,
		user.emailAddress
	);
	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['demo.unprivileged@liferay.com']
	);

	const siteRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteRole.id,
		site.id,
		user.id
	);

	const product1 = (
		await apiHelpers.headlessCommerceAdminCatalog.getProducts(
			new URLSearchParams({
				filter: `name eq 'U-Joint'`,
			})
		)
	).items[0];

	const productName1 = product1.name['en_US'];

	await apiHelpers.headlessCommerceAdminCatalog.patchProduct(
		product1.productId,
		{
			name: {en_US: productName1},
			productConfiguration: {
				multipleOrderQuantity: 0.1,
			},
		}
	);

	const productSkus1 = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = productSkus1[0];

	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
		sku1.id,
		{
			basePrice: productSkus1.price,
			incrementalOrderQuantity: 0.3,
			name: {en_US: 'UOM'},
			precision: 1,
			priority: 0,
		}
	);

	const product2 = (
		await apiHelpers.headlessCommerceAdminCatalog.getProducts(
			new URLSearchParams({
				filter: `name eq 'Transmission Cooler Line Assembly'`,
			})
		)
	).items[0];

	const productName2 = product2.name['en_US'];

	const productSkus2 = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product2.productId)
		.then((product) => {
			return product.skus;
		});

	const sku2 = productSkus2[0];

	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
		sku2.id,
		{
			basePrice: productSkus2.price,
			incrementalOrderQuantity: 0.3,
			name: {en_US: 'UOM'},
			precision: 1,
			priority: 0,
		}
	);

	await performLogout(page);
	await performLogin(page, 'demo.unprivileged');

	await page.goto(`/web/${site.name}`);

	await expect(
		commerceThemeMiniumCatalogPage.quantitySelector(
			commerceThemeMiniumCatalogPage.productCard(productName1)
		)
	).toHaveValue('1.2');

	await commerceThemeMiniumCatalogPage
		.quantitySelector(
			commerceThemeMiniumCatalogPage.productCard(productName1)
		)
		.focus();
	await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
		'1.2',
		'9999.9',
		'0.3'
	);

	await expect(
		commerceThemeMiniumCatalogPage.quantitySelector(
			commerceThemeMiniumCatalogPage.productCard(productName2)
		)
	).toHaveValue('3');

	await commerceThemeMiniumCatalogPage
		.quantitySelector(
			commerceThemeMiniumCatalogPage.productCard(productName2)
		)
		.focus();
	await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
		'3',
		'9999',
		'3'
	);

	try {
		await commerceThemeMiniumCatalogPage
			.productCardAddToCartButton(product1.name['en_US'])
			.click();

		await commerceMiniCartPage.miniCartButton.click();

		await expect(
			commerceThemeMiniumCatalogPage.quantitySelector(
				commerceMiniCartPage.miniCartItem(productName1)
			)
		).toHaveValue('1.2');

		await commerceMiniCartPage.miniCartButtonClose.click();

		await commerceThemeMiniumCatalogPage
			.productCardAddToCartButton(product2.name['en_US'])
			.click();

		await commerceMiniCartPage.miniCartButton.click();

		await expect(
			commerceThemeMiniumCatalogPage.quantitySelector(
				commerceMiniCartPage.miniCartItem(productName2)
			)
		).toHaveValue('3');

		await commerceThemeMiniumCatalogPage
			.quantitySelector(commerceMiniCartPage.miniCartItem(productName1))
			.focus();
		await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
			'1.2',
			'9999.9',
			'0.3'
		);

		await expect(commerceMiniCartPage.miniCartTotalPrice).toHaveText(
			'$ 246.00'
		);

		await commerceThemeMiniumCatalogPage
			.quantitySelector(commerceMiniCartPage.miniCartItem(productName2))
			.focus();
		await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
			'3',
			'9999',
			'3'
		);

		await expect(commerceMiniCartPage.miniCartTotalPrice).toHaveText(
			'$ 246.00'
		);

		await commerceMiniCartPage.miniCartButtonClose.click();

		await commerceThemeMiniumCatalogPage
			.productCard(productName1)
			.getByRole('link')
			.first()
			.click();

		await expect(
			commerceThemeMiniumCatalogPage.quantitySelector(
				page.locator('.product-detail')
			)
		).toHaveValue('1.2');

		await commerceThemeMiniumCatalogPage
			.quantitySelector(page.locator('.product-detail'))
			.focus();
		await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
			'1.2',
			'9999.9',
			'0.3'
		);

		await page.goto(`/web/${site.name}`);

		await commerceThemeMiniumCatalogPage
			.productCard(productName2)
			.getByRole('link')
			.first()
			.click();

		await expect(
			commerceThemeMiniumCatalogPage.quantitySelector(
				page.locator('.product-detail')
			)
		).toHaveValue('3');

		await commerceThemeMiniumCatalogPage
			.quantitySelector(page.locator('.product-detail'))
			.focus();
		await commerceThemeMiniumCatalogPage.checkQuantitiesInPopOverMessages(
			'3',
			'9999',
			'3'
		);
	}
	finally {
		const orders =
			await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();

		apiHelpers.data.push({id: orders.items[0].id, type: 'order'});
	}
});
