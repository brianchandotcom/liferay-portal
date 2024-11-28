/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CreateObjectField} from '../../../helpers/ObjectAdminApiHelper';
import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';

export class ObjectFieldsPage {
	readonly addObjectFieldButton: Locator;
	readonly deleteObjectFieldOption: Locator;
	readonly fieldsTabItem: Locator;
	readonly page: Page;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;
	readonly saveButton: Locator;
	readonly objectFieldLabelInput: Locator;
	readonly objectFieldOptionsDropdown: Locator;

	constructor(page: Page) {
		this.addObjectFieldButton = page.getByLabel('Add Object Field');
		this.deleteObjectFieldOption = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.fieldsTabItem = page.locator('.nav-item .nav-link').filter({
			hasText: 'Fields',
		});
		this.page = page;
		this.objectFieldLabelInput = page.locator('input[name="label"]');
		this.objectFieldOptionsDropdown = page.getByText('Select an Option');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
	}

	async addObjectField({
		attachmentSource,
		listTypeDefinitionName,
		objectFieldBusinessType,
		objectFieldLabel,
	}: CreateObjectField) {
		await this.addObjectFieldButton.waitFor();
		await this.addObjectFieldButton.click();

		await this.objectFieldLabelInput.waitFor();
		await this.objectFieldLabelInput.fill(objectFieldLabel);

		await this.objectFieldOptionsDropdown.click();

		await this.page
			.getByRole('option', {exact: true, name: objectFieldBusinessType})
			.click();

		if (objectFieldBusinessType === 'Attachment') {
			await this.objectFieldOptionsDropdown.click();
			await this.page
				.getByRole('option', {name: attachmentSource})
				.click();
		}

		if (
			objectFieldBusinessType === 'Multiselect Picklist' ||
			objectFieldBusinessType === 'Picklist'
		) {
			await this.objectFieldOptionsDropdown.click();
			await this.page
				.getByRole('option', {name: listTypeDefinitionName})
				.click();
		}

		await this.saveButton.click();
	}

	async deleteObjectField(nth: number) {
		await this.page.locator('.dnd-td.item-actions').nth(nth).waitFor();

		await this.page
			.locator('.dnd-td.item-actions')
			.nth(nth)
			.locator('.dropdown-toggle')
			.click();

		await this.deleteObjectFieldOption.click();
	}

	async goto(objectDefinitionLabel: string) {
		await this.viewObjectDefinitionsPage.goto();

		await this.viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinitionLabel
		);

		await this.fieldsTabItem.click();
	}
}
