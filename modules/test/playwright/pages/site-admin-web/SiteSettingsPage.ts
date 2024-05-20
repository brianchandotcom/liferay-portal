/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';

export class SiteSettingsPage {
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
	}

	async goto() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToSiteSettings();
	}

	async goToSiteSetting(categoryKey: string, configurationName?: string) {
		await this.goto();
		await this.page
			.getByRole('link', {
				exact: true,
				name: categoryKey,
			})
			.click();
		if (configurationName) {
			await this.page
				.getByRole('menuitem', {
					exact: true,
					name: configurationName,
				})
				.click();
		}
	}
}
