/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {DataTablePage} from './DataTablePage';

export class AccountGroupAccountSelectorPage {
	readonly accountsTable: DataTablePage;
	readonly assignButton: Locator;
	readonly frame: FrameLocator;
	readonly page: Page;

	constructor(page: Page) {
		this.assignButton = page.getByRole('button', {
			exact: true,
			name: 'Assign',
		});
		this.frame = page.frameLocator('iframe[id="modalIframe"]');
		this.page = page;

		this.accountsTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#p_p_id_com_liferay_account_admin_web_internal_portlet_AccountGroupsAdminPortlet_'
			)
		);
	}

	async selectAccounts(accounts: Array<string>, select = true) {
		for (const account of accounts) {
			const checkbox = await this.accountsTable.rowCheckbox(account);

			if (select) {
				await checkbox.check();
			}
			else {
				await checkbox.uncheck();
			}
		}

		await this.assignButton.click();

		await waitForAlert(this.page);
	}
}
