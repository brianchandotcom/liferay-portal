/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	loginTest
);

const data = [];

test.afterEach(async ({apiHelpers}) => {
	for await (const item of data.reverse()) {
		switch (item.type) {
			case 'catalog':
				await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
					item.id
				);

				break;
			case 'channel':
				await apiHelpers.headlessCommerceAdminChannel.deleteChannel(
					item.id
				);

				break;
			case 'option':
				await apiHelpers.headlessCommerceAdminCatalog.deleteOption(
					item.id
				);

				break;
			case 'product':
				await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
					item.id
				);

				break;
			case 'site':
				await apiHelpers.headlessSite.deleteSite(item.id);

				break;
			default:
				break;
		}
	}
});

test('mini cart bundle with UOM', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	commerceMiniCartPage,
	commerceProductAdminPage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-9599', true);

	const site = await apiHelpers.headlessSite.createSite('Mini Cart Site');

	data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Mini Cart Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const option1 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'color',
		'Color',
		1
	);

	data.push({id: option1.id, type: 'option'});

	const option2 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'size',
		'Size',
		2
	);

	data.push({id: option2.id, type: 'option'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Mini Cart Catalog',
	});

	data.push({id: catalog.id, type: 'catalog'});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	data.push({id: product1.productId, type: 'product'});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product2'},
	});

	data.push({id: product2.productId, type: 'product'});

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

	data.push({id: productBundle.productId, type: 'product'});

	await applicationsMenuPage.goToProducts();

	await commerceProductAdminPage.managementToolbarSearchInput.fill(
		'ProductBundle'
	);
	await commerceProductAdminPage.managementToolbarSearchInput.press('Enter');

	await page.getByRole('link', {exact: true, name: 'ProductBundle'}).click();

	await commerceProductAdminPage.generateSkus();

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

	await applicationsMenuPage.goToSite('Mini Cart Site');

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Catalog');
	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.changeCurrentTheme(
		'Select Minium By Liferay, Inc.'
	);
	await commerceLayoutsPage.siteHomePageLink.click();

	await commerceMiniCartPage.miniCartButton.click();
	await commerceMiniCartPage.searchProductsInput.fill(sku1.sku);

	await page
		.getByRole('menuitem', {exact: true, name: `${sku1.sku} ProductBundle`})
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

	await expect(
		page.getByRole('cell', {exact: true, name: 'Box'})
	).toBeVisible();
	await expect(
		page.getByRole('cell', {exact: true, name: 'Pallet'})
	).toBeVisible();

	await page.getByLabel('Size').selectOption({label: 'XS'});
	await page.getByLabel('Color').selectOption({label: 'Black'});

	await expect(page.getByText('Price as Configured$ 40.00')).toBeVisible();

	await expect(commerceMiniCartPage.editUnitOfMeasureLabel).toBeHidden();
	await expect(commerceMiniCartPage.unitOfMeasureTableLabel).toBeHidden();
	await expect(commerceMiniCartPage.miniCartSaveButton).toBeEnabled();

	await page.getByLabel('Size').selectOption({label: 'XL'});

	await expect(page.getByText('Price as Configured$ 50.00')).toBeVisible();
	await expect(
		page.getByRole('cell', {exact: true, name: 'Package'})
	).toBeVisible();

	await commerceMiniCartPage.miniCartUnitOfMeasureSelector.selectOption(
		sku2SkuUnitOfMeasure.key
	);

	await expect(commerceMiniCartPage.miniCartSaveButton).toBeDisabled();

	await commerceMiniCartPage.editQuantitySelector.fill('4');

	await expect(commerceMiniCartPage.miniCartSaveButton).toBeEnabled();

	await commerceMiniCartPage.miniCartSaveButton.click();
	await commerceMiniCartPage.showOptionsButton.click();

	await expect(
		page.getByText(sku2SkuUnitOfMeasure.key, {exact: true})
	).toBeVisible();
	await expect(page.getByText('Black', {exact: true})).toBeVisible();
	await expect(page.getByText('XL', {exact: true})).toBeVisible();
	await expect(
		page.getByText('$ 50.00', {exact: true}).first()
	).toBeVisible();

	await apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-9599', false);
});
