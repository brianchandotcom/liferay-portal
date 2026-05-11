/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceAdminInventoryItemPage {
	readonly incomingAddButton: Locator;
	readonly incomingRowActionsButton: (warehouseName: string) => Locator;
	readonly incomingRowDateCell: (warehouseName: string) => Locator;
	readonly incomingRowLink: (warehouseName: string) => Locator;
	readonly incomingRowQuantityCell: (warehouseName: string) => Locator;
	readonly incomingTab: Locator;
	readonly inventoryByWarehouseTable: Locator;
	readonly modalDateInput: Locator;
	readonly modalDestinationSelect: Locator;
	readonly modalFrameLocator: FrameLocator;
	readonly modalQuantityInput: Locator;
	readonly modalSubmitButton: Locator;
	readonly onOrderRowText: (value: string) => Locator;
	readonly onOrderTab: Locator;
	readonly page: Page;
	readonly searchInput: Locator;
	readonly sidePanelDateInput: Locator;
	readonly sidePanelFrameLocator: FrameLocator;
	readonly sidePanelQuantityInput: Locator;
	readonly sidePanelSafetyStockInput: Locator;
	readonly sidePanelSaveButton: Locator;
	readonly warehouseRowActionsButton: (warehouseName: string) => Locator;
	readonly warehouseRowAvailableCell: (warehouseName: string) => Locator;
	readonly warehouseRowLink: (warehouseName: string) => Locator;
	readonly warehouseRowOnHandCell: (warehouseName: string) => Locator;
	readonly warehouseRowSafetyStockCell: (warehouseName: string) => Locator;

	constructor(page: Page) {
		this.incomingAddButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.incomingRowActionsButton = (warehouseName: string) =>
			page.getByRole('button', {name: `${warehouseName} Actions`});
		this.incomingRowDateCell = (warehouseName: string) =>
			page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: warehouseName})})
				.getByRole('cell')
				.nth(1);
		this.incomingRowLink = (warehouseName: string) =>
			page.getByRole('link', {exact: true, name: warehouseName});
		this.incomingRowQuantityCell = (warehouseName: string) =>
			page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: warehouseName})})
				.getByRole('cell')
				.nth(2);
		this.incomingTab = page.getByRole('link', {
			exact: true,
			name: 'Incoming',
		});
		this.onOrderRowText = (value: string) =>
			page.locator('table').getByText(value, {exact: false});
		this.onOrderTab = page.getByRole('link', {
			exact: true,
			name: 'On Order',
		});
		this.searchInput = page
			.getByTestId('managementToolbar')
			.getByRole('searchbox', {name: 'Search'});
		this.inventoryByWarehouseTable = page.locator('.fds table');
		this.modalFrameLocator = page.frameLocator('.fds-modal-body iframe');
		this.modalDateInput = this.modalFrameLocator.locator(
			'input[name$="date"]'
		);
		this.modalDestinationSelect = this.modalFrameLocator.locator(
			'select[name$="commerceInventoryWarehouseId"]'
		);
		this.modalQuantityInput = this.modalFrameLocator.locator(
			'input[name$="quantity"]'
		);
		this.modalSubmitButton = this.modalFrameLocator.getByRole('button', {
			exact: true,
			name: 'Submit',
		});
		this.page = page;
		this.sidePanelFrameLocator = page.frameLocator(
			'.fds-side-panel iframe'
		);
		this.sidePanelDateInput = this.sidePanelFrameLocator.locator(
			'input[name$="date"]'
		);
		this.sidePanelQuantityInput = this.sidePanelFrameLocator.locator(
			'input[name$="quantity"]'
		);
		this.sidePanelSafetyStockInput = this.sidePanelFrameLocator.locator(
			'input[name$="reservedQuantity"]'
		);
		this.sidePanelSaveButton = this.sidePanelFrameLocator
			.getByRole('button', {name: 'Save'})
			.first();
		this.warehouseRowActionsButton = (warehouseName: string) =>
			page.getByRole('button', {name: `${warehouseName} Actions`});
		this.warehouseRowAvailableCell = (warehouseName: string) =>
			page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: warehouseName})})
				.getByRole('cell')
				.nth(3);
		this.warehouseRowLink = (warehouseName: string) =>
			page.getByRole('link', {exact: true, name: warehouseName});
		this.warehouseRowOnHandCell = (warehouseName: string) =>
			page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: warehouseName})})
				.getByRole('cell')
				.nth(1);
		this.warehouseRowSafetyStockCell = (warehouseName: string) =>
			page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: warehouseName})})
				.getByRole('cell')
				.nth(2);
	}
}
