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
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
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
		paymentMethod: 'paypal',
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
