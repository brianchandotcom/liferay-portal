/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {FormsPage} from './FormsPage';

export class FormBuilderPage {
	readonly entriesTab: Locator;
	readonly formsPage: FormsPage;
	readonly formSettingsButton: Locator;
	readonly formSettingsDoneButton: Locator;
	readonly formTab: Locator;
	readonly formTitle: Locator;
	readonly newFormHeading: Locator;
	readonly newPageButton: Locator;
	readonly openFormButton: Locator;
	readonly page: Page;
	readonly previewButton: Locator;
	readonly publishButton: Locator;
	readonly requireCaptchaToggle: Locator;
	readonly saveButton: Locator;
	readonly settingsAdvancedTab: Locator;
	readonly unpublishButton: Locator;

	constructor(page: Page) {
		this.entriesTab = page.getByRole('button', {name: 'Entries'});
		this.formsPage = new FormsPage(page);
		this.formSettingsButton = page.getByRole('button', {name: 'Settings'});
		this.formSettingsDoneButton = page.getByRole('button', {name: 'Done'});
		this.formTab = page.getByRole('button', {name: 'Form'});
		this.formTitle = page.getByPlaceholder('Untitled Form');
		this.newFormHeading = page.getByRole('heading', {name: 'New Form'});
		this.newPageButton = page.getByRole('button', {name: 'New Page'});
		this.openFormButton = page.getByRole('button', {
			name: 'Open Form',
		});
		this.page = page;
		this.previewButton = page.getByRole('button', {name: 'Preview'});
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.requireCaptchaToggle = page.getByLabel('Require CAPTCHA');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.settingsAdvancedTab = page.getByRole('tab', {name: 'Advanced'});
		this.unpublishButton = page.getByRole('button', {name: 'Unpublish'});
	}

	async clickOpenFormButton() {
		await this.openFormButton.click();
	}

	async clickPreviewButton() {
		await this.previewButton.click();
	}

	async clickSaveButton() {
		await this.saveButton.click();
	}

	async fillFormTitle(title: string) {
		await this.formTitle.fill(title);
	}

	async goToNew(siteUrl?: Site['friendlyUrlPath']) {
		await this.formsPage.goTo(siteUrl);

		await expect(this.formsPage.formsHeader).toBeVisible();

		await this.formsPage.clickManagementToolbarNewButton();
	}

	async openFieldSettings(fieldLabel: string) {
		await this.page
			.locator('.ddm-field .form-group')
			.getByLabel(fieldLabel, {exact: true})
			.click({force: true});
	}

	async openFormSubmission() {
		await this.publishButton.click();

		await this.page
			.locator('#ToastAlertContainer')
			.getByLabel('Close')
			.click();

		await this.openFormButton.click();
	}
}
