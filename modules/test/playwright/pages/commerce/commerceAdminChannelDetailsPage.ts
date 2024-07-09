/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
export class CommerceAdminChannelDetailsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly channelNameLink: (channelName: string) => Locator;
	readonly countryTab: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly showSeparateOrderItemsToggle: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.channelNameLink = (channelName: string) =>
			page.getByRole('link', {
				exact: true,
				name: channelName,
			});
		this.countryTab = page.getByRole('link', {name: 'Countries'});
		this.saveButton = page.getByRole('link', {name: 'Save'});
		this.showSeparateOrderItemsToggle = page.getByLabel(
			'Show Separate Order Items'
		);
		this.page = page;
	}

	async goto(checkTabVisibility = true) {
		await this.applicationsMenuPage.goToCommerceChannels(
			checkTabVisibility
		);
	}

	async goToCountries() {
		await this.countryTab.click();
	}
}
