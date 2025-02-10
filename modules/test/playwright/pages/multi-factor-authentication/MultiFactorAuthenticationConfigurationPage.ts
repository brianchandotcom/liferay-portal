/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class MultiFactorAuthenticationConfigurationPage {
	readonly enabledCheckBox: Locator;
	readonly saveButton: Locator;
	readonly page: Page;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly updateButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.enabledCheckBox = this.page.getByText('Enabled');
		this.saveButton = this.page.getByRole('button', {name: 'Save'});
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.updateButton = this.page.getByRole('button', {name: 'Update'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async goTo() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Multi-Factor Authentication',
			'Multi-Factor Authentication and Email One-Time Password Configuration'
		);

		await this.enabledCheckBox.waitFor();
	}

	async enable() {
		await this.enabledCheckBox.waitFor();

		await this.enabledCheckBox.check();

		if (await this.page.isVisible('button:has-text("Update")')) {
			await this.updateButton.click();
		}
		else {
			await this.saveButton.click();
		}
	}

	async disable() {
		await this.enabledCheckBox.waitFor();

		await this.enabledCheckBox.uncheck();

		await this.updateButton.click();
	}
}
