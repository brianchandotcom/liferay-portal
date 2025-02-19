/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class SitesAdminPage {
	readonly page: Page;

	private readonly searchButton: Locator;
	private readonly searchInput: Locator;

	constructor(page: Page) {
		this.page = page;

		this.searchButton = this.page.getByLabel('Search for', {exact: true});
		this.searchInput = this.page.getByPlaceholder('Search for');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.sites}`
		);
	}

	async searchSite(keywords: string) {
		await this.searchInput.click();
		await this.searchInput.clear();
		await this.searchInput.fill(keywords);

		await this.searchButton.click();

		await this.page.getByText('Search Results').waitFor();
	}
}
