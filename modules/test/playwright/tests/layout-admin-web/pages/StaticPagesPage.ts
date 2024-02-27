/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-product-menu/ProductMenuPage';

export class StaticPagesPage {
	readonly page: Page;

	readonly productMenuPage: ProductMenuPage;
	readonly utilityPagesLink: Locator;

	constructor(page: Page) {
		this.page = page;

		this.productMenuPage = new ProductMenuPage(page);
		this.utilityPagesLink = page.getByRole('link', {name: 'Utility Pages'});
	}

	async goto() {
		await this.productMenuPage.goToPagesMenuItem();
	}

	async goToUtilityPages() {
		await this.goto();
		await this.utilityPagesLink.click();
	}
}
