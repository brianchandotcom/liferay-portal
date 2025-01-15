/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceAdminCurrenciesPage {
	readonly actionsButton: Locator;
	readonly activeFilter: (value: string) => Locator;
	readonly activeMenuItem: Locator;
	readonly activeToggleMenuItem: Locator;
	readonly addCurrencyAddButton: Locator;
	readonly addFilterButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly backLink: Locator;
	readonly currencyNameLink: (currencyName: string) => Locator;
	readonly deleteMenuItem: Locator;
	readonly filterButton: Locator;
	readonly filterManagementToolbar: Locator;
	readonly firstRowCurrencyCellName: (currencyName: string) => Locator;
	readonly lastRowCurrencyCellName: (currencyName: string) => Locator;
	readonly noResultsFoundText: Locator;
	readonly primaryMenuItem: Locator;
	readonly priorityButton: Locator;
	readonly resetFilterButton: Locator;
	readonly search: Locator;
	readonly searchButton: Locator;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.activeFilter = (value) => page.getByLabel(value);
		this.activeMenuItem = page.getByRole('menuitem', {name: 'Active'});
		this.activeToggleMenuItem = page.getByRole('menuitem', {
			name: 'Toggle Active',
		});
		this.addCurrencyAddButton = page.getByRole('button', {
			exact: true,
			name: 'Add Currency',
		});
		this.addFilterButton = page.getByRole('button', {name: 'Add Filter'});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.currencyNameLink = (currencyName) =>
			page.getByRole('link', {name: currencyName});
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.filterButton = page.getByRole('button', {name: 'Filter'});
		this.filterManagementToolbar = page
			.getByTestId('management-toolbar')
			.getByRole('button', {name: 'Filter'});
		this.firstRowCurrencyCellName = (currencyName) =>
			page
				.locator('.dnd-tbody .dnd-tr')
				.first()
				.locator('.cell-name')
				.filter({hasText: currencyName});
		this.lastRowCurrencyCellName = (currencyName) =>
			page
				.locator('.dnd-tbody .dnd-tr')
				.last()
				.locator('.cell-name')
				.filter({hasText: currencyName});
		this.noResultsFoundText = page.getByText('No Results Found');
		this.primaryMenuItem = page.getByRole('menuitem', {name: 'Primary'});
		this.priorityButton = page.getByRole('button', {name: 'Priority'});
		this.resetFilterButton = page.getByRole('button', {
			name: 'Reset Filters',
		});
		this.search = page.getByPlaceholder('Search');
		this.searchButton = page.getByRole('button', {name: 'Search'});
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceCurrencies();
	}
}
