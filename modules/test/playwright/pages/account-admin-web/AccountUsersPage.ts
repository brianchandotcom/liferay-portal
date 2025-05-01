/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {DataTablePage} from './DataTablePage';

export class AccountUsersPage {
	readonly activateButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly assignRolesMenuItem: Locator;
	readonly assignUserMenuItem: Locator;
	readonly clearButton: Locator;
	readonly deactivateButton: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (option: string) => Locator;
	readonly impersonateUserMenuItem: Locator;
	readonly inviteUserMenuItem: Locator;
	readonly page: Page;
	readonly removeButton: Locator;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.activateButton = page
			.getByRole('button', {
				name: 'Activate',
			})
			.or(
				page.getByRole('link', {
					name: 'Activate',
				})
			);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.assignRolesMenuItem = page.getByRole('menuitem', {
			name: 'Assign Roles',
		});
		this.assignUserMenuItem = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.clearButton = page.getByRole('button', {name: 'Clear'});
		this.deactivateButton = page
			.getByRole('button', {
				name: 'Deactivate',
			})
			.or(page.getByRole('link', {name: 'Deactivate'}));
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
		this.impersonateUserMenuItem = page.getByRole('menuitem', {
			name: 'Impersonate User',
		});
		this.inviteUserMenuItem = page.getByRole('menuitem', {
			name: 'Invite Users',
		});
		this.page = page;
		this.removeButton = page
			.getByRole('button', {name: 'Remove'})
			.or(page.getByRole('menuitem', {name: 'Remove'}));
		this.usersTable = new DataTablePage(
			page,
			page
				.locator(
					'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountUsersSearchContainer'
				)
				.or(
					page.locator(
						'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet_accountUsersSearchContainer'
					)
				)
				.or(
					page.locator(
						'#p_p_id_com_liferay_account_admin_web_internal_portlet_AccountUsersAdminPortlet_'
					)
				)
		);
	}

	async changeFilter(option) {
		const expanded = await this.filterButton.getAttribute('aria-expanded');

		if (expanded === 'false') {
			await this.filterButton.click();
		}

		await this.filterMenuItem(option).click();
	}

	async roleName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}

	async goto(forceReload = true) {
		await this.applicationsMenuPage.goToAccountUsers(forceReload);
	}
}
