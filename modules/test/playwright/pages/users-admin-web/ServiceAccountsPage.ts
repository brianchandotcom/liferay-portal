/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {searchTableRowByValue} from './UsersAndOrganizationsPage';

export class ServiceAccountsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly impersonateUserMenuItem: Locator;
	readonly page: Page;
	readonly serviceAccountActionMenu: (screenName: string) => Promise<Locator>;
	readonly serviceAccountsTable: Locator;
	readonly serviceAccountsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.impersonateUserMenuItem = page.getByRole('menuitem', {
			name: 'Impersonate User',
		});
		this.page = page;
		this.serviceAccountActionMenu = async (screenName: string) => {
			const serviceAccountsTableRow = await this.serviceAccountsTableRow(
				2,
				screenName,
				true
			);

			if (serviceAccountsTableRow && serviceAccountsTableRow.row) {
				const serviceAccountsActionMenu =
					serviceAccountsTableRow.row.getByLabel('Show Actions');

				if (serviceAccountsActionMenu) {
					return serviceAccountsActionMenu;
				}
			}
			else {
				throw new Error(
					`Cannot locate service account row with screenName ${screenName}`
				);
			}

			throw new Error(`Cannot locate button with label: Show Actions`);
		};
		this.serviceAccountsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_ServiceAccountsPortlet_usersSearchContainer'
		);
		this.serviceAccountsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.serviceAccountsTable,
				colPosition,
				value,
				strictEqual
			);
		};
	}

	async goto() {
		await this.applicationsMenuPage.goToServiceAccounts();
	}
}
