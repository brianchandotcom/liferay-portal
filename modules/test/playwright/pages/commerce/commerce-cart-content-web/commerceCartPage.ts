/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerce-order-content-web/commerceLayoutsPage';

export class CommerceCartPage {
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;
	readonly commerceOrderItemsTable: Locator;
	readonly commerceOrderItemsTableRowQuantityInput: (
		productName: string
	) => Promise<Locator>;

	constructor(page: Page) {
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
		this.commerceOrderItemsTable = page.locator(
			`[data-searchcontainerid$=commerceOrderItems]`
		);
		this.commerceOrderItemsTableRowQuantityInput = async (productName) => {
			const orderItemRowQuantityInput = this.commerceOrderItemsTable
				.getByTestId('row')
				.filter({hasText: productName})
				.locator('[class=commerce-quantity-container]')
				.locator('input');

			return orderItemRowQuantityInput;
		};
	}

	async addCartWidget() {
		await this.layoutsPage.addWidgetToPage('Cart');
	}
}
