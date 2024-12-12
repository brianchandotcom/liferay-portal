/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import performLogin from '../../../../../utils/performLogin';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {PublishProductPayload} from '../../types';
import {products} from '../../utils/constants';

export const test = mergeTests(
	apiHelpersTest,
	marketplacePagesTest,
	marketplaceHelper
);

const accountName = `Supplier Account`;

let _account;
let _catalog;
let _productId;

test.describe('Publish Marketplace Apps', () => {
	test(`Publish Marketplace Apps `, async ({
		apiHelpers,
		marketplaceHelper,
		page,
	}) => {
		await performLogin(page, 'test');

		const {account, catalog} =
			await marketplaceHelper.createAccountUserCatalog({
				accountName,
				accountType: 'supplier',
			});

		_account = account;
		_catalog = catalog;

		const accountRole =
			await apiHelpers.headlessAdminUser.getAccountRolesByRoleName(
				_account.id,
				'Account Supplier'
			);

		await marketplaceHelper.createAccountUserSupplier({
			accountId: account.id,
			accountRoleIds: accountRole.items[0].id,
			emailAddresses: 'demo.unprivileged@liferay.com',
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			_account.id,
			['demo.unprivileged@liferay.com']
		);

		const site = await apiHelpers.headlessSite.getSiteByERC(
			'LIFERAY_MARKETPLACE'
		);

		await page.goto(`web${site.friendlyUrlPath}`);
	});

	for (const key of Object.keys(products)) {
		const product = products[key as keyof typeof products];

		test(`Test all items "${product.name}"`, async ({
			apiHelpers,
			page,
			publisherAppPage,
			publisherDashboardPage,
		}) => {
			await performLogin(page, 'demo.unprivileged');

			publisherAppPage.setPublishProduct(
				product as unknown as PublishProductPayload
			);

			// Go to Publisher Dashboard

			const site = await apiHelpers.headlessSite.getSiteByERC(
				'LIFERAY_MARKETPLACE'
			);

			await page.goto(`web${site.friendlyUrlPath}`);

			await publisherDashboardPage.goto(site.friendlyUrlPath);

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
