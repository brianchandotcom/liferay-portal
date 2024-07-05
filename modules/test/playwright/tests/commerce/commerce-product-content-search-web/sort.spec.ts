/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

async function setAndCheckSorting({
	applicationsMenuPage,
	commerceThemeMiniumCatalogPage,
	firstCardItem,
	firstCardItemAfterChange,
	page,
	siteName,
	sortingOption1,
	sortingOption2,
}) {
	await applicationsMenuPage.goToSite(siteName);
	await page.waitForTimeout(2000);

	await commerceThemeMiniumCatalogPage.optionsButton.click();
	await commerceThemeMiniumCatalogPage.configurationMenuItem.click();
	await commerceThemeMiniumCatalogPage.configurationIFrameDefaultSortingDropdownMenu.selectOption(
		sortingOption1
	);
	await commerceThemeMiniumCatalogPage.configurationIFrameSaveButton.click();
	await commerceThemeMiniumCatalogPage.configurationIFrameCloseButton.click();
	await page.reload();

	expect(
		await commerceThemeMiniumCatalogPage.orderByButton.innerText()
	).toContain(sortingOption1);
	expect(
		await commerceThemeMiniumCatalogPage.firstCardItem.innerText()
	).toContain(firstCardItem);

	await commerceThemeMiniumCatalogPage.orderByButton.click();
	await commerceThemeMiniumCatalogPage.selectSorting(sortingOption2);

	expect(
		await commerceThemeMiniumCatalogPage.firstCardItem.innerText()
	).toContain(firstCardItemAfterChange);
}

test('LPD-18714 Setting default sort for commerce products', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceThemeMiniumCatalogPage,
	page,
}) => {
	test.setTimeout(180000);

	const siteName1 = 'Minium' + getRandomInt();

	const site1 = await apiHelpers.headlessSite.createSite({
		name: siteName1,
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site1.id, type: 'site'});

	const siteName2 = 'Minium' + getRandomInt();

	const site2 = await apiHelpers.headlessSite.createSite({
		name: siteName2,
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site2.id, type: 'site'});

	const sortingOption1 = 'Name Ascending';
	const sortingOption2 = 'Name Descending';
	const sortingOption3 = 'Price High to Low';
	const sortingOption4 = 'Price Low to High';
	const firstCardItemSortingOption1 = 'ABS Sensor';
	const firstCardItemSortingOption2 = 'Wheel Seal - Front';
	const firstCardItemSortingOption3 = 'Cams';
	const firstCardItemSortingOption4 = 'Mount';

	try {
		await setAndCheckSorting({
			applicationsMenuPage,
			commerceThemeMiniumCatalogPage,
			firstCardItem: firstCardItemSortingOption1,
			firstCardItemAfterChange: firstCardItemSortingOption3,
			page,
			siteName: siteName1,
			sortingOption1,
			sortingOption2: sortingOption3,
		});

		await setAndCheckSorting({
			applicationsMenuPage,
			commerceThemeMiniumCatalogPage,
			firstCardItem: firstCardItemSortingOption2,
			firstCardItemAfterChange: firstCardItemSortingOption4,
			page,
			siteName: siteName2,
			sortingOption1: sortingOption2,
			sortingOption2: sortingOption4,
		});

		await applicationsMenuPage.goToSite(siteName1);

		expect(
			await commerceThemeMiniumCatalogPage.orderByButton.innerText()
		).toContain(sortingOption1);
		expect(
			await commerceThemeMiniumCatalogPage.firstCardItem.innerText()
		).toContain(firstCardItemSortingOption1);
	}
	finally {
		const channels1 =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`${siteName1} Portal`
			);

		apiHelpers.data.push({id: channels1.items[0].id, type: 'channel'});

		const channels2 =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`${siteName2} Portal`
			);

		apiHelpers.data.push({id: channels2.items[0].id, type: 'channel'});

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
