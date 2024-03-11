/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class DocumentLibraryPage {
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly exportImportOptionsMenuItem: Locator;

	constructor(page: Page) {
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.documentLibrary}`
		);
	}

	async deleteAllFileEntries() {
		await this.page
			.locator('input[data-modelclassname="FileEntry"]')
			.check();
		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async editEntry(entryTitle: string) {
		await this.page
			.locator(`.card-body:has-text('${entryTitle}')`)
			.getByLabel('More actions')
			.click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async openNewButton() {
		await this.page.getByRole('button', {name: 'New'}).click();
	}

	async openCreateAIImage() {
		await this.openNewButton();

		await this.page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();
	}

	async goToCreateNewFile() {
		await this.openNewButton();

		await this.page
			.getByRole('menuitem', {
				name: 'File Upload',
			})
			.click();
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
}
