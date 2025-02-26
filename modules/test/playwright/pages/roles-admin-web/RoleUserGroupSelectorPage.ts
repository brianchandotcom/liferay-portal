/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {DataTablePage} from '../account-admin-web/DataTablePage';

export class RoleUserGroupSelectorPage {
	readonly addButton: Locator;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly userGroupsTable: DataTablePage;

	constructor(page: Page) {
		this.addButton = page.getByRole('button', {
			exact: true,
			name: 'Add',
		});
		this.frame = page.frameLocator(
			'iframe[id="_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_selectAssignees_iframe_"]'
		);
		this.userGroupsTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#p_p_id_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_'
			)
		);
		this.page = page;
	}

	async assignUserGroups(names: string[]) {
		await expect(this.userGroupsTable.searchInput).toBeEditable();

		for (const name of names) {
			await (await this.userGroupsTable.rowCheckbox(name)).check();
		}
		await this.addButton.click();

		await waitForAlert(this.page);
	}
}
