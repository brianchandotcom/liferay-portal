/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {SCIMConfigurationPage} from '../../pages/scim-configuraiton-web/SCIMConfigurationPage';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(loginTest());

const RESET_SCIM_HELP_TEXT =
	'All the current SCIM Client related data and the generated OAuth 2 tokens is removed. This is necessary for being able to configure a new SCIM Client.';

test('smoke: test SCIM configuration options', async ({page}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('test', 'email');

	await scimConfigurationPage.generateToken();

	await scimConfigurationPage.revokeToken();

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC1 TC1: Reset SCIM Client provisioning data button is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC2 TC2: Reset SCIM Client provisioning data button description is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	const tooltip = page.getByLabel(RESET_SCIM_HELP_TEXT).locator('use');

	expect(await tooltip).toBeDefined();

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC3 TC3: Verify that clicking the “Reset SCIM Client provisioning data“ button clears the information in the SCIM page', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	await scimConfigurationPage.resetClientData();

	const oAuth2ApplicationNameField = page.getByLabel(
		'OAuth 2 Application Name'
	);

	const matcherField = page.getByLabel('Matcher Field');

	const accessTokenField = page.getByLabel('Access Token', {exact: true});

	await page.waitForTimeout(1000);

	await expect(oAuth2ApplicationNameField).toBeEmpty();

	await expect(matcherField).toHaveValue('');

	await expect(accessTokenField).toBeEmpty();
});

test('LPD-23255 AC3 TC4: Verify that clicking the “Reset SCIM Client provisioning data“ button revokes the generated OAuth2 token and deletes the OAuth2 Application.', async ({
	page,
}) => {
	const apiHelper = new ApiHelpers(page);

	const scimConfigurationPage = new SCIMConfigurationPage(page);

	const applicationsMenuPage = new ApplicationsMenuPage(page);

	await scimConfigurationPage.goTo();
	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	await scimConfigurationPage.generateToken();

	const accessToken = await page
		.getByLabel('Access Token', {exact: true})
		.inputValue();

	const authorizedResponse = await apiHelper.scim.getUsers(
		accessToken,
		false
	);
	expect(authorizedResponse.status()).toBe(200);

	await applicationsMenuPage.goToOauth2Administration();
	await page.waitForTimeout(1000);

	const scimOAuthClientRow = await page.getByRole('cell', {
		exact: true,
		name: 'Test SCIM Client',
	});
	expect(await scimOAuthClientRow).toBeVisible();

	await scimConfigurationPage.goTo();
	await page.waitForTimeout(1000);

	await scimConfigurationPage.resetClientData();

	const unauthorizedResponse = await apiHelper.scim.getUsers(
		accessToken,
		false
	);
	expect(unauthorizedResponse.status()).toBe(401);

	await applicationsMenuPage.goToOauth2Administration();
	await page.waitForTimeout(1000);

	expect(await scimOAuthClientRow).not.toBeVisible();
});

test('LPD-23255 AC3 TC5: Verify that clicking the “Reset SCIM Client provisioning data“ button unbinds users', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	const apiHelper = new ApiHelpers(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	const randInt = getRandomInt();

	const newUser = {
		active: true,
		emails: [
			{
				primary: true,
				type: 'default',
				value: `able${randInt}@liferay.com`,
			},
		],
		name: {
			familyName: `Baker ${randInt}`,
			givenName: `Able ${randInt}`,
		},
		userName: `able${randInt}.baker`,
	};

	await apiHelper.scim.postUser(newUser);

	const response = await (await apiHelper.scim.getUsers()).text();

	expect(response).toContain(`"totalResults":1`);

	await scimConfigurationPage.resetClientData();
	await page.waitForTimeout(1000);

	const emptyResponse = await (await apiHelper.scim.getUsers()).text();

	expect(emptyResponse).toContain(`"totalResults":0`);
});

test('LPD-23255 AC3 TC6: Verify that clicking the “Reset SCIM Client provisioning data“ button unbinds user groups.', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	const apiHelper = new ApiHelpers(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	const randInt = getRandomInt();

	const newGroup = {
		displayName: `Foo${randInt}`,
	};

	await apiHelper.scim.postGroup(newGroup);

	const response = await (await apiHelper.scim.getGroups()).text();

	expect(response).toContain(`"totalResults":1`);

	await scimConfigurationPage.resetClientData();
	await page.waitForTimeout(1000);

	const emptyResponse = await (await apiHelper.scim.getGroups()).text();

	expect(emptyResponse).toContain(`"totalResults":0`);
});
