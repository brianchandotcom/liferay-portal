/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/applicationsMenu.page';

export class UsersAndOrganizationsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly exportUsersOptionsMenuItem: Locator;
	readonly manageCustomFieldsOptionsMenuItem: Locator;
	readonly organizationsLink: Locator;
	readonly exportImportOptionsMenuItem: Locator;
	readonly usersLink: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.exportUsersOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export Users',
		});
		this.manageCustomFieldsOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Manage Custom Fields',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.organizationsLink = page.getByRole('link', {
			name: 'Organizations',
		});
		this.page = page;
		this.pageTitle = page.getByTestId('headerTitle');
		this.usersLink = page.getByRole('link', {name: 'Users'});
	}

	async goto() {
		await this.applicationsMenuPage.goToUsersAndOrganizations();
	}

	async goToOrganizations() {
		await this.goto();
		await Promise.all([
			this.organizationsLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes('screenNavigationCategoryKey=organizations')
			),
		]);
	}

	async goToUsers() {
		await this.goto();
		await Promise.all([
			this.usersLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('screenNavigationCategoryKey=users')
			),
		]);
	}

	async openOptionsMenu() {
		await this.optionsMenu.click();
	}
}
