/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountWebsitePage {
	readonly page: Page;
	readonly saveButton: Locator;
	readonly urlInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.urlInput = page.getByRole('textbox', {name: 'URL'});
	}

	async updateWebsite(urlInput: string) {
		await this.urlInput.fill(urlInput);
		await this.saveButton.click();
	}
}
