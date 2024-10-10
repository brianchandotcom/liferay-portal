/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {OpenIdInstanceSettingsPage} from '../../pages/portal-settings-authentication-openid-connect-web/OpenIdInstanceSettingsPage';
import {OpenIdSystemSettingsPage} from '../../pages/portal-settings-authentication-openid-connect-web/OpenIdSystemSettingsPage';
import getRandomString from '../../utils/getRandomString';
import performLogin from '../../utils/performLogin';
import {utilityPagesPage} from '../login-web/fixtures/utilityPageTest';
import {openIdConfig} from './config';
import {openIdSettingsPagesTest} from './fixtures/openIdSettingsPagesTest';

let providerName: string;
let utilityPageTitle: string;

const test = mergeTests(
	openIdSettingsPagesTest,
	featureFlagsTest({
		'LPD-6378': true,
	}),
	utilityPagesPage
);

async function setupOpenIdConnection(
	openIDInstanceSettingsPage: OpenIdInstanceSettingsPage,
	openIDSystemSettingsPage: OpenIdSystemSettingsPage
) {
	await openIDSystemSettingsPage.goTo();
	await openIDSystemSettingsPage.enableOpenIDConnect();
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
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		openIDSystemSettingsPage,
		page,
		utilityPagesPage,
	}) => {
		await performLogin(page, 'test');

		if (providerName) {
			await openIDSystemSettingsPage.goTo();
			await openIDSystemSettingsPage.disableOpenIDConnect();
			await openIDInstanceSettingsPage.goto();
			await openIDInstanceSettingsPage.disableOpenIDConnect();
			await openIDInstanceSettingsPage.removeOpenIDConnectProviderConnectionConfiguration(
				providerName
			);
			providerName = null;
		}

		if (utilityPageTitle) {
			await utilityPagesPage.goto();
			await utilityPagesPage.deletePage(utilityPageTitle);
			utilityPageTitle = null;
			await loginInstanceSettingsPage.goto();
			await loginInstanceSettingsPage.resetLoginPrompt();
		}
	}
);

test.describe('OpenID connect link', () => {
	test('is visible on sign-in page when OpenID connection is enabled on NOT an utility page', async ({
		openIDInstanceSettingsPage,
		openIDSystemSettingsPage,
		page,
	}) => {
		await performLogin(page, 'test');
		await setupOpenIdConnection(
			openIDInstanceSettingsPage,
			openIDSystemSettingsPage
		);
		await page.getByLabel('Test Test User Profile').click();
		await page.getByRole('menuitem', {name: 'Sign Out'}).click();
		await page
			.getByRole('button', {name: 'Search'})
			.waitFor({state: 'visible'});
		await page.getByRole('button', {name: 'Sign In'}).click();
		await expect(page.getByText(openIdConfig.openIdLink)).toBeVisible();
	});

	test('when openId connection is enabled on an utility page, then openId connect link is hidden on sign in page', async ({
		loginInstanceSettingsPage,
		openIDInstanceSettingsPage,
		openIDSystemSettingsPage,
		page,
		utilityPagesPage,
	}) => {
		await performLogin(page, 'test');
		await loginInstanceSettingsPage.goto();
		await loginInstanceSettingsPage.enableLoginPrompt();
		utilityPageTitle = getRandomString();
		await utilityPagesPage.goto();
		await utilityPagesPage.add(utilityPageTitle, 'Sign In');
		await page.getByText(utilityPageTitle).waitFor({state: 'visible'});
		await utilityPagesPage.markAsDefault(utilityPageTitle);
		await setupOpenIdConnection(
			openIDInstanceSettingsPage,
			openIDSystemSettingsPage
		);
		await page.getByLabel('Test Test User Profile').click();
		await page.getByRole('menuitem', {name: 'Sign Out'}).click();
		await page.goto(openIdConfig.loginPortletLink);
		await expect(page.getByText(openIdConfig.openIdLink)).toBeHidden();
	});
});
