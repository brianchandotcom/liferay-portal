/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminProductGroupDetailsPage {
	readonly discountsTab: Locator;
	readonly entryCell: (name: string) => Locator;
	readonly page: Page;
	readonly priceListsTab: Locator;

	constructor(page: Page) {
		this.discountsTab = page.getByRole('link', {
			exact: true,
			name: 'Discounts',
		});
		this.entryCell = (name: string) =>
			page.getByRole('cell', {name}).first();
		this.page = page;
		this.priceListsTab = page.getByRole('link', {
			exact: true,
			name: 'Price Lists',
		});
	}
}
