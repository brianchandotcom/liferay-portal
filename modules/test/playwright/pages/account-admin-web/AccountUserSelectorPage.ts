/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {DataTablePage} from './DataTablePage';

export class AccountUserSelectorPage {
	readonly assignButton: Locator;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.assignButton = page.getByRole('button', {
			exact: true,
			name: 'Assign',
		});
		this.frame = page.frameLocator('iframe[id="modalIframe"]');
		this.usersTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_selectAccountUser'
			)
		);
		this.page = page;
	}

	async assignUsers(names: string[]) {
		await expect(this.usersTable.searchInput).toBeEditable();

		for (const name of names) {
			await (await this.usersTable.rowCheckBox(name)).check();
		}
		await this.assignButton.click();

		await waitForAlert(this.page);
	}
}
