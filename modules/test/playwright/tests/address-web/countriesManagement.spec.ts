/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {countriesManagementPageTest} from '../../fixtures/CountriesManagementPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForAlert} from '../../utils/waitForAlert';
import {waitForLoading} from '../osb-faro-web/utils/loading';

export const test = mergeTests(
	apiHelpersTest,
	countriesManagementPageTest,
	loginTest()
);

test('LPD-39651 Can activate countries in bulk', async ({
	countriesManagementPage,
	page,
}) => {
	const countries: string[] = ['Antarctica', 'Aruba', 'Austria', 'Bahrain'];

	await countriesManagementPage.goto();

	await countriesManagementPage.checkMultipleCountries(countries);

	page.on('dialog', async (dialog) => await dialog.accept());

	await countriesManagementPage.deactivateButton.click();

	await waitForAlert(page);

	await countriesManagementPage.changeFilter('Inactive');

	for (const country of countries) {
		await expect(
			(await countriesManagementPage.countriesTableRow(1, country, true))
				.row
		).toBeVisible();
	}

	await countriesManagementPage.checkMultipleCountries(countries);

	await countriesManagementPage.activateButton.click();

	await waitForAlert(page);

	await countriesManagementPage.changeFilter('Active');

	for (const country of countries) {
		await expect(
			(await countriesManagementPage.countriesTableRow(1, country, true))
				.row
		).toBeVisible();
	}
});

test('LPD-41857 Can delete regions in bulk', async ({
	apiHelpers,
	countriesManagementPage,
	page,
}) => {
	await countriesManagementPage.goto();

	const regions: string[] = ['AAAA', 'BBBB', 'CCCC', 'DDDD', 'EEEE'];

	const country =
		await apiHelpers.headlessAdminAddress.getCountryByName('antarctica');

	for (const region of regions) {
		await apiHelpers.headlessAdminAddress.postCountryRegion(country.id, {
			active: true,
			name: region,
			regionCode: region,
		});
	}

	await (
		await countriesManagementPage.countriesTableRowLink('Antarctica')
	).click();

	await (await countriesManagementPage.regionsLink).click();

	await countriesManagementPage.selectPaginationItemsPerPage({
		itemsPerPage: '4  Entries per Page',
		page,
	});

	for (let i = 0; i < regions.length; i++) {
		const region = regions[i];

		await (await countriesManagementPage.regionsCheckbox(region)).check();

		if (i === 3) {
			await countriesManagementPage.paginationLink('Page  2').click();
			await waitForLoading(page);
		}
	}

	page.on('dialog', async (dialog) => await dialog.accept());

	await countriesManagementPage.deleteButton.click();

	await waitForLoading(page);

	await expect(countriesManagementPage.noRegionsMessage).toBeVisible();
});
