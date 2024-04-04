/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CheckoutPage {
	readonly addressInput: Locator;
	readonly cityInput: Locator;
	readonly continueButton: Locator;
	readonly countryInput: Locator;
	readonly nameInput: Locator;
	readonly orderSuccessMessage: Locator;
	readonly page: Page;
	readonly phoneNumberInput: Locator;
	readonly zipInput: Locator;

	constructor(page: Page) {
		this.addressInput = page.getByPlaceholder('Address', {exact: true});
		this.cityInput = page.getByPlaceholder('City', {exact: true});
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.countryInput = page.getByTitle('Country');
		this.nameInput = page.getByPlaceholder('Name', {exact: true});
		this.orderSuccessMessage = page.getByText(
			'Success! Your order has been processed.'
		);
		this.page = page;
		this.phoneNumberInput = page.getByPlaceholder('Phone Number', {
			exact: true,
		});
		this.zipInput = page.getByPlaceholder('Zip', {exact: true});
	}
}
