/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountGroupAccountsPage {
	readonly accountsTable: DataTablePage;
	readonly noAccountsMessage: Locator;
	readonly page: Page;
	readonly removeLink: (name: string) => Promise<Locator>;

	constructor(page: Page) {
		this.accountsTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_account_admin_web_internal_portlet_AccountGroupsAdminPortlet'
			)
		);
		this.noAccountsMessage = page.getByText('No accounts were found.');
		this.page = page;
		this.removeLink = async (name) => {
			return (await this.accountsTable.row(1, name, true)).row.getByRole(
				'link',
				{name: 'Remove'}
			);
		};
	}
}
