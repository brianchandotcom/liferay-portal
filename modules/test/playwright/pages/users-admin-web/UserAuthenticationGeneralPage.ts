/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class UserAuthenticationGeneralPage {
	readonly allowStrangersCheckbox: Locator;
	readonly allowStrangersWithCompanyEmailCheckbox: Locator;
	readonly allowUsersAutoLoginCheckbox: Locator;
	readonly allowUsersRequestPasswordResetCheckbox: Locator;
	readonly authenticationMethodSelect: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.allowStrangersCheckbox = page.getByLabel(
			'Allow strangers to create accounts?'
		);
		this.allowStrangersWithCompanyEmailCheckbox = page.getByLabel(
			'Allow strangers to create accounts with a company email address?'
		);
		this.allowUsersAutoLoginCheckbox = page.getByLabel(
			'Allow users to automatically log in?'
		);
		this.allowUsersRequestPasswordResetCheckbox = page.getByLabel(
			'Allow users to request password reset links?'
		);
		this.authenticationMethodSelect = page.getByLabel(
			'How do users authenticate?'
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'User Authentication',
			'General'
		);
	}

	async resetFields() {
		await this.goto();

		await this.authenticationMethodSelect.selectOption('By Email Address');
		await this.allowUsersAutoLoginCheckbox.check();
		await this.allowUsersRequestPasswordResetCheckbox.check();
		await this.allowStrangersCheckbox.check();
		await this.allowStrangersWithCompanyEmailCheckbox.check();
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
