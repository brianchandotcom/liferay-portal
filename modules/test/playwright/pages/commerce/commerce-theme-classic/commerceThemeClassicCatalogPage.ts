/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceThemeClassicCatalogPage {
	readonly changeCurrencyModal: Locator;
	readonly changeCurrencyModalHeading: Locator;
	readonly changeCurrencyModalProceedButton: Locator;
	readonly currencyListItem: (currencyCode: string) => Locator;
	readonly currencySelectorButton: (
		currencyCode: string,
		currencySymbol: string
	) => Locator;
	readonly ordersTab: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.changeCurrencyModal = page.locator('.modal-content');
		this.changeCurrencyModalHeading = this.changeCurrencyModal.getByRole(
			'heading',
			{name: 'Change Active Currency'}
		);
		this.changeCurrencyModalProceedButton =
			this.changeCurrencyModal.getByRole('button', {
				exact: true,
				name: 'Proceed',
			});
		this.currencyListItem = (currencyCode) =>
			page.locator(`[data-testid*=${currencyCode}]`);
		this.currencySelectorButton = (currencyCode, currencySymbol) =>
			page.getByRole('button', {
				exact: true,
				name: `${currencySymbol} ${currencyCode}`,
			});
		this.ordersTab = page.getByRole('menuitem', {
			exact: true,
			name: 'Orders',
		});
		this.page = page;
	}
}
