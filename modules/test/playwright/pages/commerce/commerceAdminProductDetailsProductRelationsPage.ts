/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from './commerceDNDTablePage';

export class CommerceAdminProductDetailsProductRelationsPage extends CommerceDNDTablePage {
	readonly deleteBulkButton: Locator;
	readonly page: Page;
	readonly selectItemsInput: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .dnd-table'
		);
		this.deleteBulkButton = page
			.locator('nav')
			.locator('.bulk-actions')
			.getByRole('button');
		this.page = page;
		this.selectItemsInput = page.locator('input[title="Select Items"]');
	}
}
