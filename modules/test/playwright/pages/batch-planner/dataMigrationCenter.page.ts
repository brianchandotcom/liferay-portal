/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/applicationsMenu.page';

export class DataMigrationCenterPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly newButton: Locator;
	readonly entityTypeSelector: Locator;
	readonly importFileMenuItem: Locator;
	readonly importStrategySelector: Locator;
	readonly fileSelector: Locator;
	readonly nextButton: Locator;
	readonly scopeSelector: Locator;
	readonly startImportButton: Locator;
	readonly updateStrategySelector: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
		this.newButton = page.getByRole('button', {name: 'New'});
		this.entityTypeSelector = page.getByLabel('Entity Type');
		this.importFileMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Import File',
		});
		this.importStrategySelector = page.getByLabel('Import Strategy');
		this.fileSelector = page.getByLabel('File ( .csv, .json, .jsonl)');
		this.nextButton = page.getByRole('button', {name: 'Next'});
		this.scopeSelector = page.getByLabel('Scope');
		this.startImportButton = page.getByTestId('start-import');
		this.updateStrategySelector = page.getByLabel('Update Strategy');
	}

	async goto() {
		await this.applicationsMenuPage.goToDataMigrationCenter();
	}

	async goToImportFile() {
		await this.newButton.click();
		await this.importFileMenuItem.click();
	}

	async importFile(entitType, filePath, importStrategy, updateStrategy) {
		await this.selectFile(filePath);
		await this.selectImportEntityType(entitType);
		await this.importStrategySelector.selectOption(importStrategy);
		await this.updateStrategySelector.selectOption(updateStrategy);

		if ((await this.scopeSelector.all()).length) {
			this.scopeSelector.selectOption('Liferay DXP');
		}

		await this.page.waitForTimeout(2000);

		await this.nextButton.click();
		await this.startImportButton.click();
	}

	async selectImportEntityType(entityTypeName) {
		await this.entityTypeSelector.selectOption(entityTypeName);
	}

	async selectFile(filePath) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');
		await this.fileSelector.click();
		const fileChooser = await fileChooserPromise;
		await fileChooser.setFiles(filePath);
	}
}
