/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import path from 'path';

export class FormViewPage {
	readonly languageSelector: Locator;
	readonly page: Page;
	readonly unselectFile: Locator;
	readonly uploadInput: Locator;

	constructor(page: Page) {
		this.languageSelector = page.getByRole('button', {
			name: 'Select a language, current',
		});
		this.page = page;
		this.unselectFile = page.getByLabel('Unselect File');
		this.uploadInput = page.getByLabel('Upload');
	}

	/**
	 * Retrieves the fileEntryId from the uploaded file.
	 * It extracts the file entry value from the input field, parses it from JSON,
	 * and returns the fileEntryId.
	 *
	 * @param page The Playwright page instance
	 * @returns The extracted fileEntryId
	 */

	async getFileEntryId(page: Page): Promise<string> {
		const fileEntryIdValue = await page.getAttribute(
			'input[data-field-name^="undefined"]',
			'value'
		);

		let parsedFileEntryId = JSON.parse(fileEntryIdValue);

		if (typeof parsedFileEntryId === 'string') {
			parsedFileEntryId = JSON.parse(parsedFileEntryId);
		}

		return parsedFileEntryId.fileEntryId;
	}

	async uploadFile(page: Page, dirName: string, filePath: string) {
		const fileChooserPromise = page.waitForEvent('filechooser');

		await page.getByText('Select').click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(dirName, '/dependencies', filePath)
		);
	}
}
