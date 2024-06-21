/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceCatalogPage {
	readonly clearSearchButton: Locator;
	readonly globalSearchBarButton: Locator;
	readonly globalSearchBarInput: Locator;
	readonly globalSearchBarCommerceItemLink: (
		text: string
	) => Promise<Locator>;
	readonly page: Page;

	constructor(page: Page) {
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
		this.page = page;
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
