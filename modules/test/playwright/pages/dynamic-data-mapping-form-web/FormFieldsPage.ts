/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, Locator} from '@playwright/test';

export class FormFieldsPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	};

	async addMultipleSelectItem(optionName: string, nth?: number) {
		const inputFieldLocator = this.page.getByRole('combobox');

		if (nth !== null && nth !== undefined) {
			await inputFieldLocator.nth(nth).click();	
		} else {
			await inputFieldLocator.click();
		}

		await this.page.getByRole('option', { name: optionName }).click();
	};

	getMultipleSelectItemLocator(itemName: string): Locator {
		return this.page.locator(`button[id^="clay-id-"][id$="-label-${itemName}-close"]`);
	};

	getMultipleSelectItemsLocators(itemNames: string[]): Locator[] {
		const itemLocators = [];

		itemNames.forEach((value) => {
			itemLocators.push(this.getMultipleSelectItemLocator(value));
		})

		return itemLocators;
	};

	async removeMultipleSelectItem(itemName: string, nth?: number) {
		const itemBaseLocator = this.getMultipleSelectItemLocator(itemName);

		if (nth !== null && nth !== undefined) {
			await itemBaseLocator.nth(nth).click();	
		} else {
			await itemBaseLocator.click();
		}
	};
}
