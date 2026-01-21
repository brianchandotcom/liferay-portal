/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {customFieldsPagesTest} from '../../../fixtures/customFieldsPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {TCustomField} from '../../../helpers/CustomFieldTypesHelper';
import {liferayConfig} from '../../../liferay.config';
import {OpenIdInstanceSettingsPage} from '../../../pages/portal-settings-authentication-openid-connect-web/OpenIdInstanceSettingsPage';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';
import {utilityPagesPage} from '../../login-web/main/fixtures/utilityPageTest';
import {openIdConfig} from './config';
import {openIdSettingsPagesTest} from './fixtures/openIdSettingsPagesTest';
import {CustomClaim} from './helpers/CustomClaimHelper';

let providerName: string;
let resetLoginPrompt: boolean;

const test = mergeTests(
	openIdSettingsPagesTest,
	featureFlagsTest({
		'LPD-57332': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	utilityPagesPage,
	customFieldsPagesTest,
	pagesPagesTest
);

async function setupOpenIdConnection(
	openIDInstanceSettingsPage: OpenIdInstanceSettingsPage,
	customClaim?: CustomClaim,
	matcherField?: string
) {
	await openIDInstanceSettingsPage.goto();

	await openIDInstanceSettingsPage.enableOpenIDConnect();

	providerName = getRandomString();

	await openIDInstanceSettingsPage.addOpenIDConnectProviderConnectionConfiguration(
		openIdConfig.openIdProvider,
		providerName,
		customClaim,
		matcherField
	);
}

test.afterEach(
	async ({
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		page,
		utilityPagesPage,
	}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		if (page.getByRole('button', {name: 'Sign In'}).isVisible) {
			await performLoginViaApi({page, screenName: 'test'});
		}

		await utilityPagesPage.goto();

		await utilityPagesPage.markAsDefault('Sign In');

		if (providerName) {
			await openIDInstanceSettingsPage.goto();

			await openIDInstanceSettingsPage.disableOpenIDConnect();

			await openIDInstanceSettingsPage.removeOpenIDConnectProviderConnectionConfiguration(
				providerName
			);

			providerName = null;
		}

		if (resetLoginPrompt) {
			await loginInstanceSettingsPage.goto();

			await loginInstanceSettingsPage.resetLoginPrompt();

			resetLoginPrompt = false;
		}
	}
);

test.beforeEach(async ({utilityPagesPage}) => {
	await utilityPagesPage.goto();

	await utilityPagesPage.unmarkAsDefault('Sign In');
});

test.describe('OpenID connect link', () => {
	test('is visible on sign-in page when OpenID connection is enabled on NOT an utility page', async ({
		openIDInstanceSettingsPage,
		page,
	}) => {
		await setupOpenIdConnection(openIDInstanceSettingsPage);

		await performLogout(page);

		await page
			.getByRole('button', {name: 'Search'})
			.waitFor({state: 'visible'});

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await expect(page.getByText(openIdConfig.openIdLink)).toBeVisible();
	});

	test('when openId connection is enabled on an utility page, then openId connect link is hidden on sign in page', async ({
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		page,
		site,
	}) => {
		await loginInstanceSettingsPage.goto();

		await loginInstanceSettingsPage.enableLoginPrompt();

		resetLoginPrompt = true;

		await setupOpenIdConnection(openIDInstanceSettingsPage);

		await performLogout(page);

		await page.goto('/web' + site.friendlyUrlPath);

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await expect(page.getByText(openIdConfig.openIdLink)).toBeHidden();
	});
});

test.describe('OpenID Connect custom claims', () => {
	test('can set custom claims for the oidc configuration', async ({
		addCustomFieldPage,
		openIDInstanceSettingsPage,
		viewAttributesPage,
	}) => {
		const expandoColumnName = getRandomString();

		const customField: TCustomField = {
			fieldName: expandoColumnName,
			fieldType: 'inputField',
			resource: 'User',
		};

		await addCustomFieldPage.addCustomField(customField);

		const customClaim: CustomClaim = {
			expandoColumnName,
			oidcProviderCustomClaim: getRandomString(),
		};

		await setupOpenIdConnection(openIDInstanceSettingsPage, customClaim);

		await viewAttributesPage.deleteCustomField(expandoColumnName, 'User');
	});
});

test.describe('LPD-68521 OpenID Connect Secure Matching', () => {
	test('can choose a matcherField', async ({openIDInstanceSettingsPage}) => {
		await setupOpenIdConnection(
			openIDInstanceSettingsPage,
			null,
			'Screen Name'
		);
	});
});
