/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';

export class ListTypeDefinitionsPage {
	readonly addPicklistButton: Locator;
	readonly addPickListEntryButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly frameLocator: FrameLocator;
	readonly page: Page;
	readonly picklistNameInput: Locator;
	readonly picklistEntryKey: Locator;
	readonly savePicklistButton: Locator;

	constructor(page: Page) {
		this.addPicklistButton = page
			.getByRole('button', {
				name: 'Add Picklist',
			})
			.first();
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
		this.picklistNameInput = page.getByLabel('Name');
		this.savePicklistButton = page.getByRole('button', {
			name: 'Save',
		});
		this.frameLocator = page.frameLocator('iframe');
		this.picklistEntryKey = page.getByLabel('Key');
		this.addPickListEntryButton = page
			.frameLocator('iframe')
			.getByLabel('Add Item');
	}

	async createPicklist(picklistName: string) {
		await this.addPicklistButton.click();
		await this.picklistNameInput.click();
		await this.picklistNameInput.fill(picklistName);
		await this.savePicklistButton.click();
	}

	async addPicklistItem(
		picklistName: string,
		picklistNameEntry: string,
		picklistKeyEntry?: string
	) {
		await this.page.getByRole('link', {name: picklistName}).click();
		await this.addPickListEntryButton.click();
		await this.picklistNameInput.fill(picklistNameEntry);

		if (picklistKeyEntry) {
			await this.picklistEntryKey.fill(picklistKeyEntry);
		}

		await this.savePicklistButton.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToPicklists();
	}
}
