/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../../utils/waitForAlert';

export class AssetCategoriesEditPage {
	readonly addButton: Locator;
	readonly cancelButton: Locator;
	readonly deleteButton: Locator;
	readonly descriptionField: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly propertiesTab: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addButton = page.getByRole('button', {name: 'Add'});
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.descriptionField = page
			.frameLocator('iframe[title="editor"]')
			.getByRole('textbox');
		this.nameInput = page.getByPlaceholder('Name');
		this.page = page;
		this.propertiesTab = page.getByRole('link', {name: 'properties'});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
	}

	async addProperties(
		properties: {[key: string]: string},
		{save = true} = {}
	) {
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

		if (save) {
			await this.save();
		}
	}

	async fillName(name: string) {
		await this.descriptionField.waitFor();
		await this.nameInput.fill(name);
	}

	async goto(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: action}),
			trigger: this.page
				.getByRole('row', {name: title})
				.getByLabel('Show Actions'),
		});
	}

	async goToPropertiesTab(title: string) {
		await this.goto('Edit', title);
		await this.propertiesTab.click();
	}

	async moveCategory(categoryName: string, vocabularyName: string) {
		await this.page
			.frameLocator(`iframe[title="Move ${categoryName}"]`)
			.getByText(vocabularyName)
			.click();
		await this.addButton.click();
		await waitForAlert(this.page);
	}

	async save(successMessage?: string) {
		await this.saveButton.click();
		await waitForAlert(this.page, successMessage);
	}
}
