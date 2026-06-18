/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class MultiFactorAuthenticationConfigurationPage {
	readonly actions: Locator;
	readonly enabledCheckBox: Locator;
	readonly failedAttemptsAllowed: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly oneTimePasswordLength: Locator;
	readonly page: Page;
	readonly resetDefaultValues: Locator;
	readonly retryTimeout: Locator;
	readonly saveButton: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.actions = page.getByRole('button', {name: 'Actions'});
		this.enabledCheckBox = page.getByLabel('Enabled');
		this.failedAttemptsAllowed = page.getByLabel(
			'Set the number of allowed failed attempts'
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.oneTimePasswordLength = page.getByLabel(
			'One-Time Password Length'
		);
		this.page = page;
		this.resetDefaultValues = page.getByText('Reset Default Values');
		this.retryTimeout = page.getByLabel('Retry Timeout');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.updateButton = page.getByRole('button', {name: 'Update'});
	}

	async enable({
		failedAttemptsAllowed,
		retryTimeout,
	}: {failedAttemptsAllowed?: number; retryTimeout?: number} = {}) {
		await expect(this.enabledCheckBox).toBeVisible();

		await expect(async () => {
			await this.enabledCheckBox.check();

			await expect(this.enabledCheckBox).toBeChecked();
		}).toPass();

		if (failedAttemptsAllowed !== undefined) {
			await this.failedAttemptsAllowed.fill(
				String(failedAttemptsAllowed)
			);
		}

		if (retryTimeout !== undefined) {
			await this.retryTimeout.fill(String(retryTimeout));
		}

		await this.instanceSettingsPage.saveAndWaitForAlert();
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Multi-Factor Authentication',
			'Multi-Factor Authentication and Email One-Time Password Configuration'
		);

		await expect(this.oneTimePasswordLength).toBeVisible();
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
