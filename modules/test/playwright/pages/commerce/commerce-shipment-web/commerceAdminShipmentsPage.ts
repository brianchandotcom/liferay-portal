/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {searchTableRowByValue} from '../commerceDNDTablePage';
import {CommerceIframeDNDTablePage} from '../commerceIframeDNDTablePage';

export class CommerceAdminShipmentsPage extends CommerceIframeDNDTablePage {
	readonly addProductsToShipment: Locator;
	readonly addQuantityInShipment: Locator;
	readonly backLink: Locator;
	readonly baseTrackingURLValue: Locator;
	readonly carrierDetailsEditLink: Locator;
	readonly carrierDetailsShippingMethodSelect: Locator;
	readonly carrierDetailsSubmitButton: Locator;
	readonly carrierDetailsTrackingURLInput: Locator;
	readonly customFieldInput: (fieldName: string) => Locator;
	readonly customFieldsSaveButton: Locator;
	readonly customFieldsTab: Locator;
	readonly deleteMenuItem: Locator;
	readonly deleteModalSubmitButton: Locator;
	readonly editProductCloseButton: Locator;
	readonly editProductFrame: FrameLocator;
	readonly editProductMenuItem: Locator;
	readonly editProductSaveButton: Locator;
	readonly editProductTable: Locator;
	readonly editProductTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly editProductTableRowQuantitySelector: ({
		colIndex,
		rowValue,
	}) => Promise<Locator>;
	readonly editProductWarehouseAvailabilityTable: Locator;
	readonly editProductWarehouseAvailabilityTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly globalMenuPage: GlobalMenuPage;
	readonly keyShipmentStatus: (orderStatus: string) => Locator;
	readonly page: Page;
	readonly productEllipsis: (productName: string) => Locator;
	readonly productsSkuLink: (sku: string) => Locator;
	readonly searchInput: Locator;
	readonly shipmentIdLink: (shipmentId: string) => Locator;
	readonly shipmentItemCell: (sku: string, value: string) => Locator;
	readonly shipmentItemFrame: FrameLocator;
	readonly shipmentItemRow: (sku: string) => Locator;
	readonly shipmentItemRowActions: (sku: string) => Locator;
	readonly shipmentItemsTable: Locator;
	readonly shipmentItemsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly shipmentItemsTableRowAction: (
		colposition: number,
		value: string
	) => Promise<Locator>;
	readonly shipmentItemsTableRows: () => Promise<Locator[]>;
	readonly shipmentListRowActions: (shipmentId: number | string) => Locator;
	readonly shipmentsItemSubmitButton: Locator;
	readonly shipmentStatusLink: (shipmentStatus: string) => Locator;
	readonly shippingMethodSelect: Locator;

	constructor(page: Page) {
		super(
			page,
			'iframe >> nth=1',
			'#_com_liferay_commerce_shipment_web_internal_portlet_CommerceShipmentPortlet_fm .fds table'
		);
		this.editProductFrame = page.frameLocator('iframe');

		this.addProductsToShipment = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.addQuantityInShipment =
			this.editProductFrame.getByRole('spinbutton');
		this.backLink = page.locator('span[title="Back"]');
		this.baseTrackingURLValue = page
			.locator('.item')
			.filter({hasText: 'Base Tracking URL'})
			.locator('b');
		this.carrierDetailsEditLink = page
			.getByText('Carrier Details Edit')
			.getByRole('link');
		this.carrierDetailsShippingMethodSelect = page
			.frameLocator('iframe[src*="courier_detail"]')
			.locator('select[name$="_shippingMethod"]');
		this.carrierDetailsSubmitButton = page
			.locator('.modal-item-last')
			.getByRole('button', {exact: true, name: 'Submit'});
		this.carrierDetailsTrackingURLInput = page
			.frameLocator('iframe[src*="courier_detail"]')
			.locator('[name$="_trackingURL"]');
		this.customFieldInput = (fieldName: string) =>
			page.locator(`[name$="ExpandoAttribute--${fieldName}--"]`);
		this.customFieldsSaveButton = page
			.locator('form[name$="shipmentCustomFieldFm"]')
			.getByRole('button', {exact: true, name: 'Save'});
		this.customFieldsTab = page.getByRole('link', {
			exact: true,
			name: 'Custom Fields',
		});
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.deleteModalSubmitButton = page
			.frameLocator('iframe[src*="delete_commerce_shipment"]')
			.getByRole('button', {exact: true, name: 'Submit'});
		this.editProductCloseButton = this.editProductFrame
			.getByRole('button')
			.first();
		this.editProductMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Edit',
		});
		this.editProductSaveButton = this.editProductFrame.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.editProductTable = page.locator(
			'#_com_liferay_commerce_shipment_web_internal_portlet_CommerceShipmentPortlet_editShipmentContainer .fds table'
		);
		this.editProductTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.editProductTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.editProductTableRowQuantitySelector = async ({
			colIndex = 0,
			rowValue,
		}: {
			colIndex: number;
			rowValue: number | string;
		}) => {
			const tableRow =
				await this.editProductWarehouseAvailabilityTableRow(
					colIndex,
					rowValue,
					true
				);

			if (tableRow && tableRow.column) {
				return tableRow.row.getByRole('spinbutton');
			}

			throw new Error(`Cannot locate row with rowValue: ${rowValue}`);
		};
		this.editProductWarehouseAvailabilityTable =
			this.editProductFrame.locator(
				'#_com_liferay_commerce_shipment_web_internal_portlet_CommerceShipmentPortlet_fm .fds table'
			);
		this.editProductWarehouseAvailabilityTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.editProductWarehouseAvailabilityTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.globalMenuPage = new GlobalMenuPage(page);
		this.keyShipmentStatus = (orderStatus: string) =>
			page.getByText(orderStatus);
		this.page = page;
		this.productEllipsis = (productName: string) =>
			page.getByRole('button', {
				exact: true,
				name: `${productName} Actions`,
			});
		this.productsSkuLink = (sku: string) =>
			page.getByRole('link', {exact: true, name: sku});
		this.searchInput = page
			.locator('[data-testid="visualization-mode-table"]')
			.getByRole('searchbox', {name: 'Search'});
		this.shipmentIdLink = (shipmentId: string) =>
			page
				.locator('table')
				.getByRole('link', {exact: true, name: shipmentId});
		this.shipmentItemCell = (sku: string, value: string) =>
			this.shipmentItemRow(sku).getByRole('cell', {
				exact: true,
				name: value,
			});
		this.shipmentItemFrame = page.frameLocator('iframe >> nth=1');
		this.shipmentItemRow = (sku: string) =>
			page.getByRole('row').filter({hasText: sku});
		this.shipmentItemRowActions = (sku: string) =>
			this.shipmentItemRow(sku).getByRole('button');
		this.shipmentItemsTable = this.table;
		this.shipmentItemsTableRow = this.tableRow;
		this.shipmentItemsTableRowAction = async (
			colposition: number,
			value: string
		) => {
			const shipmentTableRow = await this.shipmentItemsTableRow(
				colposition,
				value,
				true
			);

			if (shipmentTableRow && shipmentTableRow.column) {
				return shipmentTableRow.row.getByLabel('');
			}

			throw new Error(`Cannot locate shipment row with value ${value}`);
		};
		this.shipmentItemsTableRows = this.tableRows;
		this.shipmentListRowActions = (shipmentId: number | string) =>
			page
				.getByRole('row')
				.filter({hasText: String(shipmentId)})
				.getByRole('button');
		this.shipmentsItemSubmitButton = page
			.frameLocator('iframe >> nth=1')
			.getByRole('button', {exact: true, name: 'Submit'});
		this.shipmentStatusLink = (shipmentStatus: string) =>
			page.getByRole('link', {exact: true, name: shipmentStatus});
		this.shippingMethodSelect = page
			.frameLocator('iframe >> nth=1')
			.getByText('Shipping Method');
	}

	async fillShipmentItemQuantityByWarehouse(
		warehouseName: string,
		quantity: string
	) {
		await this.editProductFrame
			.locator('tr', {hasText: warehouseName})
			.getByRole('spinbutton')
			.fill(quantity);
	}

	async goTo() {
		await this.globalMenuPage.goToCommerce('Shipments');
	}

	async search(value: number | string) {
		await this.searchInput.fill(String(value));
		await this.searchInput.press('Enter');
	}
}
