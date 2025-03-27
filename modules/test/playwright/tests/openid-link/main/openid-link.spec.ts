/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {liferayConfig} from '../../../liferay.config';
import {OpenIdInstanceSettingsPage} from '../../../pages/portal-settings-authentication-openid-connect-web/OpenIdInstanceSettingsPage';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {utilityPagesPage} from '../../login-web/main/fixtures/utilityPageTest';
import {openIdConfig} from './config';
import {openIdSettingsPagesTest} from './fixtures/openIdSettingsPagesTest';

let providerName: string;
let site: Site;

const test = mergeTests(
	dataApiHelpersTest,
	openIdSettingsPagesTest,
	featureFlagsTest({
		'LPD-6378': {enabled: true},
	}),
	loginTest(),
	utilityPagesPage
);

async function setupOpenIdConnection(
	openIDInstanceSettingsPage: OpenIdInstanceSettingsPage
) {
	await openIDInstanceSettingsPage.goto();

	await openIDInstanceSettingsPage.enableOpenIDConnect();

	providerName = getRandomString();

	await openIDInstanceSettingsPage.AddOpenIDConnectProviderConnectionConfiguration(
		providerName,
		openIdConfig.openIdProvider
	);
}

test.afterEach(
	async ({
		apiHelpers,
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		page,
	}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		if (page.getByRole('button', {name: 'Sign In'}).isVisible) {
			await performLoginViaApi({page, screenName: 'test'});
		}

		if (providerName) {
			await openIDInstanceSettingsPage.goto();

			await openIDInstanceSettingsPage.disableOpenIDConnect();

			await openIDInstanceSettingsPage.removeOpenIDConnectProviderConnectionConfiguration(
				providerName
			);

			providerName = null;
		}

		if (site) {
			await loginInstanceSettingsPage.goto();

			await loginInstanceSettingsPage.resetLoginPrompt();

			expect(async () => {
				expect(
					await apiHelpers.headlessSite.deleteSite(site.id)
				).toBeOK();
			}).toPass();

			site = null;
		}
	}
);

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

		await page.getByRole('button', {name: 'Sign In'}).click();

		await expect(page.getByText(openIdConfig.openIdLink)).toBeVisible();
	});

	test('when openId connection is enabled on an utility page, then openId connect link is hidden on sign in page', async ({
		apiHelpers,
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		page,
	}) => {
		site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
			templateKey: 'com.liferay.site.initializer.welcome',
			templateType: 'site-initializer',
		});

		await loginInstanceSettingsPage.goto();

		await loginInstanceSettingsPage.enableLoginPrompt();

		await setupOpenIdConnection(openIDInstanceSettingsPage);

		await performLogout(page);

		await page.goto('/web' + site.friendlyUrlPath);

		await page.getByRole('button', {name: 'Sign In'}).click();

		await expect(page.getByText(openIdConfig.openIdLink)).toBeHidden();
	});
});
