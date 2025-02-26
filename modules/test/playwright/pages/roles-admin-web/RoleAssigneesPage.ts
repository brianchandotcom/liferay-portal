/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';

export class RoleAssigneesPage {
	readonly assigneesTable: DataTablePage;
	readonly backButton: Locator;
	readonly noDataMessage: (type: string) => Locator;
	readonly organizationsLink: Locator;
	readonly page: Page;
	readonly removeButton: Locator;
	readonly segmentsLink: Locator;
	readonly sitesLink: Locator;
	readonly userGroupsLink: Locator;
	readonly usersLink: Locator;

	constructor(page: Page) {
		this.assigneesTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_fm'
			)
		);
		this.backButton = page.getByRole('link', {name: 'Go to Roles'});
		this.noDataMessage = (type) => page.getByText(`No ${type} were found.`);
		this.organizationsLink = page.getByRole('link', {
			name: 'Organizations',
		});
		this.page = page;
		this.removeButton = page.getByRole('button', {name: 'Remove'});
		this.segmentsLink = page.getByRole('link', {name: 'Segments'});
		this.sitesLink = page.getByRole('link', {name: 'Sites'});
		this.userGroupsLink = page.getByRole('link', {name: 'User Groups'});
		this.usersLink = page.getByRole('link', {name: 'Users'});
	}
}
