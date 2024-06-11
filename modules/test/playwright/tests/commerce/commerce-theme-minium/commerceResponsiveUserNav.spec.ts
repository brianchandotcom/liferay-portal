/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test.use({
	...devices['Pixel 5'],
});

test('LPD-3391 Minium sidebar user navigation items are clickable in responsive mode', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceThemeMiniumPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Minium',
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await applicationsMenuPage.goToSite('Minium');

	await commerceThemeMiniumPage.stickerUserNav.click();

	await commerceThemeMiniumPage.myProfileItemMenu.click();

	try {
		await expect(
			page.getByRole('heading', {name: 'Account Management'})
		).toBeVisible();
	}
	finally {
		const channels =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				'Minium Portal'
			);

		apiHelpers.data.push({id: channels.items[0].id, type: 'channel'});

		const catalogs =
			await apiHelpers.headlessCommerceAdminCatalog.getCatalogsPage(
				'Minium'
			);

		apiHelpers.data.push({id: catalogs.items[0].id, type: 'catalog'});

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProductsPage(
				50,
				''
			);

		for (let i = 0; i < products.totalCount; i++) {
			if (products.items[i].catalogId === catalogs.items[0].id) {
				apiHelpers.data.push({
					id: products.items[i].productId,
					type: 'product',
				});
			}
		}

		const options =
			await apiHelpers.headlessCommerceAdminCatalog.getOptions();

		for (let i = 0; i < options.totalCount; i++) {
			apiHelpers.data.push({
				id: options.items[i].id,
				type: 'option',
			});
		}

		const optionCategories =
			await apiHelpers.headlessCommerceAdminCatalog.getOptionCategories();

		for (let i = 0; i < optionCategories.totalCount; i++) {
			apiHelpers.data.push({
				id: optionCategories.items[i].id,
				type: 'optionCategory',
			});
		}

		const specifications =
			await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

		for (let i = 0; i < specifications.totalCount; i++) {
			apiHelpers.data.push({
				id: specifications.items[i].id,
				type: 'specification',
			});
		}

		const warehouses =
			await apiHelpers.headlessCommerceAdminInventoryApiHelper.getWarehousesPage();

		for (let i = 0; i < warehouses.totalCount; i++) {
			apiHelpers.data.push({
				id: warehouses.items[i].id,
				type: 'warehouse',
			});
		}
	}
});
