/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class EditAccountChannelDefaultsPage {
	readonly addChannelAccountManagerButton: Locator;
	readonly addDefaultBillingAddressButton: Locator;
	readonly addDefaultPaymentTermButton: Locator;
	readonly addDefaultPaymentTermSelector: Locator;
	readonly addDefaultShippingAddressButton: Locator;
	readonly addressTableRowColumn: (
		columnIndex: number,
		tableName: string,
		text: string
	) => Promise<Locator>;
	readonly billingAddressAllChannelsText: Locator;
	readonly billingAddressAllOtherChannelsText: Locator;
	readonly channelAccountManagerModalChannelSelect: Locator;
	readonly channelAccountManagerModalUserSelect: Locator;
	readonly channelAccountManagerRow: (
		channelName: string,
		userName: string
	) => Locator;
	readonly channelAccountManagerRowActionsButton: (
		channelName: string,
		userName: string
	) => Locator;
	readonly channelAccountManagerTable: Locator;
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
	readonly defaultUsersTable: Locator;
	readonly deleteMenuItem: Locator;
	readonly duplicateChannelAccountManagerError: Locator;
	readonly editMenuItem: Locator;
	readonly getRowByTextFromTable: (
		tableName: string,
		text: string
	) => Locator;
	readonly modalContainer: FrameLocator;
	readonly modalIframe: Locator;
	readonly modalOptionCheckbox: (optionName: string) => Locator;
	readonly modalRadioLabels: Locator;
	readonly modalSaveButton: Locator;
	readonly page: Page;
	readonly setDefaultAddressFrameChannelDropdownMenu: Locator;
	readonly setDefaultAddressFrameChannelDropdownOptions: Locator;
	readonly setDefaultBillingAddressFrameBillingAddressDropdownMenu: Locator;
	readonly setDefaultBillingAddressFrameBillingAddressDropdownOptions: Locator;
	readonly setDefaultShippingAddressFrameBillingAddressDropdownMenu: Locator;
	readonly setDefaultShippingAddressFrameBillingAddressDropdownOptions: Locator;
	readonly shippingAddressAllChannelsText: Locator;
	readonly shippingAddressAllOtherChannelsText: Locator;

	constructor(page: Page) {
		this.defaultBillingAddressesTable = page.getByTestId(
			'defaultBillingCommerceAddresses'
		);
		this.defaultShippingAddressesTable = page.getByTestId(
			'defaultShippingCommerceAddresses'
		);
		this.defaultUsersTable = page.getByTestId('defaultUsers');
		this.modalContainer = page.frameLocator('.fds-modal-body > iframe');
		this.modalIframe = page.locator('.fds-modal-body > iframe');
		this.modalRadioLabels = this.modalContainer.locator(
			'label:has(input[type="radio"])'
		);
		this.page = page;

		this.addChannelAccountManagerButton = this.defaultUsersTable
			.getByRole('button', {name: 'Add'})
			.or(
				this.defaultUsersTable.getByRole('link', {
					exact: true,
					name: 'Add',
				})
			)
			.first();
		this.addDefaultBillingAddressButton = this.defaultBillingAddressesTable
			.getByRole('button', {name: 'Add Default Address'})
			.first();
		this.addDefaultPaymentTermButton = page
			.locator(
				"[id='_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_defaultPaymentCommerceTermEntries']"
			)
			.getByRole('button', {name: 'Add Default Term'})
			.first();
		this.addDefaultPaymentTermSelector =
			this.modalContainer.getByLabel('Term');
		this.addDefaultShippingAddressButton =
			this.defaultShippingAddressesTable
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
		this.channelAccountManagerModalChannelSelect =
			this.modalContainer.getByLabel('Channel');
		this.channelAccountManagerModalUserSelect =
			this.modalContainer.getByLabel('User');
		this.channelAccountManagerRow = (
			channelName: string,
			userName: string
		) =>
			this.channelAccountManagerTable.locator('tbody tr').filter({
				has: page.locator('td', {hasText: channelName}),
				hasText: userName,
			});
		this.channelAccountManagerRowActionsButton = (
			channelName: string,
			userName: string
		) =>
			this.channelAccountManagerRow(channelName, userName).getByRole(
				'button',
				{name: 'Actions'}
			);
		this.channelAccountManagerTable =
			this.defaultUsersTable.locator('table');
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
		this.deleteMenuItem = page
			.getByRole('menu')
			.getByRole('menuitem', {exact: true, name: 'Delete'});
		this.duplicateChannelAccountManagerError =
			this.modalContainer.getByText(
				'This user is already defined for the selected channel.'
			);
		this.editMenuItem = page
			.getByRole('menu')
			.getByRole('menuitem', {exact: true, name: 'Edit'});
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
		this.modalOptionCheckbox = (optionName: string) => {
			return this.modalContainer.getByLabel(optionName);
		};
		this.modalSaveButton = this.modalContainer.getByRole('button', {
			name: 'Save',
		});
		this.setDefaultAddressFrameChannelDropdownMenu =
			this.modalContainer.getByLabel('Channel');
		this.setDefaultAddressFrameChannelDropdownOptions =
			this.setDefaultAddressFrameChannelDropdownMenu.locator('option');
		this.setDefaultBillingAddressFrameBillingAddressDropdownMenu =
			this.modalContainer.getByLabel('Billing Address');
		this.setDefaultBillingAddressFrameBillingAddressDropdownOptions =
			this.setDefaultBillingAddressFrameBillingAddressDropdownMenu.locator(
				'option'
			);
		this.setDefaultShippingAddressFrameBillingAddressDropdownMenu =
			this.modalContainer.getByLabel('Shipping Address');
		this.setDefaultShippingAddressFrameBillingAddressDropdownOptions =
			this.setDefaultShippingAddressFrameBillingAddressDropdownMenu.locator(
				'option'
			);
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

	async addChannelAccountManager({
		channelName,
		expectDuplicateError = false,
		userScreenName,
	}: {
		channelName: string;
		expectDuplicateError?: boolean;
		userScreenName: string;
	}) {
		await this.addChannelAccountManagerButton.click();
		await this.channelAccountManagerModalChannelSelect.selectOption({
			label: channelName,
		});
		await this.channelAccountManagerModalUserSelect.selectOption({
			label: userScreenName,
		});

		await this.modalSaveButton.click();

		if (expectDuplicateError) {
			await expect(
				this.duplicateChannelAccountManagerError
			).toBeVisible();
		}
		else {
			await expect(
				this.page.locator('.fds-modal-body > iframe')
			).toHaveCount(0);
		}
	}

	async addDefaultPaymentTerm(paymentTermId: number) {
		await this.addDefaultPaymentTermButton.click();
		await expect(async () => {
			await this.addDefaultPaymentTermSelector.selectOption(
				paymentTermId.toString()
			);
			await expect(this.addDefaultPaymentTermSelector).toHaveValue(
				paymentTermId.toString()
			);
			await this.modalSaveButton.click();
		}).toPass({timeout: 5000});
	}

	async deleteChannelAccountManager({
		channelName,
		userScreenName,
	}: {
		channelName: string;
		userScreenName: string;
	}) {
		this.page.on('dialog', (dialog) => dialog.accept());

		await expect(async () => {
			await this.channelAccountManagerRowActionsButton(
				channelName,
				userScreenName
			).click();

			await expect(this.deleteMenuItem).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await this.deleteMenuItem.click();

		await waitForAlert(this.page);
	}

	async editChannelAccountManager({
		channelName,
		currentUserScreenName,
		newUserScreenName,
	}: {
		channelName: string;
		currentUserScreenName: string;
		newUserScreenName: string;
	}) {
		await expect(async () => {
			await this.channelAccountManagerRowActionsButton(
				channelName,
				currentUserScreenName
			).click();

			await expect(this.editMenuItem).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await this.editMenuItem.click();

		await this.channelAccountManagerModalUserSelect.selectOption({
			label: newUserScreenName,
		});
		await this.modalSaveButton.click();

		await expect(this.page.locator('.fds-modal-body > iframe')).toHaveCount(
			0
		);
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
