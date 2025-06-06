/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {countriesManagementPageTest} from '../../../fixtures/CountriesManagementPageTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	countriesManagementPageTest,
	loginTest()
);

async function waitForLoading(page: Page) {
	await page.waitForFunction(() => {
		const loaders = document.querySelectorAll('.loading-animation');

		return !loaders.length;
	});
}

test(
	'Can activate/deactivate a country',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		await expect(async () => {
			await (
				await countriesManagementPage.countriesTable.rowActions(
					'Antarctica',
					1,
					false
				)
			).click();
			await countriesManagementPage.deactivateButton.click({
				timeout: 500,
			});

			await waitForAlert(page);
		}).toPass();

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

		await expect(async () => {
			await (
				await countriesManagementPage.countriesTable.rowActions(
					'Antarctica'
				)
			).click();
			await countriesManagementPage.activateButton.click({timeout: 500});

			await waitForAlert(page);
		}).toPass();

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

		await countriesManagementPage.countriesTable.search('Antarctica');
		await (
			await countriesManagementPage.countriesTable.cellLink('Antarctica')
		).click();
		await countriesManagementPage.regionsLink.click();

		await expect(async () => {
			await (
				await countriesManagementPage.regionsTable.rowActions('AAAA')
			).click();
			await countriesManagementPage.deleteButton.click({timeout: 500});

			await waitForAlert(page);
		}).toPass();

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

		await countriesManagementPage.countriesTable.search('Antarctica');
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

		await waitForAlert(page);

		await expect(countriesManagementPage.noRegionsMessage).toBeVisible();
	}
);

test(
	'Can add/edit/delete a country',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editCountryPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const country = {
			key: `AA1${getRandomInt()}`,
			number: String(getRandomInt()),
			priority: '0',
			threeLetterIsocode: getRandomString().substring(0, 3),
			title: '',
			twoLetterIsocode: getRandomString().substring(0, 2),
		};

		await expect(async () => {
			await expect(
				countriesManagementPage.countriesTable.searchInput
			).toBeEditable();

			await countriesManagementPage.countriesTable.newButton.click();

			await expect(editCountryPage.titleInput).toBeVisible();
		}).toPass();

		await editCountryPage.editCountry(country);

		await editCountryPage.backButton.click();

		await countriesManagementPage.countriesTable.search(country.key);
		await expect(
			countriesManagementPage.countriesTable.cell(country.key)
		).toBeVisible();

		await expect(async () => {
			await (
				await countriesManagementPage.countriesTable.rowActions(
					country.key
				)
			).click();
			await countriesManagementPage.editButton.click({timeout: 500});
		}).toPass();

		await expect(editCountryPage.activeButton).toBeChecked();
		await expect(editCountryPage.billingAllowedInput).not.toBeChecked();
		await expect(editCountryPage.keyInput).toHaveValue(country.key);
		await expect(editCountryPage.numberInput).toHaveValue(country.number);
		await expect(editCountryPage.shippingAllowedInput).not.toBeChecked();
		await expect(editCountryPage.subjectToVATInput).not.toBeChecked();
		await expect(editCountryPage.threeLetterIsocodeInput).toHaveValue(
			country.threeLetterIsocode
		);
		await expect(editCountryPage.titleInput).toHaveValue('');
		await expect(editCountryPage.twoLetterIsocodeInput).toHaveValue(
			country.twoLetterIsocode
		);

		country.key = `AA1${getRandomInt()}`;
		country.number = String(getRandomInt());
		country.threeLetterIsocode = getRandomString().substring(0, 3);
		country.title = `AA1${getRandomInt()}`;
		country.twoLetterIsocode = getRandomString().substring(0, 2);

		await editCountryPage.editCountry(country);

		await editCountryPage.backButton.click();

		await countriesManagementPage.countriesTable.search(country.title);
		await expect(
			countriesManagementPage.countriesTable.cell(country.title)
		).toBeVisible();

		await expect(async () => {
			await (
				await countriesManagementPage.countriesTable.rowActions(
					country.title
				)
			).click();
			await countriesManagementPage.editButton.click({timeout: 500});
		}).toPass();

		await expect(editCountryPage.titleInput).toHaveValue(country.title);
		await expect(editCountryPage.keyInput).toHaveValue(country.key);
		await expect(editCountryPage.twoLetterIsocodeInput).toHaveValue(
			country.twoLetterIsocode
		);
		await expect(editCountryPage.threeLetterIsocodeInput).toHaveValue(
			country.threeLetterIsocode
		);
		await expect(editCountryPage.numberInput).toHaveValue(country.number);

		await editCountryPage.backButton.click();

		await expect(async () => {
			await countriesManagementPage.countriesTable.search(country.title);
			await (
				await countriesManagementPage.countriesTable.rowActions(
					country.title
				)
			).click();
			await countriesManagementPage.deleteButton.click({timeout: 500});

			await waitForAlert(page);
		}).toPass();

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

test(
	'Can enable/disable a region',
	{tag: ['@LPD-55901']},
	async ({apiHelpers, countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		const country =
			await apiHelpers.headlessAdminAddress.getCountryByName(
				'antarctica'
			);

		const region = {
			active: true,
			name: getRandomString(),
			regionCode: getRandomString().substring(0, 4),
		};

		await apiHelpers.headlessAdminAddress.postCountryRegion(
			country.id,
			region
		);

		try {
			await countriesManagementPage.goto();

			await countriesManagementPage.countriesTable.search('Antarctica');
			await (
				await countriesManagementPage.countriesTable.cellLink(
					'Antarctica'
				)
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.deactivateButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();

			await countriesManagementPage.regionsTable.filterButton.click();
			await countriesManagementPage.regionsTable
				.filterMenuItem('Active')
				.click();

			await expect(
				countriesManagementPage.noRegionsMessage
			).toBeVisible();

			await countriesManagementPage.regionsTable.filterButton.click();
			await countriesManagementPage.regionsTable
				.filterMenuItem('Inactive')
				.click();

			await expect(
				countriesManagementPage.regionsTable.cell(region.name)
			).toBeVisible();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.activateButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();

			await expect(
				countriesManagementPage.noRegionsMessage
			).toBeVisible();

			await countriesManagementPage.regionsTable.filterButton.click();
			await countriesManagementPage.regionsTable
				.filterMenuItem('Active')
				.click();

			await expect(
				countriesManagementPage.regionsTable.cell(region.name)
			).toBeVisible();
		}
		finally {
			await countriesManagementPage.goto();

			await countriesManagementPage.countriesTable.search('Antarctica');
			await (
				await countriesManagementPage.countriesTable.cellLink(
					'Antarctica'
				)
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();
		}
	}
);

test(
	'Can add/edit/delete a region',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editRegionPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		await countriesManagementPage.countriesTable.search('Antarctica');
		await (
			await countriesManagementPage.countriesTable.cellLink('Antarctica')
		).click();
		await countriesManagementPage.regionsLink.click();

		await expect(async () => {
			await expect(
				countriesManagementPage.regionsTable.searchInput
			).toBeEditable();

			await countriesManagementPage.regionsTable.newButton.click();

			await expect(editRegionPage.titleInput).toBeVisible();
		}).toPass();

		await editRegionPage.saveButton.click();

		await expect(editRegionPage.keyErrorMessage).toBeVisible();
		await expect(editRegionPage.regionCodeErrorMessage).toBeVisible();

		const region = {
			key: `AA1${getRandomInt()}`,
			name: '',
			priority: '1.0',
			regionCode: getRandomString().substring(0, 3),
		};

		await editRegionPage.titleInput.fill(region.name);
		await editRegionPage.keyInput.fill(region.key);
		await editRegionPage.priorityInput.fill(region.priority);
		await editRegionPage.regionCodeInput.fill(region.regionCode);
		await editRegionPage.cancelButton.click();

		await expect(
			countriesManagementPage.regionsTable.cell(region.key)
		).toHaveCount(0);

		await expect(async () => {
			await expect(
				countriesManagementPage.regionsTable.searchInput
			).toBeEditable();

			await countriesManagementPage.regionsTable.newButton.click();

			await expect(editRegionPage.titleInput).toBeVisible();
		}).toPass();

		await editRegionPage.editRegion(region);

		try {
			await expect(editRegionPage.keyErrorMessage).toHaveCount(0);
			await expect(editRegionPage.regionCodeErrorMessage).toHaveCount(0);

			await editRegionPage.backButton.click();

			await expect(
				countriesManagementPage.regionsTable.cell(region.key)
			).toBeVisible();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.key
					)
				).click();
				await countriesManagementPage.editButton.click({timeout: 500});
			}).toPass();

			await expect(editRegionPage.activeButton).toBeChecked();
			await expect(editRegionPage.keyInput).toHaveValue(region.key);
			await expect(editRegionPage.priorityInput).toHaveValue(
				region.priority
			);
			await expect(editRegionPage.regionCodeInput).toHaveValue(
				region.regionCode
			);
			await expect(editRegionPage.titleInput).toHaveValue(region.name);

			region.key = `AA1${getRandomInt()}`;
			region.name = `AA1${getRandomInt()}`;
			region.priority = '2.0';
			region.regionCode = getRandomString().substring(0, 3);

			await editRegionPage.editRegion(region);

			await editRegionPage.backButton.click();

			await expect(
				countriesManagementPage.regionsTable.cell(region.name)
			).toBeVisible();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.editButton.click({timeout: 500});
			}).toPass();

			await expect(editRegionPage.activeButton).toBeChecked();
			await expect(editRegionPage.keyInput).toHaveValue(region.key);
			await expect(editRegionPage.priorityInput).toHaveValue(
				region.priority
			);
			await expect(editRegionPage.regionCodeInput).toHaveValue(
				region.regionCode
			);
			await expect(editRegionPage.titleInput).toHaveValue(region.name);
		}
		finally {
			await countriesManagementPage.goto();

			await countriesManagementPage.countriesTable.search('Antarctica');
			await (
				await countriesManagementPage.countriesTable.cellLink(
					'Antarctica'
				)
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();
		}
	}
);

test(
	'Can sort countries by name and priority',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editCountryPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		await expect(
			countriesManagementPage.countriesTable.searchInput
		).toBeEditable();

		await expect(async () => {
			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Name')
				.click({timeout: 500});

			await waitForLoading(page);

			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Ascending')
				.click({timeout: 500});

			await expect(
				countriesManagementPage.countriesTable.cell('Antarctica')
			).toBeVisible();
			await expect(
				countriesManagementPage.countriesTable.cell('Zimbabwe')
			).toHaveCount(0);
		}).toPass();

		await expect(async () => {
			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Descending')
				.click({timeout: 500});

			await expect(
				countriesManagementPage.countriesTable.cell('Antarctica')
			).toHaveCount(0);
			await expect(
				countriesManagementPage.countriesTable.cell('Zimbabwe')
			).toBeVisible();
		}).toPass();

		const country = {
			key: `AA1${getRandomInt()}`,
			number: String(getRandomInt()),
			priority: '100',
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

		await editCountryPage.editCountry(country);

		await editCountryPage.backButton.click();

		await expect(async () => {
			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Priority')
				.click({timeout: 500});

			await waitForLoading(page);

			await expect(
				countriesManagementPage.countriesTable.searchInput
			).toBeEditable();

			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Ascending')
				.click({timeout: 500});

			await expect(
				countriesManagementPage.countriesTable.cell(country.title)
			).toHaveCount(0);
		}).toPass();

		await expect(async () => {
			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Descending')
				.click({timeout: 500});

			await expect(
				countriesManagementPage.countriesTable.cell(country.title)
			).toBeVisible();

			await (
				await countriesManagementPage.countriesTable.rowActions(
					country.title
				)
			).click();
			await countriesManagementPage.deleteButton.click({timeout: 500});

			await waitForAlert(page);
		}).toPass();

		await expect(async () => {
			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Name')
				.click({timeout: 500});

			await waitForLoading(page);

			await expect(
				countriesManagementPage.countriesTable.searchInput
			).toBeEditable();

			await countriesManagementPage.countriesTable.orderButton.click();
			await countriesManagementPage.countriesTable
				.orderMenuItem('Ascending')
				.click({timeout: 500});

			await expect(
				countriesManagementPage.countriesTable.cell('Antarctica')
			).toBeVisible();
		}).toPass();
	}
);

test(
	'Can sort regions by name and priority',
	{tag: ['@LPD-55901']},
	async ({apiHelpers, countriesManagementPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const country =
			await apiHelpers.headlessAdminAddress.getCountryByName('aruba');

		await apiHelpers.headlessAdminAddress.postCountryRegion(country.id, {
			active: true,
			name: 'AAAA',
			position: 100,
			regionCode: getRandomString().substring(0, 4),
		});
		await apiHelpers.headlessAdminAddress.postCountryRegion(country.id, {
			active: true,
			name: 'ZZZZ',
			position: 1,
			regionCode: getRandomString().substring(0, 4),
		});

		try {
			await countriesManagementPage.countriesTable.search('Aruba');
			await (
				await countriesManagementPage.countriesTable.cellLink('Aruba')
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(
				countriesManagementPage.regionsTable.searchInput
			).toBeEditable();

			await expect(async () => {
				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Name')
					.click({timeout: 500});

				await waitForLoading(page);

				await expect(
					countriesManagementPage.regionsTable.searchInput
				).toBeEditable();

				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Ascending')
					.click({timeout: 500});

				await expect(
					await countriesManagementPage.regionsTable.firstRow()
				).toContainText('AAAA');
				await expect(
					await countriesManagementPage.regionsTable.lastRow()
				).toContainText('ZZZZ');
			}).toPass();

			await expect(async () => {
				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Descending')
					.click({timeout: 500});

				await expect(
					await countriesManagementPage.regionsTable.firstRow()
				).toContainText('ZZZZ');
				await expect(
					await countriesManagementPage.regionsTable.lastRow()
				).toContainText('AAAA');
			}).toPass();

			await expect(async () => {
				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Priority')
					.click({timeout: 500});

				await waitForLoading(page);

				await expect(
					countriesManagementPage.regionsTable.searchInput
				).toBeEditable();

				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Ascending')
					.click({timeout: 500});

				await expect(
					await countriesManagementPage.regionsTable.firstRow()
				).toContainText('ZZZZ');
				await expect(
					await countriesManagementPage.regionsTable.lastRow()
				).toContainText('AAAA');
			}).toPass();

			await expect(async () => {
				await countriesManagementPage.regionsTable.orderButton.click();
				await countriesManagementPage.regionsTable
					.orderMenuItem('Descending')
					.click({timeout: 500});

				await expect(
					await countriesManagementPage.regionsTable.firstRow()
				).toContainText('AAAA');
				await expect(
					await countriesManagementPage.regionsTable.lastRow()
				).toContainText('ZZZZ');
			}).toPass();
		}
		finally {
			await countriesManagementPage.goto();

			await countriesManagementPage.countriesTable.search('Aruba');
			await (
				await countriesManagementPage.countriesTable.cellLink('Aruba')
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						'AAAA'
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();
			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						'ZZZZ'
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();

			await countriesManagementPage.regionsTable.orderButton.click();
			await countriesManagementPage.regionsTable
				.orderMenuItem('Ascending')
				.click({timeout: 500});
		}
	}
);

test(
	'Country name can be translated',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editCountryPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		const country = {
			key: `AA1${getRandomInt()}`,
			number: String(getRandomInt()),
			priority: '0',
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

		await editCountryPage.editCountry(country);

		await editCountryPage.titleTranslationButton.click();

		await expect(
			editCountryPage.notTranslatedMessage('Catalan')
		).toBeVisible();
		await expect(editCountryPage.translatedMessage('Catalan')).toHaveCount(
			0
		);

		await editCountryPage.notTranslatedMessage('Catalan').click();
		await editCountryPage.titleInput.fill(`${country.title}-es`);
		await editCountryPage.saveButton.click();

		await waitForAlert(page);

		await editCountryPage.titleTranslationButton.click();

		await expect(
			editCountryPage.notTranslatedMessage('Catalan')
		).toHaveCount(0);
		await expect(
			editCountryPage.translatedMessage('Catalan')
		).toBeVisible();

		await page.reload();

		await editCountryPage.titleTranslationButton.click();

		await expect(
			editCountryPage.notTranslatedMessage('Catalan')
		).toHaveCount(0);
		await expect(
			editCountryPage.translatedMessage('Catalan')
		).toBeVisible();

		await editCountryPage.backButton.click();

		await expect(async () => {
			await countriesManagementPage.countriesTable.search(country.title);
			await (
				await countriesManagementPage.countriesTable.rowActions(
					country.title
				)
			).click();
			await countriesManagementPage.deleteButton.click({timeout: 500});

			await waitForAlert(page);
		}).toPass();
	}
);

test(
	'Region name can be translated',
	{tag: ['@LPD-55901']},
	async ({countriesManagementPage, editRegionPage, page}) => {
		page.on('dialog', async (dialog) => await dialog.accept());

		await countriesManagementPage.goto();

		await countriesManagementPage.countriesTable.search('Antarctica');
		await (
			await countriesManagementPage.countriesTable.cellLink('Antarctica')
		).click();
		await countriesManagementPage.regionsLink.click();

		await expect(async () => {
			await expect(
				countriesManagementPage.regionsTable.searchInput
			).toBeEditable();

			await countriesManagementPage.regionsTable.newButton.click();

			await expect(editRegionPage.titleInput).toBeVisible();
		}).toPass();

		const region = {
			key: `AA1${getRandomInt()}`,
			name: `AA1${getRandomInt()}`,
			priority: '1.0',
			regionCode: getRandomString().substring(0, 3),
		};

		await editRegionPage.editRegion(region);

		try {
			await editRegionPage.titleTranslationButton.click();

			await expect(
				editRegionPage.notTranslatedMessage('Catalan')
			).toBeVisible();
			await expect(
				editRegionPage.translatedMessage('Catalan')
			).toHaveCount(0);

			await editRegionPage.notTranslatedMessage('Catalan').click();
			await editRegionPage.titleInput.fill(`${region.name}-es`);
			await editRegionPage.saveButton.click();

			await waitForAlert(page);

			await editRegionPage.titleTranslationButton.click();

			await expect(
				editRegionPage.notTranslatedMessage('Catalan')
			).toHaveCount(0);
			await expect(
				editRegionPage.translatedMessage('Catalan')
			).toBeVisible();

			await page.reload();

			await editRegionPage.titleTranslationButton.click();

			await expect(
				editRegionPage.notTranslatedMessage('Catalan')
			).toHaveCount(0);
			await expect(
				editRegionPage.translatedMessage('Catalan')
			).toBeVisible();
		}
		finally {
			await countriesManagementPage.goto();

			await countriesManagementPage.countriesTable.search('Antarctica');
			await (
				await countriesManagementPage.countriesTable.cellLink(
					'Antarctica'
				)
			).click();
			await countriesManagementPage.regionsLink.click();

			await expect(async () => {
				await (
					await countriesManagementPage.regionsTable.rowActions(
						region.name
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();
		}
	}
);
