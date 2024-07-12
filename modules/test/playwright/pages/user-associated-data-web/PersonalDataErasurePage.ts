/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from '../account-admin-web/AccountsPage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class PersonalDataErasurePage {
	readonly actionsButton: Locator;
	readonly allSelectedButton: Locator;
	readonly anonymizeButton: Locator;
	readonly anonymizeMenuItem: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly blogCountLink: (blogCountNumber: string) => Locator;
	readonly menuItemDelete: Locator;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly selectAllItemsOnPageCheckbox: Locator;
	readonly userAssociatedDataTable: Locator;
	readonly userAssociatedDataTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly userAssociatedDataTableRowCheckBox: (
		name: string
	) => Promise<Locator>;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.allSelectedButton = page
			.locator('nav')
			.filter({hasText: 'All Selected'})
			.getByRole('button');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.anonymizeButton = page.getByRole('button', {name: 'Anonymize'});
		this.anonymizeMenuItem = page.getByRole('menuitem', {
			name: 'Anonymize',
		});
		this.blogCountLink = (blogCountNumber: string) => {
			return page.getByRole('link', {name: blogCountNumber});
		};
		this.menuItemDelete = page.getByRole('menuitem', {name: 'Delete'});
		this.page = page;
		this.selectAllItemsOnPageCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.userAssociatedDataTable = page.locator(
			'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_com_liferay_blogs_uad'
		);
		this.userAssociatedDataTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.userAssociatedDataTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.userAssociatedDataTableRowCheckBox = async (name: string) => {
			const accountsTableRow = await this.userAssociatedDataTableRow(
				1,
				name,
				true
			);

			if (accountsTableRow && accountsTableRow.row) {
				return accountsTableRow.row.getByTitle('Select');
			}

			throw new Error(`Cannot locate account row with name ${name}`);
		};
	}
}
