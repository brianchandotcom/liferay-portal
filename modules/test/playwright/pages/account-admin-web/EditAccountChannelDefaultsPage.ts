/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class EditAccountChannelDefaultsPage {
	readonly addDefaultBillingAddressButton: Locator;
	readonly addDefaultShippingAddressButton: Locator;
	readonly addDefaultPaymentTermButton: Locator;
	readonly addDefaultPaymentTermSelector: Locator;
	readonly modalContainer: FrameLocator;
	readonly modalSaveButton: Locator;
	readonly addressTableRowColumn: (
		columnIndex: number,
		tableName: string,
		text: string
	) => Promise<Locator>;
	readonly billingAddressAllChannelsText: Locator;
	readonly billingAddressAllOtherChannelsText: Locator;
	readonly defaultBillingAddressesTable: Locator;
	readonly defaultShippingAddressesTable: Locator;
	readonly defaultShippingOptionsTable: Locator;
	readonly defaultShippingOptionsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly defaultShippingOptionsTableRowAction: (
		action: string,
		channelName: string
	) => Promise<Locator>;
	readonly deleteMenuItem: Locator;
	readonly getRowByTextFromTable: (
		tableName: string,
		text: string
	) => Locator;
	readonly modalOptionCheckbox: (optionName: string) => Locator;
	readonly page: Page;
	readonly setDefaultBillingAddressFrameBillingAddressDropdownMenu: Locator;
	readonly setDefaultAddressFrameChannelDropdownMenu: Locator;
	readonly setDefaultShippingAddressFrameBillingAddressDropdownMenu: Locator;
	readonly shippingAddressAllChannelsText: Locator;
	readonly shippingAddressAllOtherChannelsText: Locator;

	constructor(page: Page) {
		this.addDefaultBillingAddressButton = page
			.getByTestId('defaultBillingCommerceAddresses')
			.getByRole('button', {name: 'Add Default Address'})
			.first();
		this.addDefaultPaymentTermButton = page
			.locator(
				"[id='_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_defaultPaymentCommerceTermEntries']"
			)
			.getByRole('button', {name: 'Add Default Term'})
			.first();
		this.modalContainer = page.frameLocator('.fds-modal-body > iframe');
		this.addDefaultPaymentTermSelector =
			this.modalContainer.getByLabel('Term');
		this.addDefaultShippingAddressButton = page
			.getByTestId('defaultShippingCommerceAddresses')
			.getByRole('button', {name: 'Add Default Address'})
			.first();
		this.addressTableRowColumn = async (
			columnIndex: number,
			tableName: 'Billing' | 'Shipping',
			text: string
		) => {
			return this.getRowByTextFromTable(
				`default${tableName}CommerceAddresses`,
				text
			)
				.locator('td')
				.nth(columnIndex);
		};
		this.defaultBillingAddressesTable = page.getByTestId(
			'defaultBillingCommerceAddresses'
		);
		this.billingAddressAllChannelsText =
			this.defaultBillingAddressesTable.getByRole('cell', {
				exact: true,
				name: 'All Channels',
			});
		this.billingAddressAllOtherChannelsText =
			this.defaultBillingAddressesTable.getByRole('cell', {
				exact: true,
				name: 'All Other Channels',
			});
		this.defaultShippingAddressesTable = page.getByTestId(
			'defaultShippingCommerceAddresses'
		);
		this.defaultShippingOptionsTable = page.locator(
			'#_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_defaultCommerceShippingOption .fds table'
		);
		this.defaultShippingOptionsTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await this.searchTableRowByValue(
				this.defaultShippingOptionsTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.defaultShippingOptionsTableRowAction = async (
			action: string,
			channelName: string
		) => {
			const shippingOptionsTableRow =
				await this.defaultShippingOptionsTableRow(0, channelName, true);

			if (shippingOptionsTableRow && shippingOptionsTableRow.column) {
				return shippingOptionsTableRow.row.getByRole('button', {
					name: action,
				});
			}
			throw new Error(
				`Cannot locate shipping option row with name ${channelName}`
			);
		};
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.getRowByTextFromTable = (
			tableName: string,
			text: string
		): Locator => {
			return this.page
				.getByTestId(tableName)
				.locator('tbody')
				.locator('tr')
				.filter({
					has: this.page.getByText(text).first(),
				});
		};
		this.modalSaveButton = this.modalContainer.getByRole('button', {
			name: 'Save',
		});
		this.modalOptionCheckbox = (optionName: string) => {
			return this.modalContainer.getByLabel(optionName);
		};
		this.page = page;
		this.setDefaultBillingAddressFrameBillingAddressDropdownMenu =
			this.modalContainer.getByLabel('Billing Address');
		this.setDefaultAddressFrameChannelDropdownMenu =
			this.modalContainer.getByLabel('Channel');
		this.setDefaultShippingAddressFrameBillingAddressDropdownMenu =
			this.modalContainer.getByLabel('Shipping Address');
		this.shippingAddressAllChannelsText =
			this.defaultShippingAddressesTable.getByRole('cell', {
				exact: true,
				name: 'All Channels',
			});
		this.shippingAddressAllOtherChannelsText =
			this.defaultShippingAddressesTable.getByRole('cell', {
				exact: true,
				name: 'All Other Channels',
			});
	}

	async addDefaultPaymentTerm(paymentTermId: number) {
		await this.addDefaultPaymentTermButton.click();
		await this.addDefaultPaymentTermSelector.selectOption(
			paymentTermId.toString()
		);
		await this.modalSaveButton.click();
		await this.page.waitForTimeout(200);
	}

	searchTableRowByValue = async function (
		tableLocator: Locator,
		colPosition: number,
		value: string,
		strictEqual: boolean = false
	) {
		await tableLocator.elementHandle();

		const rows = await tableLocator.locator('tbody tr').all();

		for await (const row of rows) {
			const column = row.locator('td').nth(colPosition).first();

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
}
