/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
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

	const orderTypesPage =
		await apiHelpers.headlessCommerceAdminOrder.getOrderTypesPage();

	await Promise.all(
		(orderTypesPage.items ?? []).map((orderType: {id: number}) =>
			apiHelpers.headlessCommerceAdminOrder.deleteOrderTypes(orderType.id)
		)
	);

	const baselineChannelIds = new Set(
		setupData
			.filter((entry) => entry.type === 'channel')
			.map((entry) => entry.id)
	);
	const channelsPage =
		await apiHelpers.headlessCommerceAdminChannel.getChannelsPage('');

	await Promise.all(
		(channelsPage.items ?? [])
			.filter((c: {id: number}) => !baselineChannelIds.has(c.id))
			.map((c: {id: number}) =>
				apiHelpers.headlessCommerceAdminChannel.deleteChannel(c.id)
			)
	);

	await page.close();
});

test(
	'Can complete checkout with the order type selected in the popup',
	{tag: ['@COMMERCE-11560', '@LPD-85008']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceLayoutsPage,
		commerceMiniCartPage,
		commerceThemeMiniumCatalogPage,
		page,
		placedOrdersPage,
	}) => {
		const orderTypeNames: string[] = [];

		await test.step('Create two active order types', async () => {
			for (let i = 1; i <= 2; i++) {
				const name = `Test Order Type ${i} ${getRandomString()}`;
				orderTypeNames.push(name);

				await apiHelpers.headlessCommerceAdminOrder.postOrderType({
					active: true,
					name: {en_US: name},
				});
			}
		});

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		let selectedOrderType = '';

		await test.step('Buyer opens the order type popup via Create New Order', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();

			selectedOrderType =
				(await commerceLayoutsPage.orderTypeModalInput
					.locator('option:checked')
					.textContent()) ?? '';

			for (const name of orderTypeNames) {
				await expect(
					commerceLayoutsPage.orderTypeModalInput.locator('option', {
						hasText: name,
					})
				).toHaveCount(1);
			}

			await commerceLayoutsPage.orderTypeModalButton.click();
		});

		await test.step('Buyer adds the product, then completes checkout', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();

			await commerceMiniCartPage.miniCartButton.click();
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) =>
				url.href.includes('shipping-method')
			);
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) => url.href.includes('order-summary'));
			await checkoutPage.continueButton.click();

			await expect(checkoutPage.orderSuccessMessage).toBeVisible();
		});

		await test.step('Selected Order Type is shown in the Placed Orders list', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/placed-orders`);

			await expect(
				placedOrdersPage.table.getByText(selectedOrderType).first()
			).toBeVisible();
		});
	}
);

test(
	'Order type popup is skipped when only one order type is eligible for the channel',
	{tag: ['@COMMERCE-11591', '@LPD-85008']},
	async ({
		apiHelpers,
		commerceAdminOrderTypeDetailsPage,
		commerceAdminOrderTypesPage,
		commerceLayoutsPage,
		commerceThemeMiniumCatalogPage,
		page,
		pendingOrdersPage,
	}) => {
		const orderType1Name = `Test Order Type 1 ${getRandomString()}`;
		const orderType2Name = `Test Order Type 2 ${getRandomString()}`;
		let secondaryChannelName = '';

		await test.step('Create two order types and a secondary channel', async () => {
			for (const name of [orderType1Name, orderType2Name]) {
				await apiHelpers.headlessCommerceAdminOrder.postOrderType({
					active: true,
					name: {en_US: name},
				});
			}

			const secondaryChannel =
				await apiHelpers.headlessCommerceAdminChannel.postChannel({
					name: `Test Channel ${getRandomString()}`,
					siteGroupId: 0,
				});

			secondaryChannelName = secondaryChannel.name;
		});

		await test.step('Set channel eligibility on order types', async () => {
			await commerceAdminOrderTypesPage.goto();

			await page.getByRole('link', {name: orderType1Name}).click();
			await commerceAdminOrderTypeDetailsPage.eligibilityTab.click();
			await commerceAdminOrderTypeDetailsPage.specificChannelsRadio.check();
			await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
				channel.name
			);
			await commerceAdminOrderTypeDetailsPage
				.eligibilityRowSelectButton(channel.name)
				.click();
			await commerceAdminOrderTypeDetailsPage.saveButton.click();

			await waitForAlert(page);

			await commerceAdminOrderTypesPage.goto();

			await page.getByRole('link', {name: orderType2Name}).click();
			await commerceAdminOrderTypeDetailsPage.eligibilityTab.click();
			await commerceAdminOrderTypeDetailsPage.specificChannelsRadio.check();
			await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
				secondaryChannelName
			);
			await commerceAdminOrderTypeDetailsPage
				.eligibilityRowSelectButton(secondaryChannelName)
				.click();
			await commerceAdminOrderTypeDetailsPage.saveButton.click();

			await waitForAlert(page);
		});

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer adds product; popup is not shown; cart has 1 item', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();

			await page.waitForTimeout(1000);

			await expect(commerceLayoutsPage.orderTypeModalHeading).toHaveCount(
				0
			);
		});

		await test.step('The eligible order type appears in Pending Orders', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/pending-orders`);

			await pendingOrdersPage.viewButton.first().click();

			await expect(pendingOrdersPage.orderType).toHaveText(
				orderType1Name
			);
		});
	}
);

test(
	'Removing items from a cart preserves the previously selected order type',
	{tag: ['@COMMERCE-11563', '@LPD-85008']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceLayoutsPage,
		commerceMiniCartPage,
		commerceThemeMiniumCatalogPage,
		page,
		placedOrdersPage,
	}) => {
		const orderType1Name = `Test Order Type 1 ${getRandomString()}`;
		const orderType2Name = `Test Order Type 2 ${getRandomString()}`;

		await test.step('Create two active order types', async () => {
			for (const name of [orderType1Name, orderType2Name]) {
				await apiHelpers.headlessCommerceAdminOrder.postOrderType({
					active: true,
					name: {en_US: name},
				});
			}
		});

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer creates a new order with Order Type 1 and adds a product', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();

			await commerceLayoutsPage.orderTypeModalInput.selectOption({
				label: orderType1Name,
			});
			await commerceLayoutsPage.orderTypeModalButton.click();

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();
		});

		await test.step('Buyer removes all items from the mini-cart', async () => {
			await commerceMiniCartPage.miniCartButton.click();
			await commerceMiniCartPage.removeAllItemsButton.click();
			await commerceMiniCartPage.removeAllItemsConfirmButton.click();

			await expect(
				commerceMiniCartPage.miniCartItem('U-Joint')
			).toHaveCount(0);
		});

		await test.step('Adding a product again does not show the popup', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();

			await page.waitForTimeout(1000);

			await expect(commerceLayoutsPage.orderTypeModalHeading).toHaveCount(
				0
			);
		});

		await test.step('Checkout completes with the previously selected Order Type 1', async () => {
			await commerceMiniCartPage.miniCartButton.click();
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) =>
				url.href.includes('shipping-method')
			);
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) => url.href.includes('order-summary'));
			await checkoutPage.continueButton.click();

			await expect(checkoutPage.orderSuccessMessage).toBeVisible();

			await page.goto(`/web${site.friendlyUrlPath}/placed-orders`);

			await expect(
				placedOrdersPage.table.getByText(orderType1Name).first()
			).toBeVisible();
		});
	}
);

test(
	'Cannot link the same channel eligibility entry twice to an order type',
	{tag: ['@COMMERCE-10053', '@LPD-85008']},
	async ({
		apiHelpers,
		commerceAdminOrderTypeDetailsPage,
		commerceAdminOrderTypesPage,
		page,
	}) => {
		const orderTypeName = `Order Type Test ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postOrderType({
			active: true,
			name: {en_US: orderTypeName},
		});

		await test.step('Open the order type and set the channel eligibility once', async () => {
			await commerceAdminOrderTypesPage.goto();

			await page.getByRole('link', {name: orderTypeName}).click();
			await commerceAdminOrderTypeDetailsPage.eligibilityTab.click();
			await commerceAdminOrderTypeDetailsPage.specificChannelsRadio.check();
			await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
				channel.name
			);
			await commerceAdminOrderTypeDetailsPage
				.eligibilityRowSelectButton(channel.name)
				.click();
			await commerceAdminOrderTypeDetailsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Add the same channel a second time and assert the duplicate error', async () => {
			await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
				channel.name
			);
			await commerceAdminOrderTypeDetailsPage
				.eligibilityRowSelectButton(channel.name)
				.click();
			await commerceAdminOrderTypeDetailsPage.saveButton.click();

			await expect(
				commerceAdminOrderTypeDetailsPage.errorAlert(
					'The order type relation already exists.'
				)
			).toBeVisible();
		});
	}
);

test(
	'Order type can be reselected after deleting the order from Pending Orders',
	{tag: ['@COMMERCE-11564', '@LPD-85008']},
	async ({
		apiHelpers,
		browser,
		commerceLayoutsPage,
		commerceThemeMiniumCatalogPage,
		page,
	}) => {
		const orderType1Name = `Test Order Type 1 ${getRandomString()}`;
		const orderType2Name = `Test Order Type 2 ${getRandomString()}`;

		for (const name of [orderType1Name, orderType2Name]) {
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: name},
			});
		}

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer creates a new order with Order Type 1 and adds a product', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();

			await commerceLayoutsPage.orderTypeModalInput.selectOption({
				label: orderType1Name,
			});
			await commerceLayoutsPage.orderTypeModalButton.click();

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();
		});

		await test.step('Order is deleted from Pending Orders (via admin API)', async () => {
			const adminPage = await browser.newPage();

			await performLoginViaApi({page: adminPage, screenName: 'test'});

			const adminApiHelpers = new DataApiHelpers(adminPage);

			const ordersResponse =
				await adminApiHelpers.headlessCommerceAdminOrder.getOrdersPage();
			const order = (ordersResponse.items ?? [])[0];

			await adminApiHelpers.headlessCommerceAdminOrder.deleteOrder(
				order.id
			);

			await adminPage.close();
		});

		await test.step('Create New Order is available again and shows the popup', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();
		});
	}
);

test(
	'Order type popup only lists order types eligible for the channel',
	{tag: ['@COMMERCE-11573', '@LPD-85008']},
	async ({
		apiHelpers,
		commerceAdminOrderTypeDetailsPage,
		commerceAdminOrderTypesPage,
		commerceLayoutsPage,
		page,
	}) => {
		const orderTypeNames = [
			`Test Order Type 1 ${getRandomString()}`,
			`Test Order Type 2 ${getRandomString()}`,
			`Test Order Type 3 ${getRandomString()}`,
		];

		for (const name of orderTypeNames) {
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: name},
			});
		}

		const secondaryChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: `Test Channel ${getRandomString()}`,
				siteGroupId: 0,
			});

		await test.step('Set channel eligibility: 1 & 2 → Minium, 3 → Test Channel', async () => {
			for (const name of [orderTypeNames[0], orderTypeNames[1]]) {
				await commerceAdminOrderTypesPage.goto();

				await page.getByRole('link', {name}).click();
				await commerceAdminOrderTypeDetailsPage.eligibilityTab.click();
				await commerceAdminOrderTypeDetailsPage.specificChannelsRadio.check();
				await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
					channel.name
				);
				await commerceAdminOrderTypeDetailsPage
					.eligibilityRowSelectButton(channel.name)
					.click();
				await commerceAdminOrderTypeDetailsPage.saveButton.click();

				await waitForAlert(page);
			}

			await commerceAdminOrderTypesPage.goto();

			await page.getByRole('link', {name: orderTypeNames[2]}).click();
			await commerceAdminOrderTypeDetailsPage.eligibilityTab.click();
			await commerceAdminOrderTypeDetailsPage.specificChannelsRadio.check();
			await commerceAdminOrderTypeDetailsPage.eligibilityFindChannelInput.fill(
				secondaryChannel.name
			);
			await commerceAdminOrderTypeDetailsPage
				.eligibilityRowSelectButton(secondaryChannel.name)
				.click();
			await commerceAdminOrderTypeDetailsPage.saveButton.click();

			await waitForAlert(page);
		});

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer opens the popup; only Minium-eligible order types are listed', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();

			await expect(
				commerceLayoutsPage.orderTypeModalInput.locator('option', {
					hasText: orderTypeNames[0],
				})
			).toHaveCount(1);
			await expect(
				commerceLayoutsPage.orderTypeModalInput.locator('option', {
					hasText: orderTypeNames[1],
				})
			).toHaveCount(1);
			await expect(
				commerceLayoutsPage.orderTypeModalInput.locator('option', {
					hasText: orderTypeNames[2],
				})
			).toHaveCount(0);
		});
	}
);

test(
	'Order type can be reselected for a new order after checkout',
	{tag: ['@COMMERCE-11562', '@LPD-85008']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceLayoutsPage,
		commerceMiniCartPage,
		commerceThemeMiniumCatalogPage,
		page,
	}) => {
		const orderType1Name = `Test Order Type 1 ${getRandomString()}`;
		const orderType2Name = `Test Order Type 2 ${getRandomString()}`;

		for (const name of [orderType1Name, orderType2Name]) {
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: name},
			});
		}

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer creates a new order with Order Type 1 and adds a product', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();

			await commerceLayoutsPage.orderTypeModalInput.selectOption({
				label: orderType1Name,
			});
			await commerceLayoutsPage.orderTypeModalButton.click();

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();
		});

		await test.step('Buyer completes the checkout', async () => {
			await commerceMiniCartPage.miniCartButton.click();
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) =>
				url.href.includes('shipping-method')
			);
			await checkoutPage.continueButton.click();

			await page.waitForURL((url) => url.href.includes('order-summary'));
			await checkoutPage.continueButton.click();

			await expect(checkoutPage.orderSuccessMessage).toBeVisible();
		});

		await test.step('Create New Order is available again and shows the popup', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();
		});
	}
);

test(
	'Highest-priority order type is preselected in the popup by default',
	{tag: ['@LPD-85008']},
	async ({
		apiHelpers,
		commerceLayoutsPage,
		commerceThemeMiniumCatalogPage,
		page,
		pendingOrdersPage,
	}) => {
		const orderType1Name = `Test Order Type 1 ${getRandomString()}`;
		const orderType2Name = `Test Order Type 2 ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminOrder.postOrderType({
			active: true,
			displayOrder: 2,
			name: {en_US: orderType1Name},
		} as any);

		await apiHelpers.headlessCommerceAdminOrder.postOrderType({
			active: true,
			displayOrder: 1,
			name: {en_US: orderType2Name},
		} as any);

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await test.step('Buyer opens the popup; Order Type 2 is preselected (lower display order = higher priority)', async () => {
			await page.goto(`/web${site.friendlyUrlPath}`);

			await commerceLayoutsPage
				.accountSelectorButton('Account Selector')
				.click();
			await commerceLayoutsPage.createNewOrderButton.click();

			await expect(
				commerceLayoutsPage.orderTypeModalHeading
			).toBeVisible();
			await expect(commerceLayoutsPage.orderTypeModalInput).toHaveValue(
				/.+/
			);

			const selectedLabel = await commerceLayoutsPage.orderTypeModalInput
				.locator('option:checked')
				.textContent();

			expect(selectedLabel?.trim()).toBe(orderType2Name);

			await commerceLayoutsPage.orderTypeModalButton.click();

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceThemeMiniumCatalogPage
				.productCardAddToCartButton('U-Joint')
				.click();
		});

		await test.step('Pending Orders shows the order with Order Type 2', async () => {
			await page.goto(`/web${site.friendlyUrlPath}/pending-orders`);

			await pendingOrdersPage.viewButton.first().click();

			await expect(pendingOrdersPage.orderType).toHaveText(
				orderType2Name
			);
		});
	}
);
