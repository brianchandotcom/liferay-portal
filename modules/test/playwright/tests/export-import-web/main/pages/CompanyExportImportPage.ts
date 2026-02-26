/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';
import path from 'path';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';
import {openFieldset} from '../../../../utils/openFieldset';
import {ExportImportPage, taskStatus} from './ExportImportPage';

export class CompanyExportImportPage {
	readonly page: Page;
	readonly globalMenuPage: GlobalMenuPage;
	readonly exportImportPage: ExportImportPage;

	constructor(page: Page) {
		this.page = page;
		this.globalMenuPage = new GlobalMenuPage(page);
		this.exportImportPage = new ExportImportPage(page);
	}

	async goToImportOptions(filePath: string) {
		await this.globalMenuPage.goToApplications('Import');

		await this.exportImportPage.newImportButton.click();

		await this.page.locator('input[type="file"]').setInputFiles(filePath);

		await this.exportImportPage.continueButton.click();
	}

	async import({
		expectedUploadErrorMessage,
		filePath,
		includePermissions = false,
		taskStatus = 'success',
		useCurrentUser = false,
	}: {
		expectedUploadErrorMessage?: string;
		filePath: string;
		includePermissions?: boolean;
		taskStatus?: taskStatus;
		useCurrentUser?: boolean;
	}): Promise<{fileName: string}> {
		await this.globalMenuPage.goToApplications('Import');

		await this.exportImportPage.newImportButton.click();

		await this.page.locator('input[type="file"]').setInputFiles(filePath);

		if (expectedUploadErrorMessage) {
			await expect(
				this.page.getByText(expectedUploadErrorMessage)
			).toBeVisible();

			return;
		}

		await this.exportImportPage.continueButton.click();

		if (includePermissions) {
			await this.exportImportPage.importPermissionsCheckbox.check();
		}

		if (useCurrentUser) {
			openFieldset(this.page, 'Authorship of the Content');

			await this.exportImportPage.useCurrentUserAsAuthorCheckbox.check();
		}

		await this.exportImportPage.importButton.click();

		const fileName = path.basename(filePath);

		await this.exportImportPage
			.taskStatusLabel(fileName, taskStatus)
			.waitFor();

		return {fileName};
	}
}
