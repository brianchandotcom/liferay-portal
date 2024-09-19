/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPP-55641 Variable Shipping Rate is calculated based only on shippable products', async ({
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	commerceLayoutsPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: getRandomString(),
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
		shippingConfiguration: {
			freeShipping: false,
			shippable: true,
		},
	});

	const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = product1Skus[0];

	const basePriceListId =
		await apiHelpers.headlessCommerceAdminPricing.getBasePriceListId(
			catalog.id
		);

	await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
		price: 15,
		priceListId: basePriceListId.items[0].id,
		skuId: sku1.id,
	});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product2'},
		shippingConfiguration: {
			shippable: false,
		},
	});

	const product2Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product2.productId)
		.then((product) => {
			return product.skus;
		});

	const sku2 = product2Skus[0];

	await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
		price: 50,
		priceListId: basePriceListId.items[0].id,
		skuId: sku2.id,
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
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
					options: '[]',
					quantity: 1,
					replacedSkuId: 0,
					skuId: sku1.id,
				},
				{
					options: '[]',
					quantity: 1,
					replacedSkuId: 0,
					skuId: sku2.id,
				},
			],
		},
		channel.id
	);

	await commerceAdminChannelsPage.changeCommerceChannelSiteType(
		channel.name,
		'B2B'
	);
	await commerceAdminChannelDetailsPage.activateChannelConfiguration(
		'Variable Rate',
		'Shipping Methods'
	);
	await commerceAdminChannelDetailsPage.addVariableRateShippingOption(
		'variable rate'
	);
	await commerceAdminChannelDetailsPage.addVariableRateShippingOptionSetting(
		'variable rate',
		'10'
	);

	await applicationsMenuPage.goToSite(site.name);
	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage(getRandomString());

	await page.goto(`/web/${site.name}`);
	await checkoutPage.addCheckoutWidget();

	await checkoutPage.addressInput.fill('123 Main St');
	await checkoutPage.cityInput.fill('Miami');
	await checkoutPage.countryInput.selectOption({label: 'United States'});
	await checkoutPage.nameInput.fill('John Doe');
	await checkoutPage.phoneNumberInput.fill('1234567890');
	await checkoutPage.regionInput.selectOption({label: 'Florida'});
	await checkoutPage.zipInput.fill('33101');

	await checkoutPage.continueButton.click();
	expect(checkoutPage.shippingCost).toContainText('$ 1.50');
});
