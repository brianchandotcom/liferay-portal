/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class FormBuilderSidePanelPage {
	readonly addSelectFromListButton: Locator;
	readonly addSelectOptionButton: Locator;
	readonly addSingleSelectionButton: Locator;
	readonly advancedTab: Locator;
	readonly backButton: Locator;
	readonly htmlAutocompleteAttributeField: Locator;
	readonly label: Locator;
	readonly objectFieldSelect: Locator;
	readonly paragraphFieldTextarea: Locator;
	readonly paragraphFieldTitle: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.addSelectFromListButton = page.getByRole('button', {
			name: 'Press enter to add Select',
		});
		this.addSelectOptionButton = page.getByRole('button', {
			name: 'Add Option',
		});
		this.addSingleSelectionButton = page.getByRole('button', {
			name: 'Press enter to add Single',
		});
		this.advancedTab = page.getByRole('tab', {
			name: 'Advanced',
		});
		this.backButton = page.getByRole('button', {name: 'Back'});
		this.htmlAutocompleteAttributeField = page.getByLabel(
			'HTML Autocomplete Attribute'
		);
		this.label = page.getByLabel('Label', {exact: true}).first();
		this.objectFieldSelect = page.getByLabel('Object Field');
		this.paragraphFieldTextarea = page
			.frameLocator('iframe')
			.locator('.cke_editable');
		this.paragraphFieldTitle = page.getByPlaceholder('Enter a title.');

		this.page = page;
	}

	async addFieldByDoubleClick(formFieldTypeTitle: FormFieldTypeTitle) {
		await this.page
			.getByTitle(formFieldTypeTitle, {exact: true})
			.dblclick();
	}

	async clickAdvancedTab() {
		await this.advancedTab.click();
	}

	async clickBackButton() {
		await this.backButton.click();
	}

	async selectObjectField(objectFieldLabel: string) {
		await this.objectFieldSelect.click();

		const option = this.getSelectOptionLocator(objectFieldLabel);
		await option.click();
	}

	async fillParagraphField({text}: {text: string}) {
		await this.paragraphFieldTextarea.fill(text);

		// filling is not enough, we need need a key event to make it work

		await this.paragraphFieldTextarea.press('End');

		await this.page.waitForLoadState('networkidle');
	}

	getSelectOptionLocator(optionLabel: string) {
		return this.page.getByRole('option', {name: optionLabel});
	}
}
