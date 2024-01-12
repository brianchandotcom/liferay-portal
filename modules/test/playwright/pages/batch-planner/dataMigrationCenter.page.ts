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
		this.fileSelector = page.locator(
			'#_com_liferay_batch_planner_web_internal_portlet_BatchPlannerPortlet_importFile'
		);
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

	async importFile(
		entitType: string,
		filePath: string,
		importStrategy: string,
		updateStrategy: string
	) {
		await this.selectFile(filePath);
		await this.selectImportEntityType(entitType);
		await this.importStrategySelector.selectOption(importStrategy);
		await this.updateStrategySelector.selectOption(updateStrategy);

		if ((await this.scopeSelector.all()).length) {
			this.scopeSelector.selectOption('Liferay DXP');
		}

		await this.nextButton.click();
		await this.startImportButton.click();
	}

	async selectImportEntityType(entityTypeName: string) {
		await this.entityTypeSelector.selectOption(entityTypeName);
	}

	async selectFile(filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');
		await this.fileSelector.click();
		const fileChooser = await fileChooserPromise;
		await fileChooser.setFiles(filePath);
	}
}
