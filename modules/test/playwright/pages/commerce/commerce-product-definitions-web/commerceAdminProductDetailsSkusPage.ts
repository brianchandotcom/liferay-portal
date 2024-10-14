/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {
	CommerceDNDTablePage,
	searchTableRowByValue,
} from '../commerceDNDTablePage';

export class CommerceAdminProductDetailsSkusPage extends CommerceDNDTablePage {
	readonly page: Page;
	readonly pricinQuantity: Locator;
	readonly skusLink: Locator;
	readonly skusTable: Locator;
	readonly skusTableRowBasePrice: (price: string) => Promise<Locator>;
	readonly skusTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly skusTableRowLink: (skuName: string) => Locator;
	readonly skuUOMFrame: FrameLocator;
	readonly skuUOMTab: Locator;
	readonly skuUOMFrameCancelButton: Locator;
	readonly skuUOMFrameSaveButton: Locator;
	readonly uomTableRowLink: (uomName: string) => Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .dnd-table'
		);

		this.page = page;
		this.pricinQuantity = page
			.frameLocator('iframe')
			.first()
			.frameLocator('iframe')
			.getByLabel('Pricing Quantity');
		this.skusLink = page.getByRole('link', {
			exact: true,
			name: 'Skus',
		});
		this.skusTable = page.locator(
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .dnd-table'
		);
		this.skusTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.skusTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.skusTableRowBasePrice = async (price: string) => {
			const shipmentTableRow = await this.skusTableRow(2, price, true);

			if (shipmentTableRow && shipmentTableRow.column) {
				return shipmentTableRow.row.getByText(price, {exact: true});
			}

			throw new Error(`Cannot locate shipment row with value ${price}`);
		};
		this.skusTableRowLink = (sku: string) =>
			page.getByRole('link', {exact: true, name: sku});
		this.skuUOMFrame = page.frameLocator('iframe').first();
		this.skuUOMTab = this.skuUOMFrame.getByRole('link', {
			name: 'Units of Measure',
		});
		this.skuUOMFrameCancelButton = this.skuUOMFrame
			.frameLocator('iframe')
			.getByRole('button', {name: 'Cancel'});
		this.skuUOMFrameSaveButton = this.skuUOMFrame
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'});
		this.uomTableRowLink = (uom: string) =>
			this.skuUOMFrame.getByRole('link', {exact: true, name: uom});
	}

	async goToSkuUOM() {
		await this.skuUOMTab.click();
	}
}
