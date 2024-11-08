/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../fixtures/accountsPagesTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';
import {getDateFormatted, setFutureDate} from '../utils/date';

export const test = mergeTests(
	applicationsMenuPageTest,
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20379': true,
		'LPS-178052': true,
	}),
	loginTest(),
	notificationPagesTest,
	systemSettingsPageTest
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

	await expect(checkoutPage.commerceBillingAddress).not.toContainText(
		phoneNumber
	);
	await expect(checkoutPage.commerceBillingAddress).not.toContainText(region);
	await expect(checkoutPage.commerceBillingAddress).not.toContainText(
		zipCode
	);
	await expect(checkoutPage.commerceShippingAddress).not.toContainText(
		phoneNumber
	);
	await expect(checkoutPage.commerceShippingAddress).not.toContainText(
		region
	);
	await expect(checkoutPage.commerceShippingAddress).not.toContainText(
		zipCode
	);

	await checkoutPage.optionsButton.click();
	await checkoutPage.configurationMenuItem.click();

	await checkoutPage.configurationIFrameShowFullAddressToggle.check();
	await checkoutPage.configurationIFrameShowPhoneNumberToggle.check();
	await checkoutPage.configurationIFrameSaveButton.click();
	await waitForAlert(checkoutPage.configurationIFrame);

	await page.reload();

	await expect(checkoutPage.commerceBillingAddress).toContainText(
		phoneNumber
	);
	await expect(checkoutPage.commerceBillingAddress).toContainText(region);
	await expect(checkoutPage.commerceBillingAddress).toContainText(zipCode);
	await expect(checkoutPage.commerceShippingAddress).toContainText(
		phoneNumber
	);
	await expect(checkoutPage.commerceShippingAddress).toContainText(region);
	await expect(checkoutPage.commerceShippingAddress).toContainText(zipCode);
});

test('LPP-55128 Payment Term is reset correctly', async ({
	accountsPage,
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	commerceLayoutsPage,
	editAccountChannelDefaultsPage,
	editAccountPage,
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

	const paymentTerm1 = await apiHelpers.headlessCommerceAdminOrder.postTerm({
		label: {
			en_US: 'MoneyA',
		},
		name: 'moneya',
		priority: 0,
		type: 'payment-terms',
	});
	const paymentTerm2 = await apiHelpers.headlessCommerceAdminOrder.postTerm({
		label: {
			en_US: 'MoneyB',
		},
		name: 'moneyb',
		priority: 1,
		type: 'payment-terms',
	});
	const paymentTerm3 = await apiHelpers.headlessCommerceAdminOrder.postTerm({
		label: {
			en_US: 'PayPalA',
		},
		name: 'paypala',
		priority: 2,
		type: 'payment-terms',
	});
	const paymentTerm4 = await apiHelpers.headlessCommerceAdminOrder.postTerm({
		label: {
			en_US: 'MoneyC',
		},
		name: 'moneyc',
		priority: 3,
		type: 'payment-terms',
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

	const basePriceListId =
		await apiHelpers.headlessCommerceAdminPricing.getBasePriceListId(
			catalog.id
		);

	await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
		price: 100,
		priceListId: basePriceListId.items[0].id,
		skuId: sku.id,
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
					skuId: sku.id,
				},
			],
		},
		channel.id
	);

	await accountsPage.goto();
	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.channelDefaultsLink.click();
	await editAccountChannelDefaultsPage.addDefaultPaymentTerm(paymentTerm2.id);

	await commerceAdminChannelsPage.changeCommerceChannelSiteType(
		channel.name,
		'B2B'
	);
	await commerceAdminChannelDetailsPage.activateChannelConfiguration(
		'Money Order',
		'Payment Methods'
	);
	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'Money Order'
		)
	).click();
	await commerceAdminChannelDetailsPage.setEntryEligibility(
		'Specific Payment Terms',
		paymentTerm1.name,
		'Payment Methods'
	);
	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'Money Order'
		)
	).click();
	await commerceAdminChannelDetailsPage.setEntryEligibility(
		'Specific Payment Terms',
		paymentTerm2.name,
		'Payment Methods'
	);
	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'Money Order'
		)
	).click();
	await commerceAdminChannelDetailsPage.setEntryEligibility(
		'Specific Payment Terms',
		paymentTerm4.name,
		'Payment Methods'
	);

	await commerceAdminChannelDetailsPage.activateChannelConfiguration(
		'PayPal',
		'Payment Methods'
	);
	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'PayPal'
		)
	).click();
	await commerceAdminChannelDetailsPage.setEntryEligibility(
		'Specific Payment Terms',
		paymentTerm3.name,
		'Payment Methods'
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

	expect(page.getByLabel('Money Order')).toBeChecked();
	await checkoutPage.continueButton.click();
	await page.waitForURL((url) => url.href.includes('payment-terms'));
	expect(page.getByLabel('MoneyB')).toBeChecked();
	await checkoutPage.continueButton.click();
	await page.waitForURL((url) => url.href.includes('order-summary'));
	await checkoutPage.previousButton.click();
	await page.waitForURL((url) => url.href.includes('payment-terms'));
	await checkoutPage.previousButton.click();
	await page.waitForURL((url) => url.href.includes('payment-method'));
	await page.getByLabel('PayPal').check();
	await checkoutPage.continueButton.click();
	await page.waitForURL((url) => url.href.includes('order-summary'));
	await checkoutPage.previousButton.click();
	await page.waitForURL((url) => url.href.includes('payment-method'));
	await page.getByLabel('Money Order').check();
	await checkoutPage.continueButton.click();
	await page.waitForURL((url) => url.href.includes('payment-terms'));
	expect(page.getByLabel('MoneyB')).toBeChecked();
});

test('LPD-35329 Delivery group multishipping checkout summary', async ({
	apiHelpers,
	checkoutPage,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	page,
}) => {
	test.setTimeout(180000);

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	await commerceAdminChannelsPage.changeCommerceChannelSiteType(
		channel.name,
		'B2B'
	);

	await waitForAlert(page);

	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'Flat Rate'
		)
	).click();
	await commerceAdminChannelDetailsPage.activateChannelConfiguration(
		'Flat Rate',
		'Shipping Methods'
	);
	await commerceAdminChannelDetailsPage.addFlatRateShippingOption(
		getRandomString()
	);
	await commerceAdminChannelDetailsPage.addFlatRateShippingOption(
		getRandomString()
	);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: getRandomString(),
	});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: getRandomString()},
		shippingConfiguration: {
			freeShipping: false,
			shippable: true,
			shippingSeparately: false,
		},
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{phoneNumber: '1234567890', regionISOCode: 'AL'}
	);

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
			cartItems: [
				{
					deliveryGroupName: getRandomString(),
					quantity: 1,
					requestedDeliveryDate: setFutureDate(7),
					shippingAddressId: address.id,
					skuId: sku.id,
				},
			],
		},
		channel.id
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentTotalPortlet',
			}),
			getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	const cartItems = await apiHelpers.headlessCommerceDeliveryCart
		.getCartItems(cart.id)
		.then((response) => {
			return response.items;
		});

	const cartItem = cartItems[0];

	await page.goto(
		`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	const locale = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getBCP47LanguageId();
	});

	await expect(
		(await checkoutPage.tableRow(0, cartItem.deliveryGroupName, true)).row
	).toBeVisible();
	await expect(
		(
			await checkoutPage.tableRow(
				1,
				address.street1 + ', ' + address.city + ', ' + 'United States',
				true
			)
		).row
	).toBeVisible();
	await expect(
		(
			await checkoutPage.tableRow(
				2,
				getDateFormatted(cartItem.requestedDeliveryDate, locale),
				true
			)
		).row
	).toBeVisible();

	await checkoutPage.viewDeliveryGroupTableButton.click();

	await expect(
		checkoutPage.headingDeliveryGroupModal(cartItem.deliveryGroup)
	).toBeVisible();
	await expect(
		checkoutPage.assertDataDeliveryGroupModal(address.street1)
	).toBeVisible();
	await expect(
		checkoutPage.assertDataDeliveryGroupModal(address.street2)
	).toBeVisible();
	await expect(
		checkoutPage.assertDataDeliveryGroupModal(address.street3)
	).toBeVisible();
	await expect(
		checkoutPage.assertDataDeliveryGroupModal(
			address.city + ' , ' + 'Alabama'
		)
	).toBeVisible();
	await expect(
		checkoutPage.assertDataDeliveryGroupModal(
			address.zip + ' , ' + 'United States'
		)
	).toBeVisible();
	await expect(
		checkoutPage.configurationIFrame.getByText(
			getDateFormatted(cartItem.requestedDeliveryDate, locale)
		)
	).toBeVisible();

	await checkoutPage.iframeOkButton.click();
	await checkoutPage.continueButton.click();
	await checkoutPage.continueButton.click();

	await expect(checkoutPage.orderItemsTabLink).toBeVisible();
	await expect(checkoutPage.multishippingTabLink).toBeVisible();
	await checkoutPage.orderItemsTabLink.click();
	await expect(checkoutPage.orderItemsTableLocator).toBeVisible();
	await checkoutPage.multishippingTabLink.click();
	await expect(checkoutPage.multishippingTableLocator).toBeVisible();
	await expect(page.getByText('Shipping Address & Date')).toBeVisible();
	await expect(page.getByText('Billing Address')).toBeVisible();
	await expect(checkoutPage.orderSummaryShippingMethod).toBeVisible();
});

test('LPD-40425 Checkout order detail redirect works correctly when order DPT is enabled', async ({
	apiHelpers,
	applicationsMenuPage,
	checkoutPage,
	commerceLayoutsPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToDisplayPageTemplates();
	await commerceLayoutsPage.createDisplayPageTemplate(
		getRandomString(),
		'Order',
		site.name
	);
	await commerceLayoutsPage.addFragment('Heading');

	await expect(page.getByText('Heading Example')).toBeVisible();

	await commerceLayoutsPage.publishButton.click();

	await waitForAlert(
		page,
		'The display page template was published successfully.'
	);

	await commerceLayoutsPage.moreActionsButton.click();
	await commerceLayoutsPage.markAsDefaultMenuItem.click();

	await waitForAlert(page);

	await expect(
		commerceLayoutsPage.defaultDisplayPageTemplateIcon
	).toBeVisible();

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
	});

	const sku = product.skus[0];

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

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage(getRandomString());

	await page.goto(`/web/${site.name}`);

	await checkoutPage.addCheckoutWidget();
	await checkoutPage.performCheckout({
		shippingAddress: {
			city: 'testCity',
			countryLabel: 'United States',
			name: 'John Doe',
			regionLabel: 'Florida',
			street: 'testStreet',
			zip: '12345',
		},
	});
	await checkoutPage.goToOrderDetailsButton.click();

	await expect(page.getByText('Heading Example')).toBeVisible();
});
