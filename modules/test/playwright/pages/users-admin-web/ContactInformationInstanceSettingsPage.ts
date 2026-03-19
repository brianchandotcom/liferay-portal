/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class ContactInformationInstanceSettingsPage {
	readonly addressTypeSelect: Locator;
	readonly cityInput: Locator;
	readonly countrySelect: Locator;
	readonly emailAddressInput: Locator;
	readonly extensionInput: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly numberInput: Locator;
	readonly page: Page;
	readonly postalCodeInput: Locator;
	readonly regionSelect: Locator;
	readonly saveButton: Locator;
	readonly street1Input: Locator;
	readonly street2Input: Locator;
	readonly street3Input: Locator;
	readonly urlInput: Locator;

	constructor(page: Page) {
		this.addressTypeSelect = page.locator('[id$="addressListTypeId0"]');
		this.cityInput = page.getByLabel('City');
		this.countrySelect = page.getByLabel('Country');
		this.emailAddressInput = page.getByLabel('Email Address');
		this.extensionInput = page.getByLabel('Extension');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.numberInput = page.getByLabel('Number');
		this.page = page;
		this.postalCodeInput = page.getByLabel('Postal Code');
		this.regionSelect = page.getByLabel('Region');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.street1Input = page.getByLabel('Street 1');
		this.street2Input = page.getByLabel('Street 2');
		this.street3Input = page.getByLabel('Street 3');
		this.urlInput = page.getByLabel('URL');
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Instance Configuration',
			'Contact Information'
		);
	}
}
