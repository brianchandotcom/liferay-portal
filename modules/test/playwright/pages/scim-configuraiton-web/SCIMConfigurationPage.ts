/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SCIMConfigurationPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly oAuth2ApplicationNameField: Locator;
	readonly matcherField: Locator;
	readonly accessTokenField: Locator;
	readonly errorMessage: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.oAuth2ApplicationNameField = page.getByLabel(
			'OAuth 2 Application Name'
		);
		this.matcherField = page.getByLabel('Matcher Field');
		this.accessTokenField = page.getByText('Access Token');
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.errorMessage = page.getByText('Your request failed to complete');

		this.page.on('dialog', async (dialog) => {
			await dialog.accept();
		});
	}

	async goTo() {
		await this.instanceSettingsPage.goToInstanceSetting('SCIM', 'SCIM');

		await this.page.waitForTimeout(1000);
	}

	async configureSCIM(oAuth2ApplicationName: string, matcherField: string) {
		await this.oAuth2ApplicationNameField.fill(oAuth2ApplicationName);

		await this.matcherField.selectOption({label: matcherField});

		await this.saveButton.click();

		await expect(this.successMessage).toBeVisible();
	}

	async generateToken() {
		await this.page.getByLabel('Generate Access Token').click();
	}

	async revokeToken() {
		const revokeAllButton = this.page.getByLabel('Revoke All');

		await expect(revokeAllButton).toBeVisible();

		await revokeAllButton.click();
	}

	async resetClientData() {
		const revokeAllButton = this.page.getByLabel(
			'Reset SCIM Client Provisioning Data'
		);

		await expect(revokeAllButton).toBeVisible();

		await revokeAllButton.click();
	}
}
