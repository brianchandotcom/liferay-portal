/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {getRandomInt} from '../../../../../utils/getRandomInt';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';
import {PublishProductPayload} from '../../types';
import {products} from '../../utils/constants';

export const test = mergeTests(
	marketplaceSiteFixture,
	marketplacePagesTest,
	marketplaceHelper
);

test.describe('Can Publish Marketplace Apps', () => {
	let _account;
	let _catalog;
	let _productId;
	const accountName = `Supplier Account${getRandomInt()}`;

	test.beforeEach(
		async ({marketplace, marketplaceHelper, publisherSolutionPage}) => {
			const {account, catalog} =
				await marketplaceHelper.createAccountUserCatalog({
					accountName,
					accountType: 'supplier',
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
			marketplace,
			page,
			publisherAppPage,
			publisherDashboardPage,
		}) => {
			publisherAppPage.setPublishProduct(
				product as unknown as PublishProductPayload
			);

			// Go to Publisher Dashboard

			await publisherDashboardPage.goto(marketplace.friendlyUrlPath);

			await publisherDashboardPage.selectAccount(accountName);

			await publisherDashboardPage.gotoNewAppPage();

			// Publish the app

			await publisherAppPage.checkHeader({
				accountName,
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
