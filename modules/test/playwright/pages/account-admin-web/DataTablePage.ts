/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

export const searchTableRowByValue = async function (
	tableLocator: Locator,
	colPosition: number,
	value: string,
	strictEqual: boolean = false
) {
	await tableLocator.elementHandle();

	const rows = await tableLocator.getByRole('row').all();

	for await (const row of rows) {
		const column = row.getByRole('cell').nth(colPosition).first();

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

export class DataTablePage {
	readonly cell: (value: string) => Locator;
	readonly cellLink: (
		value: string,
		colIndex?: number,
		strictEqual?: boolean
	) => Promise<Locator>;
	readonly clearButton: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (option: string) => Locator;
	readonly newButton: Locator;
	readonly page: Page | FrameLocator;
	readonly row: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly rowActions: (
		value: string,
		colIndex?: number,
		strictEqual?: boolean
	) => Promise<Locator>;
	readonly rowCheckBox: (
		value: string,
		colIndex?: number,
		strictEqual?: boolean
	) => Promise<Locator>;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly selectAllItemsCheckbox: Locator;
	readonly table: Locator;
	readonly valueLink: (value: string) => Locator;

	constructor(page: Page | FrameLocator, table: Locator) {
		this.page = page;
		this.table = table;

		this.cell = (value) =>
			this.page.getByRole('cell', {
				exact: true,
				name: value,
			}).first();
		this.cellLink = async (value, colIndex = 1, strictEqual = true) => {
			const row = await this.row(colIndex, value, strictEqual);

			if (row && row.column) {
				return row.column
					.getByRole('link', {
						exact: strictEqual,
						name: value,
					})
					.first();
			}

			throw new Error(`Cannot locate row with value ${value}`);
		};
		this.clearButton = page.getByRole('button', {name: 'Clear'});
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.filterMenuItem = (option: string) => {
			return page.getByRole('menuitem', {
				exact: true,
				name: option,
			});
		};
		this.newButton = page
			.getByTestId('creationMenuNewButton')
			.getByText('New');
		this.row = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.table,
				colPosition,
				value,
				strictEqual
			);
		};
		this.rowActions = async (value, colIndex = 1, strictEqual = true) => {
			const row = await this.row(colIndex, value, strictEqual);

			if (row && row.column) {
				return row.row.getByRole('button');
			}

			throw new Error(`Cannot locate row with value ${value}`);
		};
		this.rowCheckBox = async (value, colIndex = 1, strictEqual = true) => {
			const row = await this.row(colIndex, value, strictEqual);

			if (row && row.row) {
				return row.row.getByRole('checkbox');
			}

			throw new Error(`Cannot locate row with value ${value}`);
		};
		this.searchButton = page.getByLabel('Search for', {exact: true});
		this.searchInput = page.getByPlaceholder('Search for', {exact: true});
		this.selectAllItemsCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.valueLink = (value) =>
			page.getByRole('link', {exact: true, name: value});
	}

	async search(value: string) {
		await this.searchInput.fill(value);
		await this.searchButton.click();
		await expect(this.searchInput).toBeEditable();
	}
}
