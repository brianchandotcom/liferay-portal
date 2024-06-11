/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {AccountsPage} from './AccountsPage';

export class AccountManagementWidgetPage extends AccountsPage {
	readonly accountCell: (accountName: string) => Locator;
	readonly page: Page;
	readonly searchInput: Locator;

	constructor(page: Page) {
		super(page);
		this.accountCell = (accountName: string) => {
			return this.page.getByRole('cell', {
				exact: true,
				name: `${accountName}`,
			});
		};

		// @ts-ignore

		this.accountsTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet_accountEntriesSearchContainer'
		);
		this.page = page;
		this.searchInput = page.locator('input[placeholder="Search for"]');
	}
}
