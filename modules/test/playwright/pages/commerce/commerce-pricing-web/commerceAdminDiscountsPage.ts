/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminDiscountsPage extends CommerceDNDTablePage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommerceDiscountPortlet_fm .dnd-table'
		);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
	}

	async enterPromoCodeToWidget(promoCode) {
		await this.page.getByPlaceholder('Enter Promo Code').fill(promoCode);
		await this.page.getByPlaceholder('Enter Promo Code').waitFor();
		await this.page
			.getByRole('button', {exact: true, name: 'Apply'})
			.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceDiscounts();
	}
}
