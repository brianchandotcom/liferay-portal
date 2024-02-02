/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceAdminProductPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly creationMenuNewButton: Locator;
	readonly generateSkusMenuItem: Locator;
	readonly managementToolbarSearchInput: Locator;
	readonly modalAddButton: Locator;
	readonly modalCancelButton: Locator;
	readonly page: Page;
	readonly productRelationsLink: Locator;
	readonly productSkusLink: Locator;
	readonly productsTableRowLink: (productName: string) => Locator;
	readonly spareProductMenuButton: Locator;
	readonly specificProductMenuLink: (productName: string) => Promise<Locator>;
	readonly validProductCheckbox: (productName: string) => Promise<Locator>;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.creationMenuNewButton = page.getByRole('button', {name: 'New'});
		this.generateSkusMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Generate All SKU Combinations',
		});
		this.managementToolbarSearchInput = page
			.getByTestId('management-toolbar')
			.getByPlaceholder('Search', {exact: true});
		this.modalAddButton = page.getByRole('button', {name: 'Add'});
		this.modalCancelButton = page.getByRole('button', {name: 'Cancel'});
		this.page = page;
		this.productSkusLink = page.getByRole('link', {
			exact: true,
			name: 'SKUs',
		});
		this.productsTableRowLink = (productName: string) =>
			page.getByRole('link', {exact: true, name: productName});
		this.spareProductMenuButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Add Spare Product',
		});
		this.specificProductMenuLink = async (productName: string) => {
			return page.getByRole('link', {name: productName});
		};
		this.validProductCheckbox = async (productName: string) => {
			return page
				.frameLocator('#modalIframe')
				.getByTestId('row')
				.filter({hasText: productName})
				.getByRole('checkbox', {disabled: false});
		};
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
