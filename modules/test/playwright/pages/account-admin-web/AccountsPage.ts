/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

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

export class AccountsPage {
	readonly accountGroupsTab: Locator;
	readonly accountsTable: Locator;
	readonly accountsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly accountsTableRowLink: (name: string) => Promise<Locator>;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly channelDefaultsTab: Locator;
	readonly newButton: Locator;
	readonly organizationAssignmentFrame: FrameLocator;
	readonly organizationsTab: Locator;
	readonly page: Page;
	readonly pageTitle: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.accountGroupsTab = page.getByRole('link', {
			name: 'Account Groups',
		});
		this.channelDefaultsTab = page.getByRole('link', {
			name: 'Channel Defaults',
		});
		this.newButton = page
			.getByTestId('creationMenuNewButton')
			.getByText('New');
		this.organizationAssignmentFrame = page.frameLocator(
			'iframe[id="modalIframe"]'
		);
		this.organizationsTab = page.getByRole('link', {
			name: 'Organizations',
		});
		this.page = page;
		this.pageTitle = page.getByTestId('headerTitle');
		this.accountsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.accountsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.accountsTableRowLink = async (name: string) => {
			const accountsTableRow = await this.accountsTableRow(1, name, true);

			if (accountsTableRow && accountsTableRow.column) {
				return accountsTableRow.column.getByRole('link', {
					name,
				});
			}

			throw new Error(`Cannot locate account row with name ${name}`);
		};
		this.accountsTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountEntriesSearchContainer'
		);
	}

	async goto() {
		await this.applicationsMenuPage.goToAccounts();
	}

	async organizationName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}
}
