/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceAdminChannelDetailsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly countryTab: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.countryTab = page.getByRole('link', {name: 'Countries'});
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceChannels();
	}

	async goToCountries() {
		await this.countryTab.click();
	}
}
