/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {SCIMConfigurationPage} from '../../pages/scim-configuraiton-web/SCIMConfigurationPage';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-96845': true,
	}),
	loginTest()
);

const RESET_SCIM_HELP_TEXT =
	'All SCIM Client related data and generated OAuth 2 tokens will be ' +
	'removed. This is necessary to configure a new SCIM Client.';

test('smoke: test SCIM configuration options', async ({page}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'test');

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

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC2 TC2: Reset SCIM Client provisioning data button description is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

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

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.resetClientData();

	await page.waitForTimeout(2000);

	await expect(scimConfigurationPage.accessTokenField).toBeEmpty();

	await expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEmpty();

	await expect(scimConfigurationPage.matcherField).toHaveValue('');
});

test('LPD-23255 AC3 TC4: Verify that clicking the “Reset SCIM Client provisioning data“ button revokes the generated OAuth2 token and deletes the OAuth2 Application.', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();
	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	const accessToken =
		await scimConfigurationPage.accessTokenField.inputValue();

	const apiHelper = new ApiHelpers(page);

	const authorizedResponse = await apiHelper.scim.getUsers(accessToken);
	expect(authorizedResponse.status()).toBe(200);

	const applicationsMenuPage = new ApplicationsMenuPage(page);

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

	const unauthorizedResponse = await apiHelper.scim.getUsers(accessToken);
	expect(unauthorizedResponse.status()).toBe(401);

	await applicationsMenuPage.goToOauth2Administration();
	await page.waitForTimeout(1000);

	expect(await scimOAuthClientRow).not.toBeVisible();
});

test('LPD-23255 AC3 TC5: Verify that clicking the “Reset SCIM Client provisioning data“ button unbinds users', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	const randomNumber = getRandomInt();

	const newUser = {
		active: true,
		emails: [
			{
				primary: true,
				type: 'default',
				value: `able${randomNumber}@liferay.com`,
			},
		],
		name: {
			familyName: `Baker ${randomNumber}`,
			givenName: `Able ${randomNumber}`,
		},
		userName: `able${randomNumber}.baker`,
	};

	const apiHelper = new ApiHelpers(page);

	await apiHelper.scim.postUser(newUser);

	const response = await (await apiHelper.scim.getUsers()).text();

	expect(response).toContain('"totalResults":1');

	await scimConfigurationPage.resetClientData();
	await page.waitForTimeout(2000);

	const emptyResponse = await (await apiHelper.scim.getUsers()).text();

	expect(emptyResponse).toContain('"totalResults":0');
});

test('LPD-23255 AC3 TC6: Verify that clicking the “Reset SCIM Client provisioning data“ button unbinds user groups.', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	const randomNumber = getRandomInt();

	const newGroup = {
		displayName: `Foo${randomNumber}`,
	};

	const apiHelper = new ApiHelpers(page);

	await apiHelper.scim.postGroup(newGroup);

	const response = await (await apiHelper.scim.getGroups()).text();

	expect(response).toContain('"totalResults":1');

	await scimConfigurationPage.resetClientData();
	await page.waitForTimeout(1000);

	const emptyResponse = await (await apiHelper.scim.getGroups()).text();

	expect(emptyResponse).toContain('"totalResults":0');
});

test('LPD-23255 AC4 TC7: Verify that Name field is disabled when SCIM is configured', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEditable();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');
	await page.waitForTimeout(1000);

	expect(scimConfigurationPage.oAuth2ApplicationNameField).not.toBeEditable();

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC5 TC8: Verify that the Name field is enabled when scim client data is reset', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await page.waitForTimeout(1000);

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	expect(scimConfigurationPage.oAuth2ApplicationNameField).not.toBeEditable();

	await scimConfigurationPage.resetClientData();
	await page.waitForTimeout(1000);

	expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEditable();
});
