/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
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
	readonly accountNameLink: (accountName: string) => Locator;
	readonly accountOrganizationsCheckbox: (
		organizationName: string
	) => Promise<Locator>;
	readonly accountOrganizationsTable: Locator;
	readonly accountOrganizationsTableCell: (
		organizationName: string
	) => Locator;
	readonly accountOrganizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly accountRolesTab: Locator;
	readonly accountsTable: Locator;
	readonly accountsTableCell: (organizationName: string) => Locator;
	readonly accountsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly accountsTableRowActions: (accountName: string) => Promise<Locator>;
	readonly accountsTableRowCheckBox: (
		accountName: string
	) => Promise<Locator>;
	readonly accountsTableRowLink: (name: string) => Promise<Locator>;
	readonly activateButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly channelDefaultsTab: Locator;
	readonly clearButton: Locator;
	readonly deactivateButton: Locator;
	readonly deleteButton: Locator;
	readonly detailsTab: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (option: string) => Locator;
	readonly filterStatus: (status: string) => Locator;
	readonly newButton: Locator;
	readonly noAccountsMessage: Locator;
	readonly organizationAssignButton: Locator;
	readonly organizationAssignmentCheckBox: (
		organizationName: string
	) => Promise<Locator>;
	readonly organizationAssignmentFrame: FrameLocator;
	readonly organizationAssignmentTable: Locator;
	readonly organizationAssignmentTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly organizationsTab: Locator;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly removeAccountOrganizationButton: Locator;
	readonly searchInput: Locator;
	readonly searchButton: Locator;

	constructor(page: Page) {
		this.accountNameLink = (accountName) => {
			return this.page.getByRole('link', {name: accountName});
		};
		this.accountGroupsTab = page.getByRole('link', {
			name: 'Account Groups',
		});
		this.accountOrganizationsCheckbox = async (
			organizationName: string
		) => {
			const accountOrganizationsTableRow =
				await this.accountOrganizationsTableRow(1, organizationName);

			if (
				accountOrganizationsTableRow &&
				accountOrganizationsTableRow.row
			) {
				return accountOrganizationsTableRow.row.getByRole('checkbox');
			}
		};
		this.accountOrganizationsTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountOrganizationsSearchContainer'
		);
		this.accountOrganizationsTableCell = (organizationName: string) => {
			return this.page.getByRole('cell', {
				exact: true,
				name: `${organizationName}`,
			});
		};
		this.accountOrganizationsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.accountOrganizationsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.accountRolesTab = page.getByRole('link', {
			name: 'Roles',
		});
		this.accountsTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountEntriesSearchContainer'
		);
		this.accountsTableCell = (accountName: string) => {
			return this.page.getByRole('cell', {
				exact: true,
				name: `${accountName}`,
			});
		};
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
		this.accountsTableRowActions = async (name: string) => {
			const accountsTableRow = await this.accountsTableRow(1, name, true);

			if (accountsTableRow && accountsTableRow.column) {
				return accountsTableRow.row.getByRole('button');
			}

			throw new Error(`Cannot locate account row with name ${name}`);
		};
		this.accountsTableRowCheckBox = async (name: string) => {
			const accountsTableRow = await this.accountsTableRow(1, name, true);

			if (accountsTableRow && accountsTableRow.row) {
				return accountsTableRow.row.getByRole('checkbox', {
					name,
				});
			}
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
		this.activateButton = page
			.getByRole('button', {name: 'Activate'})
			.or(page.getByRole('link', {name: 'Activate'}));
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.channelDefaultsTab = page.getByRole('link', {
			name: 'Channel Defaults',
		});
		this.clearButton = page.getByRole('button', {name: 'Clear'});
		this.deactivateButton = page
			.getByRole('button', {name: 'Deactivate'})
			.or(page.getByRole('link', {name: 'Deactivate'}));
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.detailsTab = page.getByRole('link', {
			name: 'Details',
		});
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.filterMenuItem = (option: string) => {
			return page.getByRole('menuitem', {
				exact: true,
				name: option,
			});
		};
		this.filterStatus = (status: string) => {
			return page.getByText('Status: ' + status);
		};
		this.newButton = page
			.getByTestId('creationMenuNewButton')
			.getByText('New');
		this.noAccountsMessage = page.getByText('No accounts were found.');
		this.organizationAssignButton = page.getByRole('button', {
			exact: true,
			name: 'Assign',
		});
		this.organizationAssignmentCheckBox = async (
			organizationName: string
		) => {
			const organizationAssignmentTableRow =
				await this.organizationAssignmentTableRow(1, organizationName);

			if (
				organizationAssignmentTableRow &&
				organizationAssignmentTableRow.row
			) {
				return organizationAssignmentTableRow.row.getByRole('checkbox');
			}
		};
		this.organizationAssignmentFrame = page.frameLocator(
			'iframe[id="modalIframe"]'
		);
		this.organizationAssignmentTable =
			this.organizationAssignmentFrame.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_organizationsSearchContainer'
			);
		this.organizationAssignmentTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.organizationAssignmentTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.organizationsTab = page.getByRole('link', {
			name: 'Organizations',
		});
		this.page = page;
		this.pageTitle = page.getByTestId('headerTitle');
		this.removeAccountOrganizationButton = page.getByRole('button', {
			name: 'Remove',
		});
		this.searchInput = page.getByPlaceholder('Search for', {exact: true});
		this.searchButton = page.getByLabel('Search for', {exact: true});
	}

	async changeFilter(option: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.filterMenuItem(option),
			trigger: this.filterButton,
		});

		if (option === 'Active') {
			await expect(this.clearButton).not.toBeVisible();
		}
		else {
			await this.filterStatus(option).waitFor({state: 'visible'});
		}
	}

	async goto() {
		await this.applicationsMenuPage.goToAccounts();
	}

	async gotoAccountAdmin() {
		await this.page.goto(`${PORTLET_URLS.accountAdmin}`);
	}

	async organizationName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}
}
