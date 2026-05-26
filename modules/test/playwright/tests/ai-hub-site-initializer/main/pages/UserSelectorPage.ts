/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {DataTablePage} from '../../../../pages/account-admin-web/DataTablePage';
import {waitForAlert} from '../../../../utils/waitForAlert';

/**
 * User-selector modal scoped to the AccountEntriesManagementPortlet. The
 * shared AccountUserSelectorPage hardcodes the AccountEntriesAdminPortlet
 * namespace, so it cannot be reused on the AI Hub /account-management page.
 */
export class UserSelectorPage {
	readonly assignButton: Locator;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.page = page;
		this.frame = page.frameLocator('iframe[id="modalIframe"]');
		this.assignButton = page.getByRole('button', {
			exact: true,
			name: 'Assign',
		});
		this.usersTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesManagementPortlet_selectAccountUser'
			)
		);
	}

	async assignUsers(names: string[]) {
		await expect(this.usersTable.searchInput).toBeEditable();

		for (const name of names) {
			await (await this.usersTable.rowCheckbox(name)).check();
		}

		await this.assignButton.click();

		await waitForAlert(this.page);
	}
}
