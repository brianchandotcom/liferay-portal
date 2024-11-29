/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ObjectRelationshipFormPage {
	readonly inheritanceCheckbox: Locator;
	readonly labelInput: Locator;
	readonly manyRecordsOfSelect: Locator;
	readonly nameInput: Locator;
	readonly oneRecordOfInput: Locator;
	readonly page: Page;
	readonly reverseOrderButton: Locator;
	readonly saveButton: Locator;
	readonly typeSelect: Locator;

	constructor(page: Page) {
		this.inheritanceCheckbox = page.getByLabel('Enable Inheritance');
		this.labelInput = page.getByLabel('LabelMandatory');
		this.manyRecordsOfSelect = page.getByLabel('Many Records OfMandatory');
		this.nameInput = page.getByLabel('NameMandatory');
		this.oneRecordOfInput = page.getByLabel('One Record OfMandatory');
		this.page = page;
		this.reverseOrderButton = page.getByLabel('reverse-order');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.typeSelect = page.getByLabel('TypeMandatory');
	}

	async selectType(option: 'Many to Many' | 'One to Many') {
		await this.typeSelect.click();

		await this.page.getByRole('option', {name: option}).click();
	}

	async selectManyRecordsOf(option: string) {
		await this.manyRecordsOfSelect.click();

		await this.page.getByRole('option', {name: option}).click();
	}
}
