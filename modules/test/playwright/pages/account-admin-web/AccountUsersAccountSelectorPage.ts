/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {DataTablePage} from './DataTablePage';

export class AccountUsersAccountSelectorPage {
	readonly accountsTable: DataTablePage;
	readonly chooseButton: (name: string) => Locator;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly selectButton: Locator;

	constructor(page: Page) {
		this.chooseButton = (name) =>
			this.frame
				.getByRole('cell', {
					exact: true,
					name,
				})
				.locator('..')
				.getByRole('button', {name: 'Choose'});
		this.frame = page.frameLocator('iframe[title*="Select"]');
		this.page = page;
		this.selectButton = page.getByRole('button', {
			exact: true,
			name: 'Select',
		});

		this.accountsTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#p_p_id_com_liferay_account_admin_web_internal_portlet_AccountUsersAdminPortlet_'
			)
		);
	}
}
