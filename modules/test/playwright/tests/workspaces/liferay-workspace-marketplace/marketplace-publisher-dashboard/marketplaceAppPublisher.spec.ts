/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import {marketplacePagesTest} from '../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../fixtures/marketplaceSite';
import {createMarketplaceAccountUserCatalog} from '../helpers/marketplaceApiHelpers';
import {PublishProductPayload} from '../types';
import {products} from '../utils/constants';

export const test = mergeTests(
	dataApiHelpersTest,
	marketplaceSiteFixture,
	marketplacePagesTest
);

const ACCOUNT_NAME = {
	PERSON: `Person Account${getRandomInt()}`,
	SUPPLIER: `Supplier Account${getRandomInt()}`,
};

test.describe('Can Publish Marketplace Apps', () => {
	let _account;
	let _catalog;
	let _productId;

	test.beforeEach(
		async ({apiHelpers, marketplace, publisherSolutionPage}) => {
			const {account, catalog} =
				await createMarketplaceAccountUserCatalog({
					accountName: ACCOUNT_NAME.SUPPLIER,
					accountType: 'supplier',
					apiHelpers,
				});

			_account = account;

			_catalog = catalog;

			await publisherSolutionPage.goto(
				`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
			);
		}
	);

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessAdminUser.deleteAccount(_account.id);

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(_productId);

		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
			_catalog.id
		);
	});

	for (const key of Object.keys(products)) {
		const product = products[key as keyof typeof products];

		test(`can publish "${product.name}"`, async ({
			apiHelpers,
			page,
			publisherAppPage,
			publisherDashboardPage,
		}) => {
			publisherAppPage.setPublishProduct(
				product as unknown as PublishProductPayload
			);

			// Go to Publisher Dashboard

			await publisherDashboardPage.goto();

			await publisherDashboardPage.selectAccount(ACCOUNT_NAME.SUPPLIER);

			await publisherDashboardPage.gotoNewAppPage();

			// Publish the app

			await publisherAppPage.checkHeader({
				accountName: ACCOUNT_NAME,
				appName: 'New App',
			});
			await publisherAppPage.continue();
			await publisherAppPage.fillProfile();
			await publisherAppPage.fillBuild();

			const createdProduct =
				await apiHelpers.headlessCommerceAdminCatalog.getProducts(
					new URLSearchParams({
						filter: `name eq '${product.name}'`,
					})
				);

			const productId = createdProduct.items[0].productId;

			_productId = productId;

			const productVirtualSettings =
				await apiHelpers.headlessCommerceAdminCatalog.getProductVirtualSettings(
					productId
				);

			await expect(
				productVirtualSettings.productVirtualSettingsFileEntries[0]
					.version === product.dxpVersions[0]
			).toBeTruthy();

			await publisherAppPage.fillStoreFront();
			await publisherAppPage.fillVersion();
			await publisherAppPage.fillPricing();
			await publisherAppPage.fillSupport();
			await publisherAppPage.reviewAndSubmit();

			await expect(page.getByText(product.name)).toBeTruthy();
		});
	}
});
