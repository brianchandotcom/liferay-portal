/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../../fixtures/accountsPagesTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest()
);

let catalog: {id: number};
let channel: {id: number; name: string};
let setupData: Array<{id: number | string; type: string}>;
let site: Site;

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const miniumResult = await miniumSetUp(apiHelpers);

	catalog = miniumResult.catalog;
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

test(
	'Can set default currency per channel',
	{tag: ['@COMMERCE-9922', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrencies.getByText(
					'Euro'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await expect(
			commerceChannelDefaultsPage.defaultCommerceCurrencies.getByText(
				'Euro'
			)
		).toBeVisible();
	}
);

test(
	'Can edit default currency',
	{tag: ['@COMMERCE-9934', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
					'Euro'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesActions.click();
		await commerceChannelDefaultsPage.editMenuItem.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Brazilian Real'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
					'Brazilian Real'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await expect(
			commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
				'Brazilian Real'
			)
		).toBeVisible();
	}
);

test(
	'Can delete default currency',
	{tag: ['@COMMERCE-9931', '@COMMERCE-9937', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
					'Euro'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		page.on('dialog', (dialog) => dialog.accept());

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesActions.click();
		await commerceChannelDefaultsPage.deleteMenuItem.click();

		await expect(
			commerceChannelDefaultsPage.defaultCommerceCurrenciesCell('Euro')
		).not.toBeVisible();

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint')
		).toBeVisible();
		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint').getByText('$')
		).toBeVisible();
		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint').getByText('€')
		).not.toBeVisible();
	}
);

test(
	'Can link all channels to a currency',
	{tag: ['@COMMERCE-9923', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);
		await apiHelpers.headlessCommerceAdminChannel.postChannel({
			name: 'Channel2',
			siteGroupId: site.id,
			type: 'site',
		});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
					'Euro'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				'Channel2'
			);
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage
					.defaultCommerceCurrenciesCell('Euro')
					.nth(1)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'British Pound'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage
					.defaultCommerceCurrenciesCell('All Other Channels')
					.first()
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});
	}
);

test(
	'Default currency rules can work on storefront',
	{tag: ['@COMMERCE-9925', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
	}) => {
		const {account: account1, buyerUser: buyer1} =
			await createAccountWithBuyerUser(apiHelpers, site.id);

		const {buyerUser: buyer2} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account1.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
				'Euro'
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceCurrenciesCell(
					'Euro'
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyer1.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint').getByText('€')
		).toBeVisible();

		await performUserSwitch(page, buyer2.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint').getByText('$')
		).toBeVisible();
		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint').getByText('€')
		).not.toBeVisible();
	}
);

test(
	'Can add default discount',
	{tag: ['@COMMERCE-9854', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({
					filter: `name eq 'U-Joint'`,
				})
			);

		const productId = products.items[0].productId;

		await apiHelpers.headlessCommerceAdminPricing.postDiscount({
			active: true,
			discountProducts: [{productId}],
			level: 'L1',
			percentageLevel1: 10,
			target: 'products',
			title: 'Discount 1 10% off',
			usePercentage: true,
		});

		const discount2 =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 20,
				target: 'products',
				title: 'Discount 2 20% off',
				usePercentage: true,
			});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceDiscountsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFrameDiscountSelect.selectOption(
				discount2.title
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceDiscountsCell(
					discount2.title
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await expect(
			commerceThemeMiniumCatalogPage.productCard('U-Joint')
		).toBeVisible();

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(productDetailsPage.priceContainer).toBeVisible();

		await expect(
			await productDetailsPage.priceField('$ 19.20')
		).toBeVisible();
	}
);

test(
	'Can select all other channels for default discount',
	{tag: ['@COMMERCE-9857', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({
					filter: `name eq 'U-Joint'`,
				})
			);

		const productId = products.items[0].productId;

		const discount =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 10,
				target: 'products',
				title: 'Discount 1 10% off',
				usePercentage: true,
			});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceDiscountsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameDiscountSelect.selectOption(
				discount.title
			);
			await commerceChannelDefaultsPage.editFrameOverrideCheckbox.check();
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceDiscountsCell(
					discount.title
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await expect(
			commerceChannelDefaultsPage
				.defaultCommerceDiscountsCell('All Channels')
				.first()
		).toBeVisible();

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField('$ 21.60')
		).toBeVisible();
	}
);

test(
	'Can update default discounts entry',
	{tag: ['@COMMERCE-9858', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({
					filter: `name eq 'U-Joint'`,
				})
			);

		const productId = products.items[0].productId;

		const discount1 =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 10,
				target: 'products',
				title: 'Discount 1 10% off',
				usePercentage: true,
			});
		const discount2 =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 20,
				target: 'products',
				title: 'Discount 2 20% off',
				usePercentage: true,
			});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceDiscountsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFrameDiscountSelect.selectOption(
				discount2.title
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceDiscountsCell(
					discount2.title
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField('$ 19.20')
		).toBeVisible();

		await performUserSwitch(page, 'test');

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceDiscountsActions.click();
		await commerceChannelDefaultsPage.editMenuItem.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameDiscountSelect.selectOption(
				discount1.title
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceDiscountsCell(
					discount1.title
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField('$ 21.60')
		).toBeVisible();
	}
);

test(
	'Default discount can override account eligibility',
	{tag: ['@COMMERCE-9856', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceAdminDiscountDetailsPage,
		commerceAdminDiscountsPage,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account: account1} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${getRandomString()}`,
			type: 'business',
		});

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({
					filter: `name eq 'U-Joint'`,
				})
			);

		const productId = products.items[0].productId;

		const discount1 =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 10,
				target: 'products',
				title: 'Discount 1 10% off',
				usePercentage: true,
			});

		await commerceAdminDiscountsPage.goto();

		await commerceAdminDiscountsPage.discountLink(discount1.title).click();

		await commerceAdminDiscountDetailsPage.eligibilityTab.click();
		await commerceAdminDiscountDetailsPage.specificAccountsRadio.click();
		await commerceAdminDiscountDetailsPage.addEligibilityEntry(
			'Find an Account',
			account1.name
		);

		const discount2 =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				discountProducts: [{productId}],
				level: 'L1',
				percentageLevel1: 20,
				target: 'products',
				title: 'Discount 2 20% off',
				usePercentage: true,
			});

		await commerceAdminDiscountsPage.goto();

		await commerceAdminDiscountsPage.discountLink(discount2.title).click();
		await commerceAdminDiscountDetailsPage.eligibilityTab.click();
		await commerceAdminDiscountDetailsPage.specificChannelsRadio.click();
		await commerceAdminDiscountDetailsPage.addEligibilityEntry(
			'Find a Channel',
			channel.name
		);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account2.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceDiscountsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameDiscountSelect.selectOption(
				discount1.title
			);
			await commerceChannelDefaultsPage.editFrameOverrideCheckbox.check();
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommerceDiscountsCell(
					discount1.title
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField('$ 21.60')
		).toBeVisible();
	}
);

test(
	'Can edit and delete default price list',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 1',
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 2',
				type: 'price-list',
			});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList1.name
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommercePriceListsCell(
					priceList1.name
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await commerceChannelDefaultsPage.defaultCommercePriceListsActions.click();
		await commerceChannelDefaultsPage.editMenuItem.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				'All Other Channels'
			);
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList2.name
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommercePriceListsCell(
					priceList2.name
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await expect(
			commerceChannelDefaultsPage
				.defaultCommercePriceListsCell('All Channels')
				.first()
		).toBeVisible();

		commerceChannelDefaultsPage.page.on('dialog', (dialog) =>
			dialog.accept()
		);

		await commerceChannelDefaultsPage.defaultCommercePriceListsActions.click();
		await commerceChannelDefaultsPage.deleteMenuItem.click();

		await expect(
			commerceChannelDefaultsPage.defaultCommercePriceListsCell(
				priceList2.name
			)
		).not.toBeVisible();
	}
);

test(
	'Can see highest priority price list price in hierarchy mode',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({filter: `name eq 'U-Joint'`})
			);

		const skuId = products.items[0].skus[0].id;

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 1',
				priority: 1,
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 2',
				priority: 2,
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 20,
			priceListId: priceList1.id,
			skuId,
		});
		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 18,
			priceListId: priceList2.id,
			skuId,
		});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList1.name
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommercePriceListsCell(
					priceList1.name
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField(
				'$ 20.00',
				productDetailsPage.priceContainer
			)
		).toBeVisible();

		await performUserSwitch(page, 'test');

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		commerceChannelDefaultsPage.page.on('dialog', (dialog) =>
			dialog.accept()
		);

		await commerceChannelDefaultsPage.defaultCommercePriceListsActions.click();
		await commerceChannelDefaultsPage.deleteMenuItem.click();

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField(
				'$ 18.00',
				productDetailsPage.priceContainer
			)
		).toBeVisible();
	}
);

test(
	'Can see lowest price list price in lowest mode',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commercePricingSystemSettingsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		await commercePricingSystemSettingsPage.setPriceListDiscoveryMethod(
			'lowest'
		);

		try {
			const {account, buyerUser} = await createAccountWithBuyerUser(
				apiHelpers,
				site.id
			);

			const products =
				await apiHelpers.headlessCommerceAdminCatalog.getProducts(
					new URLSearchParams({filter: `name eq 'U-Joint'`})
				);

			const skuId = products.items[0].skus[0].id;

			const priceList1 =
				await apiHelpers.headlessCommerceAdminPricing.postPriceList({
					catalogId: catalog.id,
					currencyCode: 'USD',
					name: 'Test Price List 1',
					type: 'price-list',
				});

			const priceList2 =
				await apiHelpers.headlessCommerceAdminPricing.postPriceList({
					catalogId: catalog.id,
					currencyCode: 'USD',
					name: 'Test Price List 2',
					type: 'price-list',
				});

			await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
				price: 20,
				priceListId: priceList1.id,
				skuId,
			});
			await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
				price: 18,
				priceListId: priceList2.id,
				skuId,
			});

			await accountsPage.goto();
			await accountsPage.accountNameLink(account.name).click();
			await editAccountPage.channelDefaultsLink.click();

			await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

			await expect(async () => {
				await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
					channel.name
				);
				await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
					priceList1.name
				);
				await commerceChannelDefaultsPage.editFrameSaveButton.click();

				await expect(
					commerceChannelDefaultsPage.defaultCommercePriceListsCell(
						priceList1.name
					)
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(`/web/${site.name}/catalog`);

			await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

			await expect(
				await productDetailsPage.priceField(
					'$ 18.00',
					productDetailsPage.priceContainer
				)
			).toBeVisible();
		}
		finally {
			await performUserSwitch(page, 'test');

			await commercePricingSystemSettingsPage.setPriceListDiscoveryMethod();
		}
	}
);

test(
	'Can select only all other channels for default price list',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		await apiHelpers.headlessCommerceAdminChannel.postChannel({
			name: 'Test Channel 1',
			siteGroupId: site.id,
			type: 'site',
		});
		await apiHelpers.headlessCommerceAdminChannel.postChannel({
			name: 'Test Channel 2',
			siteGroupId: site.id,
			type: 'site',
		});

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 1',
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 2',
				type: 'price-list',
			});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		for (const [channelName, priceListName] of [
			[channel.name, priceList1.name],
			['Test Channel 1', priceList2.name],
			['Test Channel 2', priceList1.name],
		]) {
			await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

			await expect(async () => {
				await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
					channelName
				);
				await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
					priceListName
				);
				await commerceChannelDefaultsPage.editFrameSaveButton.click();

				await expect(
					commerceChannelDefaultsPage.defaultCommercePriceListsCell(
						priceListName
					)
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});
		}

		await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList2.name
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage
					.defaultCommercePriceListsCell('All Other Channels')
					.first()
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});
	}
);

test(
	'Can set default price list per channel',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account: account1, buyerUser: buyer1} =
			await createAccountWithBuyerUser(apiHelpers, site.id);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({filter: `name eq 'U-Joint'`})
			);

		const skuId = products.items[0].skus[0].id;

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 1',
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 2',
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 20,
			priceListId: priceList1.id,
			skuId,
		});
		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 18,
			priceListId: priceList2.id,
			skuId,
		});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account1.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList1.name
			);
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommercePriceListsCell(
					priceList1.name
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyer1.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField(
				'$ 20.00',
				productDetailsPage.priceContainer
			)
		).toBeVisible();
	}
);

test(
	'Default price list can override other eligibilities',
	{tag: ['@COMMERCE-9704', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		commerceChannelDefaultsPage,
		commerceThemeMiniumCatalogPage,
		editAccountPage,
		page,
		productDetailsPage,
	}) => {
		const {account: account1, buyerUser: buyer1} =
			await createAccountWithBuyerUser(apiHelpers, site.id);

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProducts(
				new URLSearchParams({filter: `name eq 'U-Joint'`})
			);

		const skuId = products.items[0].skus[0].id;

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 1',
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: 'Test Price List 3',
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 20,
			priceListId: priceList1.id,
			skuId,
		});
		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 50,
			priceListId: priceList2.id,
			skuId,
		});

		await commerceAdminPriceListsPage.goto();
		await commerceAdminPriceListsPage
			.priceListLink(priceList1.name)
			.click();

		await commerceAdminPriceListDetailsPage.eligibilityTab.click();
		await commerceAdminPriceListDetailsPage.specificAccountsRadio.click();
		await commerceAdminPriceListDetailsPage.addEligibilityEntry(
			'Find an Account',
			account1.name
		);
		await commerceAdminPriceListDetailsPage.specificChannelsRadio.click();
		await commerceAdminPriceListDetailsPage.addEligibilityEntry(
			'Find a Channel',
			channel.name
		);

		await accountsPage.goto();
		await accountsPage.accountNameLink(account1.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommercePriceListsButton.click();

		await expect(async () => {
			await commerceChannelDefaultsPage.editFrameChannelSelect.selectOption(
				channel.name
			);
			await commerceChannelDefaultsPage.editFramePriceListSelect.selectOption(
				priceList2.name
			);
			await commerceChannelDefaultsPage.editFrameOverrideCheckbox.check();
			await commerceChannelDefaultsPage.editFrameSaveButton.click();

			await expect(
				commerceChannelDefaultsPage.defaultCommercePriceListsCell(
					priceList2.name
				)
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await performUserSwitch(page, buyer1.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.productLink('U-Joint').click();

		await expect(
			await productDetailsPage.priceField(
				'$ 50.00',
				productDetailsPage.priceContainer
			)
		).toBeVisible();
	}
);
