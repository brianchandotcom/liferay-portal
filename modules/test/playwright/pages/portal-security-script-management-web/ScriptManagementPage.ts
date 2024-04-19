/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class ScriptManagementPage {
	readonly allowScriptCheckbox: Locator;
	readonly doneButton: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;
	readonly activeGroovyItem: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.allowScriptCheckbox = page.getByRole('checkbox');
		this.doneButton = page.getByRole('button').filter({
			hasText: 'Done',
		});
		this.saveButton = page.getByRole('button').filter({
			hasText: 'Save',
		});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.page = page;
	}

	async disableScriptManagementConfiguration(expectSuccess: boolean = true) {
		await this.goto();
		await this.allowScriptCheckbox.uncheck();
		await this.saveConfiguration();

		/**
		 * These expects are to make tests using this function wait a little before proceeding to another action.
		 * As many tests use this function we decided to place them here instead of repeating them everywhere that use it.
		 */
		if (expectSuccess) {
			await expect(this.successMessage).toBeVisible();
			await expect(this.allowScriptCheckbox).not.toBeChecked();
		}
	}

	async enableScriptManagementConfiguration() {
		await this.goto();
		await this.allowScriptCheckbox.check();
		await this.saveConfiguration();

		/**
		 * These expects are to make tests using this function wait a little before proceeding to another action.
		 * As many tests use this function we decided to place them here instead of repeating them everywhere that use it.
		 */
		await expect(this.successMessage).toBeVisible();
		await expect(this.allowScriptCheckbox).toBeChecked();
	}

	getActiveGroovyItemLocator(groovyItemText: string) {
		return this.page.getByRole('link').filter({
			hasText: groovyItemText,
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.scriptManagement}`,
			{waitUntil: 'load'}
		);
	}

	async saveConfiguration() {
		await this.saveButton.click();
	}
}
