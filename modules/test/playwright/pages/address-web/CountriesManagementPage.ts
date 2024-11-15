/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForLoading} from '../../tests/osb-faro-web/utils/loading';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

const searchTableRowByValue = async function (
	tableLocator: Locator,
	colPosition: number,
	value: string,
	strictEqual: boolean = false
) {
	await tableLocator.elementHandle();

	const rows = await tableLocator.getByRole('row').all();

	for await (const row of rows) {
		const column = row.getByRole('cell').nth(colPosition).first();

		const colValue = (await column.allInnerTexts()).join('');

		if (
			(strictEqual && colValue === value) ||
			(!strictEqual &&
				colValue.toLowerCase().indexOf(value.toLowerCase()) >= 0)
		) {
			return {column, row};
		}
	}

	throw new Error(`Cannot locate table row with value ${value}`);
};

export class CountriesManagementPage {
	readonly activateButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly countriesCheckbox: (countryName: string) => Promise<Locator>;
	readonly countriesTable: Locator;
	readonly countriesTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly countriesTableRowLink: (countryName: string) => Promise<Locator>;
	readonly deactivateButton: Locator;
	readonly deleteButton: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (option: string) => Locator;
	readonly filterStatus: (status: string) => Locator;
	readonly noRegionsMessage: Locator;
	readonly page: Page;
	readonly paginationLink: (paginationPageNumber: string) => Locator;
	readonly regionsCheckbox: (regionName: string) => Promise<Locator>;
	readonly regionsLink: Locator;
	readonly regionsTable: Locator;
	readonly regionsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.activateButton = page.getByRole('button', {name: 'Activate'});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.countriesCheckbox = async (countryName: string) => {
			const countriesTableRow = await this.countriesTableRow(
				1,
				countryName
			);
			if (countriesTableRow && countriesTableRow.row) {
				return countriesTableRow.row.getByRole('checkbox');
			}
		};
		this.countriesTable = page.locator(
			'#_com_liferay_address_web_internal_portlet_CountriesManagementAdminPortlet_countrySearchContainer'
		);
		this.countriesTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.countriesTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.countriesTableRowLink = async (countryName: string) => {
			const countriesTableRow = await this.countriesTableRow(
				1,
				countryName,
				true
			);
			if (countriesTableRow && countriesTableRow.column) {
				return countriesTableRow.column.getByRole('link', {
					name: countryName,
				});
			}
			throw new Error(
				`Cannot locate country row with name ${countryName}`
			);
		};
		this.deactivateButton = page.getByRole('button', {name: 'Deactivate'});
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.filterMenuItem = (option: string) => {
			return page.getByRole('menuitem', {
				exact: true,
				name: option,
			});
		};
		this.filterStatus = (status: string) => {
			return page.getByText('Status: ' + status);
		};
		this.noRegionsMessage = page.getByText('There are no regions.');
		this.page = page;
		this.paginationLink = (paginationPageNumber: string) => {
			return page.getByRole('link', {
				exact: true,
				name: paginationPageNumber,
			});
		};
		this.regionsCheckbox = async (regionName: string) => {
			const regionsTableRow = await this.regionsTableRow(1, regionName);
			if (regionsTableRow && regionsTableRow.row) {
				return regionsTableRow.row.getByRole('checkbox');
			}
		};
		this.regionsLink = page.getByRole('link', {
			name: 'Regions',
		});
		this.regionsTable = page.locator(
			'#_com_liferay_address_web_internal_portlet_CountriesManagementAdminPortlet_regionSearchContainer'
		);
		this.regionsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.regionsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.searchInput = this.page.getByPlaceholder('Search for');
	}

	async changeFilter(option: 'Active' | 'Inactive') {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.filterMenuItem(option),
			trigger: this.filterButton,
		});

		await this.filterStatus(option).waitFor({state: 'visible'});
	}

	async checkMultipleCountries(countries: string[]) {
		for (const country of countries) {
			await (await this.countriesCheckbox(country)).check();
		}
	}

	async goto() {
		await this.applicationsMenuPage.goToCountriesManagement();
	}

	async selectPaginationItemsPerPage({
		itemsPerPage,
		page,
	}: {
		itemsPerPage: string;
		page: Page;
	}) {
		await waitForLoading(page);

		await page.locator('.pagination-items-per-page').click();

		await page
			.getByRole('option', {exact: true, name: itemsPerPage})
			.click();

		await waitForLoading(page);
	}
}
