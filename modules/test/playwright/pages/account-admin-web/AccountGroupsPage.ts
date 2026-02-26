/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';
import {DataTablePage} from './DataTablePage';

export class AccountGroupsPage {
	readonly accountGroupLink: (name: string) => Locator;
	readonly accountGroupsTable: DataTablePage;
	readonly globalMenuPage: GlobalMenuPage;
	readonly deleteButton: Locator;
	readonly editLink: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.accountGroupLink = (name) =>
			page.getByRole('link', {exact: true, name});
		this.accountGroupsTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_account_admin_web_internal_portlet_AccountGroupsAdminPortlet'
			)
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.editLink = page.getByRole('menuitem', {name: 'Edit'});
		this.page = page;
	}

	async goto(forceReload = true) {
		if (forceReload) {
			this.globalMenuPage.goToHome();
		}

		await this.globalMenuPage.goToControlPanel('Account Groups');
	}
}
