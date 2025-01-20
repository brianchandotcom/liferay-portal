/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from './AccountsPage';

export class AccountAddressesPage {
	readonly addAddressButton: Locator;
	readonly addressNameLink: (name: string) => Locator;
	readonly addressesTable: Locator;
	readonly addressesTableCell: (name: string) => Locator;
	readonly addressesTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly addressesTableRowActions: (name: string) => Promise<Locator>;
	readonly addressesTableRowCheckBox: (name: string) => Promise<Locator>;
	readonly deleteButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.addAddressButton = page.getByRole('link', {name: 'Add Address'});
		this.addressNameLink = (name) => {
			return page.getByRole('link', {exact: true, name});
		};
		this.addressesTable = page.locator(
			'#portlet_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet'
		);
		this.addressesTableCell = (name: string) => {
			return this.page.getByRole('cell', {
				exact: true,
				name: `${name}`,
			});
		};
		this.addressesTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.addressesTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.addressesTableRowActions = async (name: string) => {
			const addressesTableRow = await this.addressesTableRow(
				1,
				name,
				true
			);

			if (addressesTableRow && addressesTableRow.column) {
				return addressesTableRow.row.getByRole('button');
			}

			throw new Error(`Cannot locate address row with name ${name}`);
		};
		this.addressesTableRowCheckBox = async (name: string) => {
			const addressesTableRow = await this.addressesTableRow(
				1,
				name,
				true
			);

			if (addressesTableRow && addressesTableRow.row) {
				return addressesTableRow.row.getByRole('checkbox', {
					name,
				});
			}
		};
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.page = page;
	}
}
