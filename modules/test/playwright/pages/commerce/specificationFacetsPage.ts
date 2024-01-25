/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from './commerceLayoutsPage';

export class SpecificationFacetsPage {
	readonly addSearchOptionsLabel: Locator;
	readonly addSpecificationFacetLabel: Locator;
	readonly addWidgetButton: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly panelList: Locator;
	readonly searchFormInput: Locator;
	readonly searchOptionsAllowEmptySearchesInput: Locator;
	readonly searchOptionsConfigurationEditButton: Locator;
	readonly searchOptionsConfigurationSaveButton: Locator;

	constructor(page: Page) {
		this.addSearchOptionsLabel = page
			.getByTestId('addPanelTabItem')
			.filter({hasText: /^Search Options$/})
			.getByRole('button', {exact: true, name: 'Add Content'});
		this.addSpecificationFacetLabel = page
			.getByTestId('addPanelTabItem')
			.filter({hasText: /^Specification Facet$/})
			.getByRole('button', {exact: true, name: 'Add Content'});
		this.addWidgetButton = page.getByTestId('add');
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
		this.pageTitle = page
			.getByTestId('headerTitle')
			.filter({hasText: 'Specification Facet Page'});
		this.panelList = page
			.getByTestId('specificationFacetPanel')
			.getByRole('button');
		this.searchFormInput = page.getByRole('textbox', {
			name: 'Search Form',
		});
		this.searchOptionsAllowEmptySearchesInput = page
			.frameLocator('#modalIframe')
			.getByTestId('allowEmptySearches');
		this.searchOptionsConfigurationEditButton =
			page.getByTestId('searchOptionsHref');
		this.searchOptionsConfigurationSaveButton = page
			.frameLocator('#modalIframe')
			.getByTestId('searchOptionsFooter')
			.getByRole('button', {exact: true, name: 'Save'});
	}

	async addSearchOptionsWidget() {
		await this.searchFormInput.click();
		await this.searchFormInput.fill('Search Options');
		await this.addSearchOptionsLabel.click();
	}

	async addSpecificationFacetWidget() {
		await this.searchFormInput.click();
		await this.searchFormInput.fill('Specification Facet');
		await this.addSpecificationFacetLabel.click();
	}

	async addRequiredFacetWidgets() {
		await this.addWidgetButton.click();
		await this.addSearchOptionsWidget();
		await this.addSpecificationFacetWidget();
	}

	async configureSearchOptions() {
		await this.searchOptionsConfigurationEditButton.click();
		await this.searchOptionsAllowEmptySearchesInput.waitFor({
			state: 'attached',
		});
		await this.searchOptionsAllowEmptySearchesInput.click();
		await this.searchOptionsConfigurationSaveButton.click();
	}

	async goto() {
		await this.layoutsPage.goto();
	}

	async goToPage() {
		await this.layoutsPage.goToPages();
		await Promise.all([
			this.layoutsPage.pageLabel.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('specification-facet-page')
			),
		]);
	}

	async reloadPage() {
		await this.page.reload();
	}
}
