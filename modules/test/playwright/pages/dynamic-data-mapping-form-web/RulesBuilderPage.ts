/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class RulesBuilderPage {
	readonly actionSelect: Locator;
	readonly addElementsButton: Locator;
	readonly autofillDataProviderSelect: Locator;
	readonly conditionLeftFormFieldSelect: Locator;
	readonly conditionOperatorSelect: Locator;
	readonly conditionOperatorValueSourceSelect: Locator;
	readonly conditionRightFormFieldSelect: Locator;
	readonly dataProviderOutputSelect: Locator;
	readonly page: Page;
	readonly rulesTab: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.actionSelect = page
			.locator('li')
			.filter({hasText: 'Do'})
			.getByRole('combobox');
		this.addElementsButton = page.getByTitle('Add Elements');
		this.autofillDataProviderSelect = page
			.locator('ul')
			.filter({hasText: 'ActionsDoAutofillFrom Data'})
			.getByRole('combobox')
			.nth(1);
		this.conditionLeftFormFieldSelect = page.getByTestId('field-left-id');
		this.conditionOperatorSelect = page.getByTestId('field-operator-id');
		this.conditionOperatorValueSourceSelect = page.getByTestId(
			'field-binary-operator-id'
		);
		this.conditionRightFormFieldSelect = page.getByTestId('field-right-id');
		this.dataProviderOutputSelect = page
			.locator('li')
			.filter({hasText: 'DoAutofillFrom Data'})
			.getByRole('combobox')
			.nth(2);
		this.page = page;
		this.rulesTab = page.getByRole('button', {name: 'Rules'});
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async selectAction(actionName: string) {
		await this.actionSelect.click();
		await this.page.getByRole('option', {name: actionName}).click();
	}

	async selectAutofillDataProvider(dataProviderName: string) {
		await this.autofillDataProviderSelect.click();
		await this.page.getByRole('option', {name: dataProviderName}).click();
	}

	async selectConditionLeftFormField(fieldName: string) {
		await this.conditionLeftFormFieldSelect.click();
		await this.page.getByRole('option', {name: fieldName}).click();
	}

	async selectConditionOperator(operatordName: string) {
		await this.conditionOperatorSelect.click();
		await this.page.getByRole('option', {name: operatordName}).click();
	}

	async selectConditionOperatorValueSource(valueSource: string) {
		await this.conditionOperatorValueSourceSelect.click();
		await this.page.getByRole('option', {name: valueSource}).click();
	}

	async selectConditionRightFormField(fieldName: string) {
		await this.conditionRightFormFieldSelect.click();
		await this.page.getByRole('option', {name: fieldName}).click();
	}

	async selectDataProviderOutput(fieldName: string) {
		await this.dataProviderOutputSelect.click();
		await this.page.getByRole('option', {name: fieldName}).click();
	}
}
