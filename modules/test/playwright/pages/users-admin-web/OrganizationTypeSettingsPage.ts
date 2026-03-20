/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {SystemSettingsPage} from '../configuration-admin-web/SystemSettingsPage';

export class OrganizationTypeSettingsPage {
	readonly addButton: Locator;
	readonly childrenTypesInput: Locator;
	readonly countryEnabledCheckbox: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly rootableCheckbox: Locator;
	readonly systemSettingsPage: SystemSettingsPage;

	constructor(page: Page) {
		this.addButton = page.getByRole('link', {name: 'Add'});
		this.childrenTypesInput = page.getByRole('textbox', {
			name: 'Children Types',
		});
		this.countryEnabledCheckbox = page.getByLabel('Country Enabled');
		this.nameInput = page.getByRole('textbox', {name: 'Name'}).last();
		this.page = page;
		this.rootableCheckbox = page.getByLabel('Rootable');
		this.systemSettingsPage = new SystemSettingsPage(page);
	}

	async goto() {
		await this.systemSettingsPage.goToSystemSetting(
			'Users',
			'Organization Type'
		);
	}

	async deleteOrgType(orgTypeName: string) {
		await this.goto();

		const orgTypeLink = this.page.getByRole('link', {
			name: orgTypeName,
		});

		if (await orgTypeLink.isVisible().catch(() => false)) {
			await orgTypeLink.click();
			await this.page.waitForLoadState('networkidle');

			await this.systemSettingsPage.clickOnAction('Delete');

			await this.page.waitForLoadState('networkidle');
		}
	}
}
