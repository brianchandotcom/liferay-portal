/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForAlert} from '../../utils/waitForAlert';

export class StyleBooksPage {
	readonly page: Page;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly importFrame: FrameLocator;

	constructor(page: Page) {
		this.page = page;
		this.searchButton = this.page.getByTitle('Search for', {exact: true});
		this.searchInput = page.getByPlaceholder('Search for');
		this.importFrame = page.frameLocator('iframe[title*="Import"]');
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

	async create(styleBookName: string, baseThemeName?: string) {
		await this.page.getByRole('button', {exact: true, name: 'Add'}).click();

		await this.page
			.getByRole('textbox', {name: 'Name'})
			.fill(styleBookName);

		if (baseThemeName) {
			await this.page.getByLabel('Create Style Book For').click();

			await this.page.getByRole('option', {name: baseThemeName}).click();
		}

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page);
	}

	async previewFragmentCollection(collectionName: string) {
		await this.page
			.getByRole('button', {name: 'Fragments'})
			.or(this.page.getByRole('button', {name: 'Pages'}))
			.or(this.page.getByRole('button', {name: 'Page Templates'}))
			.click();

		await this.page.getByRole('menuitem', {name: 'Fragments'}).click();

		await this.page.getByRole('button', {name: 'Account'}).click();

		await this.page.getByRole('menuitem', {name: collectionName}).click();
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

	async importStyleBookFile(fileName: string, filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Import',
			}),
			trigger: this.page.getByRole('button', {name: 'Options'}),
		});

		await this.importFrame.getByLabel('Select File').click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);

		await this.importFrame.getByRole('button', {name: 'Import'}).click();

		await this.page
			.getByLabel('Import')
			.getByLabel('Close', {exact: true})
			.click();
	}

	async markAsDefault(styleBookName: string) {
		await this.search(styleBookName);

		await this.page.getByLabel('More actions').click();

		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

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
			.locator('input')
			.first();

		if (section && (await input.isHidden())) {
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
