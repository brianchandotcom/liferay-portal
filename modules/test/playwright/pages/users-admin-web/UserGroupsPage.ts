/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
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

export class UserGroupsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly customField: (fieldName: string) => Promise<Locator>;
	readonly deleteButton: Locator;
	readonly editUserGroupMenuItem: Locator;
	readonly managePagesMenuItem: Locator;
	readonly nameInput: Locator;
	readonly newUserGroupButton: Locator;
	readonly noUserGroupsMessage: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly userGroupPagesPermissionsIFrame: FrameLocator;
	readonly userGroupPagesPermissionsMenuItem: Locator;
	readonly userGroupPagesPermissionsTable: DataTablePage;
	readonly userGroupsTable: Locator;
	readonly userGroupsTableCell: (value: string, exact?: boolean) => Locator;
	readonly userGroupsTableCheckbox: (screenName: string) => Promise<Locator>;
	readonly userGroupsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly userGroupsTableRowActions: (
		screenName: string
	) => Promise<Locator>;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.customField = async (fieldName: string) => {
			await page.getByText('Custom Fields').waitFor({timeout: 15 * 1000});

			const customField = await page.getByText(fieldName);

			if (customField.isVisible()) {
				return customField;
			}

			throw new Error(`Cannot locate Custom Field ${fieldName}`);
		};
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.editUserGroupMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.managePagesMenuItem = page.getByRole('menuitem', {
			name: 'Manage Pages',
		});
		this.nameInput = page.getByLabel('Name');
		this.newUserGroupButton = page
			.getByRole('button', {name: 'New'})
			.or(page.getByRole('link', {name: 'Add User Group'}));
		this.noUserGroupsMessage = page.getByText('No user groups were found.');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.userGroupPagesPermissionsIFrame = page.frameLocator(
			'iframe[title="User Group Pages Permissions"]'
		);
		this.userGroupPagesPermissionsMenuItem = page.getByRole('link', {
			name: 'User Group Pages Permissions',
		});
		this.userGroupPagesPermissionsTable = new DataTablePage(
			this.userGroupPagesPermissionsIFrame,
			this.userGroupPagesPermissionsIFrame.locator(
				'#_com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet_rolesSearchContainer'
			)
		);
		this.userGroupsTable = page.locator(
			'#_com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet_userGroupsSearchContainer'
		);
		this.userGroupsTableCell = (value, exact = true) =>
			this.page
				.getByRole('cell', {
					exact,
					name: value,
				})
				.first();
		this.userGroupsTableCheckbox = async (screenName: string) => {
			const userGroupsTableRow = await this.userGroupsTableRow(
				1,
				screenName,
				true
			);

			if (userGroupsTableRow && userGroupsTableRow.column) {
				return userGroupsTableRow.row.getByRole('checkbox');
			}

			throw new Error(
				`Cannot locate user group row with screenName ${screenName}`
			);
		};
		this.userGroupsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.userGroupsTable,
				colPosition,
				value,
				strictEqual
			);
		};

		this.userGroupsTableRowActions = async (screenName: string) => {
			const userGroupsTableRow = await this.userGroupsTableRow(
				1,
				screenName,
				true
			);

			if (userGroupsTableRow && userGroupsTableRow.column) {
				return userGroupsTableRow.row.getByRole('button');
			}

			throw new Error(
				`Cannot locate user group row with screenName ${screenName}`
			);
		};
	}

	async goto(forceReload?: boolean) {
		await this.applicationsMenuPage.goToUserGroups(forceReload);
	}

	async goToWithLimitedAccess() {
		await this.applicationsMenuPage.goToUserGroupsWithLimitedAccess();
	}
}
