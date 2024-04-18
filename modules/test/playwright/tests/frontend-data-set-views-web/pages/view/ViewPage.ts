/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ViewsPage} from '../ViewsPage';

export class ViewPage {
	readonly page: Page;
	private readonly pageContainer: Locator;
	private readonly tabsContainer: Locator;
	private readonly viewsPage: ViewsPage;

	constructor(page: Page) {
		this.page = page;
		this.pageContainer = page.locator('.fds-view');
		this.tabsContainer = page.locator('nav.navbar');
		this.viewsPage = new ViewsPage(page);
	}

	async goto({
		dataSetLabel,
		viewLabel,
	}: {
		dataSetLabel: string;
		viewLabel: string;
	}) {
		await this.viewsPage.goto(dataSetLabel);

		await this.viewsPage.openDataSetView(viewLabel);

		await expect(this.pageContainer).toBeInViewport();
	}

	async selectTab(tabLabel: string) {
		const tabLink = this.tabsContainer.getByRole('button', {
			exact: true,
			name: tabLabel,
		});

		await tabLink.click();

		await tabLink.and(this.page.locator('.active')).waitFor();

		await expect(
			this.pageContainer.locator('.loading-animation')
		).not.toBeInViewport();
	}
}
