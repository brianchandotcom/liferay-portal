/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {customFieldsPagesTest} from '../../../fixtures/customFieldsPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {oauthClientAdminPagesTest} from '../../../fixtures/oauthClientAdminPagesTest';
import {TCustomField} from '../../../helpers/CustomFieldTypesHelper';
import getRandomString from '../../../utils/getRandomString';

let authServerLocalMetadataCreated = false;
let oauthClientsCreated = false;
let userCustomFieldsCreated = false;

const test = mergeTests(
	customFieldsPagesTest,
	oauthClientAdminPagesTest,
	featureFlagsTest({
		'LPD-49855': {enabled: true},
		'LPD-63415': {enabled: true},
	}),
	loginTest()
);

test.afterEach(
	async ({
		AuthServerLocalMetadatasPage,
		OAuthClientClientsPage,
		viewAttributesPage,
	}) => {
		if (authServerLocalMetadataCreated) {
			await AuthServerLocalMetadatasPage.goTo();

			await AuthServerLocalMetadatasPage.deleteAuthServerLocalMetadata();

			authServerLocalMetadataCreated = false;
		}

		if (userCustomFieldsCreated) {
			await viewAttributesPage.deleteCustomFields('User');
		}

		if (oauthClientsCreated) {
			await OAuthClientClientsPage.goTo();

			await OAuthClientClientsPage.deleteOAuthClientEntries();

			oauthClientsCreated = false;
		}
	}
);

test.describe('Enable Configuration of oauth-authorization-server Well-Known URIs in the OAuthClient Portlet', () => {
	test(
		'Create an oauth-authorization-server and validate',
		{tag: '@LPD-67473'},
		async ({AuthServerLocalMetadatasPage}) => {
			await AuthServerLocalMetadatasPage.goTo();

			await AuthServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'',
				'The Issuer field is required.'
			);

			await AuthServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'https://localhost.com'
			);

			authServerLocalMetadataCreated = true;

			await AuthServerLocalMetadatasPage.addAuthServerLocalMetadata(
				'https://localhost.com',
				'Duplicate'
			);

			if (
				await AuthServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.isHidden()
			) {
				await AuthServerLocalMetadatasPage.applicationsMenuPage.goToOAuthClientAdministration();
			}

			await AuthServerLocalMetadatasPage.authServerLocalMetadataTab.click();
			await AuthServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.click();
			await expect(
				await AuthServerLocalMetadatasPage.page.getByRole('link', {
					name: 'https://localhost.com/o/.well-known/oauth-authorization-server',
				})
			).toBeVisible();

			await AuthServerLocalMetadatasPage.openIdConfigurationTab.click();

			await expect(
				await AuthServerLocalMetadatasPage.page.getByRole('link', {
					name: 'https://localhost.com/.well-known/openid-configuration/1B2M2Y8AsgTpgAmY7PhCfg**/local',
				})
			).toBeVisible();

			await AuthServerLocalMetadatasPage.oAuthAuthorizatoinServerTab.click();
		}
	);
});

test.describe('oAuth Client Admin Clients', () => {
	test(
		'Configure OIDC custom claims and matcher field',
		{tag: '@LPD-67470'},
		async ({OAuthClientClientsPage, addCustomFieldPage}) => {
			const customField: TCustomField = {
				fieldName: getRandomString(),
				fieldType: 'inputField',
				resource: 'User',
			};

			await addCustomFieldPage.addCustomField(customField);

			userCustomFieldsCreated = true;

			await OAuthClientClientsPage.goTo();

			const customClaim: TCustomClaim = {
				expandoColumnName: customField.fieldName,
				oidcProviderCustomClaim: getRandomString(),
			};

			const infoJSON: TInfoJSON = {
				client_id: getRandomString(),
				client_secret: getRandomString(),
			};

			const oauthClientEntry: TOAuthClientEntry = {
				authServerWellKnownURI:
					'https://accounts.google.com/.well-known/openid-configuration',
				customClaims: [customClaim],
				infoJSON,
				matcherField: 'screenName',
			};

			expect(
				await OAuthClientClientsPage.addOAuthClientEntry(
					oauthClientEntry,
					'Missing screenName value at OpenId Connect User Information Mapper JSON'
				)
			).toBeNull();

			const user: TOIDCUser = {
				screenName: getRandomString(),
			};

			const oidcUserInfoMapperJSON: TOIDCUserInfoMapperJSON = {
				user,
			};

			oauthClientEntry.oidcUserInfoMapperJSON = oidcUserInfoMapperJSON;

			await OAuthClientClientsPage.goTo();

			const newOAuthClientEntry =
				await OAuthClientClientsPage.addOAuthClientEntry(
					oauthClientEntry
				);

			oauthClientsCreated = true;

			await OAuthClientClientsPage.goToEntry(oauthClientEntry);

			await expect(OAuthClientClientsPage.matcherField).toHaveValue(
				'screenName'
			);

			if (newOAuthClientEntry?.customClaims) {
				for (const customClaim of newOAuthClientEntry.customClaims) {
					await OAuthClientClientsPage.checkCustomClaimValues(
						customClaim
					);
				}
			}
		}
	);
});
