/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {performLogout} from '../../../utils/performLogin';
import getFragmentDefinition from '../../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

test('COMMERCE-12316 Mini cart bundle with UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminProductPage,
	commerceMiniCartPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'COMMERCE_CART_FRAGMENTS-mini-cart',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Mini Cart Channel',
		siteGroupId: site.id,
	});

	const option1 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'color',
		'Color',
		1
	);
	const option2 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'size',
		'Size',
		2
	);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Mini Cart Catalog',
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});
	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product2'},
	});
	const productBundle =
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			name: {en_US: 'ProductBundle'},
			productOptions: [
				{
					fieldType: 'select',
					key: 'color',
					name: {
						en_US: 'Color',
					},
					optionId: option1.id,
					priceType: 'static',
					priority: 1,
					productOptionValues: [
						{
							deltaPrice: 10.0,
							key: 'black',
							name: {
								en_US: 'Black',
							},
							priority: 1,
							quantity: 1,
							skuId: product1.skus[0].id,
						},
						{
							deltaPrice: 20.0,
							key: 'white',
							name: {
								en_US: 'White',
							},
							priority: 2,
							quantity: 1,
						},
					],
					skuContributor: true,
				},
				{
					fieldType: 'select',
					key: 'size',
					name: {
						en_US: 'Size',
					},
					optionId: option2.id,
					priceType: 'static',
					priority: 2,
					productOptionValues: [
						{
							deltaPrice: 30.0,
							key: 'xs',
							name: {
								en_US: 'XS',
							},
							priority: 1,
							quantity: 1,
						},
						{
							deltaPrice: 40.0,
							key: 'xl',
							name: {
								en_US: 'XL',
							},
							priority: 2,
							quantity: 1,
							skuId: product2.skus[0].id,
						},
					],
					skuContributor: true,
				},
			],
		});

	await applicationsMenuPage.goToProducts();

	await commerceAdminProductPage.managementToolbarSearchInput.fill(
		'ProductBundle'
	);
	await commerceAdminProductPage.managementToolbarSearchInput.press('Enter');
	await commerceAdminProductPage
		.managementToolbarItemLink('ProductBundle')
		.click();
	await commerceAdminProductPage.generateSkus();

	await expect(page.getByText('Showing 1 to 5 of 5 entries.')).toBeVisible();

	const productBundleSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(productBundle.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = productBundleSkus.find(
		(sku) => sku.sku === 'WHITEXL' || sku.sku === 'XLWHITE'
	);
	const sku2 = productBundleSkus.find(
		(sku) => sku.sku === 'BLACKXL' || sku.sku === 'XLBLACK'
	);

	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
		sku1.id,
		{
			incrementalOrderQuantity: 2,
			name: {en_US: 'Pallet'},
			priority: 2,
			rate: 3,
		}
	);
	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
		sku2.id,
		{
			incrementalOrderQuantity: 3,
			name: {en_US: 'Box'},
			primary: true,
			priority: 1,
			rate: 1,
		}
	);

	const sku1SkuUnitOfMeasure =
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
	const sku2SkuUnitOfMeasure =
		await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
			sku2.id,
			{
				incrementalOrderQuantity: 2,
				name: {en_US: 'Package'},
				priority: 2,
				rate: 0.5,
			}
		);

	await page.goto(
		`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	await commerceMiniCartPage.miniCartButton.click();
	await commerceMiniCartPage.searchProductsInput.fill(sku1.sku);
	await commerceMiniCartPage
		.quickAddToCartSku(`${sku1.sku} ProductBundle`)
		.click();
	await commerceMiniCartPage.quickAddToCartButton.click();
	await commerceMiniCartPage.showOptionsButton.click();

	await expect(
		page.getByText(sku1SkuUnitOfMeasure.key, {exact: true})
	).toBeVisible();
	await expect(page.getByText('White', {exact: true})).toBeVisible();
	await expect(page.getByText('XL', {exact: true})).toBeVisible();
	await expect(
		page.getByText('$ 60.00', {exact: true}).first()
	).toBeVisible();

	await commerceMiniCartPage.cartItemActionsButton.click();
	await commerceMiniCartPage.editMenuItem.click();

	await expect(commerceMiniCartPage.editOptionsLabel).toBeVisible();
	await expect(commerceMiniCartPage.editQuantityLabel).toBeVisible();
	await expect(commerceMiniCartPage.editUnitOfMeasureLabel).toBeVisible();
	await expect(commerceMiniCartPage.unitOfMeasureTableLabel).toBeVisible();
	await expect(commerceMiniCartPage.miniCartSaveButton).toBeEnabled();

	await expect(page.getByText('Price as Configured$ 60.00')).toBeVisible();

	await expect(
		page.getByRole('cell', {exact: true, name: 'Box'})
	).toBeVisible();
	await expect(
		page.getByRole('cell', {exact: true, name: 'Pallet'})
	).toBeVisible();

	await commerceMiniCartPage.selectOption('XS', 'Size');

	await expect(page.getByText('List Price$ 50.00')).toBeVisible();

	await expect(page.getByText('Price as Configured$ 150.00')).toBeVisible();

	await commerceMiniCartPage.selectOption('Black - $ 10.00', 'Color');

	await expect(page.getByText('List Price$ 40.00')).toBeVisible();

	await expect(page.getByText('Price as Configured$ 120.00')).toBeVisible();

	await expect(commerceMiniCartPage.editUnitOfMeasureLabel).toBeHidden();
	await expect(commerceMiniCartPage.unitOfMeasureTableLabel).toBeHidden();
	await expect(commerceMiniCartPage.miniCartSaveButton).toBeEnabled();

	await commerceMiniCartPage.selectOption('XL + $ 10.00', 'Size');

	await expect(page.getByText('List Price$ 50.00')).toBeVisible();

	await expect(page.getByText('Price as Configured$ 50.00')).toBeVisible();

	await expect(
		page.getByRole('cell', {exact: true, name: 'Package'})
	).toBeVisible();

	await commerceMiniCartPage.miniCartUnitOfMeasureSelector.selectOption(
		sku2SkuUnitOfMeasure.key
	);

	await expect(commerceMiniCartPage.miniCartSaveButton).toBeDisabled();

	await commerceMiniCartPage.editQuantitySelector.fill('6');

	await expect(page.getByText('Price as Configured$ 150.00')).toBeVisible();

	await expect(commerceMiniCartPage.miniCartSaveButton).toBeEnabled();

	await commerceMiniCartPage.miniCartSaveButton.click();
	await commerceMiniCartPage.showOptionsButton.click();

	await expect(
		page.getByText(sku2SkuUnitOfMeasure.key, {exact: true})
	).toBeVisible();
	await expect(page.getByText('Black', {exact: true})).toBeVisible();
	await expect(page.getByText('XL', {exact: true})).toBeVisible();
	await expect(
		page.getByText('$ 150.00', {exact: true}).first()
	).toBeVisible();
});

test('LPD-3496 Mini cart bundle without enough quantity', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminProductPage,
	commerceMiniCartPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'COMMERCE_CART_FRAGMENTS-mini-cart',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const option = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'color',
		'Color',
		1
	);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		productConfiguration: {
			allowBackOrder: false,
		},
	});

	const productBundleName = 'ProductBundle';

	const productBundle =
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			name: {en_US: productBundleName},
			productOptions: [
				{
					fieldType: 'select',
					key: option.key,
					name: option.name,
					optionId: option.id,
					priceType: 'static',
					priority: 1,
					productOptionValues: [
						{
							deltaPrice: 10.0,
							key: 'black',
							name: {
								en_US: 'Black',
							},
							priority: 1,
							quantity: 1,
							skuId: product.skus[0].id,
						},
						{
							deltaPrice: 20.0,
							key: 'white',
							name: {
								en_US: 'White',
							},
							priority: 2,
							quantity: 1,
						},
					],
					skuContributor: true,
				},
			],
		});

	await applicationsMenuPage.goToProducts();

	await commerceAdminProductPage.managementToolbarSearchInput.fill(
		productBundleName
	);
	await commerceAdminProductPage.managementToolbarSearchInput.press('Enter');

	await page
		.getByRole('link', {exact: true, name: productBundleName})
		.click();

	await commerceAdminProductPage.generateSkus();

	await expect(page.getByText('Showing 1 to 3 of 3 entries.')).toBeVisible();

	const productBundleSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(productBundle.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productBundleSkus.find((sku) => sku.sku === 'WHITE');

	await page.goto(
		`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	await commerceMiniCartPage.miniCartButton.click();
	await commerceMiniCartPage.searchProductsInput.fill(sku.sku);
	await commerceMiniCartPage
		.quickAddToCartSku(`${sku.sku} ${productBundleName}`)
		.click();
	await commerceMiniCartPage.quickAddToCartButton.click();
	await commerceMiniCartPage.cartItemActionsButton.click();
	await commerceMiniCartPage.editMenuItem.click();

	await expect(commerceMiniCartPage.editOptionsLabel).toBeVisible();

	await commerceMiniCartPage.selectOption('Black', 'Color');

	await expect(page.getByLabel('Color')).toBeEnabled();

	await commerceMiniCartPage.miniCartSaveButton.click();

	await expect(page.getByText(/Error.*quantity.*unavailable/)).toBeVisible();
});

test('LPD-26906 Mini cart bundle quantity edit', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminProductPage,
	commerceMiniCartPage,
	page,
}) => {
	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: 'Buyer ' + getRandomString(),
		rolePermissions: [
			{
				actionIds: ['MANAGE_ADDRESSES', 'VIEW_ADDRESSES'],
				primaryKey: '0',
				resourceName: 'com.liferay.account.model.AccountEntry',
				scope: 3,
			},
			{
				actionIds: ['VIEW'],
				primaryKey: companyId,
				resourceName: 'com.liferay.commerce.model.CommerceOrderType',
				scope: 1,
			},
			{
				actionIds: [
					'ADD_COMMERCE_ORDER',
					'CHECKOUT_OPEN_COMMERCE_ORDERS',
					'MANAGE_COMMERCE_ORDER_DELIVERY_TERMS',
					'MANAGE_COMMERCE_ORDER_PAYMENT_METHODS',
					'MANAGE_COMMERCE_ORDER_PAYMENT_TERMS',
					'MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS',
					'VIEW_BILLING_ADDRESS',
					'VIEW_COMMERCE_ORDERS',
					'VIEW_OPEN_COMMERCE_ORDERS',
				],
				primaryKey: '0',
				resourceName: 'com.liferay.commerce.order',
				scope: 3,
			},
		],
	});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'COMMERCE_CART_FRAGMENTS-mini-cart',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const optionKey = getRandomString();

	const option = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		optionKey,
		'Color',
		1
	);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
	});

	const productBundle =
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			name: {en_US: getRandomString()},
			productOptions: [
				{
					fieldType: 'select',
					key: optionKey,
					name: {
						en_US: 'Color',
					},
					optionId: option.id,
					priceType: 'static',
					priority: 1,
					productOptionValues: [
						{
							deltaPrice: 10.0,
							key: 'black',
							name: {
								en_US: 'Black',
							},
							priority: 1,
							quantity: 1,
							skuId: product.skus[0].id,
						},
					],
					skuContributor: true,
				},
			],
		});

	await applicationsMenuPage.goToProducts();

	const productBundleName = productBundle.name['en_US'];

	await commerceAdminProductPage.managementToolbarSearchInput.fill(
		productBundleName
	);
	await commerceAdminProductPage.managementToolbarSearchInput.press('Enter');
	await commerceAdminProductPage
		.managementToolbarItemLink(productBundleName)
		.click();
	await commerceAdminProductPage.generateSkus();

	await expect(page.getByText('Showing 1 to 2 of 2 entries.')).toBeVisible();

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await page.goto(
		`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	await commerceMiniCartPage.miniCartButton.click();
	await commerceMiniCartPage.searchProductsInput.fill('BLACK');
	await commerceMiniCartPage
		.quickAddToCartSku(`BLACK ${productBundleName}`)
		.click();
	await commerceMiniCartPage.quickAddToCartButton.click();
	await commerceMiniCartPage.showOptionsButton.click();

	await expect(page.getByText('Black', {exact: true})).toBeVisible();
	await expect(
		page.getByText('$ 10.00', {exact: true}).first()
	).toBeVisible();

	await commerceMiniCartPage.editQuantitySelector.fill('2');

	await expect(
		page.getByText('$ 20.00', {exact: true}).first()
	).toBeVisible();
});
