/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceAdminProductPage {
	readonly addVirtualProductFileEntryButton: Locator;
	readonly addVirtualSkuFileEntryButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly creationMenuNewButton: Locator;
	readonly generateSkusMenuItem: Locator;
	readonly managementToolbarItemLink: (productName: string) => Locator;
	readonly managementToolbarSearchInput: Locator;
	readonly modalAddButton: Locator;
	readonly modalCancelButton: Locator;
	readonly page: Page;
	readonly productSkuTableRowLink: (sku: string) => Locator;
	readonly productSkuVirtualFileEntryCancelButton: Locator;
	readonly productSkuVirtualFileEntrySaveButton: Locator;
	readonly productSkuVirtualFileEntryURLInput: Locator;
	readonly productSkuVirtualOverrideToggle: Locator;
	readonly productSkusLink: Locator;
	readonly productVirtualFileEntryCancelButton: Locator;
	readonly productVirtualFileEntrySaveButton: Locator;
	readonly productVirtualFileEntryURLInput: Locator;
	readonly productVirtualLink: Locator;
	readonly productsTableRowLink: (productName: string) => Locator;
	readonly spareProductMenuButton: Locator;
	readonly specificProductMenuLink: (productName: string) => Promise<Locator>;
	readonly validProductCheckbox: (productName: string) => Promise<Locator>;
	readonly virtualSettingsOverrideLink: Locator;

	constructor(page: Page) {
		this.addVirtualProductFileEntryButton = page
			.getByRole('button', {exact: true, name: 'Add File Entry'})
			.first();
		this.addVirtualSkuFileEntryButton = page
			.frameLocator('iframe')
			.getByRole('button', {exact: true, name: 'Add File Entry'})
			.first();
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.creationMenuNewButton = page.getByRole('button', {name: 'New'});
		this.generateSkusMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Generate All SKU Combinations',
		});
		this.managementToolbarItemLink = (productName: string) =>
			page.getByRole('link', {exact: true, name: productName});
		this.managementToolbarSearchInput = page
			.getByTestId('management-toolbar')
			.getByPlaceholder('Search', {exact: true});
		this.modalAddButton = page.getByRole('button', {name: 'Add'});
		this.modalCancelButton = page.getByRole('button', {name: 'Cancel'});
		this.page = page;
		this.productSkuTableRowLink = (sku: string) =>
			page.getByRole('link', {name: sku});
		this.productSkuVirtualFileEntryCancelButton = page
			.frameLocator('iframe')
			.frameLocator('iframe >> nth=1')
			.getByRole('button', {exact: true, name: 'Cancel'});
		this.productSkuVirtualFileEntrySaveButton = page
			.frameLocator('iframe')
			.frameLocator('iframe >> nth=1')
			.getByRole('button', {exact: true, name: 'Save'});
		this.productSkuVirtualFileEntryURLInput = page
			.frameLocator('iframe')
			.frameLocator('iframe >> nth=1')
			.getByLabel('URL');
		this.productSkuVirtualOverrideToggle = page
			.frameLocator('iframe')
			.getByLabel('Override', {exact: true});
		this.productSkusLink = page.getByRole('link', {
			exact: true,
			name: 'SKUs',
		});
		this.productVirtualFileEntryCancelButton = page
			.frameLocator('iframe >> nth=1')
			.getByRole('button', {exact: true, name: 'Cancel'});
		this.productVirtualFileEntrySaveButton = page
			.frameLocator('iframe >> nth=1')
			.getByRole('button', {exact: true, name: 'Save'});
		this.productVirtualFileEntryURLInput = page
			.frameLocator('iframe >> nth=1')
			.getByLabel('URL');
		this.productVirtualLink = page.getByRole('link', {
			exact: true,
			name: 'Virtual',
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
		this.virtualSettingsOverrideLink = page
			.frameLocator('iframe')
			.getByRole('link', {
				exact: true,
				name: 'Virtual Settings Override',
			});
	}

	async generateSkus() {
		await this.productSkusLink.click();

		if (await this.creationMenuNewButton.isHidden()) {
			await this.productSkusLink.click();
		}

		await this.creationMenuNewButton.click();
		await this.generateSkusMenuItem.click();
		await this.page.reload();
	}

	async goto(checkTabVisibility = true) {
		await this.applicationsMenuPage.goToProducts(checkTabVisibility);
	}

	async gotoProduct(productName: string, checkTabVisibility = true) {
		await this.goto(checkTabVisibility);
		await this.managementToolbarSearchInput.fill(productName);
		await this.managementToolbarSearchInput.press('Enter');
		await this.productsTableRowLink(productName).click();
	}
}
