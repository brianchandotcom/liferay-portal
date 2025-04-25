/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class RolesPage {
	readonly accountRolesLink: Locator;
	readonly applicationsMenuButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly deleteButton: Locator;
	readonly numberAssigneesCell: (
		roleName: string,
		value: string
	) => Promise<Locator>;
	readonly optionsButton: Locator;
	readonly organizationRolesLink: Locator;
	readonly page: Page;
	readonly roleCell: (value: string, exact?: boolean) => Locator;
	readonly rolesTable: DataTablePage;
	readonly siteRolesLink: Locator;
	readonly statusText: (value: string) => Locator;
	readonly userLink: Locator;

	constructor(page: Page) {
		this.accountRolesLink = page.getByRole('link', {
			exact: true,
			name: 'Account Roles',
		});
		this.applicationsMenuButton = page.getByLabel(
			'Open Applications MenuCtrl+'
		);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.deleteButton = page
			.getByRole('menuitem', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.numberAssigneesCell = async (roleName, value) =>
			(await this.rolesTable.row(1, roleName, true)).row.getByRole(
				'cell',
				{exact: true, name: value}
			);
		this.optionsButton = page.getByLabel('Options', {exact: true});
		this.organizationRolesLink = page.getByRole('link', {
			exact: true,
			name: 'Organization Roles',
		});
		this.page = page;
		this.roleCell = (value, exact = true) =>
			this.page.getByRole('cell', {
				exact,
				name: value,
			});
		this.rolesTable = new DataTablePage(
			page,
			page
				.locator(
					'#portlet_com_liferay_roles_admin_web_portlet_RolesAdminPortlet div'
				)
				.first()
		);
		this.siteRolesLink = page.getByRole('link', {
			exact: true,
			name: 'Site Roles',
		});
		this.statusText = (value) => page.getByText(value, {exact: true});
		this.userLink = page.getByRole('link', {exact: true, name: 'User'});
	}

	async goto(checkTabVisibility = true) {
		await this.applicationsMenuPage.goToRoles(checkTabVisibility);
	}

	async selectRole(roleName: string) {
		await this.page.getByRole('link', {name: roleName}).click();
	}
}
