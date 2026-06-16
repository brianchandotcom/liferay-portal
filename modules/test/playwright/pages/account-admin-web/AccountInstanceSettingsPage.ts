/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {SiteSettingsPage} from '../site-admin-web/SiteSettingsPage';

export class AccountInstanceSettingsPage {
	readonly addRepeatableFieldButton: Locator;
	readonly allowedAccountTypesCombobox: Locator;
	readonly deleteRepeatableFieldButtons: Locator;
	readonly page: Page;
	readonly siteSettingsPage: SiteSettingsPage;

	constructor(page: Page) {
		this.addRepeatableFieldButton = page
			.locator('.ddm-form-field-repeatable-add-button')
			.first();
		this.allowedAccountTypesCombobox = page
			.getByLabel('Allowed Account Types')
			.first()
			.getByRole('combobox');
		this.deleteRepeatableFieldButtons = page.locator(
			'.ddm-form-field-repeatable-delete-button'
		);
		this.page = page;
		this.siteSettingsPage = new SiteSettingsPage(page);
	}

	allowedAccountTypeOption(name: string) {
		return this.page.getByRole('option', {name});
	}

	async setAllowedAccountType(siteUrl: string, allowedType: string) {
		await this.siteSettingsPage.goToSiteSetting(
			'Accounts',
			'Accounts',
			siteUrl
		);

		await this.siteSettingsPage.saveConfiguration();

		await this.siteSettingsPage.goToSiteSetting(
			'Accounts',
			'Accounts',
			siteUrl
		);

		await expect(this.addRepeatableFieldButton).toBeVisible();

		let count = await this.deleteRepeatableFieldButtons.count();

		while (count > 0) {
			await this.deleteRepeatableFieldButtons.first().click();

			count -= 1;

			await expect(this.deleteRepeatableFieldButtons).toHaveCount(count, {
				timeout: 2000,
			});
		}

		await this.allowedAccountTypesCombobox.click();
		await this.allowedAccountTypeOption(allowedType).click({force: true});

		await this.page.keyboard.press('Escape');

		await this.siteSettingsPage.saveConfiguration();
	}
}
