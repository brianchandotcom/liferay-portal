/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerce-order-content-web/commerceLayoutsPage';

export class SpecificationFacetsPage {
	readonly addSearchOptionsLabel: Locator;
	readonly addOptionFacetLabel: Locator;
	readonly addSpecificationFacetLabel: Locator;
	readonly addWidgetButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly configurationSaveButton: Locator;
	readonly displayTemplateSelect: Locator;
	readonly frequencyThresholdInput: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly optionFacetConfigurationEditButton: Locator;
	readonly page: Page;
	readonly pageLabel: Locator;
	readonly pageTitle: Locator;
	readonly panelList: Locator;
	readonly searchFormInput: Locator;
	readonly searchOptionsAllowEmptySearchesInput: Locator;
	readonly searchOptionsConfigurationEditButton: Locator;
	readonly searchOptionsConfigurationSaveButton: Locator;
	readonly selectSpecificationFacetPageInput: Locator;
	readonly specificationFacetConfigurationEditButton: Locator;
	readonly specificationFacetOrderSpecificationInput: Locator;

	constructor(page: Page) {
		this.addSearchOptionsLabel = page
			.getByTestId('addPanelTabItem')
			.filter({hasText: /^Search Options$/})
			.getByRole('button', {exact: true, name: 'Add Content'});
		this.addOptionFacetLabel = page
			.getByTestId('addPanelTabItem')
			.filter({hasText: /^Option Facet$/})
			.getByRole('button', {exact: true, name: 'Add Content'});
		this.addSpecificationFacetLabel = page
			.getByTestId('addPanelTabItem')
			.filter({hasText: /^Specification Facet$/})
			.getByRole('button', {exact: true, name: 'Add Content'});
		this.addWidgetButton = page.getByTestId('add');
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.configurationSaveButton = page
			.frameLocator('iframe[id="modalIframe"]')
			.getByRole('button', {name: 'Save'});
		this.displayTemplateSelect = page
			.frameLocator('iframe[id="modalIframe"]')
			.getByLabel('Display Template');
		this.frequencyThresholdInput = page
			.frameLocator('iframe[id="modalIframe"]')
			.getByLabel('Frequency Threshold');
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.optionFacetConfigurationEditButton = page
			.locator('//section[contains(@id, "CPOptionFacetsPortlet")]')
			.getByLabel('Options');
		this.page = page;
		this.pageLabel = page
			.getByTestId('layoutHref')
			.getByLabel('Specification Facet Page');
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
		this.selectSpecificationFacetPageInput = page
			.getByTestId('selectLayout')
			.getByLabel('Select Specification Facet Page');
		this.specificationFacetConfigurationEditButton = page
			.locator(
				'//section[contains(@id, "CPSpecificationOptionFacetsPortlet")]'
			)
			.getByLabel('Options');
		this.specificationFacetOrderSpecificationInput = page
			.frameLocator('iframe[id="modalIframe"]')
			.getByLabel('Order Specifications By');
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

	async addOptionFacetWidget() {
		await this.searchFormInput.click();
		await this.searchFormInput.fill('Option Facet');
		await this.addOptionFacetLabel.click();
	}

	async addRequiredFacetWidgets() {
		await this.addWidgetButton.click();
		await this.addSearchOptionsWidget();
		await this.addOptionFacetWidget();
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

	async configureOptionFacetFrequencyThreshold(value: string) {
		await this.optionFacetConfigurationEditButton.click();
		await this.configurationMenuItem.click();
		await this.displayTemplateSelect.waitFor({
			state: 'attached',
		});
		await this.frequencyThresholdInput.fill(value);
		await this.configurationSaveButton.click();
	}

	async configureSpecificationFacetFrequencyThreshold(value: string) {
		await this.specificationFacetConfigurationEditButton.click();
		await this.configurationMenuItem.click();
		await this.displayTemplateSelect.waitFor({
			state: 'attached',
		});
		await this.frequencyThresholdInput.fill(value);
		await this.configurationSaveButton.click();
	}

	async configureSpecificationFacetOrdering(value: string) {
		await this.specificationFacetConfigurationEditButton.click();
		await this.configurationMenuItem.click();
		await this.specificationFacetOrderSpecificationInput.selectOption(
			value
		);
		await this.configurationSaveButton.click();
		await this.reloadPage();
	}

	async deleteSpecificationPage() {
		await this.selectSpecificationFacetPageInput.click();
		await this.layoutsPage.deletePageButton.click();
		await this.layoutsPage.deleteLayoutModal.waitFor({
			state: 'attached',
		});
		await Promise.all([
			this.layoutsPage.deleteLayoutModal.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
						)
			),
		]);
	}

	async goto() {
		await this.layoutsPage.goto();
	}

	async goToPage() {
		await this.layoutsPage.goToPages();
		await Promise.all([
			this.pageLabel.click(),
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
