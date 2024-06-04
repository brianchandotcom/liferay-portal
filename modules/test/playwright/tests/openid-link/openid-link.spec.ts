/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests, test} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import performLogin from '../../utils/performLogin';
import {utilityPagesPage} from '../login-web/fixtures/utilityPageTest';
import {openIdConfig} from './config';

let providerName: string;

test('when openId connection is enabled on NOT an utility page, then openId connect link is visible on sign in page', async ({
	page,
}) => {
	await performLogin(page, 'test');
	await setupOpenIdConnection(page);
	await expect(page.getByText(openIdConfig.openIdLink)).toBeVisible();
	await performLogin(page, 'test');
});

export const testOnUtilityPage = mergeTests(
	featureFlagsTest({
		'LPD-6378': true,
	}),
	loginTest(),
	utilityPagesPage
);

testOnUtilityPage(
	'when openId connection is enabled on an utility page, then openId connect link is hidden on sign in page',
	async ({loginInstanceSettingsPage, page, utilityPagesPage}) => {
		await loginInstanceSettingsPage.goto();
		await loginInstanceSettingsPage.enableLoginPrompt();

		await utilityPagesPage.goto();

		const title = getRandomString();

		await utilityPagesPage.add(title, 'Sign In', 2000);
		await expect(page.getByText(title)).toBeVisible();
		await utilityPagesPage.markAsDefault(title);

		await setupOpenIdConnection(page);

		await page.goto(openIdConfig.loginPortletLink);
		await expect(page.getByText(openIdConfig.openIdLink)).toBeHidden();

		await performLogin(page, 'test');

		await utilityPagesPage.goto();
		await utilityPagesPage.deletePage(title);

		await loginInstanceSettingsPage.goto();
		await loginInstanceSettingsPage.disableLoginPrompt();
	}
);

async function setupOpenIdConnection(page: Page) {
	await page.getByLabel('Open Applications Menu').click();
	await page.getByRole('tab', {name: 'Control Panel'}).click();
	await page.getByRole('menuitem', {name: 'Instance Settings'}).click();
	await page.getByRole('link', {name: 'SSO'}).click();
	await page
		.getByRole('menuitem', {exact: true, name: 'OpenID Connect'})
		.click();
	await page.getByText(' Enabled ').check();
	await page.getByRole('button', {name: 'Save'}).click();
	await page
		.getByRole('menuitem', {name: 'OpenID Connect Provider Connection'})
		.click();
	await page.getByRole('link', {name: 'Add'}).click();
	await page.getByLabel('Provider Name').click();
	providerName = getRandomString();
	await page.getByLabel('Provider Name').fill(providerName);
	await page.getByLabel('Discovery Endpoint', {exact: true}).click();
	await page
		.getByLabel('Discovery Endpoint', {exact: true})
		.fill(openIdConfig.openIdProvider);
	await page.getByLabel('OpenID Connect Client ID').click();
	await page.getByLabel('OpenID Connect Client ID').fill(getRandomString());
	await page.getByLabel('OpenID Connect Client Secret').click();
	await page
		.getByLabel('OpenID Connect Client Secret')
		.fill(getRandomString());
	await page.getByRole('button', {name: 'Save'}).click();
	await page.waitForTimeout(3000);
	await page.getByLabel('Test Test User Profile').click();
	await page.getByRole('menuitem', {name: 'Sign Out'}).click();
	await page
		.getByRole('button', {name: 'Search'})
		.waitFor({state: 'visible'});
	await page.getByRole('button', {name: 'Sign In'}).click();
}

test.afterEach(async ({page}) => {
	await page.getByLabel('Open Applications Menu').click();
	await page.getByRole('tab', {name: 'Control Panel'}).click();
	await page.getByRole('menuitem', {name: 'Instance Settings'}).click();
	await page.getByRole('link', {name: 'SSO'}).click();
	await page
		.getByRole('menuitem', {exact: true, name: 'OpenID Connect'})
		.click();
	await page.getByText(' Enabled ').uncheck();
	await page.getByRole('button', {name: 'Save'}).click();

	if (providerName) {
		await page
			.getByRole('menuitem', {name: 'OpenID Connect Provider Connection'})
			.click();
		await page.waitForTimeout(3000);
		await page
			.getByRole('row', {name: providerName + ' Actions'})
			.getByTitle('Actions')
			.click();
		await page.getByText('Delete').click();
		await expect(
			page.getByText('Success:Your request completed successfully.')
		).toBeVisible();
	}
});
