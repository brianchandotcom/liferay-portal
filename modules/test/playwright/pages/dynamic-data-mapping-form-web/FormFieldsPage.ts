/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

export class FormFieldsPage {
	readonly page: Page;
	readonly repeatFieldButton: Locator;
	readonly richTextAddImageButton: Locator;
	readonly richTextFrame: FrameLocator;
	readonly richTextSourceButton: Locator;
	readonly richTextToolbar: Locator;

	constructor(page: Page) {
		this.page = page;
		this.repeatFieldButton = page.getByTitle('Duplicate');
		this.richTextAddImageButton = page
			.getByLabel('Rich Text')
			.getByTitle('Image');
		this.richTextFrame = page.frameLocator(
			'.ddm-field-container iframe[title="editor"]'
		);
		this.richTextSourceButton = page.locator(
			'span.cke_toolbar.cke_toolbar_last [title="Source"]'
		);
		this.richTextToolbar = page.locator(
			'.ddm-field-container .ddm-field span.cke_top.cke_reset_all'
		);
	}

	async addSelectItem(optionName: string, nth?: number) {
		const inputFieldLocator = this.page.getByRole('combobox');

		if (nth !== null && nth !== undefined) {
			await inputFieldLocator.nth(nth).click();
		}
		else {
			await inputFieldLocator.click();
		}

		await this.page.getByRole('option', {name: optionName}).click();
	}

	getMultipleSelectItemLocator(itemName: string): Locator {
		return this.page.locator(
			`button[id^="clay-id-"][id$="-label-${itemName}-close"]`
		);
	}

	getMultipleSelectItemsLocators(itemNames: string[]): Locator[] {
		const itemLocators = [];

		itemNames.forEach((value) => {
			itemLocators.push(this.getMultipleSelectItemLocator(value));
		});

		return itemLocators;
	}

	async removeMultipleSelectItem(itemName: string, nth?: number) {
		const itemBaseLocator = this.getMultipleSelectItemLocator(itemName);

		if (nth !== null && nth !== undefined) {
			await itemBaseLocator.nth(nth).click();
		}
		else {
			await itemBaseLocator.click();
		}
	}

	async richTextselectImage(imageName: string) {
		const fileSelectorIFrame = this.page.frameLocator(
			'iframe[title="Select Item"]'
		);

		await fileSelectorIFrame
			.getByRole('link', {name: 'Sites and Libraries'})
			.click();

		await fileSelectorIFrame
			.getByRole('link', {name: 'Liferay DXP Site'})
			.click();

		await fileSelectorIFrame
			.getByRole('link', {name: 'Provided by Liferay'})
			.click();

		await expect(
			fileSelectorIFrame.getByText('Drag & Drop Your Images or')
		).toBeVisible();

		await fileSelectorIFrame.getByText(imageName).click();
	}
}
