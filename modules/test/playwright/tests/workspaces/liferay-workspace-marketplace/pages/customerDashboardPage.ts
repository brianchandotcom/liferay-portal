/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CustomerDashBoardPage {
	readonly accountSearchDropdown: Locator;
	readonly detailDashboardTab: Locator;
	readonly downloadButton: Locator;
	readonly downloadDashboardTab: Locator;
	readonly dropDownDownloadbutton: Locator;
	readonly page: Page;
	readonly purchasedApp: (productName: string) => Locator;
	readonly tableKebabButton: (productName: string) => Locator;

	constructor(page: Page) {
		this.accountSearchDropdown = page.locator('#account-search.dropdown');
		this.detailDashboardTab = page.getByRole('link', {name: 'Details'});
		this.downloadButton = page.getByRole('button', {name: 'Download'});
		this.downloadDashboardTab = page.getByRole('link', {name: 'Download'});
		this.dropDownDownloadbutton = page.getByRole('menuitem', {
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

	async selectAccount(accountName: string) {
		await this.accountSearchDropdown.click();
		await this.page.getByRole('menuitem', {name: accountName}).click();

		// Necessary to wait few seconds because the page forces a full reload
		// using window.reload()

		await this.page.waitForTimeout(2000);
	}

	async goto(url: string) {
		await this.page.goto(url, {
			waitUntil: 'networkidle',
		});
	}
}
