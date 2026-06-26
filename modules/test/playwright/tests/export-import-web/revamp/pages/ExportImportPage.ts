/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {zipFolder} from '../../../../utils/zip';

import type {taskStatus} from '../../main/pages/ExportImportPage';

export class ExportImportPage {
	readonly completedLabel: Locator;
	readonly continueButton: Locator;
	readonly exportButton: Locator;
	readonly exportMenuItem: Locator;
	readonly fileSelector: Locator;
	readonly importButton: Locator;
	readonly importMenuItem: Locator;
	readonly nameInput: Locator;
	readonly newButton: Locator;
	readonly page: Page;
	readonly taskStatusLabel: (
		taskName: string,
		taskStatus?: taskStatus
	) => Locator;

	constructor(page: Page) {
		this.completedLabel = page.getByText('completed');
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.exportButton = page.getByRole('button', {name: 'Export'});
		this.exportMenuItem = page.getByRole('menuitem', {
			name: 'Export',
		});
		this.fileSelector = page.getByText('Select Files');
		this.importButton = page.getByRole('button', {name: 'Import'});
		this.importMenuItem = page.getByRole('menuitem', {
			name: 'Import',
		});
		this.nameInput = page.getByRole('textbox', {name: 'Name'});
		this.newButton = page
			.getByRole('button', {exact: true, name: 'New'})
			.first();
		this.page = page;
		this.taskStatusLabel = (taskName, taskStatus = 'success') => {
			const taskStatusTexts: Record<taskStatus, string> = {
				completedWithErrors: 'Completed with errors',
				success: 'Successful',
			};

			return this.page
				.locator('tr', {hasText: taskName})
				.locator('.cell-status')
				.getByText(taskStatusTexts[taskStatus], {exact: true});
		};
	}

	async export(name: string) {
		await this.newButton.click();

		await this.nameInput.fill(name);

		await this.exportButton.click();
	}

	async import(folderPath: string, name: string) {
		await this.nameInput.fill(name);

		await this.selectFile(folderPath);

		await this.completedLabel.waitFor();

		await this.continueButton.click();

		await this.continueButton.click();

		await this.importButton.waitFor();

		await this.importButton.click();

		await this.taskStatusLabel(name).waitFor();
	}

	async selectFile(folderPath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.fileSelector.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			folderPath.endsWith('.lar')
				? await zipFolder(folderPath)
				: folderPath
		);
	}
}
