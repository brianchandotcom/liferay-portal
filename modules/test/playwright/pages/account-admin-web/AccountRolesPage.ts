/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountRolesPage {
	readonly addNewRoleButton: Locator;
	readonly assignUsersButton: Locator;
	readonly assignUsersTable: DataTablePage;
	readonly backButton: Locator;
	readonly editRoleButton: Locator;
	readonly page: Page;
	readonly removeButton: Locator;
	readonly roleNameHeading: (roleName: string) => Locator;
	readonly rolesTable: DataTablePage;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.addNewRoleButton = page.getByTestId('creationMenuNewButton');
		this.assignUsersButton = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.assignUsersTable = new DataTablePage(
			page,
			page
				.locator(
					'#portlet_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet div'
				)
				.first()
		);
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.editRoleButton = page.locator('svg.lexicon-icon-ellipsis-v');
		this.page = page;
		this.removeButton = page.getByRole('button', {name: 'Remove'});
		this.roleNameHeading = (roleName) =>
			page.getByRole('heading', {name: roleName});
		this.rolesTable = new DataTablePage(
			page,
			page
				.locator(
					'#portlet_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet div'
				)
				.first()
		);
		this.searchInput = page.getByPlaceholder('Search for');
	}

	async roleName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}
}
