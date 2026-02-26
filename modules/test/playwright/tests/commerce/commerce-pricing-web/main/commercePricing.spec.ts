/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {getRandomInt} from '../../../../utils/getRandomInt';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Tier Price quantities are not converted to scientific notation',
	{tag: ['@LPD-75285']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		page,
	}) => {
		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		const productSkus = await apiHelpers.headlessCommerceAdminCatalog
			.getProduct(product.productId)
			.then((product) => {
				return product.skus;
			});

		const productSku = productSkus[0];

		const currencies =
			await apiHelpers.headlessCommerceAdminCatalog.getCurrenciesPage(
				'USD'
			);

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: currencies.items[0].code,
				name: 'Price List' + getRandomInt(),
				type: 'price-list',
			});

		await commerceAdminPriceListsPage.goto();

		await (
			await commerceAdminPriceListsPage.tableRowLink({
				colIndex: 0,
				rowValue: priceList.name,
			})
		).click();

		await commerceAdminPriceListDetailsPage.entriesTab.click();

		await commerceAdminPriceListDetailsPage.findSkuInput.click();
		await commerceAdminPriceListDetailsPage.findSkuInput.fill(
			productSku.sku
		);

		await commerceAdminPriceListDetailsPage.selectButton.click();

		await page.reload();

		await commerceAdminPriceListDetailsPage
			.skusTableRowLink(productSku.sku)
			.click();

		await commerceAdminPriceListDetailsPage.addTierPriceButton.click();

		await commerceAdminPriceListDetailsPage.addTierPriceEntryQuantity.fill(
			'50'
		);

		const price = '100';
		await commerceAdminPriceListDetailsPage.addTierPriceEntryPrice.fill(
			price
		);

		await commerceAdminPriceListDetailsPage.addTierPriceEntrySaveButton.click();

		await commerceAdminPriceListDetailsPage
			.skuLink(`$ ${price}.00`)
			.click();

		await expect(
			commerceAdminPriceListDetailsPage.editPriceTierPrice
		).toHaveValue(`${price}.00`);
	}
);
