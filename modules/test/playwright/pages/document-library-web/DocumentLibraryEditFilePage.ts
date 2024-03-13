/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DocumentLibraryPage} from './DocumentLibraryPage';

export class DocumentLibraryEditFilePage {
	readonly documentLibraryPage: DocumentLibraryPage;
	readonly page: Page;
	readonly publishDateSelector: Locator;
	readonly saveButton: Locator;
	readonly scheduleButton: Locator;
	readonly titleSelector: Locator;

	constructor(page: Page) {
		this.documentLibraryPage = new DocumentLibraryPage(page);
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.scheduleButton = page.getByRole('button', {name: 'Schedule'});
		this.page = page;
		this.publishDateSelector = page.getByLabel('Publish Date');
		this.titleSelector = page.getByLabel('Title');
	}

	async goto() {
		await this.documentLibraryPage.goto();

		await this.documentLibraryPage.goToCreateNewFile();
	}

	async publishNewFileWithScheduleDate(scheduleDate: string, title: string) {
		await this.goto();

		await this.titleSelector.fill(title);

		const isClosed =
			!(await this.scheduleButton.getAttribute('aria-expanded')) ||
			(await this.scheduleButton.getAttribute('aria-expanded')) ===
				'false';

		if (isClosed) {
			await this.scheduleButton.click();
		}

		await this.publishDateSelector.click();
		await this.publishDateSelector.fill(scheduleDate);
		await this.publishDateSelector.click();
		await this.publishDateSelector.press('Escape');
		await this.page
			.locator(
				'[id="_com_liferay_document_library_web_portlet_DLAdminPortlet_displayDateTime"]'
			)
			.fill('00:00');

		await this.saveButton.click();
	}
}
