/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminProductDetailsProductOptionsPage extends CommerceDNDTablePage {
	readonly addOptionsSearch: Locator;
	readonly createNewOptionsButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly optionActionsButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .fds table'
		);
		this.addOptionsSearch = page.getByPlaceholder(
			'Find or create an option'
		);
		this.createNewOptionsButton = page.getByRole('button', {
			name: 'Create New',
		});
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.optionActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.page = page;
	}
}
