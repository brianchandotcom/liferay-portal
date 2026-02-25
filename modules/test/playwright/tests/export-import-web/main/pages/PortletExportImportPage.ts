/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page} from '@playwright/test';

import {getTempDir} from '../../../../utils/temp';

export class PortletExportImportPage {
	readonly frame: FrameLocator;
	readonly page: Page;

	constructor(page: Page) {
		this.frame = page.frameLocator('iframe[title="Export \\/ Import"]');
		this.page = page;
	}

	async exportLARFile(fileNamePattern: RegExp): Promise<string> {
		await this.frame.getByRole('button', {name: 'Export'}).click();

		const downloadLink = this.frame
			.getByRole('cell', {name: fileNamePattern})
			.first()
			.getByRole('link');

		const downloadPromise = this.page.waitForEvent('download');

		await downloadLink.click();

		const download = await downloadPromise;

		const filePath = `${getTempDir()}/${download.suggestedFilename()}`;

		await download.saveAs(filePath);

		return filePath;
	}

	async importLARFile(filePath: string): Promise<void> {
		await this.frame.getByRole('link', {name: 'Import'}).click();

		await this.frame.locator('input[type="file"]').setInputFiles(filePath);
		await this.frame.getByRole('button', {name: 'Continue'}).click();

		await this.frame.getByRole('button', {name: 'Import'}).click();
	}
}
