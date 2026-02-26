/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminPriceListsPage extends CommerceDNDTablePage {
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommercePriceListPortlet_fm .fds table'
		);

		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Price Lists');
	}
}
