/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForAlert} from '../../utils/waitForAlert';

export class StyleBooksPage {
	readonly page: Page;
	readonly searchButton: Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.searchButton = this.page.getByTitle('Search for', {exact: true});
		this.searchInput = page.getByPlaceholder('Search for');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.styleBooks}`
		);
	}

	async changePreviewPage(currentPage: string, nextPage: string) {
		await this.page.getByRole('button', {name: currentPage}).click();

		await this.page.getByRole('menuitem', {name: nextPage}).click();
	}

	async create(styleBookName: string) {
		await this.page.getByRole('button', {exact: true, name: 'Add'}).click();

		await this.page.getByRole('textbox').fill(styleBookName);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await this.page
			.getByText('Success:Your request completed successfully.')
			.waitFor();

		const loadingAnimation = this.page.locator(
			'.style-book-editor__page-preview .loading-animation'
		);

		await loadingAnimation.waitFor();
		await loadingAnimation.waitFor({state: 'hidden'});
	}

	async delete(styleBookName: string) {
		await this.search(styleBookName);

		await this.page.getByLabel('More actions').click();

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();

		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async edit(styleBookName: string) {
		await this.search(styleBookName);

		await this.page.getByLabel('More actions').click();

		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async markAsDefault(styleBookName: string) {
		await this.search(styleBookName);

		await this.page.getByLabel('More actions').click();

		await this.page
			.getByRole('menuitem', {exact: false, name: 'Mark as Default'})
			.click();
	}

	async publish() {
		await this.page.getByRole('button', {name: 'Publish'}).click();

		await this.page
			.getByRole('dialog')
			.getByRole('button', {name: 'Publish'})
			.click();

		await waitForAlert(this.page);
	}

	async search(styleBookName: string) {
		await this.searchInput.fill(styleBookName);

		await this.searchButton.click();

		await expect(
			this.page.getByText(`1 Result Found for "${styleBookName}"`)
		).toBeVisible();
	}

	async selectTokenCategory(category: string) {
		await this.page
			.locator('.style-book-editor__sidebar-content .form-control-select')
			.click();

		await this.page.getByText(category).click();
	}

	async updateTokenInput(label: string, value: string, section?: string) {
		const parentElement = section
			? this.page.locator('.panel').filter({hasText: section})
			: this.page;

		const input = parentElement
			.locator('.form-group')
			.filter({hasText: label})
			.locator('input');

		if (section && input.isHidden()) {
			await this.page.getByRole('button', {name: section}).click();
		}

		await fillAndClickOutside(this.page, input, value);
	}

	async updateTokenInputColor(
		label: string,
		colorHEX: string,
		section?: string
	) {
		const parentElement = section
			? this.page.locator('.panel').filter({hasText: section})
			: this.page;

		const labelLocator = '[aria-label="' + label + '"]';

		const colorInput = parentElement
			.locator(labelLocator)
			.locator('.layout__color-picker__input');

		await fillAndClickOutside(this.page, colorInput, colorHEX);
	}

	async waitForAutoSave() {
		const statusText = this.page.locator('.style-book-editor__status-text');

		await statusText.getByText('Saved').waitFor();
	}
}
