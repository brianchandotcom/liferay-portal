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
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

let catalog: {id: number; name: string};
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

test.afterEach(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const baselinePriceListIds = new Set(
		setupData
			.filter((entry) => entry.type === 'price-list')
			.map((entry) => entry.id)
	);

	const priceListsPage =
		await apiHelpers.headlessCommerceAdminPricing.getPriceLists();

	await Promise.all(
		(priceListsPage.items ?? [])
			.filter((p: {id: number}) => !baselinePriceListIds.has(p.id))
			.map((p: {id: number}) =>
				apiHelpers.headlessCommerceAdminPricing.deletePriceList(p.id)
			)
	);

	await page.close();
});

test(
	'Create a new price list via the admin UI',
	{tag: ['@LPD-89343']},
	async ({apiHelpers, commerceAdminPriceListsPage}) => {
		const priceListName = `Test Price List ${getRandomString()}`;

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.addPriceListButton.click();

		await expect(async () => {
			await commerceAdminPriceListsPage.addPriceListModalNameInput.fill(
				priceListName
			);

			await expect(
				commerceAdminPriceListsPage.addPriceListModalNameInput
			).toHaveValue(priceListName, {timeout: 1000});
		}).toPass({timeout: 10000});

		await commerceAdminPriceListsPage.addPriceListModalCatalogSelect.selectOption(
			{label: catalog.name}
		);
		await commerceAdminPriceListsPage.addPriceListModalCurrencySelect.selectOption(
			{label: 'USD'}
		);

		await commerceAdminPriceListsPage.addPriceListModalSubmitButton.click();

		await expect(async () => {
			const priceListsPage =
				await apiHelpers.headlessCommerceAdminPricing.getPriceLists({
					search: priceListName,
				});

			expect(
				priceListsPage.items?.find(
					(p: {name: string}) => p.name === priceListName
				)
			).toBeDefined();
		}).toPass({timeout: 10000});
	}
);

test(
	'Add Price List modal requires Name and Currency',
	{tag: ['@LPD-89343']},
	async ({commerceAdminPriceListsPage}) => {
		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.addPriceListButton.click();

		await test.step('Submitting with no Name surfaces a "Name is required" error', async () => {
			await commerceAdminPriceListsPage.addPriceListModalCatalogSelect.selectOption(
				{label: catalog.name}
			);
			await commerceAdminPriceListsPage.addPriceListModalCurrencySelect.selectOption(
				{label: 'USD'}
			);

			await commerceAdminPriceListsPage.addPriceListModalSubmitButton.click();

			await expect(
				commerceAdminPriceListsPage.requiredFieldError('Name')
			).toBeVisible();
		});

		await test.step('Filling Name but clearing Currency surfaces a "Currency is required" error', async () => {
			await commerceAdminPriceListsPage.addPriceListModalNameInput.fill(
				`Test Price List ${getRandomString()}`
			);
			await commerceAdminPriceListsPage.addPriceListModalCurrencySelect.selectOption(
				''
			);

			await commerceAdminPriceListsPage.addPriceListModalSubmitButton.click();

			await expect(
				commerceAdminPriceListsPage.requiredFieldError('Currency')
			).toBeVisible();
		});
	}
);

test(
	'Price entry can be edited via the side panel and deleted via row actions, with storefront price reflecting each change',
	{tag: ['@COMMERCE-6226', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		page,
		productDetailsPage,
	}) => {
		const priceListName = `Test Price List ${getRandomString()}`;
		const productName = `Test Product ${getRandomString()}`;
		const skuName = `TestSKU-${getRandomString()}`;
		const basePrice = 10;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {en_US: productName},
				skus: [
					{
						cost: 0,
						price: basePrice,
						published: true,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: priceListName,
				priority: 10,
				type: 'price-list',
			});

		const priceEntry =
			await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
				price: 100,
				priceListId: priceList.id,
				skuId: product.skus[0].id,
			});

		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await apiHelpers.headlessCommerceAdminPricing.postPriceListAccountRel(
			priceList.id,
			account.id
		);

		const verifyStorefrontPrice = async (expectedPrice: string) => {
			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(
				`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
			);

			await expect(
				await productDetailsPage.priceField(
					expectedPrice,
					productDetailsPage.priceContainer
				)
			).toBeVisible();
		};

		await test.step('Initial price entry: storefront shows $100', async () => {
			await verifyStorefrontPrice('$ 100.00');
		});

		await test.step('Edit the price via the side panel, storefront shows $80', async () => {
			await performLoginViaApi({page, screenName: 'test'});

			await commerceAdminPriceListsPage.goto();

			await commerceAdminPriceListsPage
				.priceListLink(priceListName)
				.click();

			await commerceAdminPriceListDetailsPage.entriesTab.click();

			await commerceAdminPriceListDetailsPage
				.skusTableRowLink(skuName)
				.click();

			await commerceAdminPriceListDetailsPage.sidePanelPriceInput.fill(
				'80'
			);
			await commerceAdminPriceListDetailsPage.sidePanelSaveButton.click();

			await expect(async () => {
				const updated =
					await apiHelpers.headlessCommerceAdminPricing.getPriceEntry(
						priceEntry.priceEntryId
					);

				expect(Number(updated.price)).toBe(80);
			}).toPass({timeout: 10000});

			await verifyStorefrontPrice('$ 80.00');
		});

		await test.step('Delete the entry, storefront falls back to base $10', async () => {
			await performLoginViaApi({page, screenName: 'test'});

			await commerceAdminPriceListsPage.goto();

			await commerceAdminPriceListsPage
				.priceListLink(priceListName)
				.click();

			await commerceAdminPriceListDetailsPage.entriesTab.click();

			await commerceAdminPriceListDetailsPage
				.skuRowActionsButton(skuName)
				.click();
			await commerceAdminPriceListDetailsPage.skuRowRemoveMenuItem.click();

			await expect(async () => {
				const entriesPage =
					await apiHelpers.headlessCommerceAdminPricing.getPriceListEntries(
						priceList.id
					);

				expect(entriesPage.totalCount ?? 0).toBe(0);
			}).toPass({timeout: 10000});

			await verifyStorefrontPrice(`$ ${basePrice}.00`);
		});
	}
);

test(
	'A Tier Price entry cannot be saved with 0 quantity',
	{tag: ['@COMMERCE-9767', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
	}) => {
		const priceListName = `Test Price List ${getRandomString()}`;
		const skuName = `TestSKU-${getRandomString()}`;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				skus: [
					{
						cost: 0,
						price: 10,
						published: true,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: priceListName,
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: 50,
			priceListId: priceList.id,
			skuId: product.skus[0].id,
		});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await commerceAdminPriceListDetailsPage.entriesTab.click();

		await commerceAdminPriceListDetailsPage
			.skusTableRowLink(skuName)
			.click();

		await commerceAdminPriceListDetailsPage.addTierPriceButton.click();

		await commerceAdminPriceListDetailsPage.addTierPriceEntryQuantity.fill(
			'0'
		);
		await commerceAdminPriceListDetailsPage.addTierPriceEntryPrice.fill(
			'0'
		);

		await commerceAdminPriceListDetailsPage.addTierPriceEntrySaveButton.click();

		await expect(
			commerceAdminPriceListDetailsPage.addTierPriceEntryQuantityNotAllowedError
		).toBeVisible();
		await expect(
			commerceAdminPriceListDetailsPage.addTierPriceEntrySaveButton
		).toBeVisible();
	}
);

test(
	'Cannot link the same eligibility entry twice to a price list',
	{tag: ['@COMMERCE-10053', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		page,
	}) => {
		const randomString = getRandomString().slice(0, 8);
		const accountName = `Test Account Run${randomString}`;
		const priceListName = `Test Price List Run${randomString}`;

		await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: 'business',
		});
		const orderType =
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: `Test Order Type Run${randomString}`},
			});
		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: priceListName,
			type: 'price-list',
		});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await commerceAdminPriceListDetailsPage.eligibilityTab.click();

		const addThenAssertDisabled = async (
			placeholder: string,
			searchTerm: string,
			entryName: string
		) => {
			await commerceAdminPriceListDetailsPage
				.eligibilityFindInput(placeholder)
				.click();
			await commerceAdminPriceListDetailsPage
				.eligibilityFindInput(placeholder)
				.fill(searchTerm);

			await expect(
				commerceAdminPriceListDetailsPage.eligibilityRowSelectButton(
					entryName
				)
			).toBeEnabled();

			await commerceAdminPriceListDetailsPage
				.eligibilityRowSelectButton(entryName)
				.click();

			await commerceAdminPriceListDetailsPage
				.eligibilityFindInput(placeholder)
				.click();
			await commerceAdminPriceListDetailsPage
				.eligibilityFindInput(placeholder)
				.fill(searchTerm);

			await expect(
				commerceAdminPriceListDetailsPage.eligibilityRowSelectButton(
					entryName
				)
			).toBeDisabled();

			await page.keyboard.press('Escape');
		};

		await test.step('Channel — Select is disabled after the entry is already added', async () => {
			await commerceAdminPriceListDetailsPage.specificChannelsRadio.check();
			await addThenAssertDisabled(
				'Find a Channel',
				channel.name,
				channel.name
			);
		});

		await test.step('Account — Select is disabled after the entry is already added', async () => {
			await commerceAdminPriceListDetailsPage.specificAccountsRadio.check();
			await addThenAssertDisabled(
				'Find an Account',
				`Run${randomString}`,
				accountName
			);
		});

		await test.step('Order Type — Select is disabled after the entry is already added', async () => {
			await commerceAdminPriceListDetailsPage.specificOrderTypesRadio.check();
			await addThenAssertDisabled(
				'Find an Order Type',
				`Run${randomString}`,
				orderType.name.en_US
			);
		});
	}
);

test(
	'Price list can be associated with both Specific Accounts and Specific Account Groups',
	{tag: ['@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		page,
	}) => {
		const randomString = getRandomString().slice(0, 8);
		const priceListName = `Test Price List Run${randomString}`;
		const accountNames = [
			`Test Account 1 Run${randomString}`,
			`Test Account 2 Run${randomString}`,
		];
		const accountGroupNames = [
			`Test Account Group 1 Run${randomString}`,
			`Test Account Group 2 Run${randomString}`,
		];

		for (const accountName of accountNames) {
			await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'business',
			});
		}

		for (const accountGroupName of accountGroupNames) {
			await apiHelpers.headlessAdminUser.postAccountGroup({
				name: accountGroupName,
			});
		}

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: priceListName,
			type: 'price-list',
		});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await commerceAdminPriceListDetailsPage.eligibilityTab.click();

		const addEntry = async (
			placeholder: string,
			searchTerm: string,
			entryName: string
		) => {
			await expect(async () => {
				await commerceAdminPriceListDetailsPage
					.eligibilityFindInput(placeholder)
					.click();
				await commerceAdminPriceListDetailsPage
					.eligibilityFindInput(placeholder)
					.fill(searchTerm);

				await expect(
					commerceAdminPriceListDetailsPage.eligibilityRowSelectButton(
						entryName
					)
				).toBeEnabled({
					timeout: 2000,
				});
			}).toPass({timeout: 30000});

			await commerceAdminPriceListDetailsPage
				.eligibilityRowSelectButton(entryName)
				.click();

			await page.keyboard.press('Escape');
		};

		await test.step('Add both accounts as Specific Accounts eligibility', async () => {
			await commerceAdminPriceListDetailsPage.specificAccountsRadio.check();

			for (const accountName of accountNames) {
				await addEntry(
					'Find an Account',
					`Run${randomString}`,
					accountName
				);
			}

			for (const accountName of accountNames) {
				await expect(
					commerceAdminPriceListDetailsPage.eligibilityEntryCell(
						accountName
					)
				).toBeVisible();
			}
		});

		await test.step('Switch to Specific Account Groups and add both account groups', async () => {
			await commerceAdminPriceListDetailsPage.specificAccountGroupsRadio.check();

			for (const accountGroupName of accountGroupNames) {
				await addEntry(
					'Find an Account Group',
					`Run${randomString}`,
					accountGroupName
				);
			}

			for (const accountGroupName of accountGroupNames) {
				await expect(
					commerceAdminPriceListDetailsPage.eligibilityEntryCell(
						accountGroupName
					)
				).toBeVisible();
			}
		});
	}
);

test(
	'Price list applies to all associated accounts and falls back to the base price for unassociated accounts',
	{tag: ['@LPD-89343']},
	async ({apiHelpers, page, productDetailsPage}) => {
		const skuName = `TestSKU-${getRandomString()}`;
		const productName = `Test Product ${getRandomString()}`;
		const basePrice = 50;
		const associatedPrice = 45;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {en_US: productName},
				skus: [
					{
						cost: 0,
						price: basePrice,
						published: true,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: `Test Price List ${getRandomString()}`,
				priority: 10,
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: associatedPrice,
			priceListId: priceList.id,
			skuId: product.skus[0].id,
		});

		const associated1 = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);
		const associated2 = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);
		const unassociated = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await apiHelpers.headlessCommerceAdminPricing.postPriceListAccountRel(
			priceList.id,
			associated1.account.id
		);
		await apiHelpers.headlessCommerceAdminPricing.postPriceListAccountRel(
			priceList.id,
			associated2.account.id
		);

		for (const {buyerUser} of [associated1, associated2]) {
			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(
				`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
			);

			await expect(
				await productDetailsPage.priceField(
					`$ ${associatedPrice}.00`,
					productDetailsPage.priceContainer
				)
			).toBeVisible();
		}

		await performUserSwitch(page, unassociated.buyerUser.alternateName);

		await page.goto(
			`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
		);

		await expect(
			await productDetailsPage.priceField(
				`$ ${basePrice}.00`,
				productDetailsPage.priceContainer
			)
		).toBeVisible();
	}
);

test(
	'Price list qualifier — each account sees the price from its eligible price list, and channel-restricted price lists do not leak to other channels',
	{tag: ['@COMMERCE-6069', '@LPD-89343']},
	async ({apiHelpers, page, productDetailsPage}) => {
		const skuName = `TestSKU-${getRandomString()}`;
		const productName = `Test Product ${getRandomString()}`;
		const basePrice = 30;
		const priceList1Price = 100;
		const priceList2Price = 200;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {en_US: productName},
				skus: [
					{
						cost: 0,
						price: basePrice,
						published: true,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		const otherChannel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: `Other Channel ${getRandomString()}`,
				siteGroupId: 0,
			});

		const priceList1 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: `Price List A ${getRandomString()}`,
				priority: 10,
				type: 'price-list',
			});
		const priceList2 =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: `Price List B ${getRandomString()}`,
				priority: 10,
				type: 'price-list',
			});

		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: priceList1Price,
			priceListId: priceList1.id,
			skuId: product.skus[0].id,
		});
		await apiHelpers.headlessCommerceAdminPricing.postPriceEntry({
			price: priceList2Price,
			priceListId: priceList2.id,
			skuId: product.skus[0].id,
		});

		const buyer1 = await createAccountWithBuyerUser(apiHelpers, site.id);
		const buyer2 = await createAccountWithBuyerUser(apiHelpers, site.id);

		await apiHelpers.headlessCommerceAdminPricing.postPriceListAccountRel(
			priceList1.id,
			buyer1.account.id
		);
		await apiHelpers.headlessCommerceAdminPricing.postPriceListAccountRel(
			priceList2.id,
			buyer2.account.id
		);
		await apiHelpers.headlessCommerceAdminPricing.postPriceListChannel(
			priceList1.id,
			channel.id
		);
		await apiHelpers.headlessCommerceAdminPricing.postPriceListChannel(
			priceList2.id,
			otherChannel.id
		);

		await performUserSwitch(page, buyer1.buyerUser.alternateName);

		await page.goto(
			`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
		);

		await expect(
			await productDetailsPage.priceField(
				`$ ${priceList1Price}.00`,
				productDetailsPage.priceContainer
			)
		).toBeVisible();

		await performUserSwitch(page, buyer2.buyerUser.alternateName);

		await page.goto(
			`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
		);

		await expect(
			await productDetailsPage.priceField(
				`$ ${basePrice}.00`,
				productDetailsPage.priceContainer
			)
		).toBeVisible();
	}
);

test(
	'Find a SKU finder on price list Entries only lists products from the same catalog',
	{tag: ['@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
	}) => {
		const priceListName = `Test Price List ${getRandomString()}`;
		const sameCatalogSku = `SameCatalog-${getRandomString()}`;
		const otherCatalogSku = `OtherCatalog-${getRandomString()}`;

		const otherCatalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
				name: `Other Catalog ${getRandomString()}`,
			});

		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			active: true,
			catalogId: catalog.id,
			name: {en_US: `Same Catalog Product ${getRandomString()}`},
			skus: [
				{
					cost: 0,
					price: 10,
					published: true,
					purchasable: true,
					sku: sameCatalogSku,
				},
			],
		});
		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			active: true,
			catalogId: otherCatalog.id,
			name: {en_US: `Other Catalog Product ${getRandomString()}`},
			skus: [
				{
					cost: 0,
					price: 10,
					published: true,
					purchasable: true,
					sku: otherCatalogSku,
				},
			],
		});

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: priceListName,
			type: 'price-list',
		});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await commerceAdminPriceListDetailsPage.entriesTab.click();

		await commerceAdminPriceListDetailsPage.findSkuInput.click();
		await commerceAdminPriceListDetailsPage.findSkuInput.fill('atalog');

		await expect(
			commerceAdminPriceListDetailsPage.eligibilityEntryCell(
				sameCatalogSku
			)
		).toBeVisible();
		await expect(
			commerceAdminPriceListDetailsPage.eligibilityEntryCell(
				otherCatalogSku
			)
		).toHaveCount(0);
	}
);

for (const variant of [
	{expectedPrice: '$ 60.00', modifierType: 'fixed-amount'},
	{expectedPrice: '$ 55.00', modifierType: 'percentage'},
	{expectedPrice: '$ 10.00', modifierType: 'replace'},
] as const) {
	test(
		`${variant.modifierType} price modifier is applied to the product on the storefront`,
		{tag: ['@COMMERCE-6225', '@LPD-89343']},
		async ({apiHelpers, page, productDetailsPage}) => {
			const skuName = `TestSKU-${getRandomString()}`;
			const productName = `Test Product ${getRandomString()}`;
			const basePrice = 50;

			const product =
				await apiHelpers.headlessCommerceAdminCatalog.postProduct({
					active: true,
					catalogId: catalog.id,
					name: {en_US: productName},
					skus: [
						{
							cost: 0,
							price: basePrice,
							published: true,
							purchasable: true,
							sku: skuName,
						},
					],
				});

			const priceList =
				await apiHelpers.headlessCommerceAdminPricing.postPriceList({
					catalogId: catalog.id,
					currencyCode: 'USD',
					name: `Test Price List ${getRandomString()}`,
					priority: 10,
					type: 'price-list',
				});

			const priceModifier =
				await apiHelpers.headlessCommerceAdminPricing.postPriceModifier(
					priceList.id,
					{
						active: true,
						modifierAmount: 10,
						modifierType: variant.modifierType,
						target: 'products',
						title: `Test Modifier ${getRandomString()}`,
					}
				);

			await apiHelpers.headlessCommerceAdminPricing.postPriceModifierProduct(
				priceModifier.id,
				product.productId
			);

			const {buyerUser} = await createAccountWithBuyerUser(
				apiHelpers,
				site.id
			);

			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(
				`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
			);

			await expect(
				await productDetailsPage.priceField(
					variant.expectedPrice,
					productDetailsPage.priceContainer
				)
			).toBeVisible();

			await performLoginViaApi({page, screenName: 'test'});

			await apiHelpers.headlessCommerceAdminPricing.deletePriceModifier(
				priceModifier.id
			);

			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(
				`/web${site.friendlyUrlPath}/p/${encodeURIComponent(productName)}`
			);

			await expect(
				await productDetailsPage.priceField(
					`$ ${basePrice}.00`,
					productDetailsPage.priceContainer
				)
			).toBeVisible();
		}
	);
}

test(
	'A draft product SKU can be added to a price list via the Entries finder',
	{tag: ['@COMMERCE-9409', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
	}) => {
		const skuName = `DraftSKU-${getRandomString()}`;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {en_US: `Draft Product ${getRandomString()}`},
				skus: [
					{
						cost: 0,
						price: 10,
						published: false,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		const draftSkuId = product.skus[0].id;

		const basePriceListsResponse =
			await apiHelpers.headlessCommerceAdminPricing.getBasePriceList(
				catalog.id
			);
		const basePriceListName = basePriceListsResponse.items[0].name;

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage
			.priceListLink(basePriceListName)
			.click();

		await commerceAdminPriceListDetailsPage.entriesTab.click();

		await commerceAdminPriceListDetailsPage.findSkuInput.click();
		await commerceAdminPriceListDetailsPage.findSkuInput.fill(skuName);

		await expect(
			commerceAdminPriceListDetailsPage.eligibilityEntryCell(skuName)
		).toBeVisible();

		await commerceAdminPriceListDetailsPage.selectButton.click();

		await expect(async () => {
			const entriesResponse =
				await apiHelpers.headlessCommerceAdminPricing.getPriceListEntries(
					basePriceListsResponse.items[0].id
				);

			const skuIds = (entriesResponse.items ?? []).map(
				(entry: {skuId: number}) => entry.skuId
			);

			expect(skuIds).toContain(draftSkuId);
		}).toPass({timeout: 10000});
	}
);

test(
	'A created price list cannot be moved to a different catalog (Catalog field is disabled)',
	{tag: ['@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
	}) => {
		const priceListName = `Test Price List ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: priceListName,
			type: 'price-list',
		});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await expect(
			commerceAdminPriceListDetailsPage.catalogSelect
		).toBeDisabled();
	}
);

test(
	'Base price lists cannot be deleted, but new price lists can',
	{tag: ['@COMMERCE-7597', '@LPD-89343']},
	async ({apiHelpers, commerceAdminPriceListsPage}) => {
		const basePriceListsResponse =
			await apiHelpers.headlessCommerceAdminPricing.getBasePriceList(
				catalog.id
			);
		const basePriceListName = basePriceListsResponse.items[0].name;

		const newPriceListName = `Test Price List ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: newPriceListName,
			type: 'price-list',
		});

		await commerceAdminPriceListsPage.goto();

		await test.step('Base price list row has no Delete action', async () => {
			await (
				await commerceAdminPriceListsPage.priceListRowActions(
					basePriceListName
				)
			).click();

			await expect(
				commerceAdminPriceListsPage.deleteMenuItem
			).toHaveCount(0);

			await commerceAdminPriceListsPage.page.keyboard.press('Escape');
		});

		await test.step('Newly created price list row has a Delete action', async () => {
			await (
				await commerceAdminPriceListsPage.priceListRowActions(
					newPriceListName
				)
			).click();

			await expect(
				commerceAdminPriceListsPage.deleteMenuItem
			).toBeVisible();
		});
	}
);

test(
	'Base promotions cannot be deleted, but new promotions can',
	{tag: ['@COMMERCE-7597', '@LPD-89343']},
	async ({apiHelpers, commerceAdminPromotionsPage}) => {
		const basePromotionsResponse =
			await apiHelpers.headlessCommerceAdminPricing.getBasePromoPriceList(
				catalog.id
			);
		const basePromotionName = basePromotionsResponse.items[0].name;

		const newPromotionName = `Test Promotion ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog.id,
			currencyCode: 'USD',
			name: newPromotionName,
			type: 'promotion',
		});

		await commerceAdminPromotionsPage.goto();

		await test.step('Base promotion row has no Delete action', async () => {
			await (
				await commerceAdminPromotionsPage.promotionRowActions(
					basePromotionName
				)
			).click();

			await expect(
				commerceAdminPromotionsPage.deleteMenuItem
			).toHaveCount(0);

			await commerceAdminPromotionsPage.page.keyboard.press('Escape');
		});

		await test.step('Newly created promotion row has a Delete action', async () => {
			await (
				await commerceAdminPromotionsPage.promotionRowActions(
					newPromotionName
				)
			).click();

			await expect(
				commerceAdminPromotionsPage.deleteMenuItem
			).toBeVisible();
		});
	}
);

test(
	'A user with the Discount Manager role can access the Price Lists panel and sees both base price lists',
	{tag: ['@COMMERCE-9405', '@LPD-89343']},
	async ({apiHelpers, commerceAdminPriceListsPage, page}) => {
		const screenName = `discount-manager-${getRandomString().slice(0, 8)}`;

		const discountManagerUser =
			await apiHelpers.headlessAdminUser.postUserAccount({
				alternateName: screenName,
				emailAddress: `${screenName}@liferay.com`,
				familyName: 'Manager',
				givenName: 'Discount',
			});

		const discountManagerRole =
			await apiHelpers.headlessAdminUser.getRoleByName(
				'Discount Manager'
			);

		await apiHelpers.headlessAdminUser.assignUserToRole(
			discountManagerRole.externalReferenceCode,
			discountManagerUser.id
		);

		userData[screenName] = {
			name: 'Discount',
			password: 'test',
			surname: 'Manager',
		};

		await performUserSwitch(page, screenName);

		await commerceAdminPriceListsPage.goto();

		const basePriceListsResponse =
			await apiHelpers.headlessCommerceAdminPricing.getBasePriceList(
				catalog.id
			);

		await expect(
			commerceAdminPriceListsPage.priceListLink(
				basePriceListsResponse.items[0].name
			)
		).toBeVisible();
		await expect(
			commerceAdminPriceListsPage.priceListLink('Master Base Price List')
		).toBeVisible();

		await expect(
			commerceAdminPriceListsPage.sidebarMenuItem('Promotions')
		).toBeVisible();

		for (const restrictedPortlet of [
			'Catalogs',
			'Channels',
			'Inventory',
			'Orders',
			'Products',
			'Shipments',
		]) {
			await expect(
				commerceAdminPriceListsPage.sidebarMenuItem(restrictedPortlet)
			).toHaveCount(0);
		}
	}
);

test(
	'Sort By Price High to Low orders products by their price-list-effective price on the storefront',
	{tag: ['@COMMERCE-12371', '@LPD-89343']},
	async ({apiHelpers, commerceThemeMiniumCatalogPage, page}) => {
		const productName = `Test Product ${getRandomString()}`;

		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			active: true,
			catalogId: catalog.id,
			name: {en_US: productName},
			skus: [
				{
					cost: 0,
					price: 9999,
					published: true,
					purchasable: true,
					sku: `SKU-${getRandomString()}`,
				},
			],
		});

		await page.goto(`/web${site.friendlyUrlPath}/catalog`);

		await commerceThemeMiniumCatalogPage.orderByButton.click();
		await commerceThemeMiniumCatalogPage.selectSorting('Price High to Low');

		await expect(async () => {
			await expect(
				await commerceThemeMiniumCatalogPage.firstCardItem.innerText()
			).toContain(productName);
		}).toPass({timeout: 30000});
	}
);

test(
	'Item finder on price list Entries shows all UOMs created for a SKU',
	{tag: ['@COMMERCE-12301', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
	}) => {
		const skuName = `TestSKU-${getRandomString()}`;
		const literName = `Liter-${getRandomString()}`;
		const gallonName = `Gallon-${getRandomString()}`;

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId: catalog.id,
				name: {en_US: `Test Product ${getRandomString()}`},
				skus: [
					{
						cost: 0,
						price: 5,
						published: true,
						purchasable: true,
						sku: skuName,
					},
				],
			});

		await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
			product.skus[0].id,
			{
				basePrice: 10,
				key: literName,
				name: {en_US: literName},
			}
		);

		await apiHelpers.headlessCommerceAdminCatalog.postSkuUnitOfMeasure(
			product.skus[0].id,
			{
				basePrice: 20,
				key: gallonName,
				name: {en_US: gallonName},
			}
		);

		const basePriceListsResponse =
			await apiHelpers.headlessCommerceAdminPricing.getBasePriceList(
				catalog.id
			);

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage
			.priceListLink(basePriceListsResponse.items[0].name)
			.click();

		await commerceAdminPriceListDetailsPage.entriesTab.click();

		await commerceAdminPriceListDetailsPage.findSkuInput.click();
		await commerceAdminPriceListDetailsPage.findSkuInput.fill(skuName);

		await expect(
			commerceAdminPriceListDetailsPage.eligibilityEntryCell(literName)
		).toBeVisible();
		await expect(
			commerceAdminPriceListDetailsPage.eligibilityEntryCell(gallonName)
		).toBeVisible();
	}
);

test(
	'Catalog Base Price List autocomplete only lists price lists from that catalog',
	{tag: ['@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminCatalogDetailsPage,
		commerceAdminCatalogsPage,
	}) => {
		const catalog1Name = `Catalog 1 ${getRandomString()}`;
		const catalog2Name = `Catalog 2 ${getRandomString()}`;
		const priceListName = `PL ${getRandomString()}`;

		const catalog1 =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
				name: catalog1Name,
			});

		await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
			name: catalog2Name,
		});

		await apiHelpers.headlessCommerceAdminPricing.postPriceList({
			catalogId: catalog1.id,
			currencyCode: 'USD',
			name: priceListName,
			type: 'price-list',
		});

		await commerceAdminCatalogsPage.goto();

		await commerceAdminCatalogsPage.catalogLink(catalog1Name).click();

		await commerceAdminCatalogDetailsPage.basePriceListAutocomplete.click();
		await commerceAdminCatalogDetailsPage.basePriceListAutocomplete.fill(
			priceListName
		);

		await expect(
			commerceAdminCatalogDetailsPage.basePriceListDropdownItem(
				priceListName
			)
		).toBeVisible();

		await commerceAdminCatalogsPage.goto();

		await commerceAdminCatalogsPage.catalogLink(catalog2Name).click();

		await commerceAdminCatalogDetailsPage.basePriceListAutocomplete.click();
		await commerceAdminCatalogDetailsPage.basePriceListAutocomplete.fill(
			priceListName
		);

		await expect(
			commerceAdminCatalogDetailsPage.basePriceListDropdownItem(
				priceListName
			)
		).toHaveCount(0);
	}
);

test(
	'Product Group details page lists the discounts and price lists that reference it',
	{tag: ['@COMMERCE-6236', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminProductGroupDetailsPage,
		commerceAdminProductGroupsPage,
	}) => {
		const discountTitle = `Test Discount ${getRandomString()}`;
		const priceListName = `Test Price List ${getRandomString()}`;
		const productGroupTitle = `Test Group ${getRandomString()}`;

		const productGroup =
			await apiHelpers.headlessCommerceAdminCatalog.postProductGroup({
				title: productGroupTitle,
			});

		const discount =
			await apiHelpers.headlessCommerceAdminPricing.postDiscount({
				active: true,
				level: 'L1',
				percentageLevel1: 20,
				target: 'product-groups',
				title: discountTitle,
				usePercentage: true,
			});

		await apiHelpers.headlessCommerceAdminPricing.postDiscountProductGroup(
			discount.id,
			productGroup.id
		);

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: priceListName,
				type: 'price-list',
			});

		const priceModifier =
			await apiHelpers.headlessCommerceAdminPricing.postPriceModifier(
				priceList.id,
				{
					active: true,
					modifierAmount: 10,
					modifierType: 'percentage',
					target: 'product-groups',
					title: `Test Modifier ${getRandomString()}`,
				}
			);

		await apiHelpers.headlessCommerceAdminPricing.postPriceModifierProductGroup(
			priceModifier.id,
			productGroup.id
		);

		await commerceAdminProductGroupsPage.goto();

		await commerceAdminProductGroupsPage
			.productGroupLink(productGroupTitle)
			.click();

		await commerceAdminProductGroupDetailsPage.discountsTab.click();

		await expect(
			commerceAdminProductGroupDetailsPage.entryCell(discountTitle)
		).toBeVisible();

		await commerceAdminProductGroupDetailsPage.priceListsTab.click();

		await expect(
			commerceAdminProductGroupDetailsPage.entryCell(priceListName)
		).toBeVisible();
	}
);

test(
	'A price modifier can be added and deleted from a price list via the admin UI',
	{tag: ['@COMMERCE-6225', '@LPD-89343']},
	async ({
		apiHelpers,
		commerceAdminPriceListDetailsPage,
		commerceAdminPriceListsPage,
		page,
	}) => {
		const modifierName = `Test Modifier ${getRandomString()}`;
		const priceListName = `Test Price List ${getRandomString()}`;

		page.on('dialog', (dialog) => dialog.accept());

		const priceList =
			await apiHelpers.headlessCommerceAdminPricing.postPriceList({
				catalogId: catalog.id,
				currencyCode: 'USD',
				name: priceListName,
				type: 'price-list',
			});

		await commerceAdminPriceListsPage.goto();

		await commerceAdminPriceListsPage.priceListLink(priceListName).click();

		await commerceAdminPriceListDetailsPage.priceModifiersTab.click();

		await commerceAdminPriceListDetailsPage.addPriceModifierButton.click();

		await commerceAdminPriceListDetailsPage.addPriceModifierName.fill(
			modifierName
		);
		await commerceAdminPriceListDetailsPage.addPriceModifierTarget.selectOption(
			'products'
		);
		await commerceAdminPriceListDetailsPage.addPriceModifierType.selectOption(
			'fixed-amount'
		);
		await commerceAdminPriceListDetailsPage.addPriceModifierSaveButton.click();

		await expect(async () => {
			const modifiers =
				await apiHelpers.headlessCommerceAdminPricing.getPriceListModifiers(
					priceList.id
				);

			expect(
				(modifiers.items ?? []).map(
					(modifier: {title: string}) => modifier.title
				)
			).toContain(modifierName);
		}).toPass({timeout: 10000});

		await expect(async () => {
			await commerceAdminPriceListDetailsPage
				.priceModifierRowActions(modifierName)
				.click();

			await expect(
				commerceAdminPriceListDetailsPage.priceModifierRowDeleteMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await commerceAdminPriceListDetailsPage.priceModifierRowDeleteMenuItem.click();

		await expect(async () => {
			const modifiers =
				await apiHelpers.headlessCommerceAdminPricing.getPriceListModifiers(
					priceList.id
				);

			expect(modifiers.totalCount ?? 0).toBe(0);
		}).toPass({timeout: 10000});
	}
);
