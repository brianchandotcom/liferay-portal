/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountOrganizationsPage {
	readonly organizationRemoveButton: (
		organizationName: string
	) => Promise<Locator>;
	readonly organizationsTable: DataTablePage;
	readonly page: Page;
	readonly removeButton: Locator;

	constructor(page: Page) {
		this.organizationRemoveButton = async (organizationName: string) => {
			const organizationsTableRow = await this.organizationsTable.row(
				1,
				organizationName
			);

			if (organizationsTableRow && organizationsTableRow.row) {
				return organizationsTableRow.row.getByRole('link', {
					name: 'Remove',
				});
			}
		};
		this.organizationsTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_accountOrganizationsSearchContainer'
			)
		);
		this.page = page;
		this.removeButton = page.getByRole('button', {name: 'Remove'});
	}
}
