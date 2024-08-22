/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {IdentityProviderPage} from '../../../pages/saml-web/IdentityProviderPage';

export type TIdentityProvider = {
	authnRequestSigningAllowsDynamicAcsUrl?: boolean;
	requireAuthnRequestSignature?: boolean;
	sessionIdleTimeout?: string;
	sessionMaximumAge?: string;
	signMetadata?: boolean;
	sslRequired?: boolean;
};

export async function configureIdentityProvider(
	page: Page,
	identityProvider: TIdentityProvider = {}
) {
	const identityProviderPage = await new IdentityProviderPage(page);

	await identityProviderPage.goTo();

	if (identityProvider.authnRequestSigningAllowsDynamicAcsUrl === false) {
		await identityProviderPage.authnRequestSigningAllowsDynamicAcsUrl.setChecked(
			false
		);
	}
	else {
		await identityProviderPage.authnRequestSigningAllowsDynamicAcsUrl.setChecked(
			true
		);
	}

	if (identityProvider.requireAuthnRequestSignature === false) {
		await identityProviderPage.requireAuthnRequestSignature.setChecked(
			false
		);
	}
	else {
		await identityProviderPage.requireAuthnRequestSignature.setChecked(
			true
		);
	}

	if (identityProvider.sessionIdleTimeout) {
		await identityProviderPage.sessionIdleTimeout.fill(
			identityProvider.sessionIdleTimeout
		);
	}
	else {
		await identityProviderPage.sessionIdleTimeout.fill('0');
	}

	if (identityProvider.sessionMaximumAge) {
		await identityProviderPage.sessionMaximumAge.fill(
			identityProvider.sessionMaximumAge
		);
	}
	else {
		await identityProviderPage.sessionMaximumAge.fill('0');
	}

	if (identityProvider.signMetadata === false) {
		await identityProviderPage.signMetadata.setChecked(false);
	}
	else {
		await identityProviderPage.signMetadata.setChecked(true);
	}

	if (identityProvider.sslRequired === true) {
		await identityProviderPage.sslRequired.setChecked(true);
	}
	else {
		await identityProviderPage.sslRequired.setChecked(false);
	}

	await identityProviderPage.saveButton.click();

	await identityProviderPage.successMessage.waitFor();
}
