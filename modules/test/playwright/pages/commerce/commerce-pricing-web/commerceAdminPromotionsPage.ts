/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminPromotionsPage extends CommerceDNDTablePage {
	readonly deleteMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly promotionLink: (name: string) => Locator;
	readonly promotionRowActions: (name: string) => Promise<Locator>;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommercePromotionPortlet_fm .fds table'
		);

		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.promotionLink = (name: string) =>
			page.getByRole('link', {name}).first();
		this.promotionRowActions = async (name: string) => {
			const result = await this.tableRow(0, name, true);

			return result.row.getByRole('button', {name: 'Actions'});
		};
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Promotions');
	}
}
