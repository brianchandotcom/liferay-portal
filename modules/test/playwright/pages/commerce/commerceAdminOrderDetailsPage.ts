/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from './commerceDNDTablePage';

export class CommerceAdminOrderDetailsPage extends CommerceDNDTablePage {
	readonly checkoutButton: Locator;
	readonly commerceOrderAccountEntryName: Locator;
	readonly headerDetailsTitle: Locator;
	readonly page: Page;
	readonly reorderButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_editOrderContainer .dnd-table'
		);
		this.checkoutButton = page.getByRole('button', {
			exact: true,
			name: 'Checkout',
		});
		this.commerceOrderAccountEntryName = page.getByTestId(
			'commerceOrderAccountEntryName'
		);
		this.headerDetailsTitle = page.getByTestId('headerDetailsTitle');
		this.page = page;
		this.reorderButton = page.getByRole('button', {
			exact: true,
			name: 'Reorder',
		});
	}

	async reorder() {
		this.reorderButton.click();
		this.checkoutButton.click();
	}
}
