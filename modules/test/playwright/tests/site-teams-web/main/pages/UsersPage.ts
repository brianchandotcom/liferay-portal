/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../../../../pages/account-admin-web/DataTablePage';

export class UsersPage {
	readonly deleteButton: Locator;
	readonly newButton: Locator;
	readonly noUsersMessage: Locator;
	readonly page: Page;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('menuitem', {name: 'Delete'}));
		this.newButton = page.getByRole('button', {name: 'Add'});
		this.noUsersMessage = page.getByText('No users were found.');
		this.page = page;
		this.usersTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_site_teams_web_portlet_SiteTeamsPortlet_usersSearchContainer'
			)
		);
	}
}
