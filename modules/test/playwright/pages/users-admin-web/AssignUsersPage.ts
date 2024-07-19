/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from '../account-admin-web/AccountsPage';

export class AssignUsersPage {
	readonly assignUsersFrame: FrameLocator;
	readonly doneButton: Locator;
	readonly page: Page;
	readonly usersTable: Locator;
	readonly usersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly usersTableRowCheckbox: (name: string) => Promise<Locator>;

	constructor(page: Page) {
		this.assignUsersFrame = page.frameLocator('iframe[id="modalIframe"]');
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.page = page;
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
		this.usersTableRowCheckbox = async (name: string) => {
			const accountsTableRow = await this.usersTableRow(1, name, true);

			if (accountsTableRow && accountsTableRow.row) {
				return accountsTableRow.row.getByRole('checkbox', {
					name,
				});
			}

			throw new Error(`Cannot locate user row with name ${name}`);
		};
		this.usersTable = this.assignUsersFrame.locator(
			'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entriesSearchContainer'
		);
	}
}
