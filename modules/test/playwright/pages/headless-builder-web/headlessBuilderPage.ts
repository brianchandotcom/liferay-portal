/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class HeadlessBuilderPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly createApplicationButton: Locator;
	readonly page: Page;
	readonly addNewAPIApplicationButton: Locator;
	readonly newAPIApplicationTitleBox: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.createApplicationButton = page.getByRole('button', {
			name: 'Create',
		});
		this.page = page;
		this.addNewAPIApplicationButton = page.getByLabel(
			'Add New API Application'
		);
		this.newAPIApplicationTitleBox = page.getByPlaceholder('Enter title.');
	}

	async accessApplicationActions(applicationTitle: string) {
		await this.page
			.locator(
				`[class="dropdown-toggle component-action dropdown-toggle ml-1 btn btn-unstyled"]:right-of(:text("${applicationTitle}"))`
			)
			.first()
			.click();
	}

	async deleteApplicationThroughUI(applicationTitle: string) {
		await this.accessApplicationActions(applicationTitle);
		await this.page.getByRole('menuitem', {name: 'Delete'}).click();
		await this.page
			.getByLabel('Delete API Application')
			.getByRole('textbox')
			.fill('My-app');
		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async goto() {
		await this.applicationsMenuPage.goToAPIBuilder();
	}

	async goToEditAPIApplication(apiApplicationName: string) {
		await this.page.getByRole('link', {name: apiApplicationName}).click();
	}
}
