/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';

export class AssetCategoriesEditPage {
	readonly page: Page;
	readonly propertiesTab: Locator;
	readonly cancelButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.propertiesTab = page.getByRole('link', {name: 'properties'});
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.saveButton = page.getByRole('button', {name: 'Save'});

		this.page = page;
	}

	async goto(title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: this.page
				.getByRole('row', {name: title})
				.getByLabel('Show Actions'),
		});
		await this.propertiesTab.waitFor();
	}

	async addProperty(key: string, value: string) {
		await this.propertiesTab.click();

		const keyInputs = this.page.getByLabel('key');

		const count = await keyInputs.count();

		if (await keyInputs.last().getAttribute('value')) {
			await this.page.getByRole('button', {name: 'Add'}).last().click();
			await keyInputs.nth(count).waitFor();
		}

		const keyInput = keyInputs.last();
		const valueInput = this.page.getByLabel('value').last();

		await fillAndClickOutside(this.page, keyInput, key);
		await fillAndClickOutside(this.page, valueInput, value);

		await this.saveButton.click();
	}
}
