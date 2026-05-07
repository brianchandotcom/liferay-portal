/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {CommerceAdminChannelsPage} from '../../../../pages/commerce/commerce-channel-web/commerceAdminChannelsPage';
import {
	performLoginViaApi,
	performLogout,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest()
);

let channel: {id: number; name: string};
let setupData: Array<{id: number | string; type: string}>;
let site: Site;

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const miniumResult = await miniumSetUp(apiHelpers);

	channel = miniumResult.channel;
	site = miniumResult.site;

	const commerceAdminChannelsPage = new CommerceAdminChannelsPage(page);

	await commerceAdminChannelsPage.goto();
	await (
		await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
	).click();
	await commerceAdminChannelsPage
		.ordersTabToggle('Show Separate Order Items')
		.click();
	await commerceAdminChannelsPage.headerActionsSaveButton.click();

	await waitForAlert(page);

	setupData = [...apiHelpers.data];

	await page.close();
});

test.afterAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	apiHelpers.setData(setupData);

	await apiHelpers.clearData();

	await page.close();
});

test.afterEach(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const ordersPage =
		await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();

	await Promise.all(
		(ordersPage.items ?? []).map((order) =>
			apiHelpers.headlessCommerceAdminOrder.deleteOrder(order.id)
		)
	);

	await page.close();
});

test(
	'Order items are split in placed orders when Show Separate Order Items is enabled',
	{tag: ['@COMMERCE-12600']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceMiniCartPage,
		page,
		placedOrderPage,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const sku =
			await apiHelpers.headlessCommerceAdminCatalog.getSkuByName(
				'MIN55861'
			);

		await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{quantity: 1, skuId: sku.id},
					{quantity: 1, skuId: sku.id},
				],
			},
			channel.id
		);

		await test.step('Login as buyer and open the mini cart', async () => {
			await performLogout(page);
			await performLoginViaApi({
				page,
				screenName: buyerUser.alternateName,
			});

			await page.goto(`/web/${site.name}`, {waitUntil: 'networkidle'});

			await commerceMiniCartPage.miniCartButton.click();
		});

		await test.step('Mini cart shows two split U-Joint line items', async () => {
			await expect(
				commerceMiniCartPage.miniCartItem('U-Joint')
			).toHaveCount(2);
		});

		await test.step('Checkout the order with a new shipping address', async () => {
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				phoneNumber: '12345',
				regionLabel: 'Alabama',
				street: 'Test Address',
				zip: 'Test Zip Code',
			});

			await checkoutPage.continueButton.click();

			await page.waitForLoadState('networkidle');

			await checkoutPage.continueButton.click();

			await page.waitForLoadState('networkidle');

			await checkoutPage.continueButton.click();

			await expect(checkoutPage.orderSuccessMessage).toBeVisible();
		});

		await test.step('Verify the placed order shows two split U-Joint rows', async () => {
			await checkoutPage.goToOrderDetailsButton.click();

			await page.waitForLoadState('networkidle');

			await expect(placedOrderPage.orderItemLink('U-Joint')).toHaveCount(
				2
			);
			await expect(
				placedOrderPage.paginationText('Showing 1 to 2 of 2 entries.')
			).toBeVisible();
		});
	}
);

test(
	'Order items are split on the mini cart with multiple SKU options when Show Separate Order Items is enabled',
	{tag: ['@COMMERCE-12491']},
	async ({apiHelpers, commerceMiniCartPage, page}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const skuNames = ['MIN93016A', 'MIN93016B', 'MIN93016C'];

		const skus = await Promise.all(
			skuNames.map((skuName) =>
				apiHelpers.headlessCommerceAdminCatalog.getSkuByName(skuName)
			)
		);

		await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: skus.flatMap((sku) => [
					{quantity: 1, skuId: sku.id},
					{quantity: 1, skuId: sku.id},
				]),
			},
			channel.id
		);

		await test.step('Login as buyer and open the mini cart', async () => {
			await performLogout(page);
			await performLoginViaApi({
				page,
				screenName: buyerUser.alternateName,
			});

			await page.goto(`/web/${site.name}`, {waitUntil: 'networkidle'});

			await commerceMiniCartPage.miniCartButton.click();
		});

		await test.step('Mini cart shows six split Brake Fluid line items, two per SKU', async () => {
			await expect(
				commerceMiniCartPage.miniCartItem('Brake Fluid')
			).toHaveCount(6);

			for (const skuName of skuNames) {
				await expect(
					commerceMiniCartPage.miniCartSku(skuName)
				).toHaveCount(2);
			}
		});
	}
);

test(
	'Order items are split on the mini cart for a SKU with a Unit of Measure when Show Separate Order Items is enabled',
	{tag: ['@COMMERCE-12492']},
	async ({apiHelpers, commerceMiniCartPage, page}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const sku =
			await apiHelpers.headlessCommerceAdminCatalog.getSkuByName(
				'MIN55861'
			);

		const uom =
			await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
				sku.id,
				{
					basePrice: 10,
					name: {en_US: 'uomName1'},
					priority: 0,
				}
			);

		await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{
						quantity: 1,
						skuId: sku.id,
						skuUnitOfMeasure: {key: uom.key},
					},
					{
						quantity: 1,
						skuId: sku.id,
						skuUnitOfMeasure: {key: uom.key},
					},
				],
			},
			channel.id
		);

		await test.step('Login as buyer and open the mini cart', async () => {
			await performLogout(page);
			await performLoginViaApi({
				page,
				screenName: buyerUser.alternateName,
			});

			await page.goto(`/web/${site.name}`, {waitUntil: 'networkidle'});

			await commerceMiniCartPage.miniCartButton.click();
		});

		await test.step('Mini cart shows two split U-Joint line items for the SKU with the new Unit of Measure', async () => {
			await expect(
				commerceMiniCartPage.miniCartItem('U-Joint')
			).toHaveCount(2);
		});
	}
);

test(
	'Order items are split on the mini cart with multiple products when Show Separate Order Items is enabled',
	{tag: ['@COMMERCE-12489']},
	async ({apiHelpers, commerceMiniCartPage, page}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const skus = await Promise.all(
			['MIN55861', 'MIN93015'].map((skuName) =>
				apiHelpers.headlessCommerceAdminCatalog.getSkuByName(skuName)
			)
		);

		await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: skus.flatMap((sku) => [
					{quantity: 1, skuId: sku.id},
					{quantity: 1, skuId: sku.id},
				]),
			},
			channel.id
		);

		await test.step('Login as buyer and open the mini cart', async () => {
			await performLogout(page);
			await performLoginViaApi({
				page,
				screenName: buyerUser.alternateName,
			});

			await page.goto(`/web/${site.name}`, {waitUntil: 'networkidle'});

			await commerceMiniCartPage.miniCartButton.click();
		});

		await test.step('Mini cart shows four split line items, two per product', async () => {
			await expect(
				commerceMiniCartPage.miniCartItem('ABS Sensor')
			).toHaveCount(2);
			await expect(
				commerceMiniCartPage.miniCartItem('U-Joint')
			).toHaveCount(2);
		});
	}
);
