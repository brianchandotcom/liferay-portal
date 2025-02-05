/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ResultRankingsViewPage {
	readonly addResultRankingButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly resultRankingsTable: Locator;
	readonly resultRankingItem: (label: string) => Promise<Locator>;
	readonly navBar: Locator;

	constructor(page: Page) {
		this.addResultRankingButton = page.getByRole('link', {
			name: 'New Ranking',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.navBar = page.locator('nav');
		this.resultRankingsTable = page.getByRole('table');

		this.page = page;

		// Result Rankings Table

		this.resultRankingItem = async (searchQuery: string) => {
			return this.resultRankingsTable.locator('tr').filter({
				has: page.getByRole('link', {name: searchQuery}),
			});
		};
	}

	// Navigation

	async goto() {
		await this.applicationsMenuPage.goToResultRankings();
	}

	async createResultRanking(searchQuery: string) {
		await this.addResultRankingButton.click();

		await this.page.getByLabel('Search Query').fill(searchQuery);

		await this.page
			.getByRole('button', {name: 'Customize Results'})
			.click();

		await expect(
			this.page.getByRole('heading', {name: searchQuery})
		).toBeVisible();

		await this.page.getByRole('button', {name: 'Save'}).click();
	}

	async deleteAllResultRankings() {
		this.page.on('dialog', (dialog) => dialog.accept());

		await this.navBar.getByLabel('Select All Items on the Page').click();

		await this.navBar.getByRole('button', {name: 'Delete'}).click();

		await expect(
			this.page.getByText('No Custom Results Yet')
		).toBeVisible();
	}

	async deleteResultRanking(searchQuery: string) {
		this.page.on('dialog', (dialog) => dialog.accept());

		await this.selectTableMenuOption(searchQuery, 'Delete');

		await expect(
			await this.resultRankingItem(searchQuery)
		).not.toBeVisible();
	}

	async selectTableLink(searchQuery: string) {
		await expect(this.resultRankingsTable).toBeVisible();

		const itemLink = this.resultRankingsTable.getByRole('link', {
			name: searchQuery,
		});

		await itemLink.click();
	}

	async selectTableMenuOption(searchQuery: string, option: string) {
		await expect(this.resultRankingsTable).toBeVisible();

		const itemRow = await this.resultRankingItem(searchQuery);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page
				.locator('.dropdown-menu')
				.getByRole('link', {name: option}),
			trigger: itemRow.locator('.component-action.dropdown-toggle'),
		});
	}
}
