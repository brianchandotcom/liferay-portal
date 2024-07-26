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

export class CommerceIframeDNDTablePage {
	readonly emptyTableMessage: Locator;
	readonly table: Locator;
	readonly tableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly tableRows: () => Promise<Locator[]>;
	readonly tableRowLink: ({colIndex, rowValue}) => Promise<Locator>;

	constructor(
		page: Page,
		tableIframeIdentifier: string,
		tableIdentifier: string
	) {
		this.emptyTableMessage = page.getByText('No Results Found');
		this.table = page
			.frameLocator(tableIframeIdentifier)
			.locator(tableIdentifier);
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
}
