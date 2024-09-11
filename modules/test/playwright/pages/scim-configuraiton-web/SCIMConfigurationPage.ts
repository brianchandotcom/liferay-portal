/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {reloadUntilVisible} from '../../utils/reloadUntilVisible';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SCIMConfigurationPage {
	readonly accessTokenField: Locator;
	readonly alertMessage: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly errorMessage: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly matcherField: Locator;
	readonly oAuth2ApplicationNameField: Locator;
	readonly page: Page;
	readonly resetButton: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.accessTokenField = page.getByLabel('Access Token', {exact: true});
		this.alertMessage = page.getByText(
			'The access token for the SCIM client will expire at'
		);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.errorMessage = page.getByText('Your request failed to complete');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.matcherField = page.getByLabel('Matcher Field');
		this.oAuth2ApplicationNameField = page.getByLabel(
			'OAuth 2 Application Name'
		);
		this.page = page;
		this.page.on('dialog', async (dialog) => {
			await dialog.accept();
		});
		this.resetButton = page.getByLabel(
			'Reset SCIM Client Provisioning Data'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async configureSCIM(matcherField: string, oAuth2ApplicationName: string) {
		await this.resetClientData(false);

		await this.oAuth2ApplicationNameField.fill(oAuth2ApplicationName);

		await this.matcherField.selectOption({label: matcherField});

		await this.saveButton.click();

		await expect(this.successMessage).toBeVisible();

		await reloadUntilVisible({
			myLocator: this.resetButton,
			page: this.page,
		});
	}

	async generateToken() {
		await this.page.getByLabel('Generate Access Token').click();

		await expect(this.successMessage).toBeVisible();

		await this.page.waitForTimeout(500);
	}

	async goTo() {
		await this.instanceSettingsPage.goToInstanceSetting('SCIM', 'SCIM');

		await this.oAuth2ApplicationNameField.waitFor();

		await this.page.waitForTimeout(500);
	}

	async resetClientData(assertVisible = true) {
		if (assertVisible || (await this.resetButton.isVisible())) {
			await expect(this.resetButton).toBeVisible();

			await this.resetButton.click();

			await expect(this.successMessage).toBeVisible();

			await this.page.waitForTimeout(500);
		}
	}

	async revokeToken() {
		const revokeAllButton = this.page.getByLabel('Revoke All');

		await expect(revokeAllButton).toBeVisible();

		await revokeAllButton.click();

		await expect(this.successMessage).toBeVisible();

		await this.page.waitForTimeout(500);
	}
}
