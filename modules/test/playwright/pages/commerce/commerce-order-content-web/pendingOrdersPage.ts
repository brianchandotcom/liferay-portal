/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {
	CommerceDNDTablePage,
	searchTableRowByValue,
} from '../commerceDNDTablePage';
import {CommerceLayoutsPage} from './commerceLayoutsPage';

export class PendingOrdersPage extends CommerceDNDTablePage {
	readonly approveButton: Locator;
	readonly checkoutButton: Locator;
	readonly doneButton: Locator;
	readonly editMenuItem: Locator;
	readonly errorMessageCloseButton: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly orderItemActionsButton: Locator;
	readonly orderItemActionsButtonEdit: Locator;
	readonly orderItemsTable: Locator;
	readonly orderItemsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly orderItemsTableRowLink: (productName: string) => Promise<Locator>;
	readonly page: Page;
	readonly pageLabel: Locator;
	readonly pageTitle: Locator;
	readonly panelList: Locator;
	readonly skuLink: (sku: string) => Locator;
	readonly viewButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#portlet_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet .dnd-table'
		);

		this.approveButton = page.getByText('Approve');
		this.checkoutButton = page.getByText('Checkout');
		this.doneButton = page.getByRole('button', {
			exact: true,
			name: 'Done',
		});
		this.editMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Edit',
		});
		this.errorMessageCloseButton = page.getByRole('button', {
			name: 'close',
		});
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.orderItemActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.orderItemActionsButtonEdit = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.orderItemsTable = page.locator(
			'#portlet_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet .dnd-table'
		);
		this.orderItemsTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.orderItemsTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.orderItemsTableRowLink = async (productName: string) => {
			const orderItemsTableRow = await this.orderItemsTableRow(
				0,
				productName,
				true
			);

			if (orderItemsTableRow && orderItemsTableRow.column) {
				return orderItemsTableRow.row.getByRole('button', {
					exact: true,
					name: 'Actions',
				});
			}

			throw new Error(
				`Cannot locate order item row with productName ${productName}`
			);
		};
		this.page = page;
		this.pageLabel = page
			.getByTestId('layoutHref')
			.getByLabel('Pending Orders Page');
		this.pageTitle = page
			.getByTestId('headerTitle')
			.filter({hasText: 'Pending Orders Page'});
		this.panelList = page
			.getByTestId('specificationFacetPanel')
			.getByRole('button');
		this.skuLink = (sku) => page.getByRole('link', {name: sku});
		this.viewButton = page.getByLabel('View');
	}

	async addPendingOrdersWidget() {
		await this.layoutsPage.addWidgetToPage('Open Carts');
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
