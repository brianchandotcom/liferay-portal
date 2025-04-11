/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class AccountSettingsPage {
	readonly accountSettingsMenuItem: Locator;
	readonly currentPasswordInput: Locator;
	private readonly displayMenuItem: Locator;
	readonly languageSelect: Locator;
	readonly multiFactorAuthentitacionNavigationItem: Locator;
	readonly newPasswordInput: Locator;
	readonly page: Page;
	readonly passwordErrorMessage: (message: string) => Locator;
	readonly passwordMenuItem: Locator;
	private readonly preferencesNavigationItem: Locator;
	readonly reenterPasswordInput: Locator;
	readonly rolesMenuItem: Locator;
	readonly saveButton: Locator;
	private readonly timeZoneSelect: Locator;
	readonly userDisplayData: Locator;
	readonly userPersonalMenuButton: Locator;

	constructor(page: Page) {
		this.accountSettingsMenuItem = page.getByRole('menuitem', {
			name: 'Account Settings',
		});
		this.currentPasswordInput = page.getByLabel('Current Password');
		this.displayMenuItem = page.getByRole('link', {
			name: 'Display Settings',
		});
		this.languageSelect = page.getByLabel('Language');
		this.multiFactorAuthentitacionNavigationItem = page.getByRole('link', {
			name: 'Multi-Factor Authentication',
		});
		this.newPasswordInput = page.getByLabel('New Password');
		this.page = page;
		this.passwordErrorMessage = (message: string) => {
			return page.getByText(message);
		};
		this.passwordMenuItem = page.getByRole('link', {name: 'Password'});
		this.preferencesNavigationItem = page.locator('.nav-link', {
			hasText: 'Preferences',
		});
		this.reenterPasswordInput = page.getByLabel('Reenter Password');
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

		await expect(this.accountSettingsMenuItem).toBeVisible();

		await this.accountSettingsMenuItem.click();

		await expect(this.userDisplayData).toBeVisible();
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

		await expect(
			this.multiFactorAuthentitacionNavigationItem
		).toBeVisible();

		await this.multiFactorAuthentitacionNavigationItem.click();

		await expect(this.page.getByLabel('Shared Secret')).toBeVisible();
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
