/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

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

export class CommerceAdminOrdersPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly ordersTable: Locator;
	readonly ordersTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly ordersTableRowLink: (orderId: number | string) => Promise<Locator>;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.ordersTable = page.locator(
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_fm .dnd-table'
		);
		this.ordersTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.ordersTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.ordersTableRowLink = async (orderId: number | string) => {
			const ordersTableRow = await this.ordersTableRow(1, orderId, true);

			if (ordersTableRow && ordersTableRow.column) {
				return ordersTableRow.column.getByRole('link', {
					name: String(orderId),
				});
			}

			throw new Error(`Cannot locate order row with orderId ${orderId}`);
		};
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceOrders();
	}
}
