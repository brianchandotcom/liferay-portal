/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {
	CommerceDNDTablePage,
	searchTableRowByValue,
} from '../commerceDNDTablePage';

export class CommerceAdminInventoryPage extends CommerceDNDTablePage {
	readonly backLink: Locator;
	readonly changeLogLink: Locator;
	readonly commerceInventoryTable: Locator;
	readonly commerceInventoryTableActions: (sku: string) => Promise<Locator>;
	readonly commerceInventoryTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly deleteItemMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;

	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#p_p_id_com_liferay_commerce_inventory_web_internal_portlet_CommerceInventoryPortlet_ .fds table'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.backLink = page.locator('span[title="Back"]');
		this.changeLogLink = page.getByRole('link', {name: 'Changelog'});
		this.commerceInventoryTable = page.locator(
			'#p_p_id_com_liferay_commerce_inventory_web_internal_portlet_CommerceInventoryPortlet_ .fds table'
		);
		this.commerceInventoryTableActions = async (sku: string) => {
			const itemsTableRow = await this.commerceInventoryTableRow(
				0,
				sku,
				true
			);

			if (itemsTableRow && itemsTableRow.column) {
				return itemsTableRow.row.getByRole('button', {
					exact: true,
					name: `${sku} Actions`,
				});
			}

			throw new Error(`Cannot locate inventory row with value ${sku}`);
		};
		this.commerceInventoryTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.commerceInventoryTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.deleteItemMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.page = page;
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Inventory');
	}
}
