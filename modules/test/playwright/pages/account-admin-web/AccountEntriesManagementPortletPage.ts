/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerce/commerce-order-content-web/commerceLayoutsPage';
import {searchTableRowByValue} from '../users-admin-web/UsersAndOrganizationsPage';

export class AccountEntriesManagementPortletPage {
	readonly accountEntriesTable: Locator;
	readonly accountEntriesTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly accountEntriesTableRowActions: (
		accountName: string
	) => Promise<Locator>;
	readonly accountEntriesTableRowSelectedCheck: (
		accountName: string
	) => Promise<Locator>;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;
	readonly pageLabel: Locator;
	readonly searchInput: Locator;
	readonly selectAccountButton: Locator;

	constructor(page: Page) {
		this.accountEntriesTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet_accountEntriesSearchContainer'
		);
		this.accountEntriesTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.accountEntriesTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.accountEntriesTableRowActions = async (accountName: string) => {
			const accountEntriesTableRow = await this.accountEntriesTableRow(
				1,
				accountName,
				true
			);

			if (accountEntriesTableRow && accountEntriesTableRow.row) {
				const accountActionsMenu = accountEntriesTableRow.row
					.locator('[class$=action-column]')
					.getByRole('button');

				if (accountActionsMenu) {
					return accountActionsMenu;
				}
			}
			else {
				throw new Error(
					`Cannot locate button with label: Show Actions`
				);
			}
			throw new Error(
				`Cannot locate account row with accountName ${accountName}`
			);
		};
		this.accountEntriesTableRowSelectedCheck = async (
			accountName: string
		) => {
			const accountEntriesTableRow = await this.accountEntriesTableRow(
				1,
				accountName,
				true
			);

			if (accountEntriesTableRow && accountEntriesTableRow.row) {
				const accountSelectedColumn = accountEntriesTableRow.row
					.locator('[class$=selected-column]')
					.locator('[class$=icon-check]');

				if (accountSelectedColumn) {
					return accountSelectedColumn;
				}
			}
			throw new Error(
				`Cannot locate account row with accountName ${accountName}`
			);
		};
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
		this.pageLabel = page
			.getByTestId('layoutHref')
			.getByLabel('Account Management Page');
		this.searchInput = page.getByPlaceholder('Search for');
		this.selectAccountButton = page.getByRole('link', {
			name: 'Select Account',
		});
	}

	async selectAccount(accountName: string) {
		await this.searchInput.fill(accountName);
		await this.searchInput.press('Enter');
		await this.page.waitForTimeout(500);

		await (await this.accountEntriesTableRowActions(accountName)).click();

		await this.selectAccountButton.click();
		await this.page.waitForResponse((resp) => resp.status() === 200);
	}
}
