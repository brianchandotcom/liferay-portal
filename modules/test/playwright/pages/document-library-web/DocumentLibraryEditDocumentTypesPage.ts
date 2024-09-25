/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DocumentLibraryPage} from './DocumentLibraryPage';

export class DocumentLibraryEditDocumentTypesPage {
	readonly documentLibraryPage: DocumentLibraryPage;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly titleSelector: Locator;

	constructor(page: Page) {
		this.documentLibraryPage = new DocumentLibraryPage(page);
		this.page = page;
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.titleSelector = page.getByPlaceholder('Untitled');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.documentLibraryPage.goto(siteUrl);
		await this.documentLibraryPage.changeTab('Document Types');
		await this.documentLibraryPage.openNewDLTypeButton();
	}

	async addField(type: string) {
		await this.goto();
		await this.page.getByTitle(type).dblclick();
	}

	async createNewDLTypeWithNumericField(
		title: string,
		siteUrl?: Site['friendlyUrlPath']
	) {
		await this.goto(siteUrl);
		await this.addField('Numeric');
		await this.titleSelector.fill(title);
		await this.saveButton.click();
	}

	async createNewDLTypeWithUploadField(
		title: string,
		siteUrl?: Site['friendlyUrlPath']
	) {
		await this.goto(siteUrl);
		await this.addField('Upload');
		await this.titleSelector.fill(title);
		await this.saveButton.click();
	}
}
