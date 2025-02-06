/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {checkSameDate} from '../utils/date';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20379': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest(),
	pageEditorPagesTest
);

test('LPD-30855 Can map order item information', async ({
	apiHelpers,
	commerceAdminChannelsPage,
	commerceAdminProductPage,
	commerceLayoutsPage,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}) => {
	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: getRandomString(),
		siteGroupId: site.id,
	});

	await commerceAdminChannelsPage.changeCommerceChannelSiteType(
		channel.name,
		'B2B'
	);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'person',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	await apiHelpers.headlessCommerceAdminAccount.postAddress(account.id, {
		phoneNumber: '12345',
		regionISOCode: 'LA',
	});

	const option = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'color',
		'Color',
		1
	);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const linkedProduct =
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
		});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: getRandomString()},
		productOptions: [
			{
				fieldType: 'select',
				key: option.key,
				name: option.name,
				optionId: option.id,
				priceType: 'static',
				priority: 1,
				productOptionValues: [
					{
						deltaPrice: 10.0,
						key: 'black',
						name: {
							en_US: 'Black',
						},
						priority: 1,
						quantity: 1,
						skuId: linkedProduct.skus[0].id,
					},
				],
				skuContributor: true,
			},
		],
	});

	await commerceAdminProductPage.gotoProduct(product.name['en_US']);
	await commerceAdminProductPage.generateSkus();

	await expect(page.getByText('Showing 1 to 2 of 2 entries.')).toBeVisible();

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus.find((sku) => sku.sku === 'BLACK');

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Order',
		name: displayPageTemplateName,
	});
	await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

	await pageEditorPage.addFragment('Content Display', 'Collection Display');
	await pageEditorPage.selectFragment(
		await pageEditorPage.getFragmentId('Collection Display')
	);

	await page.getByText('No Collection Selected Yet').click();

	await pageEditorPage.chooseCollectionDisplayOption(
		'Related Items Collection Providers',
		'Order Items'
	);
	await pageEditorPage.waitForChangesSaved();
	await pageEditorPage.addFragment(
		'Basic Components',
		'Heading',
		page.locator('.page-editor__collection-item-old.empty').first()
	);

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
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

	await commerceLayoutsPage.selectDisplayPageTemplatePreviewItem(
		cart.id.toString()
	);

	const headingId = await pageEditorPage.getFragmentId('Heading');

	await pageEditorPage.selectEditable(headingId, 'element-text');

	await commerceLayoutsPage.labelField.selectOption('Order ID');

	await expect(
		commerceLayoutsPage.pageEditorText(cart.id.toString()).first()
	).toBeVisible();

	await commerceLayoutsPage.labelField.selectOption('Author Name');

	await expect(
		commerceLayoutsPage.pageEditorText(cart.author).first()
	).toBeVisible();

	await commerceLayoutsPage.labelField.selectOption('Create Date');

	await expect(page.getByLabel('Configuration Panel', { exact: true }).getByLabel('Mapping')).toContainText('Field Type: Date');

	const pageEditorCreateDate = await commerceLayoutsPage
		.pageEditorElement('h1')
		.first()
		.innerText();

	expect(checkSameDate(cart.createDate, pageEditorCreateDate)).toBe(
		true
	);

	await commerceLayoutsPage.labelField.selectOption('Modified Date');

	await expect(page.getByLabel('Configuration Panel', { exact: true }).getByLabel('Mapping')).toContainText('Field Type: Date');

	const pageEditorModifiedDate = await commerceLayoutsPage
		.pageEditorElement('h1')
		.first()
		.innerText();

	expect(checkSameDate(cart.modifiedDate, pageEditorModifiedDate)).toBe(
		true
	);

	await commerceLayoutsPage.labelField.selectOption('Order Item ID');

	await expect(
		commerceLayoutsPage
			.pageEditorText(cart.cartItems[0].id.toString())
			.first()
	).toBeVisible();

	await commerceLayoutsPage.labelField.selectOption('Unit Price');

	await expect(
		commerceLayoutsPage
			.pageEditorText(cart.cartItems[0].price.priceFormatted.toString())
			.first()
	).toBeVisible();

	if (cart.cartItems[0].price.promoPriceFormatted) {
		await commerceLayoutsPage.labelField.selectOption('Promo Price');

		await expect(
			commerceLayoutsPage
				.pageEditorText(
					cart.cartItems[0].price.promoPriceFormatted.toString()
				)
				.first()
		).toBeVisible();
	}

	if (cart.cartItems[0].price.discountFormatted) {
		await commerceLayoutsPage.labelField.selectOption('Discount');

		await expect(
			commerceLayoutsPage
				.pageEditorText(
					cart.cartItems[0].price.discountFormatted.toString()
				)
				.first()
		).toBeVisible();
	}

	await commerceLayoutsPage.labelField.selectOption('Total Price');

	await expect(
		commerceLayoutsPage
			.pageEditorText(
				cart.cartItems[0].price.finalPriceFormatted.toString()
			)
			.first()
	).toBeVisible();

	await commerceLayoutsPage.labelField.selectOption('Options');

	await expect(commerceLayoutsPage.pageEditorText('Black')).toBeVisible();

	await displayPageTemplatesPage.publishTemplate();

	await displayPageTemplatesPage.deleteTemplate(
		displayPageTemplateName
	);

	await expect(
		page.getByText(displayPageTemplateName, {exact: true})
	).not.toBeVisible();
});
