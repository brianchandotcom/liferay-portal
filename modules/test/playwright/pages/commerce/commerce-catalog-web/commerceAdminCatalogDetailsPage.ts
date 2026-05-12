/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminCatalogDetailsPage {
	readonly basePriceListAutocomplete: Locator;
	readonly basePriceListDropdownItem: (name: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.basePriceListAutocomplete = page
			.locator('#base-price-list-autocomplete-root input[type="text"]')
			.first();
		this.basePriceListDropdownItem = (name: string) =>
			page
				.locator('.autocomplete-dropdown-menu')
				.getByText(name, {exact: true});
		this.page = page;
	}
}
