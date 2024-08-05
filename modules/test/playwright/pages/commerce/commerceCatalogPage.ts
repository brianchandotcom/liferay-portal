/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceCatalogPage {
	readonly catalogSearch: Locator;
	readonly clearSearchButton: Locator;
	readonly globalSearchBarButton: Locator;
	readonly globalSearchBarInput: Locator;
	readonly globalSearchBarCommerceItemLink: (
		text: string
	) => Promise<Locator>;
	readonly globalSearchBarCommerceOrderLink: (
		orderId: string,
		accountName: string
	) => Promise<Locator>;
	readonly page: Page;
	readonly productLink: (productName: string) => Promise<Locator>;

	constructor(page: Page) {
		this.catalogSearch = page.getByTestId('searchInput');
		this.clearSearchButton = page.getByRole('button', {
			name: 'Clear Search',
		});
		this.globalSearchBarButton = page
			.locator('.commerce-topbar-button__icon')
			.first();
		this.globalSearchBarInput = page
			.locator('#search-bar')
			.getByPlaceholder('Search');
		this.globalSearchBarCommerceItemLink = async (text) => {
			return this.page.getByRole('link', {name: text});
		};
		this.globalSearchBarCommerceOrderLink = async (
			orderId: string,
			accountName: string
		) => {
			return this.page
				.getByRole('link', {name: orderId})
				.filter({hasText: accountName});
		};
		this.page = page;
		this.productLink = async (productName: string) => {
			return this.page.getByRole('link', {
				exact: true,
				name: productName,
			});
		};
	}

	async search(query: string) {
		await this.globalSearchBarInput.waitFor({state: 'visible'});
		await this.globalSearchBarInput.fill(query);
	}

	async focusGlobalSearchBarInput() {
		await this.page.waitForSelector(
			'.commerce-topbar-button.js-toggle-search'
		);
		await this.globalSearchBarButton.click();
	}
}
