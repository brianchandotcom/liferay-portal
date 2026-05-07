/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminWarehouseDetailsPage {
	readonly backButton: Locator;
	readonly cityInput: Locator;
	readonly countrySelect: Locator;
	readonly descriptionTextarea: Locator;
	readonly detailsActiveToggle: Locator;
	readonly geolocateButton: Locator;
	readonly geolocationField: (coordinates: string) => Locator;
	readonly itemNoLongerValidError: Locator;
	readonly latitudeInput: Locator;
	readonly longitudeInput: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly postalCodeInput: Locator;
	readonly regionSelect: Locator;
	readonly saveButton: Locator;
	readonly street1Input: Locator;
	readonly street2Input: Locator;
	readonly street3Input: Locator;
	readonly warehouseId: Locator;

	constructor(page: Page) {
		this.backButton = page.locator('span[title="Back"]');
		this.cityInput = page.getByLabel('City', {exact: true});
		this.countrySelect = page.getByLabel('Country', {exact: true});
		this.descriptionTextarea = page.locator(
			'textarea[name$="description"]'
		);
		this.detailsActiveToggle = page.getByLabel('Active');
		this.geolocateButton = page
			.getByRole('button', {exact: true, name: 'Geolocate'})
			.or(page.getByRole('link', {exact: true, name: 'Geolocate'}));
		this.geolocationField = (coordinates) => page.getByLabel(coordinates);
		this.itemNoLongerValidError = page.getByText(
			'This item is no longer valid. Please try again.'
		);
		this.latitudeInput = page.getByLabel('Latitude', {exact: true});
		this.longitudeInput = page.getByLabel('Longitude', {exact: true});
		this.nameInput = page.locator('input[name$="name"]');
		this.page = page;
		this.postalCodeInput = page.getByLabel('Postal Code', {exact: true});
		this.regionSelect = page.getByLabel('Region', {exact: true});
		this.saveButton = page
			.getByRole('button', {exact: true, name: 'Save'})
			.or(page.getByRole('link', {exact: true, name: 'Save'}));
		this.street1Input = page.getByLabel('Street 1', {exact: true});
		this.street2Input = page.getByLabel('Street 2', {exact: true});
		this.street3Input = page.getByLabel('Street 3', {exact: true});
		this.warehouseId = page.locator('span:has-text("ID")+strong');
	}
}
