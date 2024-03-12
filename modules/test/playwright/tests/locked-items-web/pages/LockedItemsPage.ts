/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-product-menu/ProductMenuPage';

export class LockedItemsPage {
	readonly page: Page;
	readonly lockedPagesMenuItem: Locator;
	readonly lockedPagesTitle: Locator;
	readonly pageTitle: Locator;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.page = page;
		this.lockedPagesMenuItem = page.getByRole('menuitem', {
			name: 'Pages',
		});
		this.lockedPagesTitle = page
			.locator('p.sheet-title')
			.filter({hasText: /^Pages$/});
		this.pageTitle = page.getByRole('heading', {name: 'Locked Items'});
		this.productMenuPage = new ProductMenuPage(page);
	}

	async goto() {
		await this.productMenuPage.goToLockedItemsMenuItem();
	}

	async goToLockedPages() {
		await this.goto();
		await this.lockedPagesMenuItem.click();
	}
}
