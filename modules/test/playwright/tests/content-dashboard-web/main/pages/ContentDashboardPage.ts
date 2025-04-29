/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class ContentDashboardPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.contentDashboard}`
		);
	}

	async openFilterDropdown() {
		await this.page.getByLabel('Filter', {exact: true}).click();

		await this.page.getByText('Filter By', {exact: true}).waitFor();
	}

	async goToCurrentTab({assetTitle, siteUrl, tabName}) {
		await this.goto(siteUrl);

		if (assetTitle) {
			const searchBar = this.page.getByPlaceholder('Search for');

			await searchBar.fill(assetTitle);

			await this.page.keyboard.press('Enter');
		}

		const dropDownButton = this.page
			.locator('.lfr-entry-action-column .dropdown-action button')
			.first();

		await dropDownButton.click();

		const showInfoButton = this.page
			.locator('[data-action=showInfo]')
			.first();

		await showInfoButton.click();

		await this.page.waitForTimeout(1000);

		await this.page.getByRole('tab', {name: tabName}).click();

		await this.page.waitForTimeout(1000);
	}
}
