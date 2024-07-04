/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class PublisherDashboardPage {
	readonly accountSearchDropdown: Locator;
	readonly publishedApp: (productName: string) => Locator;
	readonly solutionsTab: Locator;
	readonly newAppButton: Locator;

	readonly page: Page;

	constructor(page: Page) {
		this.accountSearchDropdown = page.locator('#account-search.dropdown');
		this.publishedApp = (productName: string) =>
			page.getByRole('cell', {name: productName}).locator('span');
		this.solutionsTab = page.getByRole('link', {name: 'Solutions'});
		this.newAppButton = page.getByRole('button', {name: 'New App'});
		this.page = page;
	}

	async goto(siteUrl: Site['friendlyUrlPath']) {
		await this.page.goto(`/web${siteUrl}/publisher-dashboard`, {
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

	async gotoNewAppPage() {
		expect(this.newAppButton).not.toBeDisabled();

		await this.newAppButton.click();
	}
}
