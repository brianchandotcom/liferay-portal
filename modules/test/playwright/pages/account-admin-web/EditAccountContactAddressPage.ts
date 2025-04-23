/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountContactAddressPage {
	readonly addressDisplay: (addressContent: string) => Promise<Locator>;
	readonly cityInput: Locator;
	readonly countrySelect: Locator;
	readonly page: Page;
	readonly postalCodeInput: Locator;
	readonly regionSelect: Locator;
	readonly saveButton: Locator;
	readonly street1Input: Locator;

	constructor(page: Page) {
		this.addressDisplay = async (addressContent: string) => {
			return this.page.getByText(addressContent);
		};
		this.cityInput = page.getByLabel('City');
		this.countrySelect = page.getByLabel('Country');
		this.page = page;
		this.postalCodeInput = page.getByLabel('Postal code');
		this.regionSelect = page.getByLabel('Region');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.street1Input = page.getByLabel('Street 1');
	}

	async updateAddress(
		city: string,
		country: string,
		postalCode: string,
		region: string,
		street1: string
	) {
		await this.cityInput.fill(city);
		await this.countrySelect.selectOption(country);
		await this.postalCodeInput.fill(postalCode);
		await this.regionSelect.selectOption(region);
		await this.street1Input.fill(street1);
		await this.saveButton.click();
	}
}
