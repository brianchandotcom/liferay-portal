/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('CanViewSingleSkuVirtualProductDetailPage', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	productDetailsPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'View product details',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'View product details',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'View product details',
	});

	const virtualProduct =
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			description: {en_US: 'Full description'},
			name: {en_US: 'Virtual'},
			productType: 'virtual',
			productVirtualSettings: {
				activationStatus: 1,
				duration: 4,
				maxUsages: 4,
				sampleURL: 'http://www.google.com',
				useSample: true,
			},
			shortDescription: {en_US: 'Short description'},
			skus: [
				{
					cost: 0,
					gtin: 'GTIN1',
					manufacturerPartNumber: 'mpn',
					price: 0,
					published: true,
					purchasable: true,
					sku: 'SkuVirtual',
				},
			],
		});

	const basePriceListId =
		await apiHelpers.headlessCommerceAdminPricing.getBasePriceListId(
			catalog.id
		);

	await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
		price: 100,
		priceListId: basePriceListId.items[0].id,
		skuId: virtualProduct.skus[0].id,
	});

	const basePromoPriceListId =
		await apiHelpers.headlessCommerceAdminPricing.getBasePromoPriceListId(
			catalog.id
		);

	await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
		price: 50,
		priceListId: basePromoPriceListId.items[0].id,
		skuId: virtualProduct.skus[0].id,
	});

	await apiHelpers.headlessCommerceAdminPricing.postDiscount();

	await applicationsMenuPage.goToSite('View product details');

	await commerceLayoutsPage.goToPages(false);

	await commerceLayoutsPage.createWidgetPage('View product details');

	await page.goto(`/web/${site.name}`);

	await productDetailsPage.addProductDetailsWidget();

	await page.goto(`/web/${site.name}/p/virtual`);

	await expect(page.getByText('SkuVirtual')).toBeVisible();
	await expect(page.getByText('mpn', {exact: true})).toBeVisible();
	await expect(page.getByText('GTIN1')).toBeVisible();
	await expect(page.getByText('Short description')).toBeVisible();

	await expect(page.getByText('$ 100.00')).toBeVisible();
	await expect(page.getByText('$ 100.00')).toHaveClass(
		/price-value-inactive/
	);
	await expect(page.getByText('$ 50.00')).toBeVisible();
	await expect(page.getByText('$ 50.00')).toHaveClass(/price-value-promo/);
	await expect(
		page.getByText('Full description', {exact: true})
	).toBeVisible();
	await expect(
		page.getByRole('link', {name: 'Download Sample File'})
	).toBeVisible();
});

test('View product details page', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminProductPage,
	commerceLayoutsPage,
	page,
	productDetailsPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'View product details',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'View product details',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'View product details',
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
		.productsTableRowLink('ProductBundle')
		.click();

	await commerceAdminProductPage.generateSkus();

	const productBundleSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(productBundle.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = productBundleSkus.find(
		(sku) => sku.sku === 'WHITEXL' || sku.sku === 'XLWHITE'
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
		sku1.id,
		{
			incrementalOrderQuantity: 3,
			name: {en_US: 'Box'},
			primary: true,
			priority: 1,
			rate: 1,
		}
	);

	const sku2 = productBundleSkus.find(
		(sku) => sku.sku === 'BLACKXL' || sku.sku === 'XLBLACK'
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

	await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
		sku2.id,
		{
			incrementalOrderQuantity: 2,
			name: {en_US: 'Package'},
			priority: 2,
			rate: 0.5,
		}
	);

	await applicationsMenuPage.goToSite('View product details');

	await commerceLayoutsPage.goToPages(false);

	await commerceLayoutsPage.createWidgetPage('View product details');

	await page.goto(`/web/${site.name}`);

	await productDetailsPage.addProductDetailsWidget();

	await page.goto(`/web/${site.name}/p/productbundle`);

	await expect(page.getByLabel('Size')).toBeVisible();

	await expect(page.getByLabel('Color')).toBeVisible();

	await page.getByLabel('Color').selectOption({label: 'Black'});

	await page.getByLabel('Size').selectOption({label: 'XL + $ 10.00'});

	await expect(page.getByRole('cell', {name: 'Unit'})).toBeVisible();

	await expect(page.getByText('$ 50.00')).toBeVisible();
});
