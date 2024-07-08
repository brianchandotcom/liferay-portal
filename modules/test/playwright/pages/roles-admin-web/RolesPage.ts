/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class RolesPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly deleteButton: Locator;
	readonly optionsButton: Locator;
	readonly userLink: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
		this.deleteButton = page.getByRole('menuitem', {name: 'Delete'});
		this.optionsButton = page.getByLabel('Options', {exact: true});
		this.userLink = page.getByRole('link', {exact: true, name: 'User'});
	}

	async goto() {
		await this.applicationsMenuPage.goToRoles();
	}

	async selectRole(roleName: string) {
		await this.page.getByRole('link', {name: roleName}).click();
	}
}
