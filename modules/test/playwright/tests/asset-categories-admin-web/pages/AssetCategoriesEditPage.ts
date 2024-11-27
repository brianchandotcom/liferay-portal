/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../../utils/waitForAlert';

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
	}

	async goToPropertiesTab(title: string) {
		await this.goto(title);
		await this.propertiesTab.click();
	}

	async addProperties(properties: {[key: string]: string}) {
		await this.propertiesTab.click();

		const keyInputs = this.page.getByLabel('key');

		for (const [key, value] of Object.entries(properties)) {
			if (await keyInputs.last().inputValue()) {
				const count = await keyInputs.count();

				await this.page
					.getByRole('button', {name: 'Add'})
					.last()
					.click();
				await keyInputs.nth(count).waitFor();
			}

			const keyInput = keyInputs.last();
			const valueInput = this.page.getByLabel('value').last();

			await keyInput.fill(key);
			await valueInput.fill(value);
		}

		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async save() {
		await this.saveButton.click();
		await waitForAlert(this.page);
	}
}
