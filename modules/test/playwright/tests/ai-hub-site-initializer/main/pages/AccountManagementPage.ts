/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../../../../pages/account-admin-web/DataTablePage';

export class AccountManagementPage {
	readonly accountCell: (accountName: string) => Locator;
	readonly accountsTable: DataTablePage;
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;

		this.accountsTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet_accountEntriesSearchContainer'
			)
		);
		this.accountCell = (accountName) =>
			page.getByRole('cell', {exact: true, name: accountName});
	}

	async goto(siteFriendlyUrlPath: string) {
		await this.page.goto(`/web${siteFriendlyUrlPath}/account-management`);
	}
}
