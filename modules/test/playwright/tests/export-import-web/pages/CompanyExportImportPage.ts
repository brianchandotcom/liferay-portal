/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';
import path from 'path';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ExportImportPage} from './ExportImportPage';

export class CompanyExportImportPage {
	readonly page: Page;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly exportImportPage: ExportImportPage;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.exportImportPage = new ExportImportPage(page);
	}

	async export(
		itemLabel: string,
		includePermissions: boolean = false,
		taskName?: string
	): Promise<string> {
		await this.applicationsMenuPage.goToExport();

		await this.page.getByTestId('creationMenuNewButton').nth(1).click();

		await this.page.getByLabel(itemLabel).click();

		taskName
			? await this.exportImportPage.title.fill(taskName)
			: (taskName = 'Export');

		if (includePermissions) {
			await this.exportImportPage.exportPermissionsButton.click();
		}

		await this.exportImportPage.exportButton.click();

		await this.page
			.getByText(taskName)
			.locator('../../..')
			.getByText('Successful')
			.waitFor();

		taskName += '-';

		return await this.exportImportPage.downloadExportProcess(taskName);
	}

	async goToImportOptions(filePath: string) {
		await this.applicationsMenuPage.goToImport();

		await this.exportImportPage.newImportButton.click();

		await this.page.locator('input[type="file"]').setInputFiles(filePath);

		await this.exportImportPage.continueButton.click();
	}

	async import(
		filePath: string,
		includePermissions: boolean = false
	): Promise<void> {
		await this.applicationsMenuPage.goToImport();

		await this.exportImportPage.newImportButton.click();

		await this.page.locator('input[type="file"]').setInputFiles(filePath);

		await this.exportImportPage.continueButton.click();

		if (includePermissions) {
			await this.exportImportPage.importPermissionsButton.click();
		}

		await this.exportImportPage.importButton.click();

		const fileName = path.basename(filePath);
		await this.page
			.getByText(fileName)
			.locator('../../..')
			.getByText('Successful')
			.waitFor();
	}
}
