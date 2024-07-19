/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from '../account-admin-web/AccountsPage';

export class OrganizationUsersPage {
	readonly page: Page;
	readonly usersTable: Locator;
	readonly usersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly usersTableRowLink: (name: string) => Promise<Locator>;

	constructor(page: Page) {
		this.page = page;
		this.usersTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_organizationUsersSearchContainer'
		);
		this.usersTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.usersTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.usersTableRowLink = async (name: string) => {
			const accountsTableRow = await this.usersTableRow(1, name, true);

			if (accountsTableRow && accountsTableRow.column) {
				return accountsTableRow.row.getByRole('link', {
					name,
				});
			}

			throw new Error(`Cannot locate user row with name ${name}`);
		};
	}
}
