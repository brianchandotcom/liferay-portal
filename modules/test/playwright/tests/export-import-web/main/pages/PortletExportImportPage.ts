/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {getTempDir} from '../../../../utils/temp';

export class PortletExportImportPage {
	readonly frame: FrameLocator;
	readonly page: Page;

	readonly exportButton: Locator;
	readonly exportDeletionsButton: Locator;
	readonly importDeletionsButton: Locator;

	constructor(page: Page) {
		this.frame = page.frameLocator('iframe[title="Export \\/ Import"]');
		this.page = page;

		this.exportButton = this.frame.getByRole('button', {name: 'Export'});
		this.exportDeletionsButton = this.frame.getByLabel(
			'Export Individual Deletions'
		);
		this.importDeletionsButton = this.frame.getByLabel(
			'Replicate Individual Deletions'
		);
	}

	async exportLARFile({
		fileNamePattern,
		includeDeletions = false,
	}: {
		fileNamePattern: RegExp;
		includeDeletions?: boolean;
	}): Promise<{filePath: string}> {
		if (includeDeletions) {
			await this.exportDeletionsButton.check();
		}

		await this.exportButton.click();

		const downloadLink = this.frame
			.getByRole('cell', {name: fileNamePattern})
			.first()
			.getByRole('link');

		const downloadPromise = this.page.waitForEvent('download');

		await downloadLink.click();

		const download = await downloadPromise;

		const filePath = `${getTempDir()}/${download.suggestedFilename()}`;

		await download.saveAs(filePath);

		return {filePath};
	}

	async importLARFile({
		filePath,
		includeDeletions = false,
	}: {
		filePath: string;
		includeDeletions?: boolean;
	}): Promise<void> {
		await this.frame.getByRole('link', {name: 'Import'}).click();

		await this.frame.locator('input[type="file"]').setInputFiles(filePath);
		await this.frame.getByRole('button', {name: 'Continue'}).click();

		if (includeDeletions) {
			await this.importDeletionsButton.check();
		}

		await this.frame.getByRole('button', {name: 'Import'}).click();

		await expect(
			this.frame.getByRole('cell', {name: 'Successful'}).first()
		).toBeVisible();
	}
}
