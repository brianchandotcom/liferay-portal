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

	return null;
};

export class DataTablePage {
	readonly cell: (value: string, exact?: boolean) => Locator;
	readonly cellLink: (
		value: string,
		colIndex?: number,
		strictEqual?: boolean
	) => Promise<Locator>;
	readonly clearButton: Locator;
	readonly filterButton: Locator;
	readonly filterMenuItem: (option: string) => Locator;
	readonly firstRow: () => Promise<Locator>;
	readonly lastRow: () => Promise<Locator>;
	readonly newButton: Locator;
	readonly orderButton: Locator;
	readonly orderMenuItem: (option: string) => Locator;
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
	readonly rowCheckbox: (
		value: string,
		colIndex?: number,
		strictEqual?: boolean
	) => Promise<Locator>;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly selectAllItemsCheckbox: Locator;
	readonly selectViewButton: Locator;
	readonly selectViewCardButton: Locator;
	readonly selectViewListButton: Locator;
	readonly selectViewTableButton: Locator;
	readonly table: Locator;
	readonly valueLink: (value: string, exact?: boolean) => Locator;
	readonly viewStatus: (status: string) => Locator;

	constructor(page: Page | FrameLocator, table: Locator) {
		this.page = page;
		this.table = table;

		this.cell = (value, exact = true) =>
			this.page
				.getByRole('cell', {
					exact,
					name: value,
				})
				.first();
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

			return null;
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
		this.firstRow = async () => {
			await this.table.elementHandle();

			return this.table.getByRole('row').nth(1);
		};
		this.lastRow = async () => {
			await this.table.elementHandle();

			return this.table.getByRole('row').last();
		};
		this.newButton = page
			.getByTestId('creationMenuNewButton')
			.or(page.locator('.management-bar'))
			.getByText('New');
		this.orderButton = page.getByLabel('Order');
		this.orderMenuItem = (option: string) => {
			return page.getByRole('menuitem', {
				exact: true,
				name: option,
			});
		};
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

			return null;
		};
		this.rowCheckbox = async (value, colIndex = 1, strictEqual = true) => {
			const row = await this.row(colIndex, value, strictEqual);

			if (row && row.row) {
				return row.row.getByRole('checkbox');
			}

			return null;
		};
		this.searchButton = page.getByLabel('Search for', {exact: true});
		this.searchInput = page.getByPlaceholder('Search for', {exact: true});
		this.selectAllItemsCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.selectViewButton = page.getByLabel('Select View');
		this.selectViewCardButton = page.getByRole('menuitem', {
			name: 'Cards',
		});
		this.selectViewListButton = page.getByRole('menuitem', {name: 'List'});
		this.selectViewTableButton = page.getByRole('menuitem', {
			name: 'Table',
		});
		this.valueLink = (value, exact = true) =>
			page.getByRole('link', {exact, name: value});
		this.viewStatus = (status) =>
			page.getByTitle(`Select View, Currently Selected: ${status}`);
	}

	async changeView(view: string) {
		if (view === 'List') {
			await expect(async () => {
				await this.selectViewButton.click();

				await expect(this.selectViewListButton).toBeVisible({
					timeout: 100,
				});
			}).toPass();

			await this.selectViewListButton.click();
			await expect(this.viewStatus(view)).toBeVisible();

			return;
		}
		else if (view === 'Cards') {
			await expect(async () => {
				await this.selectViewButton.click();

				await expect(this.selectViewCardButton).toBeVisible({
					timeout: 100,
				});
			}).toPass();

			await this.selectViewCardButton.click();
			await expect(this.viewStatus(view)).toBeVisible();

			return;
		}

		await expect(async () => {
			await this.selectViewButton.click();

			await expect(this.selectViewTableButton).toBeVisible({
				timeout: 100,
			});
		}).toPass();

		await this.selectViewTableButton.click();
		await expect(this.viewStatus(view)).toBeVisible();
	}

	async search(value: string) {
		await this.searchInput.fill(value);
		await this.searchButton.click();
		await expect(this.searchInput).toBeEditable();
	}
}
