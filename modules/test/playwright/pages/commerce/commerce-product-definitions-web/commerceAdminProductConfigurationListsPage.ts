/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminProductConfigurationListsPage extends CommerceDNDTablePage {
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPConfigurationListsPortlet_fm .dnd-table'
		);
		this.page = page;
	}
}
