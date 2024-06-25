/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceThemeMiniumPage {
	readonly goToMiniumLink: Locator;
	readonly globalSearchButton: Locator;
	readonly globalSearchClearButton: Locator;
	readonly globalSearchInput: Locator;
	readonly globalSearchSuggestions: Locator;
	readonly globalSearchSuggestionsItem: (text: string) => Promise<Locator>;
	readonly myProfileItemMenu: Locator;
	readonly page: Page;
	readonly stickerUserNav: Locator;

	constructor(page: Page) {
		this.goToMiniumLink = page.getByRole('link', {name: 'Go to Minium'});
		this.page = page;
		this.globalSearchButton = page.locator('.commerce-topbar-button');
		this.globalSearchClearButton = page.getByLabel('Clear Search');
		this.globalSearchInput = page
			.locator('#search-bar')
			.getByPlaceholder('Search');
		this.globalSearchSuggestions = page.locator('.commerce-suggestions');
		this.globalSearchSuggestionsItem = async (text: string) => {
			return this.globalSearchSuggestions.getByRole('link', {name: text});
		};
		this.myProfileItemMenu = page.getByRole('link', {name: 'My Profile'});
		this.stickerUserNav = page.locator('.sticker').first();
	}
}
