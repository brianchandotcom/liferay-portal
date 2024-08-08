/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SCIMConfigurationPage {
	readonly accessTokenField: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly errorMessage: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly matcherField: Locator;
	readonly oAuth2ApplicationNameField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.accessTokenField = page.getByText('Access Token');
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
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async configureSCIM(matcherField: string, oAuth2ApplicationName: string) {
		await this.oAuth2ApplicationNameField.fill(oAuth2ApplicationName);

		await this.matcherField.selectOption({label: matcherField});

		await this.saveButton.click();

		await expect(this.successMessage).toBeVisible();
	}

	async generateToken() {
		await this.page.getByLabel('Generate Access Token').click();
	}

	async goTo() {
		await this.instanceSettingsPage.goToInstanceSetting('SCIM', 'SCIM');
	}

	async resetClientData() {
		const resetButton = this.page.getByLabel(
			'Reset SCIM Client Provisioning Data'
		);

		await expect(resetButton).toBeVisible();

		await resetButton.click();
	}

	async revokeToken() {
		const revokeAllButton = this.page.getByLabel('Revoke All');

		await expect(revokeAllButton).toBeVisible();

		await revokeAllButton.click();
	}
}
