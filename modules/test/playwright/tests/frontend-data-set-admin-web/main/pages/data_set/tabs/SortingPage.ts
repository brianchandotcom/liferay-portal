/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {DataSetPage} from '../DataSetPage';

export class SortingPage {
	readonly activeToggle: Locator;
	private readonly addSortingButton: Locator;
	private readonly dataSetPage: DataSetPage;
	readonly inactiveToggle: Locator;
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
		this.activeToggle = page.getByLabel('Active', {exact: true});
		this.addSortingButton = page.getByLabel('New Sort');
		this.dataSetPage = new DataSetPage(page);
		this.inactiveToggle = page.getByLabel('Inactive', {
			exact: true,
		});
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

	async assertTableCellContent({page, rowIndex = 0, sortData}) {
		await page
			.locator('.orderable-table > tbody > .orderable-table-row')
			.first()
			.waitFor();

		const tableRowContent = await page
			.locator('.orderable-table-row')
			.nth(rowIndex)
			.locator('td');

		const expectedRowContent = [
			sortData.name,
			sortData.sortBy,
			sortData.default,
			sortData.status,
		];

		await expect(tableRowContent).toContainText(expectedRowContent);

		if (!Object.keys(sortData).includes('actionsDropdown')) {
			return;
		}

		if (sortData.actionsDropdown) {
			await expect(tableRowContent.locator('.dropdown')).toBeAttached();
		}
		else {
			await expect(
				tableRowContent.locator('.dropdown')
			).not.toBeAttached();
		}
	}

	async assertSortsTableRowCount(rowCount: number) {
		await expect(
			this.sortingTable.locator('tbody').locator('tr')
		).toHaveCount(rowCount);
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Sorting');
	}

	async open({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.open({
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
