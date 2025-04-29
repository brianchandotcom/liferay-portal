/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {TagsAdminPage} from './TagsAdminPage';

export class TagsEditPage {
	readonly nameInput: Locator;
	readonly saveButton: Locator;
	readonly page: Page;
	readonly tagsAdminPage: TagsAdminPage;

	constructor(page: Page) {
		this.nameInput = page.getByPlaceholder('Name');
		this.saveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.tagsAdminPage = new TagsAdminPage(page);
		this.page = page;
	}

	async add(title: string, siteUrl?: Site['friendlyUrlPath']) {
		await this.tagsAdminPage.goto(siteUrl);
		await this.tagsAdminPage.gotoAdd();

		await this.nameInput.fill(title);
		await this.saveButton.click();
	}
}
