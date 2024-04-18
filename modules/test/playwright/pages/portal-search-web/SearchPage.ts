/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';

export class SearchPage {
	readonly page: Page;
	readonly modalIFrame: FrameLocator;
	readonly searchBarInputInMainContent: Locator;
	readonly searchBarInputInNavBar: Locator;
	readonly searchOptionsConfigurationLink: Locator;
	readonly searchResults: Locator;
	readonly searchResultsItems: Locator;
	readonly searchResultsPaginationBar: Locator;
	readonly searchResultsPaginationDescription: Locator;
	readonly searchResultsPaginationItemsPerPageToggle: Locator;
	readonly searchResultsPaginationItemsPerPageDropdown: Locator;
	readonly searchResultsTotalLabel: Locator;

	constructor(page: Page) {
		this.page = page;

		this.modalIFrame = page.frameLocator('iframe[id="modalIframe"]');
		this.searchBarInputInMainContent = page
			.locator('#main-content')
			.getByPlaceholder('Search...');
		this.searchBarInputInNavBar = page
			.locator('.navbar')
			.getByPlaceholder('Search...');
		this.searchOptionsConfigurationLink = page.getByText(
			'Configure additional search options in this page'
		);
		this.searchResults = page.locator('.portlet-search-results');

		// Search Results Elements

		this.searchResultsItems =
			this.searchResults.locator('.list-group-item');
		this.searchResultsPaginationBar =
			this.searchResults.locator('.pagination');
		this.searchResultsPaginationDescription = this.searchResults.locator(
			'.pagination-results'
		);
		this.searchResultsPaginationItemsPerPageToggle =
			this.searchResults.locator('.pagination-items-per-page button');
		this.searchResultsPaginationItemsPerPageDropdown =
			this.searchResults.locator(
				'.pagination-items-per-page .dropdown-menu'
			);
		this.searchResultsTotalLabel = this.searchResults.locator(
			'.search-total-label'
		);
	}

	async getSearchFacetCheckbox(
		searchFacetTerm: string | RegExp,
		type: string
	): Promise<Locator> {
		return this.page
			.locator(
				`xpath=//div[contains(.,'${type}') and contains(@class, 'panel-group')]`
			)
			.getByRole('checkbox', {name: searchFacetTerm});
	}

	async getSearchFacetLink(
		searchFacetTerm: string,
		type: string
	): Promise<Locator> {
		return this.page.locator(
			`xpath=//div[contains(.,'${type}') and contains(@class, 'panel-group')]//*[contains(.,'${searchFacetTerm}') and contains(@class, 'term-name')]`
		);
	}

	async goto() {
		await this.page.goto('/search');
	}

	async searchKeywordInMainContent(searchText: string) {
		await this.searchBarInputInMainContent.fill(searchText);

		await this.searchBarInputInMainContent.press('Enter');
	}

	async searchKeywordInNavBar(searchText: string) {
		await this.searchBarInputInNavBar.fill(searchText);

		await this.searchBarInputInNavBar.press('Enter');
	}

	async selectPaginationItemsPerPage(delta: number) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.searchResultsPaginationItemsPerPageDropdown.locator(
				`xpath=//*[@id='${delta}']`
			),
			trigger: this.searchResultsPaginationItemsPerPageToggle,
		});

		await expect(this.searchResultsPaginationItemsPerPageToggle).toHaveText(
			`${delta.toString()} Entries Per Page`
		);
	}

	async selectPaginationPageNumber(pageNumber: number) {
		await this.searchResultsPaginationBar
			.getByText(pageNumber.toString())
			.first()
			.click();

		await expect(
			this.searchResultsPaginationBar
				.getByText(pageNumber.toString())
				.first()
		).toHaveAttribute('aria-current', 'page');
	}

	async selectSearchFacetCheckbox(searchFacetCheckbox: Locator) {
		await searchFacetCheckbox.check();

		await expect(searchFacetCheckbox).toBeChecked();
	}

	async selectSearchFacetLink(searchFacetLink: Locator) {
		await searchFacetLink.click();

		await expect(searchFacetLink).not.toBeDisabled();

		await expect(searchFacetLink).toHaveClass(/facet-term-selected/);
	}

	async selectSearchOptionConfiguration(option: string, value: boolean) {
		await this.searchOptionsConfigurationLink.click();

		const configurationCheckbox = this.modalIFrame.locator(
			`xpath=//*[text()[contains(.,'${option}')]]//input`
		);

		const checked = await configurationCheckbox.isChecked();

		if (value) {
			if (!checked) {
				await configurationCheckbox.check();
			}

			await expect(configurationCheckbox).toBeChecked();
		}

		if (!value) {
			if (checked) {
				await configurationCheckbox.uncheck();
			}

			await expect(configurationCheckbox).not.toBeChecked();
		}

		await this.modalIFrame.getByRole('button', {name: 'Save'}).click();

		await this.modalIFrame.getByRole('button', {name: 'Cancel'}).click();
	}
}
