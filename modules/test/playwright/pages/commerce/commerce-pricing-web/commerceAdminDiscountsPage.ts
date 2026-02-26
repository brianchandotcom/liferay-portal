/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminDiscountsPage extends CommerceDNDTablePage {
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommerceDiscountPortlet_fm .fds table'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
	}

	async enterPromoCodeToWidget(promoCode: string) {
		await this.page.getByPlaceholder('Enter Promo Code').fill(promoCode);
		await this.page.getByPlaceholder('Enter Promo Code').waitFor();
		await this.page
			.getByRole('button', {exact: true, name: 'Apply'})
			.click();
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Discounts');
	}
}
