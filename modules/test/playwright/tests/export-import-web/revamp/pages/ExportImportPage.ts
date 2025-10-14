/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {zipFolder} from '../../../../utils/zip';

export class ExportImportPage {
	readonly continueButton: Locator;
	readonly currentAndPreviousTab: Locator;
	readonly exportButton: Locator;
	readonly exportMenuItem: Locator;
	readonly fileSelector: Locator;
	readonly importButton: Locator;
	readonly importMenuItem: Locator;
	readonly newExportButton: Locator;
	readonly newExportTab: Locator;
	readonly newImportTab: Locator;
	readonly page: Page;
	readonly title: Locator;

	constructor(page: Page) {
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.currentAndPreviousTab = page.getByRole('link', {
			name: 'Current and Previous',
		});
		this.exportButton = page.getByRole('button', {name: 'Export'});
		this.exportMenuItem = page.getByRole('menuitem', {
			name: 'Export',
		});
		this.fileSelector = page.getByRole('button', {name: 'Select File'});
		this.importButton = page.getByRole('button', {name: 'Import'});
		this.importMenuItem = page.getByRole('menuitem', {
			name: 'Import',
		});
		this.newExportButton = page.getByRole('link', {name: 'Custom Export'});
		this.newExportTab = page.getByRole('link', {
			name: 'New Export Process',
		});
		this.newImportTab = page.getByRole('link', {
			name: 'New Import Process',
		});
		this.page = page;
		this.title = page.getByLabel('Export the selected data to');
	}

	taskSuccessLabel(taskName: string) {
		return this.page
			.locator('[data-qa-id="row"]', {hasText: taskName})
			.getByText('Successful');
	}

	async export(title: string) {
		await this.title.fill(title);
		await this.exportButton.click();
	}

	async import(folderPath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.fileSelector.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(await zipFolder(folderPath));

		await this.continueButton.click();

		await this.importButton.waitFor({state: 'visible'});

		await this.importButton.click();

		await this.page.waitForSelector(
			'[data-qa-id=row]:nth-of-type(1) .background-task-status-successful',
			{state: 'visible'}
		);
	}
}
