/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from '../commerceDNDTablePage';

export class CommerceAdminProductDetailsMediaPage {
	readonly addImageButton: Locator;
	readonly creationMenuNewButton: Locator;
	readonly mediaAttachmentsTable: Locator;
	readonly mediaForm: Locator;
	readonly mediaImagesTable: Locator;
	readonly page: Page;
	readonly tableRow: (
		tableName: Locator,
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly tableRowModifiedDateField: (
		rowValue: string,
		table: Locator
	) => Promise<Locator>;

	constructor(page: Page) {
		this.addImageButton = page.getByLabel('Add Image');
		this.mediaForm = page.locator(
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm'
		);
		this.mediaAttachmentsTable = this.mediaForm
			.locator('.dnd-table')
			.nth(1);
		this.mediaImagesTable = this.mediaForm.locator('.dnd-table').nth(0);
		this.page = page;
		this.tableRow = async (
			table: Locator,
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				table,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.tableRowModifiedDateField = async (rowValue, table) => {
			const tableRow = await this.tableRow(table, 1, rowValue, true);

			if (tableRow && tableRow.row) {
				return tableRow.row.locator('.cell-modifiedDate');
			}

			throw new Error(`Cannot locate row with rowValue: ${rowValue}`);
		};
	}
}
