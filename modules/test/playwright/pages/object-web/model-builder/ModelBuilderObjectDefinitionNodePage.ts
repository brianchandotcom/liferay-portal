/**
 * SPDX-FileCopyrightText: (c)2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ModelBuilderLeftSidebarPage} from './ModelBuilderLeftSidebarPage';
import {ModelBuilderPage} from './ModelBuilderPage';

export class ModelBuilderObjectDefinitionNodePage {
	readonly addObjectFieldButton: Locator;
	readonly deleteObjectDefinitionOption: Locator;
	readonly modelBuilderPage: ModelBuilderPage;
	readonly modalDeleteObjectDefinitionTextField: Locator;
	readonly modalDeleteObjectDefinitionConfirmationButton: Locator;
	readonly modelBuilderLeftSidebarPage: ModelBuilderLeftSidebarPage;
	readonly newObjectFieldSaveButton: Locator;
	readonly objectFieldBusinessTypeSelect: Locator;
	readonly objectFieldLabelInput: Locator;
	readonly objectFieldPicklistSelect: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.addObjectFieldButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Add Field',
		});
		this.deleteObjectDefinitionOption = page.getByRole('menuitem', {
			name: 'Delete Object',
		});
		this.modalDeleteObjectDefinitionConfirmationButton = page
			.getByRole('dialog')
			.getByRole('button', {exact: true, name: 'Delete'});
		this.modalDeleteObjectDefinitionTextField = page.getByPlaceholder(
			'Confirm Object Definition Name'
		);
		this.modelBuilderPage = new ModelBuilderPage(page);
		this.modelBuilderLeftSidebarPage = new ModelBuilderLeftSidebarPage(
			page
		);
		this.newObjectFieldSaveButton = page
			.getByLabel('New Field')
			.getByRole('button', {
				name: 'Save',
			});
		this.objectFieldBusinessTypeSelect = page
			.locator('div.form-group')
			.filter({hasText: /^TypeMandatorySelect an Option$/})
			.getByRole('combobox');
		this.objectFieldLabelInput = page
			.locator('div.form-group')
			.filter({hasText: /^LabelMandatory$/})
			.getByRole('textbox');
		this.objectFieldPicklistSelect = page
			.locator('div.form-group')
			.filter({hasText: /^PicklistSelect an Option$/})
			.getByRole('combobox');
		this.page = page;
	}

	async clickHideFieldsButton(objectDefinitionName: string) {
		await this.modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Hide Fields'})
			.click();
	}

	async clickObjectDefinitionActionsButton(objectDefinitionLabel: string) {
		await this.modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinitionLabel})
			.getByLabel('Show Actions')
			.click();
	}

	async clickShowAllFieldsButton(objectDefinitionName: string) {
		await this.modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Show All Fields'})
			.click();
	}

	async createObjectField({
		listTypeDefinitionName,
		mandatory,
		objectDefinitionName,
		objectFieldBusinessType,
		objectFieldLabel,
	}: CreateObjectField) {
		await this.openAddNewObjectFieldModal(objectDefinitionName);

		await this.fillObjectFieldLabelInput(objectFieldLabel);

		await this.selectNewObjectFieldBusinessTypeOption(
			objectFieldBusinessType
		);

		if (objectFieldBusinessType === 'Picklist') {
			await this.objectFieldPicklistSelect.click();
			await this.page
				.getByRole('option', {
					exact: true,
					name: listTypeDefinitionName,
				})
				.click();
		}

		if (mandatory) {
			await this.page.getByLabel('Mandatory', {exact: true}).check();
		}

		await this.newObjectFieldSaveButton.click();
	}

	async deleteObjectDefinition(objectDefinitionName: string) {
		await this.deleteObjectDefinitionOption.click();
		await this.modalDeleteObjectDefinitionTextField.click();
		await this.modalDeleteObjectDefinitionTextField.fill(
			objectDefinitionName
		);
		await this.modalDeleteObjectDefinitionConfirmationButton.click();
	}

	async fillObjectFieldLabelInput(objectFieldLabel: string) {
		await this.objectFieldLabelInput.fill(objectFieldLabel);
	}

	getLinkedObjectDefinitionIconLocator(objectDefinitionLabel: string) {
		return this.modelBuilderPage.objectDefinitionNodes
			.filter({
				hasText: objectDefinitionLabel,
			})
			.locator('svg.lexicon-icon-link');
	}

	async openAddNewObjectFieldModal(objectDefinitionName: string) {
		await this.modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinitionName})
			.click();

		await this.modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Add Field or Relationship'})
			.click();

		await this.addObjectFieldButton.click();
	}

	async selectNewObjectFieldBusinessTypeOption(
		objectFieldBusinessType: string
	) {
		await this.objectFieldBusinessTypeSelect.click();
		await this.page
			.getByRole('option', {exact: true, name: objectFieldBusinessType})
			.click();
	}
}
