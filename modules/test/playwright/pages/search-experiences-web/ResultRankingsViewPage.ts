/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class ResultRankingsViewPage {
	readonly addButton: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly navBar: Locator;

	readonly table: {
		readonly container: Locator;
		readonly link: (searchQuery: string) => Promise<Locator>;
		readonly row: (searchQuery: string) => Promise<Locator>;
	};

	constructor(page: Page) {
		this.addButton = page.getByRole('link', {
			name: 'New Ranking',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.navBar = page.locator('nav');

		this.page = page;

		const tableContainer = page.getByRole('table');

		this.table = {
			container: tableContainer,
			link: async (searchQuery: string) => {
				return tableContainer.getByRole('link', {name: searchQuery});
			},
			row: async (searchQuery: string) => {
				return tableContainer.locator('tr').filter({
					has: page.getByRole('link', {name: searchQuery}),
				});
			},
		};
	}

	async goto() {
		await this.globalMenuPage.goToApplications('Result Rankings');
	}

	async createResultRanking(searchQuery: string) {
		await this.addButton.click();

		await this.page.getByLabel('Search Query').fill(searchQuery);

		await this.page
			.getByRole('button', {name: 'Customize Results'})
			.click();

		await expect(
			this.page.getByRole('heading', {name: searchQuery})
		).toBeVisible();
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

		await expect(await this.table.row(searchQuery)).not.toBeVisible();
	}

	async selectTableLink(searchQuery: string) {
		await expect(this.table.container).toBeVisible();

		const tableLink = await this.table.link(searchQuery);

		await tableLink.click();
	}

	async selectTableMenuOption(searchQuery: string, option: string) {
		await expect(this.table.container).toBeVisible();

		const tableRow = await this.table.row(searchQuery);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page
				.locator('.dropdown-menu')
				.getByRole('link', {name: option}),
			trigger: tableRow.locator('.component-action.dropdown-toggle'),
		});
	}
}
