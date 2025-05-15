/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../helpers/ApiHelpers';

export class FormBuilderSidePanelPage {
	readonly addMultipleSelectionButton: Locator;
	readonly addSelectFromListButton: Locator;
	readonly addSelectOptionButton: Locator;
	readonly addSingleSelectionButton: Locator;
	readonly advancedTab: Locator;
	readonly allowGuestUsers: Locator;
	readonly backButton: Locator;
	readonly collapsibleToggleSwitch: Locator;
	readonly helpText: Locator;
	readonly htmlAutocompleteAttributeField: Locator;
	readonly label: Locator;
	readonly objectFieldSelect: Locator;
	readonly page: Page;
	readonly paragraphFieldTextarea: Locator;
	readonly paragraphFieldTitle: Locator;
	readonly predefinedValueField: Locator;
	readonly requireConfirmationToggleSwitch: Locator;
	readonly requiredFieldToggleSwitch: Locator;

	constructor(page: Page) {
		this.addMultipleSelectionButton = page.getByRole('button', {
			name: 'Press enter to add Multiple Selection field.',
		});
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
		this.allowGuestUsers = page.getByLabel('Allow Guest Users to Send');
		this.backButton = page.getByRole('button', {name: 'Back'});
		this.collapsibleToggleSwitch = page.getByRole('switch', {
			name: 'Collapsible',
		});
		this.helpText = page.getByLabel('Help Text');
		this.htmlAutocompleteAttributeField = page.getByLabel(
			'HTML Autocomplete Attribute'
		);
		this.label = page.getByLabel('Label', {exact: true}).first();
		this.objectFieldSelect = page.getByLabel('Object Field');
		this.page = page;
		this.paragraphFieldTextarea = page
			.frameLocator('iframe')
			.locator('.cke_editable');
		this.paragraphFieldTitle = page.getByPlaceholder('Enter a title.');
		this.predefinedValueField = page.getByLabel('Predefined Value');
		this.requiredFieldToggleSwitch = page.getByText('Required Field');
		this.requireConfirmationToggleSwitch = page.getByLabel(
			'Require Confirmation'
		);
	}

	async addFieldByDoubleClick(formFieldTypeTitle: FormFieldTypeTitle) {
		await this.page
			.getByTitle(formFieldTypeTitle, {exact: true})
			.dblclick();
	}

	async addFieldToFieldGroup(
		sourceField: FormFieldTypeTitle,
		position: number
	) {
		await this.page
			.getByRole('tabpanel')
			.getByTitle(sourceField, {exact: true})
			.dragTo(this.page.locator('.ddm-drag').nth(position));
	}

	async clickAdvancedTab() {
		await this.advancedTab.click();
	}

	async clickBackButton() {
		await this.backButton.click();
	}

	async fillParagraphField(apiHelpers: ApiHelpers, text: string) {
		await this.paragraphFieldTextarea.fill(text);

		// filling is not enough, we need need a key event to make it work

		await this.paragraphFieldTextarea.press('End');

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(this.page);
	}

	async selectObjectField(objectFieldLabel: string) {
		await this.objectFieldSelect.click();

		const option = this.getSelectOptionLocator(objectFieldLabel);
		await option.click();
	}

	getSelectOptionLocator(optionLabel: string) {
		return this.page.getByRole('option', {name: optionLabel});
	}
}
