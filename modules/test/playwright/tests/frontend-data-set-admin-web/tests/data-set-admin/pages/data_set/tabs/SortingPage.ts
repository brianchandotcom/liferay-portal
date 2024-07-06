/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataSetPage} from '../DataSetPage';

export class SortingPage {
	private readonly addSortingButton: Locator;
	private readonly dataSetPage: DataSetPage;
	readonly sortingTable: Locator;
	private readonly addSortingDialog: {
		cancelButton: Locator;
		defaultCheckbox: Locator;
		labelInput: Locator;
		saveButton: Locator;
		sortByInput: Locator;
	};
	readonly page: Page;

	constructor(page: Page) {
		this.addSortingButton = page.getByLabel('New Sort');
		this.dataSetPage = new DataSetPage(page);
		this.sortingTable = page.locator('.table-responsive');
		this.addSortingDialog = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			defaultCheckbox: page.getByLabel('Use as Default Sorting'),
			labelInput: page.getByLabel('Label'),
			saveButton: page.getByRole('button', {name: 'Save'}),
			sortByInput: page.getByLabel('Sort By'),
		};
		this.page = page;
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Sorting');
	}

	async openAddSortingModal() {
		await this.addSortingButton.click();
	}

	async selectTab(tabLabel: string) {
		await this.dataSetPage.selectTab(tabLabel);
	}

	async getTableColumnInnerTexts(index: number): Promise<string[]> {
		return await this.sortingTable
			.locator(`tbody > tr > td:nth-child(${index})`)
			.allInnerTexts();
	}
}
