/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../product-navigation-product-menu/ProductMenu.page';
export class DocumentLibraryPage {
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly exportImportOptionsMenuItem: Locator;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
	}

	async goto() {
		await this.productMenuPage.goToDocumentsAndMediaMenuItem();
		await this.page.waitForLoadState();
	}

	async editEntry(entryTitle: string) {
		await this.page
			.locator(`.card-body:has-text('${entryTitle}')`)
			.getByLabel('More actions')
			.click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async openNewButton() {
		await this.page.getByRole('button', {name: 'New'}).click();
	}

	async openCreateAIImage() {
		await this.openNewButton();

		await this.page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
}
