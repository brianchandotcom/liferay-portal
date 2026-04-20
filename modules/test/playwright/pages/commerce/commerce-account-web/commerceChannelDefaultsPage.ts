/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceChannelDefaultsPage {
	readonly defaultBillingCommerceAddresses: Locator;
	readonly defaultBillingCommerceAddressesActions: Locator;
	readonly defaultBillingCommerceAddressesButton: Locator;
	readonly defaultCommerceCurrencies: Locator;
	readonly defaultCommerceCurrenciesActions: Locator;
	readonly defaultCommerceCurrenciesButton: Locator;
	readonly defaultCommerceCurrenciesCell: (name: string) => Locator;
	readonly defaultCommerceDiscounts: Locator;
	readonly defaultCommerceDiscountsActions: Locator;
	readonly defaultCommerceDiscountsButton: Locator;
	readonly defaultCommerceDiscountsCell: (name: string) => Locator;
	readonly defaultCommercePaymentMethod: Locator;
	readonly defaultCommercePaymentMethodButton: Locator;
	readonly defaultCommercePriceLists: Locator;
	readonly defaultCommercePriceListsActions: Locator;
	readonly defaultCommercePriceListsButton: Locator;
	readonly defaultCommercePriceListsCell: (name: string) => Locator;
	readonly defaultCommerceShippingOption: Locator;
	readonly defaultCommerceShippingOptionButton: Locator;
	readonly defaultDeliveryCommerceTermEntries: Locator;
	readonly defaultDeliveryCommerceTermEntriesActionsButton: Locator;
	readonly defaultDeliveryCommerceTermEntriesButton: Locator;
	readonly defaultDeliveryCommerceTermEntriesCell: (
		name: string,
		exact?: boolean
	) => Locator;
	readonly defaultPaymentCommerceTermEntries: Locator;
	readonly defaultPaymentCommerceTermEntriesActionsButton: Locator;
	readonly defaultPaymentCommerceTermEntriesButton: Locator;
	readonly defaultPaymentCommerceTermEntriesCell: (
		name: string,
		exact?: boolean
	) => Locator;
	readonly defaultShippingCommerceAddresses: Locator;
	readonly defaultShippingCommerceAddressesActions: Locator;
	readonly defaultShippingCommerceAddressesButton: Locator;
	readonly defaultUsers: Locator;
	readonly defaultUsersButton: Locator;
	readonly editFrame: FrameLocator;
	readonly editFrameChannelOptions: Locator;
	readonly editFrameChannelSelect: Locator;
	readonly editFrameCurrencySelect: Locator;
	readonly editFrameDiscountSelect: Locator;
	readonly editFrameOverrideCheckbox: Locator;
	readonly editFramePriceListSelect: Locator;
	readonly editFrameSaveButton: Locator;
	readonly editFrameTermSelect: Locator;
	readonly deleteMenuItem: Locator;
	readonly editMenuItem: Locator;
	readonly channelEntry: (channelEntryName: string) => Locator;
	readonly channelEntryAddButton: (channelEntryName: string) => Locator;
	readonly channelEntryHeader: (channelEntryHeaderName: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.defaultBillingCommerceAddresses = page.getByTestId(
			'defaultBillingCommerceAddresses'
		);
		this.defaultBillingCommerceAddressesActions =
			this.defaultBillingCommerceAddresses.getByRole('button', {
				name: 'Actions',
			});
		this.defaultBillingCommerceAddressesButton =
			this.defaultBillingCommerceAddresses
				.getByTestId('managementToolbar')
				.locator('[data-testid="fdsCreationActionButton"]');
		this.defaultCommerceCurrencies = page.getByTestId(
			'defaultCommerceCurrencies'
		);
		this.defaultCommerceCurrenciesActions =
			this.defaultCommerceCurrencies.getByRole('button', {
				name: 'Actions',
			});
		this.defaultCommerceCurrenciesButton =
			this.defaultCommerceCurrencies.getByLabel('Add Default Currency');
		this.defaultCommerceCurrenciesCell = (name: string) =>
			this.defaultCommerceCurrencies.getByRole('cell', {name});
		this.defaultCommerceDiscounts = page.getByTestId(
			'defaultCommerceDiscounts'
		);
		this.defaultCommerceDiscountsActions =
			this.defaultCommerceDiscounts.getByRole('button', {
				name: 'Actions',
			});
		this.defaultCommerceDiscountsButton =
			this.defaultCommerceDiscounts.getByLabel('Add Default Discount');
		this.defaultCommerceDiscountsCell = (name: string) =>
			this.defaultCommerceDiscounts.getByRole('cell', {name});
		this.defaultCommercePaymentMethod = page.getByTestId(
			'defaultCommercePaymentMethod'
		);
		this.defaultCommercePaymentMethodButton =
			this.defaultCommercePaymentMethod.getByLabel('Edit').first();
		this.defaultCommercePriceLists = page.getByTestId(
			'defaultCommercePriceLists'
		);
		this.defaultCommercePriceListsActions =
			this.defaultCommercePriceLists.getByRole('button', {
				name: 'Actions',
			});
		this.defaultCommercePriceListsButton =
			this.defaultCommercePriceLists.getByLabel('Add Default Price List');
		this.defaultCommercePriceListsCell = (name: string) =>
			this.defaultCommercePriceLists.getByRole('cell', {name});
		this.defaultCommerceShippingOption = page.getByTestId(
			'defaultCommerceShippingOption'
		);
		this.defaultCommerceShippingOptionButton =
			this.defaultCommerceShippingOption.getByLabel('Edit').first();
		this.defaultDeliveryCommerceTermEntries = page.getByTestId(
			'defaultDeliveryCommerceTermEntries'
		);
		this.defaultDeliveryCommerceTermEntriesActionsButton =
			this.defaultDeliveryCommerceTermEntries.getByRole('button', {
				name: 'Actions',
			});
		this.defaultDeliveryCommerceTermEntriesButton =
			this.defaultDeliveryCommerceTermEntries.getByLabel(
				'Add Default Term'
			);
		this.defaultDeliveryCommerceTermEntriesCell = (
			name: string,
			exact?: boolean
		) =>
			this.defaultDeliveryCommerceTermEntries.getByRole('cell', {
				exact,
				name,
			});
		this.defaultPaymentCommerceTermEntries = page.getByTestId(
			'defaultPaymentCommerceTermEntries'
		);
		this.defaultPaymentCommerceTermEntriesActionsButton =
			this.defaultPaymentCommerceTermEntries.getByRole('button', {
				name: 'Actions',
			});
		this.defaultPaymentCommerceTermEntriesButton =
			this.defaultPaymentCommerceTermEntries.getByLabel(
				'Add Default Term'
			);
		this.defaultPaymentCommerceTermEntriesCell = (
			name: string,
			exact?: boolean
		) =>
			this.defaultPaymentCommerceTermEntries.getByRole('cell', {
				exact,
				name,
			});
		this.defaultShippingCommerceAddresses = page.getByTestId(
			'defaultShippingCommerceAddresses'
		);
		this.defaultShippingCommerceAddressesActions =
			this.defaultShippingCommerceAddresses.getByRole('button', {
				name: 'Actions',
			});
		this.defaultShippingCommerceAddressesButton =
			this.defaultShippingCommerceAddresses
				.getByTestId('managementToolbar')
				.locator('[data-testid="fdsCreationActionButton"]');
		this.defaultUsers = page.getByTestId('defaultUsers');
		this.defaultUsersButton = this.defaultUsers.getByLabel('Add User');
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.editFrame = page.frameLocator('.fds-modal-body > iframe');
		this.editMenuItem = page.getByRole('menuitem', {name: 'Edit'});
		this.page = page;

		this.editFrameChannelSelect = this.editFrame.getByLabel('Channel');
		this.editFrameChannelOptions =
			this.editFrameChannelSelect.locator('option');
		this.editFrameCurrencySelect =
			this.editFrame.getByLabel('Currency Required');
		this.editFrameDiscountSelect = this.editFrame.getByLabel('Discount');
		this.editFrameOverrideCheckbox = this.editFrame.getByLabel(
			'Override Eligibility'
		);
		this.editFramePriceListSelect = this.editFrame.getByLabel('Price List');
		this.editFrameSaveButton = this.editFrame.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.editFrameTermSelect = this.editFrame.getByLabel('Term');
		this.channelEntry = (channelEntryName: string) => {
			return page.getByTestId(new RegExp(`.*${channelEntryName}.*`, 'g'));
		};
		this.channelEntryAddButton = (channelEntryName: string) => {
			return this.channelEntry(channelEntryName).getByRole('button', {
				name: 'Add',
			});
		};
		this.channelEntryHeader = (channelEntryHeaderName: string) => {
			return page.getByRole('heading', {name: channelEntryHeaderName});
		};
	}
}
