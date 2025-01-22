/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class AccountDefaultAddressSelectorPage {
	readonly addAddressButton: (type: string) => Locator;
	readonly defaultAddressFrame: (type: string) => FrameLocator;
	readonly filterButton: (type: string) => Locator;
	readonly page: Page;
	readonly saveButton: (type: string) => Locator;
	readonly searchInput: (type: string) => Locator;
	readonly selectAddressInput: (name: string, type: string) => Locator;

	constructor(page: Page) {
		this.defaultAddressFrame = (type) => {
			return page.frameLocator(
				`iframe[title="Set Default ${type} Address"]`
			);
		};
		this.addAddressButton = (type) => {
			return this.defaultAddressFrame(type).getByRole('link', {
				exact: true,
				name: 'Add Address',
			});
		};
		this.filterButton = (type) => {
			return this.defaultAddressFrame(type).getByRole('button', {
				exact: true,
				name: 'Filter',
			});
		};
		this.page = page;
		this.saveButton = (type) => {
			return this.page
				.getByLabel(`Set Default ${type} Address`)
				.getByRole('button', {name: 'Save'});
		};
		this.searchInput = (type) => {
			return this.defaultAddressFrame(type).getByPlaceholder(
				'Search for',
				{exact: true}
			);
		};
		this.selectAddressInput = (name, type) => {
			return this.defaultAddressFrame(type).getByLabel(name);
		};
	}

	async setDefaultAddress(name: string, type: string) {
		await expect(this.searchInput(type)).toBeEditable();
		await this.selectAddressInput(name, type).click();
		await this.saveButton(type).click();

		await waitForAlert(this.page);
	}
}
