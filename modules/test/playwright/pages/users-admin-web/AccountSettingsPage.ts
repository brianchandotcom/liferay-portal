/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class AccountSettingsPage {
	readonly accountSettingsMenuItem: Locator;
	readonly page: Page;
	readonly rolesMenuItem: Locator;
	readonly saveButton: Locator;
	readonly userDisplayData: Locator;
	readonly userPersonalMenuButton: Locator;
	readonly languageSelect: Locator;

	constructor(page: Page) {
		this.accountSettingsMenuItem = page.getByRole('menuitem', {
			name: 'Account Settings',
		});
		this.page = page;
		this.rolesMenuItem = page.getByRole('link', {
			name: 'Roles',
		});
		this.saveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.userDisplayData = page.getByText('User Display Data');
		this.userPersonalMenuButton = page.getByTestId('userPersonalMenu');
		this.languageSelect = page.getByLabel('Language');
	}

	async goToAccountSettings() {
		await this.userPersonalMenuButton.click();
		await this.accountSettingsMenuItem.click();
	}

	async goToAccountSettingsRoles() {
		await this.goToAccountSettings();
		await Promise.all([
			this.rolesMenuItem.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('screenNavigationEntryKey=roles')
			),
		]);
	}

	async goToDisplaySettings() {
		await this.goToAccountSettings();

		await this.page.locator('.nav-link', {hasText: 'Preferences'}).click();

		await this.page
			.locator('.nav-link', {hasText: 'Display Settings'})
			.click();
	}

	async selectAccountLanguage(option: string) {
		await this.languageSelect.selectOption(option);
		await this.saveButton.click();
	}

	async setTimeZone(timeZone: string) {
		const timeZoneSelect = this.page.getByLabel('Time Zone');

		await timeZoneSelect.selectOption(timeZone);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page);
	}
}
