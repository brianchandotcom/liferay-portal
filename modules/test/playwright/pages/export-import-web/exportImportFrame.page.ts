/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page, expect} from '@playwright/test';

import {zipFolder} from '../../utils/util';

export class ExportImportFramePage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async importLARFile(folderPath: string) {
		await this.page.frame({url: /.*localhost.*/}).waitForLoadState();

		const exportImportFrame = this.page.frameLocator(
			'iframe[title="Export \\/ Import"]'
		);

		await exportImportFrame.getByRole('link', {name: 'Import'}).click();

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await exportImportFrame
			.getByRole('button', {name: 'Select File'})
			.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(await zipFolder(folderPath));

		await exportImportFrame.getByRole('button', {name: 'Continue'}).click();
		await exportImportFrame.getByRole('button', {name: 'Import'}).click();
		await expect(
			exportImportFrame
				.getByTestId('row')
				.nth(0)
				.locator('.background-task-status-successful')
		).toBeVisible();
	}

	async close() {
		await this.page.getByLabel('close', {exact: true}).click();
	}
}
