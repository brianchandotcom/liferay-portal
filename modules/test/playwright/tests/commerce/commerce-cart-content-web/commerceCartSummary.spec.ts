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

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-3360 Checkout with single approval', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceAdminChannelsPage,
	commerceCartSummaryPage,
	commerceLayoutsPage,
	commerceMiniCartPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: `${site.name} Channel`,
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: `${site.name} Catalog`,
	});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	const product1Skus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product1.productId)
		.then((product) => {
			return product.skus;
		});

	const sku1 = product1Skus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	await commerceAdminChannelsPage.changeCommerceChannelSiteType(
		channel.name,
		'B2B'
	);

	await commerceAdminChannelsPage.changeCommerceChannelBuyerOrderApprovalWorkflow(
		'Single Approver (Version 1)',
		channel.name
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
			],
		},
		channel.id
	);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Commerce Cart Page');

	await page.goto(`/web/${site.name}`);

	await commerceCartSummaryPage.addCartSummaryWidget();

	await commerceMiniCartPage.submitButton.waitFor({state: 'visible'});

	await expect(commerceMiniCartPage.submitButton).toBeVisible();
});
