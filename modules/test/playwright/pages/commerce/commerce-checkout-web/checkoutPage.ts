/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerceLayoutsPage';

export class CheckoutPage {
	readonly addressInput: Locator;
	readonly billingAddress: Locator;
	readonly cityInput: Locator;
	readonly configurationIFrame: FrameLocator;
	readonly configurationIFrameSaveButton: Locator;
	readonly configurationIFrameShowFullAddressToggle: Locator;
	readonly configurationIFrameShowPhoneNumberToggle: Locator;
	readonly configurationMenuItem: Locator;
	readonly continueButton: Locator;
	readonly countryInput: Locator;
	readonly goToOrderDetailsButton: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly nameInput: Locator;
	readonly optionsButton: Locator;
	readonly orderSuccessMessage: Locator;
	readonly page: Page;
	readonly phoneNumberInput: Locator;
	readonly previousButton: Locator;
	readonly regionInput: Locator;
	readonly shippingAddress: Locator;
	readonly shippingAddressSelect: Locator;
	readonly zipInput: Locator;

	constructor(page: Page) {
		this.addressInput = page.getByPlaceholder('Address', {exact: true});
		this.billingAddress = page.getByTestId('commerceBillingAddress');
		this.cityInput = page.getByPlaceholder('City', {exact: true});
		this.configurationIFrame = page.frameLocator(
			'iframe[id="modalIframe"]'
		);
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.configurationIFrameSaveButton = this.configurationIFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.configurationIFrameShowFullAddressToggle =
			this.configurationIFrame.getByLabel(
				'Order Summary Show Full Address'
			);
		this.configurationIFrameShowPhoneNumberToggle =
			this.configurationIFrame.getByLabel(
				'Order Summary Show Phone Number'
			);
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.countryInput = page.getByTitle('Country');
		this.goToOrderDetailsButton = page.getByRole('button', {
			name: 'Go to Order Details',
		});
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.nameInput = page.getByPlaceholder('Name', {exact: true});
		this.optionsButton = page
			.locator(
				'#portlet_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet'
			)
			.getByLabel('Options');
		this.orderSuccessMessage = page.getByText(
			'Success! Your order has been processed.'
		);
		this.page = page;
		this.phoneNumberInput = page.getByPlaceholder('Phone Number', {
			exact: true,
		});
		this.previousButton = page.getByRole('button', {name: 'Previous'});
		this.regionInput = page.getByTitle('Region');
		this.shippingAddress = page.getByTestId('commerceShippingAddress');
		this.shippingAddressSelect = page.getByText('Choose Shipping Address');
		this.zipInput = page.getByPlaceholder('Zip', {exact: true});
	}

	async addCheckoutWidget() {
		await this.layoutsPage.addWidgetToPage('Checkout');
	}

	async chooseShippingAddress(index) {
		this.shippingAddressSelect.selectOption(index);
		this.continueButton.click();
	}
}
