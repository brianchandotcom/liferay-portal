/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {DataTablePage} from '../../../../pages/account-admin-web/DataTablePage';

export class SelectUserPage {
	readonly addButton: Locator;
	readonly frame: FrameLocator;
	readonly page: Page;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.addButton = page.getByText('Add', {exact: true});
		this.frame = page.frameLocator('iframe[title*="Add New User"]');
		this.page = page;
		this.usersTable = new DataTablePage(
			this.frame,
			this.frame.locator(
				'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entriesContainer'
			)
		);
	}
}
