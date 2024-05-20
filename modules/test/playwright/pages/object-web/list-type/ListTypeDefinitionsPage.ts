/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';

export class ListTypeDefinitionsPage {
	readonly addPicklistButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly picklistNameInput: Locator;
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
	}

	async createPicklist(picklistName: string) {
		await this.addPicklistButton.click();
		await this.picklistNameInput.click();
		await this.picklistNameInput.fill(picklistName);
		await this.savePicklistButton.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToPicklists();
	}
}
