/**
 * SPDX-FileCopyrightText: (c) 2004 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerce/commerceLayoutsPage';

export class SearchBarPortletPage {
	readonly addSuggestionsButton: Locator;
	readonly cancelButton: Locator;
	readonly commerceContributorMenuItem: Locator;
	readonly commerceLayoutsPage: CommerceLayoutsPage;
	readonly configurationMenuItem: Locator;
	readonly deleteButton: Locator;
	readonly optionsButton: Locator;
	readonly page: Page;
	readonly publishButton: Locator;
	readonly saveButton: Locator;
	readonly scopeSelect: Locator;
	readonly searchBarInput: Locator;
	readonly searchBarPortletConfigurationFrame: FrameLocator;
	readonly searchSuggestionMenuItem: (itemName: string) => Locator;

	constructor(page: Page) {
		this.commerceLayoutsPage = new CommerceLayoutsPage(page);
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.optionsButton = page.locator('#column-1').getByLabel('Options');
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.searchBarInput = page.getByTestId('searchInput');
		this.searchSuggestionMenuItem = (itemName: string) =>
			page.getByRole('menuitem', {name: itemName});

		this.searchBarPortletConfigurationFrame =
			page.frameLocator('#modalIframe');

		this.addSuggestionsButton =
			this.searchBarPortletConfigurationFrame.getByLabel(
				'Suggestion Contributor'
			);
		this.cancelButton = this.searchBarPortletConfigurationFrame.getByRole(
			'button',
			{name: 'Cancel'}
		);
		this.commerceContributorMenuItem =
			this.searchBarPortletConfigurationFrame.getByRole('menuitem', {
				name: 'Commerce',
			});
		this.deleteButton =
			this.searchBarPortletConfigurationFrame.getByLabel('Delete');
		this.saveButton = this.searchBarPortletConfigurationFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.scopeSelect = this.searchBarPortletConfigurationFrame.getByLabel(
			'Scope',
			{exact: true}
		);
	}

	async addSearchBarWidget() {
		await this.commerceLayoutsPage.addWidgetToPage('Search Bar');
	}

	async openSearchBarConfiguration() {
		await this.searchBarInput.waitFor();
		await this.optionsButton.click();
		await this.configurationMenuItem.click();
	}

	async replaceSuggestionsContributorWithCommerceContributor() {
		await this.scopeSelect.selectOption('Everything');
		await this.deleteButton.click();
		await this.addSuggestionsButton.click();
		await this.commerceContributorMenuItem.click();
		await this.saveButton.click();
		await this.cancelButton.click();
	}
}
