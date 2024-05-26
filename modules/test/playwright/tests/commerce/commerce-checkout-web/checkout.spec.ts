/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest(),
	notificationPagesTest
);

test('LPD-25860 Checkout widget configuration to display full addresses and phone number', async ({
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
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

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product'},
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

	await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
			cartItems: [
				{
					options: '[]',
					quantity: 1,
					replacedSkuId: 0,
					skuId: sku.id,
				},
			],
		},
		channel.id
	);

	await applicationsMenuPage.goToSite(site.name);
	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage(getRandomString());

	await page.goto(`/web/${site.name}`);

	await checkoutPage.addCheckoutWidget();

	const phoneNumber = '1234567890';
	const region = 'Florida';
	const zipCode = '33101';

	await checkoutPage.addressInput.fill('123 Main St');
	await checkoutPage.cityInput.fill('Miami');
	await checkoutPage.countryInput.selectOption({label: 'United States'});
	await checkoutPage.nameInput.fill('John Doe');
	await checkoutPage.phoneNumberInput.fill(phoneNumber);
	await checkoutPage.regionInput.selectOption({label: region});
	await checkoutPage.zipInput.fill(zipCode);

	await checkoutPage.continueButton.click();

	await expect(checkoutPage.billingAddress).not.toContainText(phoneNumber);
	await expect(checkoutPage.billingAddress).not.toContainText(region);
	await expect(checkoutPage.billingAddress).not.toContainText(zipCode);
	await expect(checkoutPage.shippingAddress).not.toContainText(phoneNumber);
	await expect(checkoutPage.shippingAddress).not.toContainText(region);
	await expect(checkoutPage.shippingAddress).not.toContainText(zipCode);

	await checkoutPage.optionsButton.click();
	await checkoutPage.configurationMenuItem.click();

	await checkoutPage.configurationIFrameShowFullAddressToggle.check();
	await checkoutPage.configurationIFrameShowPhoneNumberToggle.check();
	await checkoutPage.configurationIFrameSaveButton.click();
	await waitForSuccessAlert(checkoutPage.configurationIFrame);

	await page.reload();

	await expect(checkoutPage.billingAddress).toContainText(phoneNumber);
	await expect(checkoutPage.billingAddress).toContainText(region);
	await expect(checkoutPage.billingAddress).toContainText(zipCode);
	await expect(checkoutPage.shippingAddress).toContainText(phoneNumber);
	await expect(checkoutPage.shippingAddress).toContainText(region);
	await expect(checkoutPage.shippingAddress).toContainText(zipCode);
});
