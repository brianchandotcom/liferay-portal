/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {CommerceDNDTablePage} from './commerceDNDTablePage';

export class CommerceAdminOrdersPage extends CommerceDNDTablePage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly backLink: Locator;
	readonly deleteItemMenuItem: Locator;
	readonly itemsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly itemsTableRows: () => Promise<Locator[]>;
	itemsTableRowAction: (countryName: string) => Promise<Locator>;
	readonly keyOrderStatus: (orderStatus: string) => Locator;
	readonly orderIdLink: (orderId: string) => Locator;
	readonly orderStatusLink: (orderStatus: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_editOrderContainer .dnd-table'
		);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.deleteItemMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.itemsTableRow = this.tableRow;
		this.itemsTableRows = this.tableRows;
		this.itemsTableRowAction = async (sku: string) => {
			const itemsTableRow = await this.itemsTableRow(1, sku, true);

			if (itemsTableRow && itemsTableRow.column) {
				return itemsTableRow.row.getByRole('button', {
					exact: true,
					name: 'Actions',
				});
			}

			throw new Error(`Cannot locate country row with name ${sku}`);
		};
		this.keyOrderStatus = (orderStatus: string) =>
			page.locator('.dnd-table').getByText(orderStatus);
		this.orderStatusLink = (orderStatus: string) =>
			page.getByRole('link', {exact: true, name: orderStatus});
		this.orderIdLink = (orderId: string) =>
			page.getByRole('link', {exact: true, name: orderId});
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceOrders();
	}
}
