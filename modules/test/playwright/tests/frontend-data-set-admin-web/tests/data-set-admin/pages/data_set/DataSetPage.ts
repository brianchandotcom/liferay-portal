/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {CustomDataSetsPage} from '../CustomDataSetsPage';

export class DataSetPage {
	readonly page: Page;
	private readonly pageContainer: Locator;
	private readonly tabsContainer: Locator;
	private readonly customDataSetsPage: CustomDataSetsPage;

	constructor(page: Page) {
		this.page = page;
		this.pageContainer = page.locator('.fds-view');
		this.tabsContainer = page.locator('nav.navbar');
		this.customDataSetsPage = new CustomDataSetsPage(page);
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.customDataSetsPage.goto();

		await this.customDataSetsPage.openDataSet(dataSetLabel);

		await Promise.all([
			expect(this.pageContainer).toBeInViewport(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('/openapi.json')
			),
		]);
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
