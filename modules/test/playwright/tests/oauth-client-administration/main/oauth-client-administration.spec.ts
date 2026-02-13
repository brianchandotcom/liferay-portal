/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {authServerLocalMetadatasPageTest} from '../../../fixtures/authServerLocalMetadatasPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(
	authServerLocalMetadatasPageTest,
	featureFlagsTest({
		'LPD-63415': {enabled: true},
	}),
	loginTest()
);

test.afterEach(async ({authServerLocalMetadatasPage}) => {
	await authServerLocalMetadatasPage.goTo();

	await authServerLocalMetadatasPage.deleteAuthServerLocalMetadata();
});

test.describe('Enable Configuration of oauth-authorization-server Well-Known URIs in the OAuthClient Portlet', () => {
	test(
		'Create an oauth-authorization-server and validate',
		{tag: '@LPD-67473'},
		async ({authServerLocalMetadatasPage}) => {
			await authServerLocalMetadatasPage.goTo();

			await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'',
				'The Issuer field is required.'
			);

			await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'https://localhost.com'
			);

			await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'https://localhost.com',
				'Duplicate'
			);

			if (
				await authServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.isHidden()
			) {
				await authServerLocalMetadatasPage.applicationsMenuPage.goToOAuthClientAdministration();
			}

			await authServerLocalMetadatasPage.authServerLocalMetadataTab.click();
			await authServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.click();
			await expect(
				await authServerLocalMetadatasPage.page.getByRole('link', {
					name: 'https://localhost.com/o/.well-known/oauth-authorization-server',
				})
			).toBeVisible();

			await authServerLocalMetadatasPage.openIdConfigurationTab.click();

			await expect(
				await authServerLocalMetadatasPage.page.getByRole('link', {
					name: 'https://localhost.com/.well-known/openid-configuration/1B2M2Y8AsgTpgAmY7PhCfg**/local',
				})
			).toBeVisible();

			await authServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.click();
		}
	);
});
