/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';
import {
	CommerceDNDTablePage,
	searchTableRowByValue,
} from '../commerceDNDTablePage';

export class CommerceAdminOrdersPage extends CommerceDNDTablePage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly backLink: Locator;
	readonly deleteItemMenuItem: Locator;
	readonly editCommerceOrderTable: Locator;
	readonly editCommerceOrderTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly editCommerceOrderTableRows: () => Promise<Locator[]>;
	readonly editCommerceOrderTableRowLink: ({
		colIndex,
		rowValue,
	}) => Promise<Locator>;
	readonly itemsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly itemsTableRows: () => Promise<Locator[]>;
	readonly itemsTableRowAction: (sku: string) => Promise<Locator>;
	readonly keyOrderStatus: (orderStatus: string) => Locator;
	readonly orderStatusLink: (orderStatus: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_fm .dnd-table'
		);

		this.editCommerceOrderTable = page.locator(
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_editOrderContainer .dnd-table'
		);
		this.editCommerceOrderTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.editCommerceOrderTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.editCommerceOrderTableRows = async () => {
			await this.editCommerceOrderTable.elementHandle();

			return await this.editCommerceOrderTable
				.locator('div.dnd-tbody div.dnd-tr')
				.all();
		};
		this.editCommerceOrderTableRowLink = async ({
			colIndex = 1,
			rowValue,
		}: {
			colIndex: number;
			rowValue: number | string;
		}) => {
			const tableRow = await this.editCommerceOrderTableRow(
				colIndex,
				rowValue,
				true
			);

			if (tableRow && tableRow.column) {
				return tableRow.column.getByRole('link', {
					name: String(rowValue),
				});
			}

			throw new Error(`Cannot locate row with rowValue: ${rowValue}`);
		};

		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.deleteItemMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.itemsTableRow = this.editCommerceOrderTableRow;
		this.itemsTableRows = this.editCommerceOrderTableRows;
		this.itemsTableRowAction = async (sku: string) => {
			const itemsTableRow = await this.itemsTableRow(1, sku, true);

			if (itemsTableRow && itemsTableRow.column) {
				return itemsTableRow.row.getByRole('button', {
					exact: true,
					name: 'Actions',
				});
			}

			throw new Error(`Cannot locate order row with value ${sku}`);
		};
		this.keyOrderStatus = (orderStatus: string) =>
			page.locator('.dnd-table').getByText(orderStatus);
		this.orderStatusLink = (orderStatus: string) =>
			page.getByRole('link', {exact: true, name: orderStatus});
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceOrders();
	}
}
