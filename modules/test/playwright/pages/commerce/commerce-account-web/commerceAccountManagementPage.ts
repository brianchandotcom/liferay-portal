/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceAccountManagementPage {
	readonly accountsTableRowLink: (accountId: number | string) => Locator;
	readonly addressesLink: Locator;
	readonly addressNameInList: (addressName: string) => Locator;
	readonly assignUsersFrame: FrameLocator;
	readonly assignUsersMenuItem: Locator;
	readonly channelDefaultsLink: Locator;
	readonly cityInput: Locator;
	readonly countryCombobox: Locator;
	readonly descriptionInput: Locator;
	readonly detailsLink: Locator;
	readonly emailAddressInput: Locator;
	readonly firstNameInput: Locator;
	readonly lastNameInput: Locator;
	readonly nameInput: Locator;
	readonly newAddressButton: Locator;
	readonly newButton: Locator;
	readonly newUserButton: Locator;
	readonly noAddressesFoundMessage: Locator;
	readonly page: Page;
	readonly postalCodeInput: Locator;
	readonly regionCombobox: Locator;
	readonly saveButton: Locator;
	readonly screenNameInput: Locator;
	readonly sectionHeading: (name: string) => Locator;
	readonly street1Input: Locator;
	readonly taxIdInput: Locator;
	readonly usersLink: Locator;

	constructor(page: Page) {
		this.accountsTableRowLink = (accountId: number | string) =>
			page.getByRole('link', {exact: true, name: String(accountId)});
		this.addressesLink = page.getByRole('link', {
			exact: true,
			name: 'Addresses',
		});
		this.addressNameInList = (addressName: string) =>
			page.getByText(addressName);
		this.assignUsersFrame = page.frameLocator(
			'iframe[title*="Assign Users to Commerce Account"]'
		);
		this.assignUsersMenuItem = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.channelDefaultsLink = page.getByRole('link', {
			exact: true,
			name: 'Channel Defaults',
		});
		this.cityInput = page.getByRole('textbox', {name: 'City'});
		this.countryCombobox = page.getByRole('combobox', {name: 'Country'});
		this.descriptionInput = page.getByRole('textbox', {
			name: 'Description',
		});
		this.detailsLink = page.getByRole('link', {
			exact: true,
			name: 'Details',
		});
		this.emailAddressInput = page.getByRole('textbox', {
			name: 'Email Address',
		});
		this.firstNameInput = page.getByRole('textbox', {name: 'First Name'});
		this.lastNameInput = page.getByRole('textbox', {name: 'Last Name'});
		this.nameInput = page.getByRole('textbox', {name: 'Name'}).first();
		this.newAddressButton = page.getByText('New', {exact: true});
		this.newButton = page.getByRole('button', {exact: true, name: 'New'});
		this.newUserButton = this.assignUsersFrame.getByRole('button', {
			name: 'New User',
		});
		this.noAddressesFoundMessage = page.getByText(
			'No addresses were found.'
		);
		this.page = page;
		this.postalCodeInput = page.getByRole('textbox', {name: 'Postal Code'});
		this.regionCombobox = page.getByRole('combobox', {name: 'Region'});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.screenNameInput = page.getByRole('textbox', {name: 'Screen Name'});
		this.sectionHeading = (name: string) =>
			page.getByRole('heading', {exact: true, name});
		this.street1Input = page.getByRole('textbox', {name: 'Street 1'});
		this.taxIdInput = page.getByRole('textbox', {name: 'Tax ID'});
		this.usersLink = page.getByRole('link', {
			exact: true,
			name: 'Users',
		});
	}
}
