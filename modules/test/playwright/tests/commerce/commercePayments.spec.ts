/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	loginTest()
);

const data = [];

test.afterEach(async ({apiHelpers}) => {
	for await (const item of data.reverse()) {
		switch (item.type) {
			case 'account':
				await apiHelpers.headlessAdminUser.deleteAccount(item.id);

				break;
			case 'cart':
				await apiHelpers.headlessCommerceDeliveryCart.deleteCart(
					item.id
				);

				break;
			case 'catalog':
				await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
					item.id
				);

				break;
			case 'channel':
				await apiHelpers.headlessCommerceAdminChannel.deleteChannel(
					item.id
				);

				break;
			case 'payment':
				await apiHelpers.headlessCommerceAdminPaymentApiHelper.deletePayment(
					item.id
				);

				break;
			case 'product':
				await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
					item.id
				);

				break;
			case 'site':
				await apiHelpers.headlessSite.deleteSite(item.id);

				break;
			default:
				break;
		}
	}
});

test('can view payments list admin page', async ({
	apiHelpers,
	applicationsMenuPage,
	commercePaymentsPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'Payment account',
		type: 'person',
	});

	data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const site = await apiHelpers.headlessSite.createSite({
		name: 'Payment Site',
	});

	data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Payment Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Payment Catalog',
	});

	data.push({id: catalog.id, type: 'catalog'});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product'},
	});

	data.push({id: product.productId, type: 'product'});

	const sku = product.skus[0];

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
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

	data.push({id: cart.id, type: 'cart'});

	const payment =
		await apiHelpers.headlessCommerceAdminPaymentApiHelper.postPayment({
			amount: 10,
			relatedItemId: cart.id,
		});

	data.push({id: payment.id, type: 'payment'});

	await apiHelpers.headlessCommerceAdminPaymentApiHelper.patchPayment(
		{
			paymentStatus: 0,
			relatedItemId: payment.relatedItemId,
		},
		payment.id
	);

	await applicationsMenuPage.goToPayments();

	await expect(page.getByText(payment.id).first()).toBeVisible();

	await commercePaymentsPage.makeRefundButton.click();
	await commercePaymentsPage.reasonInput.selectOption('product-defect');
	await commercePaymentsPage.amountInput.fill('5');
	await commercePaymentsPage.saveButton.click();

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	const refundId = (
		await commercePaymentsPage.headerDetailsTitle.textContent()
	).trim();

	data.push({id: refundId, type: 'payment'});

	await commercePaymentsPage.amountInput.fill('0');
	await commercePaymentsPage.saveButton.click();

	await expect(
		page.getByText('Error:Please enter a valid amount.')
	).toBeVisible();

	await commercePaymentsPage.addCommentButton.click();

	const comment = 'Test comment';

	await commercePaymentsPage.commentInput.fill(comment);

	await commercePaymentsPage.commentSubmitButton.click();

	await expect(page.getByText(comment, {exact: true})).toBeVisible();
	await expect(page.getByText('#' + payment.id, {exact: true})).toBeVisible();
	await expect(page.getByText('#' + cart.id, {exact: true})).toBeVisible();

	await commercePaymentsPage.ercModalOpenerButton.click();

	if (await commercePaymentsPage.ercInput.isVisible()) {
		const erc = 'test-ERC';

		await commercePaymentsPage.ercInput.fill(erc);
		await commercePaymentsPage.ercSubmitButton.click();

		await expect(page.getByText(erc, {exact: true})).toBeVisible();
	}
	else {
		await page.reload();
	}

	await commercePaymentsPage.backLink.click();

	await expect(page.getByText(refundId).first()).toBeVisible();
	await expect(page.getByText('Refund', {exact: true})).toBeVisible();
});
