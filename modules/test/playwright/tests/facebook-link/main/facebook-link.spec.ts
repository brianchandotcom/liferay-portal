/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {liferayConfig} from '../../../liferay.config';
import {FacebookInstanceSettingsPage} from '../../../pages/portal-settings-authentication-facebook-connect-web/FacebookInstanceSettingsPage';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';
import {utilityPagesPage} from '../../login-web/main/fixtures/utilityPageTest';
import {facebookConfig} from './config';
import {facebookSettingsPagesTest} from './fixtures/facebookSettingsPagesTest';

let markAsDefault: boolean;

const test = mergeTests(
	facebookSettingsPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	utilityPagesPage,
	pagesPagesTest
);

async function setupFacebookConnection(
	facebookInstanceSettingsPage: FacebookInstanceSettingsPage,
	setAsDefault: boolean
) {
	await facebookInstanceSettingsPage.goto();

	await facebookInstanceSettingsPage.enableFacebookConnect();

	markAsDefault = setAsDefault;
}

test.afterEach(
	async ({facebookInstanceSettingsPage, page, utilityPagesPage}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		if (await page.getByRole('button', {name: 'Sign In'}).isVisible) {
			await performLoginViaApi({page, screenName: 'test'});
		}

		await facebookInstanceSettingsPage.goto();

		await facebookInstanceSettingsPage.disableFacebookConnect();

		if (markAsDefault) {
			await utilityPagesPage.goto();

			await utilityPagesPage.markAsDefault('Sign In');
			markAsDefault = false;
		}
	}
);

test.describe('Facebook connect link', () => {
	test('is visible on sign-in page when Facebook connection is enabled on an utility page. As default the Sign in page is a Utility Page', async ({
		facebookInstanceSettingsPage,
		page,
	}) => {
		await setupFacebookConnection(facebookInstanceSettingsPage, false);

		await performLogout(page);

		await page
			.getByRole('button', {name: 'Search'})
			.waitFor({state: 'visible'});

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await expect(page.getByText(facebookConfig.facebookLink)).toBeVisible();
	});

	test('when Facebook connection is enabled on an utility page, then Facebook connect link is shown on sign in page', async ({
		facebookInstanceSettingsPage,
		page,
		utilityPagesPage,
	}) => {
		await setupFacebookConnection(facebookInstanceSettingsPage, true);

		await utilityPagesPage.goto();

		await utilityPagesPage.unmarkAsDefault('Sign In');

		await performLogout(page);

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await expect(page.getByText(facebookConfig.facebookLink)).toBeVisible();
	});
});
