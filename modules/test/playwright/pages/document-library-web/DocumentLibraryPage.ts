/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';

export class DocumentLibraryPage {
	readonly exportImportOptionsMenuItem: Locator;
	readonly optionsMenu: Locator;
	readonly orderMenu: Locator;
	readonly page: Page;
	readonly searchButton: Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.orderMenu = page.getByLabel('Order');
		this.page = page;
		this.searchButton = page.getByRole('button', {
			name: 'Search for',
		});
		this.searchInput = page.getByRole('searchbox', {
			name: 'Search for:',
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.documentLibrary}`
		);
	}

	async assertPrivateFileIcon(frameLocator?: FrameLocator) {
		const privateFileIcon = await (frameLocator ?? this.page)
			.getByLabel('Not Visible to Guest Users')
			.last();

		await privateFileIcon.waitFor();

		await expect(privateFileIcon).toBeVisible();
	}

	async changeTab(tabName: string) {
		await this.page.getByRole('link', {name: tabName}).click();
	}

	async changeView(viewName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: viewName}),
			trigger: this.page.getByLabel('Select View, Currently Selected: '),
		});
	}

	async deleteAllFileEntries() {
		await this.goto();
		await this.page
			.locator('input[data-modelclassname="FileEntry"]')
			.check();
		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async deleteFileEntry(name: string) {
		await this.goto();
		await this.changeView('list');
		await this.page.getByLabel(name).check();
		await this.page.getByRole('button', {name: 'Delete'}).click();
		await this.changeView('cards');
	}

	async deleteDocumentType(name: string) {
		await this.goto();
		await this.changeTab('Document Types');

		await this.page.getByRole('row', {name}).getByTitle('Actions').click();
		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Delete'}),
			trigger: this.page.getByRole('row', {name}).getByTitle('Actions'),
		});
	}

	async editEntry(entryTitle: string) {
		await this.page
			.locator(`.card-body:has-text('${entryTitle}')`)
			.getByLabel('More actions')
			.click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async editFileEntry(entryTitle: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: this.page
				.locator(`.card-body:has-text('${entryTitle}')`)
				.getByLabel('Actions'),
		});
	}

	async goToCreateNewFile() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'File Upload'}),
			trigger: this.page.getByRole('button', {exact: true, name: 'New'}),
		});
	}

	async goToCreateNewFileWithDifferentType(type: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: type}),
			trigger: this.page.getByRole('button', {exact: true, name: 'New'}),
		});
	}
	async openCreateAIImage() {
		await this.openNewButton();

		await this.page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();
	}

	async openNewButton() {
		await this.page.getByRole('button', {exact: true, name: 'New'}).click();
	}

	async openNewDLTypeButton() {
		await this.page.getByRole('link', {name: 'New Document Type'}).click();
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
	async orderBy(name: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name}),
			trigger: this.orderMenu,
		});
	}
	async searchInDL(query: string) {
		await this.searchInput.fill(query);
		await this.searchButton.click();
	}
}
