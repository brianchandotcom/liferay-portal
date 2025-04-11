/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class TimeBasedOneTimePasswordConfigurationPage {
	readonly actions: Locator;
	readonly clockSkew: Locator;
	readonly enabledCheckBox: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly resetDefaultValues: Locator;
	readonly saveButton: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.actions = page.getByRole('button', {name: 'Actions'});
		this.clockSkew = page.getByLabel('Clock Skew');
		this.enabledCheckBox = page.getByLabel('Enabled');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.resetDefaultValues = page.getByText('Reset Default Values');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.updateButton = page.getByRole('button', {name: 'Update'});
	}

	async enable() {
		await expect(this.enabledCheckBox).toBeVisible();

		await expect(async () => {
			await this.enabledCheckBox.check();

			await expect(this.enabledCheckBox).toBeChecked();
		}).toPass();

		await this.saveConfiguration();
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Multi-Factor Authentication',
			'Time-Based One-Time Password Configuration'
		);

		await expect(this.clockSkew).toBeVisible();
	}

	async resetConfiguration() {
		if (await this.actions.isVisible()) {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.resetDefaultValues,
				trigger: this.actions,
			});

			await this.page
				.getByText('Success:Your request completed successfully.')
				.waitFor();
		}
	}

	async saveConfiguration() {
		if (await this.page.isVisible('button:has-text("Update")')) {
			await this.updateButton.click();
		}
		else {
			await this.saveButton.click();
		}

		await this.page
			.getByText('Success:Your request completed successfully.')
			.waitFor();
	}
}
