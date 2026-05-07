/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {
	CommerceDNDTablePage,
	searchTableRowByValue,
} from '../commerceDNDTablePage';

export class CommerceAdminInventoryPage extends CommerceDNDTablePage {
	readonly addButton: Locator;
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
	readonly modalFrameLocator: FrameLocator;
	readonly modalQuantityInput: Locator;
	readonly modalSkuInput: Locator;
	readonly modalSubmitButton: Locator;
	readonly modalWarehouseSelect: Locator;

	readonly page: Page;
	readonly searchInput: Locator;
	readonly skuLink: (sku: string) => Locator;

	constructor(page: Page) {
		super(
			page,
			'#p_p_id_com_liferay_commerce_inventory_web_internal_portlet_CommerceInventoryPortlet_ .fds table'
		);
		this.addButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
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
		this.modalFrameLocator = page.frameLocator('.fds-modal-body iframe');
		this.modalQuantityInput = this.modalFrameLocator.locator(
			'input[name$="quantity"]'
		);
		this.modalSkuInput =
			this.modalFrameLocator.locator('input[name$="sku"]');
		this.modalSubmitButton = this.modalFrameLocator.getByRole('button', {
			exact: true,
			name: 'Submit',
		});
		this.modalWarehouseSelect = this.modalFrameLocator.locator(
			'select[name$="commerceInventoryWarehouseId"]'
		);
		this.page = page;
		this.searchInput = page
			.getByTestId('managementToolbar')
			.getByRole('searchbox', {name: 'Search'});
		this.skuLink = (sku: string) =>
			page.getByRole('link', {exact: true, name: sku});
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Inventory');
	}
}
