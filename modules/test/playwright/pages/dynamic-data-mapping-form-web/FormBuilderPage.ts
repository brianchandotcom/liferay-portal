/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {FormsPage} from './FormsPage';

export class FormBuilderPage {
	readonly formPage: FormsPage;
	readonly formTitle: Locator;
	readonly page: Page;
	readonly previewButton: Locator;
	readonly newFormHeading: Locator;

	constructor(page: Page) {
		this.formTitle = page.getByPlaceholder('Untitled Form');
		this.formPage = new FormsPage(page);
		this.page = page;
		this.previewButton = page.getByRole('button', {name: 'Preview'});
		this.newFormHeading = page.getByRole('heading', {name: 'New Form'});
	}

	async clickPreviewButton() {
		await this.previewButton.click();
	}

	async fillFormTitle(title: string) {
		await this.formTitle.fill(title);
	}

	async goToNew() {
		await this.formPage.goTo();

		await expect(this.formPage.formsHeader).toBeVisible();

		await this.formPage.clickManagementToolbarNewButton();
	}
}
