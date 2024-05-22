/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountContactAddressPage {
	readonly addressDisplay: (addressContent: string) => Promise<Locator>;
	readonly street1Input: Locator;
	readonly cityInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addressDisplay = async (addressContent: string) => {
			return this.page.getByText(addressContent);
		};
		this.street1Input = page.getByLabel('Street 1');
		this.cityInput = page.getByLabel('City');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async updateAddress(street1Input: string, cityInput: string) {
		await this.street1Input.fill(street1Input);
		await this.cityInput.fill(cityInput);
		await this.saveButton.click();
	}
}
