/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';

export class EditObjectDetailsPage {
	readonly accountRestrictedFieldCombobox: Locator;
	readonly accountRestrictionToggle: Locator;
	readonly detailsTabItem: Locator;
	readonly friendlyURLSeparator: Locator;
	readonly labelInput: Locator;
	readonly labelLocalizationButton: Locator;
	readonly page: Page;
	readonly pluralLabelInput: Locator;
	readonly pluralLabelLocalizationButton: Locator;
	readonly publishButton: Locator;
	readonly saveButton: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;

	constructor(page: Page) {
		this.accountRestrictedFieldCombobox = page.getByRole('combobox', {
			name: 'Account Restricted Field',
		});
		this.accountRestrictionToggle = page.getByLabel(
			'Enable Account Restriction',
			{exact: true}
		);
		this.detailsTabItem = page.getByRole('link', {name: 'Details'});
		this.friendlyURLSeparator = page.getByLabel(
			'Object Entry URL Separator',
			{exact: true}
		);
		this.labelInput = page.getByRole('textbox', {
			exact: true,
			name: 'Label Mandatory',
		});
		this.labelLocalizationButton = page
			.locator('div')
			.filter({hasText: /^LabelMandatory$/})
			.getByLabel('Open Localizations');
		this.page = page;
		this.pluralLabelInput = page.getByRole('textbox', {
			name: 'Plural Label Mandatory',
		});
		this.pluralLabelLocalizationButton = page
			.locator('div')
			.filter({hasText: /^Plural LabelMandatory$/})
			.getByLabel('Open Localizations');
		this.publishButton = page.getByRole('button', {
			exact: true,
			name: 'Publish',
		});
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
	}

	async enableAccountRestriction(fieldName: string) {
		await this.accountRestrictionToggle.check();

		await this.accountRestrictedFieldCombobox.click();
		await this.page.getByRole('option', {name: fieldName}).click();
	}

	async goto(objectDefinitionLabel: string) {
		await this.viewObjectDefinitionsPage.goto();

		await this.viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinitionLabel
		);
	}

	async goToDetailsTab() {
		await this.detailsTabItem.click();

		await this.page.waitForLoadState('networkidle');
	}

	async saveObjectDefinition() {
		await this.saveButton.click();
	}

	async selectLabelLanguage(language: string) {
		await this.labelLocalizationButton.click();

		await this.page
			.getByRole('option', {
				exact: false,
				name: `${language} language:`,
			})
			.click();
	}

	async selectPluralLabelLanguage(language: string) {
		await this.pluralLabelLocalizationButton.click();

		await this.page
			.getByRole('option', {
				exact: false,
				name: `${language} language:`,
			})
			.click();
	}
}
