/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DigitalSalesRoomSettingsPage {
	readonly bannerImage: Locator;
	readonly cancelButton: Locator;
	readonly clientLogoButton: Locator;
	readonly clientNameInput: Locator;
	readonly generalLink: Locator;
	readonly nextButton: Locator;
	readonly page: Page;
	readonly primaryColorInput: Locator;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;
	readonly secondaryColorInput: Locator;
	readonly selectAccountInput: Locator;
	readonly selectChannelInput: Locator;
	readonly selectOption: (value: string) => Locator;
	readonly settingsLink: Locator;
	readonly usersLink: Locator;

	constructor(page: Page) {
		this.bannerImage = page.getByTestId('bannerImage');
		this.cancelButton = page.getByRole('button', {
			exact: true,
			name: 'Cancel',
		});
		this.clientLogoButton = page.getByTestId('clientLogoButton');
		this.clientNameInput = page.getByLabel('Client Name');
		this.generalLink = page.getByRole('link', {name: 'General'});
		this.nextButton = page.getByRole('button', {name: 'Next'});
		this.page = page;
		this.primaryColorInput = page.getByTestId('primaryColorInput');
		this.roomNameInput = page.getByLabel('Room Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryColorInput = page.getByTestId('secondaryColorInput');
		this.selectAccountInput = page.getByRole('combobox', {
			name: 'Select Account',
		});
		this.selectChannelInput = page.getByRole('combobox', {
			name: 'Select Channel',
		});
		this.selectOption = (value: string) =>
			page.getByRole('option', {name: value});
		this.settingsLink = page.getByRole('link', {name: 'Settings'});
		this.usersLink = page.getByRole('link', {name: 'Users'});
	}
}
