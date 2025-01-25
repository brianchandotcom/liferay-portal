/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountPersonUserSelectorPage {
	readonly chooseButton: (name: string) => Promise<Locator>;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.chooseButton = async (name) => {
			const row = await this.usersTable.row(0, name, true);

			if (row && row.row) {
				return row.row.getByRole('button', {name: 'Choose'});
			}

			throw new Error(`Cannot locate row with value ${name}`);
		};
		this.frame = page.frameLocator(
			'iframe[id="_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_selectPersonAccountEntryUser_iframe_"]'
		);
		this.usersTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#p_p_id_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_'
			)
		);
		this.page = page;
	}

	async chooseUser(name: string) {
		await expect(this.usersTable.searchInput).toBeEditable();

		await (await this.chooseButton(name)).click();
	}
}
