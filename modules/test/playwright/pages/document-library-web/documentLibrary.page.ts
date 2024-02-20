/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../product-navigation-product-menu/ProductMenu.page';
import {AICreatorInstanceSettingsPage} from './aiCreatorSettings.page';
import {GogoShellPage} from './gogoShell.page';

export class DocumentLibraryPage {
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly exportImportOptionsMenuItem: Locator;
	readonly productMenuPage: ProductMenuPage;
	readonly aiCreatorPageSettingsPage: AICreatorInstanceSettingsPage;
	readonly gogoShellPage: GogoShellPage;

	constructor(page: Page) {
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.aiCreatorPageSettingsPage = new AICreatorInstanceSettingsPage(
			page
		);
		this.gogoShellPage = new GogoShellPage(page);
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

	async new() {
		await this.page.getByRole('button', {name: 'New'}).click();
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}

	async enableAICreator() {
		await this.aiCreatorPageSettingsPage.enableDalleCreateImages();
	}

	async disableAICreator() {
		await this.aiCreatorPageSettingsPage.disableDalleCreateImages();
	}

	async addApiKey() {
		await this.aiCreatorPageSettingsPage.addApiKey();
	}

	async removeApiKey() {
		await this.aiCreatorPageSettingsPage.removeApiKey();
	}

	async addGogoShellCommand(command: string) {
		await this.gogoShellPage.addCommand(command);
	}
}
