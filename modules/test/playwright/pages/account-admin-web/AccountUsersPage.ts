/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountUsersPage {
	readonly assignUserMenuItem: Locator;
	readonly page: Page;
	readonly removeButton: Locator;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.assignUserMenuItem = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.page = page;
		this.removeButton = page
			.getByRole('button', {name: 'Remove'})
			.or(page.getByRole('menuitem', {name: 'Remove'}));
		this.usersTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountUsersSearchContainer'
			)
		);
	}

	async roleName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}
}
