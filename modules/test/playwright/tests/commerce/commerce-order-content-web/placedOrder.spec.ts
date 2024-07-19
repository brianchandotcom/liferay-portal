/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {performLogout} from '../../../utils/performLogin';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import {miniumSetUp} from '../utils/commerce';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPD-25831 Placed orders widget configuration to display full addresses and phone number', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	placedOrdersPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const phoneNumber = '12345';

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{phoneNumber, regionISOCode: 'AL'}
	);

	await apiHelpers.headlessCommerceAdminOrder.postOrder({
		accountId: account.id,
		billingAddressId: address.id,
		channelId: channel.id,
		orderItems: [
			{
				decimalQuantity: 10,
				quantity: 2,
				skuId: sku.id,
			},
		],
		orderStatus: '0',
		paymentStatus: '0',
		shippingAddressId: address.id,
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Placed Orders Page');

	await page.goto(`/web/${site.name}`);

	await placedOrdersPage.addPlacedOrdersWidget();

	await placedOrdersPage.viewButton.click();

	await expect(placedOrdersPage.billingAddress).not.toContainText(
		'United States'
	);
	await expect(placedOrdersPage.billingAddress).not.toContainText('Alabama');
	await expect(placedOrdersPage.billingAddress).not.toContainText(
		phoneNumber
	);
	await expect(placedOrdersPage.shippingAddress).not.toContainText(
		'United States'
	);
	await expect(placedOrdersPage.shippingAddress).not.toContainText('Alabama');
	await expect(placedOrdersPage.shippingAddress).not.toContainText(
		phoneNumber
	);

	await page.goto(`/web/${site.name}`);

	await placedOrdersPage.optionsButton.click();

	await placedOrdersPage.configurationMenuItem.click();
	await placedOrdersPage.configurationIFrameShowFullAddressToggle.check();
	await placedOrdersPage.configurationIFrameShowPhoneNumberToggle.check();
	await placedOrdersPage.configurationIFrameSaveButton.click();
	await waitForSuccessAlert(placedOrdersPage.configurationIFrame);
	await page.reload();

	await placedOrdersPage.viewButton.click();

	await expect(placedOrdersPage.billingAddress).toContainText(
		'United States'
	);
	await expect(placedOrdersPage.billingAddress).toContainText('Alabama');
	await expect(placedOrdersPage.billingAddress).toContainText(phoneNumber);
	await expect(placedOrdersPage.shippingAddress).toContainText(
		'United States'
	);
	await expect(placedOrdersPage.shippingAddress).toContainText('Alabama');
	await expect(placedOrdersPage.shippingAddress).toContainText(phoneNumber);
});

test('LPD-26643 Reorder from placed orders details page', async ({
	apiHelpers,
	checkoutPage,
	commerceAdminOrderDetailsPage,
	commerceMiniCartPage,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'admin',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const {channel, site} = await miniumSetUp(apiHelpers);

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['demo.unprivileged@liferay.com']
	);
	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);
	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const buyerAccountRole = rolesResponse?.items?.filter((role) => {
		return role.name === 'Buyer';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		buyerAccountRole[0].id,
		user.emailAddress
	);

	await apiHelpers.headlessCommerceAdminAccount.postAddress(account.id, {
		phoneNumber: '12345',
		regionISOCode: 'LA',
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.getProducts(
		new URLSearchParams({
			filter: `name eq 'U-Joint'`,
		})
	);

	const productId = product.items[0].productId;

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
			cartItems: [
				{
					quantity: 1,
					skuId: sku.id,
				},
			],
		},
		channel.id
	);

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink('demo.unprivileged')
	).click();

	await editUserPage.selectUserMembershipSite(site.name);

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await page.goto(`/web/${site.name}`);

	await commerceMiniCartPage.submitCart();

	await expect(page.getByText('U-joint')).toBeVisible();

	await checkoutPage.chooseShippingAddress({index: 1});

	await expect(page.getByText('Standard Delivery (+$ 15.00)')).toBeVisible();

	await checkoutPage.continueButton.click();

	await expect(page.getByText('U-joint')).toBeVisible();

	await checkoutPage.continueButton.click();

	await expect(checkoutPage.orderSuccessMessage).toBeVisible();

	await checkoutPage.goToOrderDetailsButton.click();

	await expect(page.getByText('U-joint')).toBeVisible();

	await commerceAdminOrderDetailsPage.reorder();

	await expect(page.getByText('U-joint')).toBeVisible();

	await checkoutPage.chooseShippingAddress({index: 1});

	await expect(page.getByText('Standard Delivery (+$ 15.00)')).toBeVisible();

	await checkoutPage.continueButton.click();

	await expect(page.getByText('U-joint')).toBeVisible();

	await checkoutPage.continueButton.click();

	await expect(checkoutPage.orderSuccessMessage).toBeVisible();
});
