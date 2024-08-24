/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {
	TBoolean,
	TCheckbox,
	TCustomField,
	TDate,
	TDropDown,
	TGeolocation,
	TInputField,
	TRadio,
	TTextArea,
} from '../../helpers/CustomFieldTypesHelper';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {ViewAttributesPage} from './ViewAttributesPage';

export class AddCustomFieldPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly fieldNameField: Locator;
	readonly hiddenToggle: Locator;
	readonly localizeFieldNameToggle: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly searchableToggle: Locator;
	readonly successMessage: Locator;
	readonly visibleWithUpdateToggle: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.fieldNameField = page.getByText('Field Name Required');
		this.hiddenToggle = page.getByLabel('Hidden');
		this.localizeFieldNameToggle = page.getByLabel('Localize Field Name');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.searchableToggle = page.getByLabel('Searchable');
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);

		this.visibleWithUpdateToggle = page.getByLabel('Visible with Update');
	}

	async addCustomField(customField: TCustomField) {
		const viewAttributesPage = new ViewAttributesPage(this.page);

		await viewAttributesPage.goto(customField.resource);

		await viewAttributesPage.addCustomFieldButton.click();

		let values;

		switch (customField.fieldType) {
			case 'boolean': {
				await this.page
					.getByRole('link', {name: 'True / False'})
					.click();

				values = <TBoolean>customField.fieldValues;

				break;
			}
			case 'checkbox': {
				await this.page.getByRole('link', {name: 'Checkbox'}).click();

				values = <TCheckbox>customField.fieldValues;

				break;
			}
			case 'date': {
				await this.page.getByRole('link', {name: 'Date'}).click();

				values = <TDate>customField.fieldValues;

				break;
			}
			case 'dropdown': {
				await this.page
					.getByRole('link', {name: 'Dropdown Option'})
					.click();

				values = <TDropDown>customField.fieldValues;

				break;
			}
			case 'geolocation': {
				await this.page
					.getByRole('link', {name: 'Geolocation'})
					.click();

				values = <TGeolocation>customField.fieldValues;

				break;
			}
			case 'inputField': {
				await this.page
					.getByRole('link', {name: 'Input Field'})
					.click();

				values = <TInputField>customField.fieldValues;

				break;
			}
			case 'radio': {
				await this.page.getByRole('link', {name: 'Radio'}).click();

				values = <TRadio>customField.fieldValues;

				break;
			}
			case 'textArea': {
				await this.page.getByRole('link', {name: 'Text Area'}).click();

				values = <TTextArea>customField.fieldValues;

				break;
			}
			default: {
				return;
			}
		}

		await this._populateCommonFields(customField);

		if (values !== undefined) {
			await this._populateUniqueFields(values);
		}

		await this.saveButton.click();
		await expect(await this.successMessage).toBeVisible();
		await this.page.getByLabel('Close').click();
	}

	private async _populateCommonFields(customField: TCustomField) {
		await this.page
			.getByText('Field Name Required')
			.fill(customField.fieldName);

		if (customField.hidden !== undefined) {
			await this.page.getByLabel('Hidden').setChecked(customField.hidden);
		}

		if (customField.localizeFieldName !== undefined) {
			await this.page
				.getByLabel('Localize Field Name')
				.setChecked(customField.localizeFieldName);
		}

		if (customField.searchable !== undefined) {
			await this.page
				.getByLabel('Searchable')
				.setChecked(customField.searchable);
		}

		if (customField.visibleWithUpdate !== undefined) {
			await this.page
				.getByLabel('Visible with Update')
				.setChecked(customField.visibleWithUpdate);
		}
	}

	private async _populateUniqueFields(values: any) {
		if (values.asKeywordOrText === 'keyword') {
			await this.page.getByLabel('As Keyword', {exact: true}).click();
		}
		else if (values.asKeywordOrText === 'text') {
			await this.page.getByLabel('As Text', {exact: true}).click();
		}

		if (values.dataType) {
			await this.page
				.getByLabel('Data Type')
				.selectOption(values.dataType);
		}

		if (values.defaultValue !== undefined) {
			if (typeof values.defaultValue === 'boolean') {
				await this.page
					.getByLabel('Default Value')
					.setChecked(values.defaultValue);
			}
			else {

				// fill in date fields

			}
		}

		if (values.height) {
			await this.page.getByLabel('Height').fill(values.height.toString());
		}

		if (values.localizable !== undefined) {
			await this.page
				.getByLabel('Make Field Localizable')
				.setChecked(values.localizable);
		}

		if (values.precision) {
			await this.page
				.getByLabel('Precision')
				.selectOption(values.precision);
		}

		if (values.secret !== undefined) {
			await this.page.getByLabel('Secret').setChecked(values.secret);
		}

		if (values.startingValue) {
			await this.page
				.getByLabel('Starting Value')
				.fill(values.startingValue);
		}

		if (values.values) {
			await this.page.getByLabel('Values').fill(values.values);
		}

		if (values.width) {
			await this.page.getByLabel('Width').fill(values.width.toString());
		}
	}
}
