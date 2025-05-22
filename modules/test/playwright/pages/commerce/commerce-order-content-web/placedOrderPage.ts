/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class PlacedOrderPage {
	readonly page: Page;
	readonly reorderButton: Locator;
	readonly retryPaymentButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.reorderButton = page.getByRole('button', {name: 'Reorder'});
		this.retryPaymentButton = page.getByRole('button', {
			name: 'Retry Payment',
		});
	}
}
