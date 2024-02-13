/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';

export class FDSFragmentPage {
	readonly apiHelpers: ApiHelpers;
	readonly editPageButton: Locator;
	readonly fragmentWidgetSearchInput: Locator;
	readonly page: Page;
	readonly publishPageButton: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.page = page;
		this.fragmentWidgetSearchInput = this.page.getByLabel(
			'Search Fragments and Widgets'
		);
		this.publishPageButton = this.page.getByRole('button', {
			name: 'Publish',
		});
	}

	async goto() {
		await this.page.goto('/');
	}

	async createPage({
		siteId,
		title,
	}: {
		siteId: string;
		title: string;
	}): Promise<Layout> {
		const pageLayout =
			await this.apiHelpers.headlessDelivery.createSitePage(
				siteId,
				title
			);

		return pageLayout;
	}

	async createSite(name: string): Promise<Site> {
		const site = await this.apiHelpers.headlessSite.createSite(name);

		return site;
	}

	async deleteSite(siteId: string) {
		await this.apiHelpers.headlessSite.deleteSite(siteId);
	}

	async dragAndDropFragment(itemName) {
		const source = await this.page.getByRole('menuitem', {
			exact: true,
			name: itemName,
		});

		await source.focus();
		await source.press('Enter');
		await source.press('Enter');
	}

	async editPage({layout, site}) {
		await this.page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
		);
	}

	async goToPage({layout, site}) {
		await this.page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);
	}

	async publishPage() {
		await this.publishPageButton.click();
	}

	async searchFragmentOrWidget(itemName: string) {
		await this.fragmentWidgetSearchInput.fill(itemName);
	}
}
