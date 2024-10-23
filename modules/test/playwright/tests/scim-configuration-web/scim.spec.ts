/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {userGroupsPageTest} from '../../fixtures/userGroupsPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {liferayConfig} from '../../liferay.config';
import {VirtualInstancesPage} from '../../pages/portal-instances-web/VirtualInstancesPage';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {SCIMConfigurationPage} from '../../pages/scim-configuraiton-web/SCIMConfigurationPage';
import {getRandomInt} from '../../utils/getRandomInt';
import performLogin, {performLogout} from '../../utils/performLogin';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-96845': true,
	}),
	loginTest(),
	applicationsMenuPageTest,
	serverAdministrationPageTest,
	userGroupsPageTest,
	usersAndOrganizationsPagesTest
);

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

const RESET_SCIM_HELP_TEXT =
	'All SCIM Client related data and generated OAuth 2 tokens will be ' +
	'removed. This is necessary to configure a new SCIM Client.';

test('smoke: test SCIM configuration options', async ({page}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

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

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC2 TC2: Reset SCIM Client provisioning data button description is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

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

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.resetClientData();

	await expect(scimConfigurationPage.accessTokenField).toBeHidden();

	await expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEmpty();

	await expect(scimConfigurationPage.matcherField).toHaveValue('');
});

test('LPD-23255 AC3 TC4: Verify that clicking the “Reset SCIM Client provisioning data“ button revokes the generated OAuth2 token and deletes the OAuth2 Application.', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	const accessToken =
		await scimConfigurationPage.accessTokenField.inputValue();

	const apiHelper = new ApiHelpers(page);

	const authorizedResponse =
		await apiHelper.scim.getUsersWithOAuth(accessToken);
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

	await scimConfigurationPage.resetClientData();

	const unauthorizedResponse =
		await apiHelper.scim.getUsersWithOAuth(accessToken);
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

	const emptyResponse = await (await apiHelper.scim.getUsers()).text();

	expect(emptyResponse).toContain('"totalResults":0');
});

test('LPD-23255 AC3 TC6: Verify that clicking the “Reset SCIM Client provisioning data“ button unbinds user groups.', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

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

	const emptyResponse = await (await apiHelper.scim.getGroups()).text();

	expect(emptyResponse).toContain('"totalResults":0');
});

test('LPD-23255 AC4 TC7: Verify that Name field is disabled when SCIM is configured', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEditable();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	expect(scimConfigurationPage.oAuth2ApplicationNameField).not.toBeEditable();

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC5 TC8: Verify that the Name field is enabled when scim client data is reset', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	expect(scimConfigurationPage.oAuth2ApplicationNameField).not.toBeEditable();

	await scimConfigurationPage.resetClientData();

	expect(scimConfigurationPage.oAuth2ApplicationNameField).toBeEditable();
});

test('LPD-33284 verify that post and get users requests work with oauth token', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	const accessToken =
		await scimConfigurationPage.accessTokenField.inputValue();

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

	await apiHelper.scim.postUserWithOAuth(newUser, accessToken);

	const response = await (
		await apiHelper.scim.getUsersWithOAuth(accessToken)
	).text();

	expect(response).toContain('"totalResults":1');

	await scimConfigurationPage.resetClientData();
});

test('LPD-33284 verify that post and get groups requests work with oauth token', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	const accessToken =
		await scimConfigurationPage.accessTokenField.inputValue();

	const randomNumber = getRandomInt();

	const newGroup = {
		displayName: `Foo${randomNumber}`,
	};

	const apiHelper = new ApiHelpers(page);

	await apiHelper.scim.postGroupWithOAuth(newGroup, accessToken);

	const response = await (
		await apiHelper.scim.getGroupsWithOAuth(accessToken)
	).text();

	expect(response).toContain('"totalResults":1');

	await scimConfigurationPage.resetClientData();
});

test('LPS-190119 (TC-2 & TC-5). Admin User can Generate and Revoke SCIM Access Tokens on a new Virtual Instance.', async ({
	browser,
	page,
}) => {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.addNewVirtualInstance(
		DEFAULT_VIRTUAL_INSTANCE_NAME
	);

	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`;

	const newPage = await browser.newPage({
		baseURL: `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`,
	});

	await performLogin(
		newPage,
		'test',
		'?p_p_id=com_liferay_login_web_portlet_LoginPortlet&' +
			'p_p_state=maximized',
		`@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`
	);

	const scimConfigurationPage = new SCIMConfigurationPage(newPage);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	await scimConfigurationPage.revokeToken();

	await performLogout(newPage);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	await virtualInstancesPage.deleteVirtualInstance(
		DEFAULT_VIRTUAL_INSTANCE_NAME
	);
});

test('LPD-34644: Check if the token expiration warning message appears in the SCIM configuration UI.', async ({
	applicationsMenuPage,
	page,
	serverAdministrationPage,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	await scimConfigurationPage.generateToken();

	// Execute script to change the expiration date of the SCIM client access token to 10 days from current day

	await applicationsMenuPage.goToServerAdministration();

	const script = `
		import com.liferay.portal.kernel.dao.orm.QueryUtil;
		import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
		import com.liferay.portal.kernel.util.Time;
		import com.liferay.oauth2.provider.model.OAuth2Application;
		import com.liferay.oauth2.provider.model.OAuth2Authorization;
		import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil;
		import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalServiceUtil;
		import java.util.Date;
		import java.util.List;
		OAuth2Application oAuth2Application =
			OAuth2ApplicationLocalServiceUtil.getOAuth2Application(
			CompanyThreadLocal.getCompanyId(), "SCIM_test-scim-client");
		List<OAuth2Authorization> oAuth2Authorizations =
			OAuth2AuthorizationLocalServiceUtil.getOAuth2Authorizations(
			oAuth2Application.getOAuth2ApplicationId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		OAuth2Authorization oAuth2Authorization = oAuth2Authorizations.get(0);
		oAuth2Authorization.setAccessTokenExpirationDate(new Date(System.currentTimeMillis() + (Time.DAY * 10)));
		OAuth2AuthorizationLocalServiceUtil.updateOAuth2Authorization(oAuth2Authorization);
	`;

	await serverAdministrationPage.executeScript(script);

	await scimConfigurationPage.goTo();

	await expect(scimConfigurationPage.alertMessage).toBeVisible();

	await scimConfigurationPage.resetClientData();
});

test('LPD-37452 verify expando field is not visible for user added to SCIM', async ({
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

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

	await usersAndOrganizationsPage.goToUsers(true);

	await (
		await usersAndOrganizationsPage.usersTableRowLink(newUser.userName)
	).click();

	await expect(
		await editUserPage.customField('scimClientId')
	).not.toBeVisible();

	await scimConfigurationPage.goTo();
	await scimConfigurationPage.resetClientData();
});

test('LPD-37452 verify expando field is not visible for group added to SCIM', async ({
	page,
	userGroupsPage,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('email', 'Test SCIM Client');

	const randomNumber = getRandomInt();

	const newGroup = {
		displayName: `Foo${randomNumber}`,
	};

	const apiHelper = new ApiHelpers(page);

	await apiHelper.scim.postGroup(newGroup);

	await userGroupsPage.goto(true);

	await (
		await userGroupsPage.userGroupsTableRowActions(newGroup.displayName)
	).click();

	await userGroupsPage.editUserGroupMenuItem.click();

	await expect(await page.getByLabel('Scimclientid')).not.toBeVisible();

	await scimConfigurationPage.goTo();
	await scimConfigurationPage.resetClientData();
});
