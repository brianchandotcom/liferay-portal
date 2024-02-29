/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceProductAdminPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly creationMenuNewButton: Locator;
	readonly generateSkusMenuItem: Locator;
	readonly managementToolbarSearchInput: Locator;
	readonly page: Page;
	readonly productSkusLink: Locator;
	readonly productsTableRowLink: (productName: string) => Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.creationMenuNewButton = page.getByLabel('New', {exact: true});
		this.generateSkusMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Generate All SKU Combinations',
		});
		this.managementToolbarSearchInput = page
			.getByTestId('management-toolbar')
			.getByPlaceholder('Search', {exact: true});
		this.productSkusLink = page.getByRole('link', {
			exact: true,
			name: 'SKUs',
		});
		this.productsTableRowLink = (productName: string) =>
			page.getByRole('link', {exact: true, name: productName});
	}

	async generateSkus() {
		await this.productSkusLink.click();

		if (await this.creationMenuNewButton.isHidden()) {
			await this.productSkusLink.click();
		}

		await this.creationMenuNewButton.click();
		await this.generateSkusMenuItem.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToProducts();
	}

	async gotoProduct(productName: string) {
		await this.goto();
		await this.managementToolbarSearchInput.fill(productName);
		await this.managementToolbarSearchInput.press('Enter');
		await this.productsTableRowLink(productName).click();
	}
}
