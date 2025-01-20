/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

export class EditAccountAddressPage {
	readonly backButton: Locator;
	readonly cityInput: Locator;
	readonly countryInput: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly postalCodeInput: Locator;
	readonly regionInput: Locator;
	readonly saveButton: Locator;
	readonly street1Input: Locator;
	readonly typeInput: Locator;

	constructor(page: Page) {
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.cityInput = page.getByLabel('City');
		this.countryInput = page.getByLabel('Country');
		this.nameInput = page.getByLabel('Name');
		this.page = page;
		this.postalCodeInput = page.getByLabel('Postal Code');
		this.regionInput = page.getByLabel('Region');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.street1Input = page.getByLabel('Street 1');
		this.typeInput = page.getByLabel('Type');
	}

	async addAddress({
		city = getRandomString(),
		country = 'United States',
		name = getRandomString(),
		postalCode = getRandomInt(),
		region = 'Alabama',
		street1 = getRandomString(),
		type = 'Billing and Shipping',
	}: {
		city?: string;
		country?: string;
		name?: string;
		postalCode?: number | string;
		region?: string;
		street1?: string;
		type?: string;
	}) {
		await this.nameInput.fill(name);
		await this.countryInput.selectOption({label: country});
		await this.cityInput.fill(city);
		await this.street1Input.fill(street1);
		await this.postalCodeInput.fill(String(postalCode));
		await this.typeInput.selectOption({label: type});

		if (region) {
			await this.regionInput.selectOption({label: region});
		}

		const countryId = await this.countryInput.inputValue();
		const regionId = await this.regionInput.inputValue();
		const typeId = await this.typeInput.inputValue();

		await this.saveButton.click();

		await waitForAlert(this.page);

		return Promise.resolve({countryId, regionId, typeId});
	}
}
