/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {
	performLoginViaApi,
	performLogout,
} from '../../../../../utils/performLogin';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';
import {PublishProductPayload} from '../../types';
import {products} from '../../utils/constants';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-48862': {enabled: true},
	}),
	apiHelpersTest,
	marketplaceSiteFixture,
	marketplacePagesTest,
	marketplaceHelper
);

const accountName = `Supplier Account`;

let _account;
let _catalog;
let _productId;
let _user;

test.describe('Publish Marketplace Apps', () => {
	test.afterEach(async ({apiHelpers, page}) => {
		await performLoginViaApi(page, 'test');

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(_productId);

		await apiHelpers.headlessAdminUser.deleteAccount(_account.id);

		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
			_catalog.id
		);

		await apiHelpers.headlessAdminUser.deleteUserAccount(_user.id);
	});

	test.beforeEach(async ({apiHelpers, marketplaceHelper}) => {
		let account =
			await apiHelpers.headlessAdminUser.getAccountByName(accountName);

		if (!account) {
			account = await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'supplier',
			});
		}

		_account = account;

		const accountRole =
			await apiHelpers.headlessAdminUser.getAccountRolesByRoleName(
				_account.id,
				'Account Supplier'
			);

		await marketplaceHelper.createAccountUserSupplier({
			accountId: _account.id,
			accountRoleIds: accountRole.items[0].id,
			emailAddresses: 'demo.unprivileged@liferay.com',
		});

		const catalogData = {
			default: {},
			supplier: {accountId: account.id},
		};

		const catalogConfig = catalogData['supplier'] || catalogData.default;

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog(
				catalogConfig
			);

		_catalog = catalog;

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			_account.id,
			['demo.unprivileged@liferay.com']
		);

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'demo.unprivileged@liferay.com'
			);

		_user = user;

		await apiHelpers.jsonWebServicesUser.agreeToTermsOfUse(_user.id);
	});

	for (const key of Object.keys(products)) {
		const product = products[key as keyof typeof products];

		test(`Test all items "${product.name}"`, async ({
			apiHelpers,
			marketplace,
			page,
			publisherAppPage,
			publisherDashboardPage,
		}) => {
			publisherAppPage.setPublishProduct(
				product as unknown as PublishProductPayload
			);

			// Log in to unprivileged account

			await performLogout(page);

			await performLoginViaApi(page, 'demo.unprivileged');

			// Go to Publisher Dashboard

			await page.goto(`web${marketplace.friendlyUrlPath}`);

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
