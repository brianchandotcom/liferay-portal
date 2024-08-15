/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CustomerDashboardPage {
	readonly accountSearchDropdown: Locator;
	readonly downloadButton: Locator;
	readonly dropdownDownloadButton: Locator;
	readonly page: Page;
	readonly purchasedApp: (productName: string) => Locator;
	readonly tableKebabButton: (productName: string) => Locator;

	constructor(page: Page) {
		this.accountSearchDropdown = page.locator('#account-search.dropdown');
		this.downloadButton = page.getByRole('button', {name: 'Download'});
		this.dropdownDownloadButton = page.getByRole('menuitem', {
			name: 'Download App',
		});
		this.page = page;
		this.purchasedApp = (productName: string) =>
			page.getByRole('cell', {name: productName}).locator('span');
		this.tableKebabButton = (productName: string) =>
			page
				.getByRole('row', {name: productName})
				.locator(
					page
						.getByRole('cell', {name: 'Kebab Button'})
						.locator('button')
				);
	}

	async goto(siteUrl?: string) {
		await this.page.goto(`/web${siteUrl}/customer-dashboard`, {
			waitUntil: 'networkidle',
		});
	}

	async selectAccount(accountName: string) {
		await this.accountSearchDropdown.click();
		await this.page.getByRole('menuitem', {name: accountName}).click();

		// Necessary to wait few seconds because the page forces a full reload
		// using window.reload()

		await this.page.waitForTimeout(2000);
	}
}
