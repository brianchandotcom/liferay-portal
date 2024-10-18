/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {countriesManagementPageTest} from '../../fixtures/CountriesManagementPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(loginTest(), countriesManagementPageTest);

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
