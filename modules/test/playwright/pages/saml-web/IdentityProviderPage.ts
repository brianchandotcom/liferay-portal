/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class IdentityProviderPage {
	readonly authnRequestSigningAllowsDynamicAcsUrl: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly requireAuthnRequestSignature: Locator;
	readonly saveButton: Locator;
	readonly sessionIdleTimeout: Locator;
	readonly sessionMaximumAge: Locator;
	readonly signMetadata: Locator;
	readonly sslRequired: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.authnRequestSigningAllowsDynamicAcsUrl = page.getByText(
			'Authn Request Signing Allows'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.requireAuthnRequestSignature = page.getByText(
			'Require Authn Request'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.sessionIdleTimeout = page.getByLabel('Session Idle Timeout');
		this.sessionMaximumAge = page.getByLabel('Session Maximum Age');
		this.signMetadata = page.getByText('Sign Metadata?');
		this.sslRequired = page.getByText('SSL Required');
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async goTo(forceReload = false) {
		if (forceReload) {
			await this.globalMenuPage.goToHome();
		}

		await this.globalMenuPage.goToControlPanel('SAML Admin');
		await this.page
			.getByRole('tab', {exact: true, name: 'Identity Provider'})
			.click();

		await this.signMetadata.waitFor();
	}
}
