/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminProductDetailsConfigurationPage {
	readonly page: Page;
	readonly publishLink: Locator;
	readonly minStockQuantityInput: Locator;
	readonly purchasableInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.publishLink = page.getByRole('link', {name: 'Publish'});
		this.minStockQuantityInput = page.getByTestId('minStockQuantityInput');
		this.purchasableInput = page.getByTestId('purchasableInput');
	}
}
