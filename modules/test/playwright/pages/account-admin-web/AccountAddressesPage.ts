/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountAddressesPage {
	readonly addressesTable: DataTablePage;
	readonly deleteButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.addressesTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet'
			)
		);
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.page = page;
	}
}
