/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class PlacedOrderPage {
	readonly orderItemLink: (productName: string) => Locator;
	readonly page: Page;
	readonly paginationText: (text: string) => Locator;
	readonly reorderButton: Locator;
	readonly retryPaymentButton: Locator;
	readonly shipmentStatusText: (status: string) => Locator;

	constructor(page: Page) {
		this.page = page;
		this.orderItemLink = (productName: string) =>
			page.getByRole('link', {name: productName});
		this.paginationText = (text: string) => page.getByText(text);
		this.reorderButton = page.getByRole('button', {name: 'Reorder'});
		this.retryPaymentButton = page.getByRole('button', {
			name: 'Retry Payment',
		});
		this.shipmentStatusText = (status: string) => page.getByText(status);
	}
}
