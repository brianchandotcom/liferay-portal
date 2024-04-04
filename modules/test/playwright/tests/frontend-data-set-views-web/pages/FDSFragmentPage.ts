/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {DEFAULT_LABEL} from '../utils/constants';

export class FDSFragmentPage {
	readonly apiHelpers: ApiHelpers;
	readonly creationMenuButton: Locator;
	readonly editPageButton: Locator;
	readonly fdsActiveViewSelector: Locator;
	readonly fdsCardsWrapper: Locator;
	readonly fdsListWrapper: Locator;
	readonly fdsTableWrapper: Locator;
	readonly fragmentWidgetSearchInput: Locator;
	readonly page: Page;
	readonly publishPageButton: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.creationMenuButton = page.getByTestId('fdsCreationActionButton');
		this.fdsActiveViewSelector = page.getByLabel('Show View Options');
		this.fdsCardsWrapper = page.getByTestId('visualization-mode-cards');
		this.fdsListWrapper = page.getByTestId('visualization-mode-list');
		this.fdsTableWrapper = page.getByTestId('visualization-mode-table');
		this.fragmentWidgetSearchInput = page.getByLabel(
			'Search Fragments and Widgets'
		);
		this.page = page;
		this.publishPageButton = page.getByRole('button', {
			name: 'Publish',
		});
	}

	async goto() {
		await this.page.goto('/');
	}

	async configureDataSetFragment({
		layout,
		site,
		viewLabel = DEFAULT_LABEL.VIEW,
	}) {
		await this.editPage({layout, site});

		await this.searchFragmentOrWidget('Data Set');

		await this.dragAndDropFragment(
			'Data Set Add Data Set Mark Data Set as Favorite'
		);

		await this.page
			.getByText('Select a data set view. Beta')
			.waitFor({state: 'visible'});

		await this.page
			.getByText('Select a data set view. Beta')
			.first()
			.click();

		await this.page
			.getByRole('button', {name: 'Select Data Set View'})
			.click();

		await this.page.getByRole('dialog').isVisible();

		await this.page.getByRole('heading', {name: 'Select'}).isVisible();

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('.fds-view-item-selector')
			.waitFor({state: 'visible'});

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('li')
			.filter({hasText: viewLabel})
			.first()
			.click();

		await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {name: 'Save'})
			.click();

		await this.publishPage();

		await this.goToPage({layout, site});

		await this.page
			.locator('.data-set-wrapper')
			.waitFor({state: 'visible'});
	}

	async createPage({
		siteId,
		title,
	}: {
		siteId: string;
		title: string;
	}): Promise<Layout> {
		const pageLayout =
			await this.apiHelpers.headlessDelivery.createSitePage({
				siteId,
				title,
			});

		return pageLayout;
	}

	async createSite(name: string): Promise<Site> {
		const site = await this.apiHelpers.headlessSite.createSite({
			name,
		});

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

		await this.page
			.getByText('Select a data set view. Beta')
			.first()
			.waitFor();
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

		await this.publishPageButton.isEnabled();
	}

	async searchFragmentOrWidget(itemName: string) {
		await this.fragmentWidgetSearchInput.fill(itemName);
	}
}
