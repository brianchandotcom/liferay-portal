/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export const searchTableRowByValue = async function (
	tableLocator: Locator,
	colPosition: number,
	value: string,
	strictEqual: boolean = false
) {
	await tableLocator.elementHandle();

	const rows = await tableLocator.getByRole('row').all();

	for await (const row of rows) {
		const column = row.getByRole('cell').nth(colPosition).first();

		const colValue = (await column.allInnerTexts()).join('');

		if (
			(strictEqual && colValue === value) ||
			(!strictEqual &&
				colValue.toLowerCase().indexOf(value.toLowerCase()) >= 0)
		) {
			return {column, row};
		}
	}

	throw new Error(`Cannot locate table row with value ${value}`);
};

export class OrganizationUsersPage {
	readonly filterButton: Locator;
	readonly page: Page;
	readonly removeMenuItem: Locator;
	readonly screenName: (screenName: string) => Promise<Locator>;
	readonly usersTable: Locator;
	readonly usersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly usersTableRowActions: (screenName: string) => Promise<Locator>;
	readonly usersTableRowLink: (screenName: string) => Promise<Locator>;

	constructor(page: Page) {
		this.filterButton = page.getByRole('button', {name: 'Filter'});
		this.page = page;
		this.removeMenuItem = page.getByRole('menuitem', {
			name: 'Remove',
		});
		this.screenName = async (screenName: string) => {
			return page.getByText(screenName);
		};
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
		this.usersTableRowActions = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(1, screenName);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.row.getByLabel('Show Actions');
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.usersTableRowLink = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(1, screenName);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.column.getByRole('link', {
					name: screenName,
				});
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
	}
}
