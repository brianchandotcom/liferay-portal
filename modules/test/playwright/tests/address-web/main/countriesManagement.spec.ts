/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {countriesManagementPageTest} from '../../../fixtures/CountriesManagementPageTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {waitForLoading} from '../../osb-faro-web/main/utils/loading';

export const test = mergeTests(
	apiHelpersTest,
	countriesManagementPageTest,
	loginTest()
);

test(
	'Can activate/deactivate a country',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		await (
			await countriesManagementPage.countriesTable.rowActions(
				'Antarctica',
				1,
				false
			)
		).click();
		await countriesManagementPage.deactivateButton.click();

		await waitForAlert(page);

		await countriesManagementPage.countriesTable.filterButton.click();
		await countriesManagementPage.countriesTable
			.filterMenuItem('Active')
			.click();

		await expect(
			countriesManagementPage.countriesTable.cell('Antarctica')
		).toHaveCount(0);
		await expect(
			countriesManagementPage.countriesTable.cell('Aruba')
		).toBeVisible();

		await countriesManagementPage.countriesTable.filterButton.click();
		await countriesManagementPage.countriesTable
			.filterMenuItem('Inactive')
			.click();

		await expect(
			countriesManagementPage.countriesTable.cell('Antarctica')
		).toBeVisible();
		await expect(
			countriesManagementPage.countriesTable.cell('Aruba')
		).toHaveCount(0);

		await (
			await countriesManagementPage.countriesTable.rowActions(
				'Antarctica'
			)
		).click();
		await countriesManagementPage.activateButton.click();

		await waitForAlert(page);

		await countriesManagementPage.countriesTable.filterButton.click();
		await countriesManagementPage.countriesTable
			.filterMenuItem('Active')
			.click();

		await expect(
			countriesManagementPage.countriesTable.cell('Antarctica')
		).toBeVisible();
		await expect(
			countriesManagementPage.countriesTable.cell('Aruba')
		).toBeVisible();
	}
);

test(
	'Can activate countries in bulk',
	{tag: ['@LPD-39651']},
	async ({countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		const countries: string[] = [
			'Albania',
			'Antarctica',
			'Aruba',
			'Austria',
		];

		await countriesManagementPage.goto();

		for (const country of countries) {
			await (
				await countriesManagementPage.countriesTable.rowCheckbox(
					country
				)
			).check();
		}
		await countriesManagementPage.deactivateButton.click();

		await waitForAlert(page);

		await countriesManagementPage.countriesTable.filterButton.click();
		await countriesManagementPage.countriesTable
			.filterMenuItem('Inactive')
			.click();

		for (const country of countries) {
			await expect(
				countriesManagementPage.countriesTable.cell(country)
			).toBeVisible();
		}

		for (const country of countries) {
			await (
				await countriesManagementPage.countriesTable.rowCheckbox(
					country
				)
			).check();
		}
		await countriesManagementPage.activateButton.click();

		await waitForAlert(page);

		await countriesManagementPage.countriesTable.filterButton.click();
		await countriesManagementPage.countriesTable
			.filterMenuItem('Active')
			.click();

		for (const country of countries) {
			await expect(
				countriesManagementPage.countriesTable.cell(country)
			).toBeVisible();
		}
	}
);

test(
	'Can delete a region',
	{tag: ['@LPD-55901']},
	async ({apiHelpers, countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const country =
			await apiHelpers.headlessAdminAddress.getCountryByName(
				'antarctica'
			);

		await apiHelpers.headlessAdminAddress.postCountryRegion(country.id, {
			active: true,
			name: 'AAAA',
			regionCode: 'AAAA',
		});

		await (
			await countriesManagementPage.countriesTable.cellLink('Antarctica')
		).click();
		await countriesManagementPage.regionsLink.click();

		await (
			await countriesManagementPage.regionsTable.rowActions('AAAA')
		).click();
		await countriesManagementPage.deleteButton.click();

		await waitForLoading(page);

		await expect(countriesManagementPage.noRegionsMessage).toBeVisible();
	}
);

test(
	'Can delete regions in bulk',
	{tag: ['@LPD-41857']},
	async ({apiHelpers, countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const regions: string[] = ['AAAA', 'BBBB', 'CCCC', 'DDDD', 'EEEE'];

		const country =
			await apiHelpers.headlessAdminAddress.getCountryByName(
				'antarctica'
			);

		for (const region of regions) {
			await apiHelpers.headlessAdminAddress.postCountryRegion(
				country.id,
				{
					active: true,
					name: region,
					regionCode: region,
				}
			);
		}

		await (
			await countriesManagementPage.countriesTable.cellLink('Antarctica')
		).click();
		await countriesManagementPage.regionsLink.click();

		for (const region of regions) {
			await (
				await countriesManagementPage.regionsTable.rowCheckbox(region)
			).check();
		}

		await countriesManagementPage.deleteButton.click();

		await waitForLoading(page);

		await expect(countriesManagementPage.noRegionsMessage).toBeVisible();
	}
);

test(
	'Can add/edit/delete a county',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editCountryPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const country = {
			number: String(getRandomInt()),
			threeLetterIsocode: getRandomString().substring(0, 3),
			title: `AA1${getRandomInt()}`,
			twoLetterIsocode: getRandomString().substring(0, 2),
		};

		await expect(async () => {
			await expect(
				countriesManagementPage.countriesTable.searchInput
			).toBeEditable();

			await countriesManagementPage.countriesTable.newButton.click();

			await expect(editCountryPage.titleInput).toBeVisible();
		}).toPass();

		await editCountryPage.titleInput.fill(country.title);
		await editCountryPage.keyInput.fill(country.title);
		await editCountryPage.twoLetterIsocodeInput.fill(
			country.twoLetterIsocode
		);
		await editCountryPage.threeLetterIsocodeInput.fill(
			country.threeLetterIsocode
		);
		await editCountryPage.numberInput.fill(country.number);
		await editCountryPage.saveButton.click();

		await waitForAlert(page);

		await editCountryPage.backButton.click();

		await expect(
			countriesManagementPage.countriesTable.cell(country.title)
		).toBeVisible();

		await (
			await countriesManagementPage.countriesTable.rowActions(
				country.title
			)
		).click();
		await countriesManagementPage.editButton.click();

		await expect(editCountryPage.activeButton).toBeChecked();
		await expect(editCountryPage.billingAllowedInput).not.toBeChecked();
		await expect(editCountryPage.keyInput).toHaveValue(country.title);
		await expect(editCountryPage.numberInput).toHaveValue(country.number);
		await expect(editCountryPage.shippingAllowedInput).not.toBeChecked();
		await expect(editCountryPage.subjectToVATInput).not.toBeChecked();
		await expect(editCountryPage.threeLetterIsocodeInput).toHaveValue(
			country.threeLetterIsocode
		);
		await expect(editCountryPage.titleInput).toHaveValue(country.title);
		await expect(editCountryPage.twoLetterIsocodeInput).toHaveValue(
			country.twoLetterIsocode
		);

		country.number = String(getRandomInt());
		country.threeLetterIsocode = getRandomString().substring(0, 3);
		country.title = `AA1${getRandomInt()}`;
		country.twoLetterIsocode = getRandomString().substring(0, 2);

		await editCountryPage.titleInput.fill(country.title);
		await editCountryPage.keyInput.fill(country.title);
		await editCountryPage.twoLetterIsocodeInput.fill(
			country.twoLetterIsocode
		);
		await editCountryPage.threeLetterIsocodeInput.fill(
			country.threeLetterIsocode
		);
		await editCountryPage.numberInput.fill(country.number);
		await editCountryPage.saveButton.click();

		await waitForAlert(page);

		await editCountryPage.backButton.click();

		await expect(
			countriesManagementPage.countriesTable.cell(country.title)
		).toBeVisible();

		await (
			await countriesManagementPage.countriesTable.rowActions(
				country.title
			)
		).click();
		await countriesManagementPage.editButton.click();

		await expect(editCountryPage.titleInput).toHaveValue(country.title);
		await expect(editCountryPage.keyInput).toHaveValue(country.title);
		await expect(editCountryPage.twoLetterIsocodeInput).toHaveValue(
			country.twoLetterIsocode
		);
		await expect(editCountryPage.threeLetterIsocodeInput).toHaveValue(
			country.threeLetterIsocode
		);
		await expect(editCountryPage.numberInput).toHaveValue(country.number);

		await editCountryPage.backButton.click();

		await (
			await countriesManagementPage.countriesTable.rowActions(
				country.title
			)
		).click();
		await countriesManagementPage.deleteButton.click();

		await waitForLoading(page);

		await expect(
			countriesManagementPage.countriesTable.cell(country.title)
		).toHaveCount(0);
	}
);

test(
	'Can search a country',
	{tag: ['@LPD-55901', '@LPS-185339']},
	async ({countriesManagementPage}) => {
		await countriesManagementPage.goto();

		await countriesManagementPage.countriesTable.search(getRandomString());

		await expect(countriesManagementPage.noCountriesMessage).toBeVisible();

		await countriesManagementPage.countriesTable.search("Côte d'Ivoire");

		await expect(
			countriesManagementPage.countriesTable.cell('Antarctica')
		).toHaveCount(0);
		await expect(
			countriesManagementPage.countriesTable.cell("Côte d'Ivoire")
		).toBeVisible();

		await countriesManagementPage.countriesTable.search('');

		await expect(
			countriesManagementPage.countriesTable.cell('Antarctica')
		).toBeVisible();
	}
);
