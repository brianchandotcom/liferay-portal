/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditCountryPage {
	readonly activeButton: Locator;
	readonly backButton: Locator;
	readonly billingAllowedInput: Locator;
	readonly keyInput: Locator;
	readonly numberInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly shippingAllowedInput: Locator;
	readonly subjectToVATInput: Locator;
	readonly threeLetterIsocodeInput: Locator;
	readonly titleInput: Locator;
	readonly twoLetterIsocodeInput: Locator;

	constructor(page: Page) {
		this.activeButton = page.getByLabel('Active');
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.billingAllowedInput = page.getByLabel('Billing Allowed');
		this.keyInput = page.getByLabel('Key');
		this.numberInput = page.getByLabel('Number');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.shippingAllowedInput = page.getByLabel('Shipping Allowed');
		this.subjectToVATInput = page.getByLabel('Subject to VAT');
		this.threeLetterIsocodeInput = page.getByLabel('Three-Letter ISO Code');
		this.titleInput = page.locator(
			'[id="_com_liferay_address_web_internal_portlet_CountriesManagementAdminPortlet_title"]'
		);
		this.twoLetterIsocodeInput = page.getByLabel('Two-Letter ISO Code');
	}
}
