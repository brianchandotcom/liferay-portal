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
	userData,
} from '../../../../utils/performLogin';
import {miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

const ADDRESS_TYPES = {
	BILLING: 1,
	BILLING_AND_SHIPPING: 2,
	SHIPPING: 3,
};

const CHECKOUT_BASELINE_PERMISSIONS = (companyId: string) => [
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
];

let setupData: Array<{id: number | string; type: string}>;
let site: Site;

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const miniumResult = await miniumSetUp(apiHelpers);

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

	await page.close();
});

async function setupAccountWithAddresses(apiHelpers: DataApiHelpers) {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Commerce Account ' + getRandomString(),
		type: 'business',
	});

	const billingAddress =
		await apiHelpers.headlessCommerceAdminAccount.postAddress(account.id, {
			defaultBilling: false,
			defaultShipping: false,
			name: 'Liferay Billing',
			type: ADDRESS_TYPES.BILLING,
		});

	apiHelpers.data.push({id: billingAddress.id, type: 'address'});

	const shippingAddress =
		await apiHelpers.headlessCommerceAdminAccount.postAddress(account.id, {
			defaultBilling: false,
			defaultShipping: false,
			name: "Liferay's Shipping",
			type: ADDRESS_TYPES.SHIPPING,
		});

	apiHelpers.data.push({id: shippingAddress.id, type: 'address'});

	const billingAndShippingAddress =
		await apiHelpers.headlessCommerceAdminAccount.postAddress(account.id, {
			defaultBilling: false,
			defaultShipping: false,
			name: 'Liferay Billing and Shipping',
			type: ADDRESS_TYPES.BILLING_AND_SHIPPING,
		});

	apiHelpers.data.push({
		id: billingAndShippingAddress.id,
		type: 'address',
	});

	return {
		account,
		billingAddress,
		billingAndShippingAddress,
		shippingAddress,
	};
}

async function setupBuyerUser(
	apiHelpers: DataApiHelpers,
	account: {id?: number},
	role: {externalReferenceCode?: string}
) {
	const buyerUser = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[buyerUser.alternateName] = {
		name: buyerUser.givenName,
		password: 'test',
		surname: buyerUser.familyName,
	};

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		[buyerUser.emailAddress]
	);

	const siteMemberRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		site.id,
		buyerUser.id
	);

	await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
		role.externalReferenceCode,
		buyerUser.id
	);

	return buyerUser;
}

test(
	'Buyer without VIEW_ADDRESSES cannot see the addresses list at checkout',
	{tag: ['@LPD-85008']},
	async ({apiHelpers, checkoutPage, commerceMiniCartPage, page}) => {
		const {account} = await setupAccountWithAddresses(apiHelpers);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'Test Buyer ' + getRandomString(),
			rolePermissions: CHECKOUT_BASELINE_PERMISSIONS(
				await page.evaluate(() => {
					return Liferay.ThemeDisplay.getCompanyId();
				})
			),
		});

		const buyerUser = await setupBuyerUser(apiHelpers, account, role);

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceMiniCartPage.quickAddToCart('MIN55861');
		await commerceMiniCartPage.submitButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-address'));

		await expect(checkoutPage.shippingAddressSelect).not.toBeVisible();
	}
);

test(
	'Buyer with VIEW_ADDRESSES sees the addresses list at checkout',
	{tag: ['@LPD-79025', '@LPD-85008']},
	async ({apiHelpers, checkoutPage, commerceMiniCartPage, page}) => {
		const {account, billingAndShippingAddress, shippingAddress} =
			await setupAccountWithAddresses(apiHelpers);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'Test Buyer ' + getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_ADDRESSES'],
					primaryKey: '0',
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 3,
				},
				...CHECKOUT_BASELINE_PERMISSIONS(
					await page.evaluate(() => {
						return Liferay.ThemeDisplay.getCompanyId();
					})
				),
			],
		});

		const buyerUser = await setupBuyerUser(apiHelpers, account, role);

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceMiniCartPage.quickAddToCart('MIN55861');
		await commerceMiniCartPage.submitButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-address'));

		await expect(checkoutPage.shippingAddressSelect).toBeVisible();

		const shippingOptions = (
			await checkoutPage.commerceAddressOptions.allTextContents()
		).map((s) => s.trim());

		expect(shippingOptions).toContain(shippingAddress.name);
		expect(shippingOptions).toContain(billingAndShippingAddress.name);
	}
);

test(
	'Buyer without MANAGE_ADDRESSES can only select existing shipping and billing addresses',
	{tag: ['@LPD-85008']},
	async ({apiHelpers, checkoutPage, commerceMiniCartPage, page}) => {
		const {account, billingAddress, shippingAddress} =
			await setupAccountWithAddresses(apiHelpers);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'Test Buyer ' + getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_ADDRESSES'],
					primaryKey: '0',
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 3,
				},
				...CHECKOUT_BASELINE_PERMISSIONS(
					await page.evaluate(() => {
						return Liferay.ThemeDisplay.getCompanyId();
					})
				),
			],
		});

		const buyerUser = await setupBuyerUser(apiHelpers, account, role);

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceMiniCartPage.quickAddToCart('MIN55861');
		await commerceMiniCartPage.submitButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-address'));

		const shippingOptions = (
			await checkoutPage.commerceAddressOptions.allTextContents()
		).map((s) => s.trim());

		expect(shippingOptions).toContain('Liferay Billing and Shipping');
		expect(shippingOptions).toContain(shippingAddress.name);

		await checkoutPage.commerceAddressSelect.selectOption({
			label: shippingAddress.name,
		});

		await expect(checkoutPage.useAsBillingCheckbox).not.toBeVisible();
		await expect(checkoutPage.nameInput).toBeDisabled();
		await expect(checkoutPage.addressInput).toBeDisabled();
		await expect(checkoutPage.zipInput).toBeDisabled();
		await expect(checkoutPage.cityInput).toBeDisabled();

		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-method'));

		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('billing-address'));

		const billingOptions = (
			await checkoutPage.commerceAddressOptions.allTextContents()
		).map((s) => s.trim());

		expect(billingOptions).toContain('Liferay Billing and Shipping');
		expect(billingOptions).toContain(billingAddress.name);

		await checkoutPage.commerceAddressSelect.selectOption({
			label: billingAddress.name,
		});

		await expect(checkoutPage.nameInput).toBeDisabled();
		await expect(checkoutPage.addressInput).toBeDisabled();
		await expect(checkoutPage.zipInput).toBeDisabled();
		await expect(checkoutPage.cityInput).toBeDisabled();
	}
);
