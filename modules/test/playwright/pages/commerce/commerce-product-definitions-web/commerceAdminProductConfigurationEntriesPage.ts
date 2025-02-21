/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminProductConfigurationEntriesPage extends CommerceDNDTablePage {
	readonly differenceIcon: (locator?: Locator | Page) => Locator;
	readonly noResultsText: Locator;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#portlet_com_liferay_commerce_product_definitions_web_internal_portlet_CPConfigurationListsPortlet .fds table'
		);
		this.differenceIcon = (locator = page) => {
			return locator.locator('.product-configuration-value .icon');
		};
		this.noResultsText = page.getByText('No Results Found');
		this.page = page;
	}
}
