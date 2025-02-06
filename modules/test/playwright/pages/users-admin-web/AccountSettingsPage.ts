/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class AccountSettingsPage {
	readonly accountSettingsMenuItem: Locator;
	private readonly displayMenuItem: Locator;
	readonly languageSelect: Locator;
	readonly multiFactorAuthentitacionNavigationItem: Locator;
	readonly page: Page;
	private readonly preferencesNavigationItem: Locator;
	readonly rolesMenuItem: Locator;
	readonly saveButton: Locator;
	private readonly timeZoneSelect: Locator;
	readonly userDisplayData: Locator;
	readonly userPersonalMenuButton: Locator;

	constructor(page: Page) {
		this.accountSettingsMenuItem = page.getByRole('menuitem', {
			name: 'Account Settings',
		});
		this.displayMenuItem = page.getByRole('link', {
			name: 'Display Settings',
		});
		this.languageSelect = page.getByLabel('Language');
		this.multiFactorAuthentitacionNavigationItem = page.locator(
			'.nav-link',
			{
				hasText: 'Multi-Factor Authentication',
			}
		);
		this.page = page;
		this.preferencesNavigationItem = page.locator('.nav-link', {
			hasText: 'Preferences',
		});
		this.rolesMenuItem = page.getByRole('link', {
			name: 'Roles',
		});
		this.saveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.timeZoneSelect = page.getByLabel('Time Zone');
		this.userDisplayData = page.getByText('User Display Data');
		this.userPersonalMenuButton = page.getByTitle('User Profile Menu');
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

		await this.preferencesNavigationItem.click();

		await this.displayMenuItem.click();
	}

	async goToMultiFactorAuthenticationSettings() {
		await this.goToAccountSettings();

		await this.multiFactorAuthentitacionNavigationItem.click();
	}

	async selectAccountLanguage(option: string) {
		await this.languageSelect.selectOption(option);
		await this.saveButton.click();
	}

	async setTimeZone(timeZone: string) {
		await this.timeZoneSelect.selectOption(timeZone);

		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
