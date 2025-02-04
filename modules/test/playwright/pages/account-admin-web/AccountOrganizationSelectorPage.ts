/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {DataTablePage} from './DataTablePage';

export class AccountOrganizationSelectorPage {
	readonly assignButton: Locator;
	readonly frame: FrameLocator;
	readonly organizationsTable: DataTablePage;
	readonly page: Page;

	constructor(page: Page) {
		this.assignButton = page.getByRole('button', {
			exact: true,
			name: 'Assign',
		});
		this.frame = page.frameLocator('iframe[id="modalIframe"]');
		this.organizationsTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_organizationsSearchContainer'
			)
		);
		this.page = page;
	}

	async assignOrganizations(organizationNames: string[]) {
		await expect(this.organizationsTable.searchInput).toBeEditable();

		for (const organizationName of organizationNames) {
			await (
				await this.organizationsTable.rowCheckbox(organizationName)
			).check();
		}
		await this.assignButton.click();

		await waitForAlert(this.page);
	}
}
