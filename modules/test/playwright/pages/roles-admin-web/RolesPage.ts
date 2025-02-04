/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class RolesPage {
	readonly accountRolesLink: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly deleteButton: Locator;
	readonly optionsButton: Locator;
	readonly page: Page;
	readonly rolesTable: DataTablePage;
	readonly userLink: Locator;

	constructor(page: Page) {
		this.accountRolesLink = page.getByRole('link', {
			exact: true,
			name: 'Account Roles',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.deleteButton = page.getByRole('menuitem', {name: 'Delete'});
		this.optionsButton = page.getByLabel('Options', {exact: true});
		this.page = page;
		this.rolesTable = new DataTablePage(
			page,
			page
				.locator(
					'#portlet_com_liferay_roles_admin_web_portlet_RolesAdminPortlet div'
				)
				.first()
		);
		this.userLink = page.getByRole('link', {exact: true, name: 'User'});
	}

	async goto() {
		await this.applicationsMenuPage.goToRoles();
	}

	async selectRole(roleName: string) {
		await this.page.getByRole('link', {name: roleName}).click();
	}
}
