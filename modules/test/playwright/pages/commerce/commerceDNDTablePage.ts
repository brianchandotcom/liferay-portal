/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export const searchTableRowByValue = async function (
	tableLocator: Locator,
	colPosition: number,
	value: string,
	strictEqual: boolean = false
) {
	await tableLocator.elementHandle();

	const rows = await tableLocator.locator('div.dnd-tr').all();

	for await (const row of rows) {
		const column = row.locator('div.dnd-td').nth(colPosition).first();

		const colValue = (await column.allInnerTexts()).join('');

		if (
			(strictEqual && colValue === value) ||
			(!strictEqual &&
				colValue.toLowerCase().indexOf(value.toLowerCase()) >= 0)
		) {
			return {column, row};
		}
	}

	throw new Error(`Cannot locate table row with value ${value}`);
};

export class CommerceDNDTablePage {
	readonly addFilterButton: Locator;
	readonly backButton: Locator;
	readonly emptyTableMessage: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (name: string) => Locator;
	readonly filterValue: (value: string) => Locator;
	readonly resetFiltersButton: Locator;
	readonly table: Locator;
	readonly tableHeaders: Locator;
	readonly tableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly tableRows: () => Promise<Locator[]>;
	readonly tableRowLink: ({colIndex, rowValue}) => Promise<Locator>;

	constructor(page: Page, tableIdentifier: string) {
		this.addFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Add Filter',
		});
		this.backButton = page.getByRole('button', {exact: true, name: 'Back'});
		this.emptyTableMessage = page.getByText('No Results Found');
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.filterMenuItem = (name: string) =>
			page.getByRole('menuitem', {exact: true, name});
		this.filterValue = (value: string) => page.getByLabel(value);
		this.resetFiltersButton = page.getByRole('button', {
			exact: true,
			name: 'Reset Filters',
		});
		this.table = page.locator(tableIdentifier);
		this.tableHeaders = this.table.locator('div.dnd-tr').first();
		this.tableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.table,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.tableRows = async () => {
			await this.table.elementHandle();

			return await this.table.locator('div.dnd-tbody div.dnd-tr').all();
		};
		this.tableRowLink = async ({
			colIndex = 1,
			rowValue,
		}: {
			colIndex: number;
			rowValue: number | string;
		}) => {
			const tableRow = await this.tableRow(colIndex, rowValue, true);

			if (tableRow && tableRow.column) {
				return tableRow.column.getByRole('link', {
					name: String(rowValue),
				});
			}

			throw new Error(`Cannot locate row with rowValue: ${rowValue}`);
		};
	}

	async addDataSetFilter(
		filterName: string,
		filterValue: string,
		exclude: boolean = false
	) {
		await this.filterButton.click();
		await this.filterMenuItem(filterName).click();
		await this.filterValue(filterValue).check();

		if (exclude) {
			await this.filterValue('Exclude').check();
		}

		await this.addFilterButton.click();
		await this.backButton.click();
		await this.filterButton.click();
	}
}
