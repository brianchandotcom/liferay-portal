/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';

export class CommerceAdminCurrencyDetailsPage {
	readonly activeToggle: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly backLink: Locator;
	readonly cancelButton: Locator;
	readonly codeInput: Locator;
	readonly nameInput: Locator;
	readonly primaryToggle: Locator;
	readonly priority: Locator;
	readonly saveButton: Locator;
	readonly symbol: Locator;

	constructor(page: Page) {
		this.activeToggle = page.getByText('Active');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.codeInput = page.getByLabel('Code');
		this.nameInput = page.getByLabel('Name');
		this.primaryToggle = page.getByText('Primary');
		this.priority = page.getByLabel('Priority');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.symbol = page.getByLabel('Symbol');
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Currencies');
	}
}
