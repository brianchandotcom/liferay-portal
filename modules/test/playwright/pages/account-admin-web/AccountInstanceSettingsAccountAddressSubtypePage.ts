/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class AccountInstanceSettingsAccountAddressSubtypePage {
	readonly billingAddressSubtypePicklistInput: Locator;
	readonly billingAndShippingAddressSubtypePicklistInput: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly shippingAddressSubtypePicklistInput: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.billingAddressSubtypePicklistInput = page
			.getByTestId(
				'billingAddressSubtypeListTypeDefinitionExternalReferenceCode'
			)
			.getByLabel('Billing Address Subtype');
		this.billingAndShippingAddressSubtypePicklistInput = page
			.getByTestId(
				'billingAndShippingAddressSubtypeListTypeDefinitionExternalReferenceCode'
			)
			.getByLabel('Billing and Shipping Address Subtype');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.shippingAddressSubtypePicklistInput = page
			.getByTestId(
				'shippingAddressSubtypeListTypeDefinitionExternalReferenceCode'
			)
			.getByLabel('Shipping Address Subtype');
		this.updateButton = page.getByTestId('submitConfiguration');
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Accounts',
			'Account Address Subtype'
		);
	}

	async setAddressSubtypeExternalReferenceCodes(
		listTypeDefinitionBilling = '',
		listTypeDefinitionBillingAndShipping = '',
		listTypeDefinitionShipping = ''
	) {
		await this.goto();

		await expect(async () => {
			await this.billingAddressSubtypePicklistInput.fill(
				listTypeDefinitionBilling
			);
			await this.billingAndShippingAddressSubtypePicklistInput.fill(
				listTypeDefinitionBillingAndShipping
			);
			await this.shippingAddressSubtypePicklistInput.fill(
				listTypeDefinitionShipping
			);
			await this.updateButton.click();

			await waitForAlert(this.page);

			await expect(this.billingAddressSubtypePicklistInput).toHaveValue(
				listTypeDefinitionBilling
			);
			await expect(
				this.billingAndShippingAddressSubtypePicklistInput
			).toHaveValue(listTypeDefinitionBillingAndShipping);
			await expect(this.shippingAddressSubtypePicklistInput).toHaveValue(
				listTypeDefinitionShipping
			);
		}).toPass();
	}
}
