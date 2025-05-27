/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class EditRegionPage {
	readonly activeButton: Locator;
	readonly backButton: Locator;
	readonly cancelButton: Locator;
	readonly keyErrorMessage: Locator;
	readonly keyInput: Locator;
	readonly notTranslatedMessage: (language: string) => Locator;
	readonly page: Page;
	readonly priorityInput: Locator;
	readonly regionCodeErrorMessage: Locator;
	readonly regionCodeInput: Locator;
	readonly saveButton: Locator;
	readonly titleInput: Locator;
	readonly titleTranslationButton: Locator;
	readonly translatedMessage: (language: string) => Locator;

	constructor(page: Page) {
		this.activeButton = page.getByLabel('Active');
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.keyErrorMessage = page.getByText('The Key field is required');
		this.keyInput = page.getByLabel('Key');
		this.notTranslatedMessage = (language) =>
			page.getByLabel(
				`Not translated into ${language}. Press enter to edit ${language} translation.`,
				{exact: true}
			);
		this.page = page;
		this.priorityInput = page.getByLabel('Priority');
		this.regionCodeErrorMessage = page.getByText(
			'The Region Code field is required'
		);
		this.regionCodeInput = page.getByLabel('Region Code');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.titleInput = page.locator(
			'[id="_com_liferay_address_web_internal_portlet_CountriesManagementAdminPortlet_title"]'
		);
		this.titleTranslationButton = page.getByRole('button', {
			name: 'Current translation is',
		});
		this.translatedMessage = (language) =>
			page.getByLabel(
				`Translated into ${language}. Press enter to edit ${language} translation.`,
				{exact: true}
			);
	}

	async editRegion(region: {
		key: string;
		name: string;
		priority: string;
		regionCode: string;
	}) {
		await this.titleInput.fill(region.name);
		await this.keyInput.fill(region.key);
		await this.priorityInput.fill(region.priority);
		await this.regionCodeInput.fill(region.regionCode);
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
