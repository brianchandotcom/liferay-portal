/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class AuthServerLocalMetadatasPage {
	readonly addOAuthAuthorizationServerButton: Locator;
	readonly allowedScopes: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly authorizationEndpoint: Locator;
	readonly authServerLocalMetadataTab: Locator;
	readonly enabledField: Locator;
	readonly grantTypes: Locator;
	readonly issuer: Locator;
	readonly jwkUri: Locator;
	readonly oAuthAuthorizatoinServerTab: Locator;
	readonly oAuthAuthorizatoinServerTable: Locator;
	readonly openIdConfigurationTab: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly subjectTypesSupported: Locator;
	readonly successMessage: Locator;
	readonly tokenEndpoint: Locator;
	readonly urlErrorMessage: Locator;
	readonly userinfoEnpoint: Locator;

	constructor(page: Page) {
		this.addOAuthAuthorizationServerButton = page.getByRole('link', {
			name: 'Add OAuth Authorization',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.issuer = page.getByLabel('Issuer Required The issuer');
		this.allowedScopes = page.getByLabel('Allowed Scopes');
		this.grantTypes = page.getByLabel('Grant Types');
		this.authorizationEndpoint = page.getByLabel('Authorization Endpoint');
		this.jwkUri = page.getByLabel('JWK URI');
		this.tokenEndpoint = page.getByLabel('Token Endpoint');
		this.enabledField = page.getByText('Enabled', {exact: true});
		this.subjectTypesSupported = page.getByLabel('Subject Types Supported');
		this.userinfoEnpoint = page.getByLabel('Userinfo enpoint');
		this.authServerLocalMetadataTab = page.getByRole('link', {
			name: 'Auth Server Local Metadata',
		});
		this.oAuthAuthorizatoinServerTab = page.getByRole('link', {
			exact: true,
			name: 'OAuth Authorization Server',
		});
		this.oAuthAuthorizatoinServerTable = page.locator(
			'#_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_oAuthClientASLocalMetadataSearchContainer'
		);

		this.openIdConfigurationTab = page.getByRole('link', {
			exact: true,
			name: 'OpenId Configuration',
		});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.urlErrorMessage = page.getByText('Close Error: The URL is not a');
	}

	async addAuthServerLocalMetadata(issuer: string, expectedMessage?: string) {
		await this.addOAuthAuthorizationServerButton.click();

		await this.issuer.fill(issuer);

		await this.saveButton.click();

		if (expectedMessage !== undefined) {
			await expect(
				await this.page.getByText(expectedMessage)
			).toBeVisible();

			if (
				await this.page
					.locator('#ToastAlertContainer')
					.getByLabel('Close')
					.isVisible()
			) {
				await this.page
					.locator('#ToastAlertContainer')
					.getByLabel('Close')
					.click();
			}

			await this.page.getByRole('button', {name: 'Cancel'}).click();
		}
		else {
			await expect(await this.successMessage).toBeVisible();
			await this.page.getByLabel('Close').click();
		}
	}

	async deleteAuthServerLocalMetadata() {
		await this.page.waitForTimeout(1000);

		const row = await this.oAuthAuthorizatoinServerTable
			.getByRole('row')
			.last();

		while (await row.isVisible()) {
			this.page.once('dialog', (dialog) => {
				dialog.accept();
			});

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('link', {name: 'Delete'}),
				trigger: row.locator('.dropdown-toggle'),
			});

			await expect(await this.successMessage).toBeVisible();

			// Prevent the above expect from passing due to previous success

			await this.page.getByLabel('Close').click();
		}
	}

	async checkResult(
		oAuthAuthorizatoinServerUrl: string,
		openIdConfigurationUrl: string
	) {
		if (await this.oAuthAuthorizatoinServerTab.isHidden()) {
			await this.applicationsMenuPage.goToOAuthClientAdministration();
		}

		await this.authServerLocalMetadataTab.click();
		await this.oAuthAuthorizatoinServerTab.click();
		await expect(
			await this.page.getByRole('link', {
				name: oAuthAuthorizatoinServerUrl,
			})
		).toBeVisible();

		await this.openIdConfigurationTab.click();
		await expect(
			await this.page.getByRole('link', {name: openIdConfigurationUrl})
		).toBeVisible();

		await this.oAuthAuthorizatoinServerTab.click();
	}

	async goTo() {
		if (await this.oAuthAuthorizatoinServerTab.isHidden()) {
			await this.applicationsMenuPage.goToOAuthClientAdministration();
		}

		await this.authServerLocalMetadataTab.click();
		await this.oAuthAuthorizatoinServerTab.click();
		await expect(
			await this.addOAuthAuthorizationServerButton
		).toBeVisible();
	}
}
