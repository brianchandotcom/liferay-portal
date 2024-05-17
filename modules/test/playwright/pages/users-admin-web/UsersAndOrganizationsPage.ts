/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

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

export class UsersAndOrganizationsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly exportUsersOptionsMenuItem: Locator;
	readonly exportPersonalDataItem: Locator;
	readonly manageCustomFieldsOptionsMenuItem: Locator;
	readonly organizationActionsMenu: (
		organizationName: string
	) => Promise<Locator>;
	readonly organizationsLink: Locator;
	readonly organizationsTable: Locator;
	readonly organizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly exportImportOptionsMenuItem: Locator;
	readonly usersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly usersTableRowLink: (screenName: string) => Promise<Locator>;
	readonly usersTableRowActions: (screenName: string) => Promise<Locator>;
	readonly usersLink: Locator;
	readonly usersTable: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.exportUsersOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export Users',
		});
		this.exportPersonalDataItem = page.getByRole('menuitem', {
			name: 'Export Personal Data',
		});
		this.manageCustomFieldsOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Manage Custom Fields',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.organizationActionsMenu = async (organizationName: string) => {
			const organizationsTableRow = await this.organizationsTableRow(
				1,
				organizationName,
				true
			);

			if (organizationsTableRow && organizationsTableRow.row) {
				const organizationActionsMenu =
					organizationsTableRow.row.getByLabel('Show Actions');

				if (organizationActionsMenu) {
					return organizationActionsMenu;
				}
			}
			else {
				throw new Error(
					`Cannot locate organization row with organizationName ${organizationName}`
				);
			}

			throw new Error(`Cannot locate button with label: Show Actions`);
		};
		this.organizationsLink = page.getByRole('link', {
			name: 'Organizations',
		});
		this.organizationsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_organizationsSearchContainer'
		);
		this.organizationsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.organizationsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.page = page;
		this.pageTitle = page.getByTestId('headerTitle');
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
		this.usersTableRowLink = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(2, screenName, true);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.column.getByRole('link', {
					name: screenName,
				});
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.usersTableRowActions = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(2, screenName, true);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.row.getByLabel('Show Actions');
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.usersLink = page.getByRole('link', {name: 'Users'});
		this.usersTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_usersSearchContainer'
		);
	}

	async goto(forceReload?: boolean) {
		await this.applicationsMenuPage.goToUsersAndOrganizations(forceReload);
	}

	async goToOrganizations(forceReload?: boolean) {
		await this.goto(forceReload);
		await Promise.all([
			this.organizationsLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes('screenNavigationCategoryKey=organizations')
			),
		]);
	}

	async goToUsers(forceReload?: boolean) {
		await this.goto(forceReload);
		await Promise.all([
			this.usersLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('screenNavigationCategoryKey=users')
			),
		]);
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
}
