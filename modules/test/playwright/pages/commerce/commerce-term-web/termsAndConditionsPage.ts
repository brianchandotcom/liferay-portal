/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';

type TermType = 'Payment Terms' | 'Delivery Terms';

export class TermsAndConditionsPage {
	readonly addButton: Locator;
	readonly entryLink: (name: string) => Locator;
	readonly entryRow: (name: string) => Locator;
	readonly entryRowActionsButton: (name: string) => Locator;
	readonly entryRowStatusText: (name: string) => Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly menuDeleteItem: Locator;
	readonly modalCancelButton: Locator;
	readonly modalFrame: FrameLocator;
	readonly modalNameInput: Locator;
	readonly modalPriorityInput: Locator;
	readonly modalSaveButton: Locator;
	readonly modalTypeSelect: Locator;
	readonly page: Page;
	readonly publishLink: Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.addButton = page
			.getByRole('button', {exact: true, name: 'Add Term'})
			.first();
		this.entryLink = (name) => page.getByRole('link', {exact: true, name});
		this.entryRow = (name) => page.getByRole('row').filter({hasText: name});
		this.entryRowActionsButton = (name) =>
			this.entryRow(name)
				.getByRole('button')
				.filter({hasText: 'Actions'})
				.last();
		this.entryRowStatusText = (name) =>
			this.entryRow(name).locator('td').nth(5);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.menuDeleteItem = page.getByRole('menuitem', {name: 'Delete'});
		this.modalFrame = page.frameLocator(
			'.fds-modal-body > iframe, .modal-body-iframe > iframe'
		);
		this.modalCancelButton = this.modalFrame.getByRole('button', {
			exact: true,
			name: 'Cancel',
		});
		this.modalNameInput = this.modalFrame.getByLabel('Name');
		this.modalPriorityInput = this.modalFrame.getByLabel('Priority');
		this.modalSaveButton = this.modalFrame.getByRole('button', {
			exact: true,
			name: 'Submit',
		});
		this.modalTypeSelect = this.modalFrame.getByLabel('Type');
		this.page = page;
		this.publishLink = page.getByRole('link', {
			exact: true,
			name: 'Publish',
		});
		this.searchInput = page
			.getByTestId('managementToolbar')
			.getByRole('searchbox', {name: 'Search'});
	}

	async addEntry({
		name,
		priority = '',
		type,
	}: {
		name: string;
		priority?: number | string;
		type: TermType;
	}) {
		await this.addButton.click();
		await this.modalNameInput.fill(name);
		await this.modalTypeSelect.selectOption({label: type});

		await this.modalPriorityInput.fill(
			String(priority === '' ? 1 : priority)
		);

		await this.modalSaveButton.click();

		await expect(this.publishLink).toBeVisible();
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Terms and Conditions');
	}

	async searchEntry(term: string) {
		await this.searchInput.fill(term);
		await this.searchInput.press('Enter');
	}
}
