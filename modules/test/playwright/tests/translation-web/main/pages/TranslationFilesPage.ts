/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Download, Locator, Page, expect} from '@playwright/test';

type ExportedFile = {
	fileName: string;
	filePath: string;
};

export class TranslationFilesPage {
	readonly page: Page;

	readonly doneButton: Locator;
	readonly exportButton: Locator;
	readonly fileInput: Locator;
	readonly uploadAnotherFileButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.doneButton = page.getByRole('link', {exact: true, name: 'Done'});
		this.exportButton = page.getByRole('button', {
			exact: true,
			name: 'Export',
		});
		this.fileInput = page.locator('input[type="file"]');
		this.uploadAnotherFileButton = page.getByRole('link', {
			name: 'Upload Another File',
		});
	}

	async download(trigger: Locator): Promise<ExportedFile> {
		const downloadPromise = this.page.waitForEvent('download');

		await trigger.click();

		const download: Download = await downloadPromise;

		return {
			fileName: download.suggestedFilename(),
			filePath: await download.path(),
		};
	}

	async exportTranslation({
		format,
		languages,
	}: {
		format?: 'XLIFF 1.2' | 'XLIFF 2.0';
		languages: string[];
	}): Promise<ExportedFile> {
		if (format) {
			await this.page
				.getByLabel('Export File Format')
				.selectOption({label: format});
		}

		for (const language of languages) {
			await this.page.getByLabel(language, {exact: true}).check();
		}

		await expect(this.exportButton).toBeEnabled();

		return this.download(this.exportButton);
	}

	async importTranslation(
		filePaths: string[],
		action: 'Publish' | 'Save as Draft' | 'Submit for Workflow' = 'Publish'
	) {
		await this.fileInput.setInputFiles(filePaths);

		await this.page
			.getByRole('button', {exact: true, name: action})
			.click();

		await this.doneButton.waitFor();
	}

	static translateXliff(
		xliff: string,
		translations: {[unitId: string]: string}
	) {
		let translatedXliff = xliff;

		for (const [unitId, translation] of Object.entries(translations)) {
			const unitRegExp = new RegExp(
				`(<(?:trans-)?unit id="${unitId}"[\\s\\S]*?<target[^>]*>)[\\s\\S]*?(</target>)`
			);

			expect(translatedXliff).toMatch(unitRegExp);

			translatedXliff = translatedXliff.replace(
				unitRegExp,
				`$1<![CDATA[${translation}]]>$2`
			);
		}

		return translatedXliff;
	}
}
