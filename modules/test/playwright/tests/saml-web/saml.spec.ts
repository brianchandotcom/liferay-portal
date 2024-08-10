/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {searchAdminPageTest} from '../../fixtures/searchAdminPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {TCustomField, TInputField} from '../../helpers/CustomFieldTypesHelper';
import {
	DEFAULT_IDP_CONNECTION_VALUES,
	DEFAULT_SP_CONNECTION_VALUES,
	TIdpConnection,
	TSpConnection,
} from '../../helpers/SamlProviderConnectionHelper';
import {liferayConfig} from '../../liferay.config';
import {ViewAttributesPage} from '../../pages/expando-web/ViewAttributesPage';
import {AttributeMapping} from '../../pages/saml-web/IdentityProviderConnectionsPage';
import {SamlAdminPage} from '../../pages/saml-web/SamlAdminPage';
import {ServiceProviderConnectionsPage} from '../../pages/saml-web/ServiceProviderConnectionsPage';
import {EditUserPage} from '../../pages/users-admin-web/EditUserPage';
import {UsersAndOrganizationsPage} from '../../pages/users-admin-web/UsersAndOrganizationsPage';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {performLogout} from '../../utils/performLogin';
import {
	connectSpAndIdp,
	editIdentityProviderConnection,
	editServiceProviderConnection,
} from './utils/samlProviderConnectionUtil';
import {
	DEFAULT_IDP_NAME,
	DEFAULT_IDP_URL,
	DEFAULT_SP_NAME,
	DEFAULT_SP_URL,
	createCustomField,
	createIdpUser,
	deleteVirtualInstance,
	performSamlSafeAdminLogin,
	resetSamlKeystoreManagerTarget,
	setupSamlInstances,
	updateSamlKeystoreManagerTarget,
} from './utils/samlVirtualInstanceUtil';

export const test = mergeTests(
	loginTest(),
	searchAdminPageTest,
	usersAndOrganizationsPagesTest,
	virtualInstancesPagesTest
);

test('Create two virtual instances, one IdP and one SP, connect them, perform SP initiated SSO, perform SP initiated SLO', async ({
	browser,
	page,
}) => {

	// Set the Keystore Manager Target to Doc Lib, so we can store multiple
	// certificates in one instance

	await updateSamlKeystoreManagerTarget(
		page,
		'Document Library Keystore Manager'
	);

	await setupSamlInstances(browser, page);

	// Create a user with identical credentials on each instance

	const userAccount = await createIdpUser(browser, DEFAULT_IDP_NAME);

	// Perform SP initiated SSO

	const spInstancePage = await browser.newPage({
		baseURL: DEFAULT_SP_URL,
	});

	await spInstancePage.goto('/');

	const signInButton = await spInstancePage.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.click();

	// Verify user is redirected to the IdP instance

	await spInstancePage
		.getByText('Redirecting to your identity provider...')
		.waitFor({timeout: 30 * 1000});

	// Wait for redirection to complete, otherwise the expect clause will fail

	await spInstancePage
		.getByLabel('Email Address')
		.waitFor({timeout: 30 * 1000});

	// Verify user has been successfully redirected

	expect(await spInstancePage.url()).toContain(DEFAULT_IDP_URL);

	// Sign in

	await spInstancePage
		.getByLabel('Email Address')
		.fill(userAccount.emailAddress);
	await spInstancePage.getByLabel('Password').fill('test');
	await spInstancePage.getByLabel('Remember Me').check();
	await spInstancePage.getByRole('button', {name: 'Sign In'}).click();

	// Wait for authentication to complete, verify user is redirected back to SP

	await spInstancePage
		.getByTitle('User Profile Menu')
		.waitFor({timeout: 30 * 1000});

	expect(await spInstancePage.url()).toContain(DEFAULT_SP_URL);

	// Verify user has been imported to SP and logged in

	await expect(
		await spInstancePage.getByTitle('User Profile Menu')
	).toBeVisible();

	// Perform SP initiated SLO

	await performLogout(spInstancePage);

	await spInstancePage.waitForTimeout(8000);

	// Verify user has been logged out of SP and IdP

	await expect(
		await spInstancePage.getByRole('button', {name: 'Sign In'})
	).toBeVisible();

	await spInstancePage.goto(DEFAULT_IDP_URL);

	await spInstancePage
		.getByRole('button', {name: 'Sign In'})
		.waitFor({timeout: 30 * 1000});

	// Lastly, delete both virtual instances and reset the keystore target

	await deleteVirtualInstance(DEFAULT_IDP_NAME, page);

	await deleteVirtualInstance(DEFAULT_SP_NAME, page);

	await resetSamlKeystoreManagerTarget(page);
});

test('Create, edit, and delete a new virtual instance', async ({
	editVirtualInstancePage,
	searchAdminPage,
	virtualInstancesPage,
}) => {
	const name = getRandomString();

	await virtualInstancesPage.addNewVirtualInstance(name);

	const newName = getRandomString();

	await editVirtualInstancePage.editVirtualInstance(
		name,
		false,
		newName + '.com',
		'100',
		newName
	);

	// Reindex users so the correct number is present

	await searchAdminPage.goto();

	await searchAdminPage.goToIndexActionsTab();

	await searchAdminPage.reindexIndexActionsItem('User');

	await virtualInstancesPage.goto();

	expect(
		await virtualInstancesPage.page
			.getByRole('row')
			.getByText(name + ' ' + newName + ' ' + newName + '.com 1 100 No')
	).toBeVisible();

	await virtualInstancesPage.deleteVirtualInstance(name);
});

test('Create two virtual instances, one IdP and one SP, and verify Custom User Attributes', async ({
	browser,
	editUserPage,
	page,
	searchAdminPage,
	usersAndOrganizationsPage,
}) => {

	// Set the Keystore Manager Target to Doc Lib, so we can store multiple
	// certificates in one instance

	await updateSamlKeystoreManagerTarget(
		page,
		'Document Library Keystore Manager'
	);

	await setupSamlInstances(browser, page);

	// Create identical Custom Fields for both instances, except starting value

	const customFieldName = 'CustomField' + getRandomInt();

	const fieldValues: TInputField = {
		startingValue: 'idpStartingValue',
	};

	const customField: TCustomField = {
		fieldName: customFieldName,
		fieldType: 'inputField',
		fieldValues,
		resource: 'User',
	};

	await createCustomField(browser, customField, DEFAULT_IDP_NAME);

	fieldValues.startingValue = 'spStartingValue';

	customField.fieldValues = fieldValues;

	await createCustomField(browser, customField, DEFAULT_SP_NAME);

	// Edit IdP Connection to include User Custom Field attribute mapping

	const attributeMappings: AttributeMapping[] = [
		{
			attributeMappingType: 'User Custom Fields',
			samlAttribute: customFieldName,
			userFieldExpression: customFieldName,
		},
	];

	const idpConnection: TIdpConnection = {
		attributeMappings,
		entityId: DEFAULT_IDP_NAME,
		idpDomain: `http://${DEFAULT_IDP_NAME}:8080`,
		idpName: DEFAULT_IDP_NAME,
		spName: DEFAULT_SP_NAME,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await editIdentityProviderConnection(browser, idpConnection);

	// Edit SP Connection to include User Custom Field attribute

	const spConnection: TSpConnection = {
		entityId: DEFAULT_SP_NAME,
		idpName: DEFAULT_IDP_NAME,
		spDomain: `http://${DEFAULT_SP_NAME}:8080`,
		spName: DEFAULT_SP_NAME,
		...DEFAULT_SP_CONNECTION_VALUES,
	};

	spConnection.attributes =
		spConnection.attributes + `\nexpando:${customFieldName}`;

	await editServiceProviderConnection(browser, spConnection);

	// Create a user on the IdP instance

	const userAccount = await createIdpUser(browser, DEFAULT_IDP_NAME);

	// Perform SSO with the new user

	let spInstancePage = await browser.newPage({
		baseURL: DEFAULT_SP_URL,
	});

	await spInstancePage.goto('/');

	const signInButton = await spInstancePage.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.click();

	await spInstancePage
		.getByLabel('Email Address')
		.waitFor({timeout: 30 * 1000});

	await spInstancePage
		.getByLabel('Email Address')
		.fill(userAccount.emailAddress);
	await spInstancePage.getByLabel('Password').fill('test');
	await spInstancePage.getByLabel('Remember Me').check();
	await spInstancePage.getByRole('button', {name: 'Sign In'}).click();

	await spInstancePage
		.getByTitle('User Profile Menu')
		.waitFor({timeout: 30 * 1000});

	await performLogout(spInstancePage);

	// Perform reindex on User object

	await searchAdminPage.goto();

	await searchAdminPage.goToIndexActionsTab();

	await searchAdminPage.reindexIndexActionsItem('User');

	// Login to SP as admin, verify user custom field was imported properly

	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = DEFAULT_SP_URL;

	spInstancePage = await performSamlSafeAdminLogin(browser, DEFAULT_SP_NAME);

	usersAndOrganizationsPage = await new UsersAndOrganizationsPage(
		spInstancePage
	);

	await usersAndOrganizationsPage.goToUsers(false);

	await (
		await usersAndOrganizationsPage.usersTableRowLink(
			userAccount.alternateName
		)
	).click();

	editUserPage = await new EditUserPage(spInstancePage);

	await expect(await editUserPage.customField(customFieldName)).toHaveValue(
		'idpStartingValue'
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	// Lastly, delete both virtual instances and reset the keystore target

	await deleteVirtualInstance(DEFAULT_IDP_NAME, page);

	await deleteVirtualInstance(DEFAULT_SP_NAME, page);

	await resetSamlKeystoreManagerTarget(page);
});

test('Create two virtual instances, set localhost and one to IdP and one SP, and verify Custom User Attributes', async ({
	browser,
	editUserPage,
	page,
	searchAdminPage,
	usersAndOrganizationsPage,
}) => {

	// Set the Keystore Manager Target to Doc Lib, so we can store multiple
	// certificates in one instance

	await updateSamlKeystoreManagerTarget(
		page,
		'Document Library Keystore Manager'
	);

	await setupSamlInstances(browser, page);

	const samlAdminPage = new SamlAdminPage(page);

	await samlAdminPage.configureSAML(true, 'localhost', 'Identity Provider');

	await connectSpAndIdp(browser, 'localhost', DEFAULT_SP_NAME);

	// Create identical Custom Fields for all instances, except starting value

	const customFieldName = 'CustomField' + getRandomInt();

	const fieldValues: TInputField = {
		startingValue: 'ableStartingValue',
	};

	const customField: TCustomField = {
		fieldName: customFieldName,
		fieldType: 'inputField',
		fieldValues,
		resource: 'User',
	};

	await createCustomField(browser, customField, DEFAULT_IDP_NAME);

	fieldValues.startingValue = 'localhostStartingValue';

	customField.fieldValues = fieldValues;

	await createCustomField(browser, customField, 'localhost');

	fieldValues.startingValue = 'bakerStartingValue';

	customField.fieldValues = fieldValues;

	await createCustomField(browser, customField, DEFAULT_SP_NAME);

	// Edit IdP Connections to include User Custom Field attribute mapping

	const attributeMappings: AttributeMapping[] = [
		{
			attributeMappingType: 'User Custom Fields',
			samlAttribute: customFieldName,
			userFieldExpression: customFieldName,
		},
	];

	let idpConnection: TIdpConnection = {
		attributeMappings,
		entityId: DEFAULT_IDP_NAME,
		idpDomain: `http://${DEFAULT_IDP_NAME}:8080`,
		idpName: DEFAULT_IDP_NAME,
		spName: DEFAULT_SP_NAME,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await editIdentityProviderConnection(browser, idpConnection);

	idpConnection = {
		attributeMappings,
		entityId: 'localhost',
		idpDomain: `http://localhost:8080`,
		idpName: 'localhost',
		spName: DEFAULT_SP_NAME,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await editIdentityProviderConnection(browser, idpConnection);

	// Edit SP Connection to include User Custom Field attribute

	const spConnection: TSpConnection = {
		entityId: DEFAULT_SP_NAME,
		idpName: DEFAULT_IDP_NAME,
		spDomain: `http://${DEFAULT_SP_NAME}:8080`,
		spName: DEFAULT_SP_NAME,
		...DEFAULT_SP_CONNECTION_VALUES,
	};

	spConnection.attributes =
		spConnection.attributes + `\nexpando:${customFieldName}`;

	await editServiceProviderConnection(browser, spConnection);

	// Create a user on the IdP instances

	const userId = getRandomInt();

	const userAccount = await createIdpUser(browser, 'localhost', userId);

	await createIdpUser(browser, DEFAULT_IDP_NAME, userId);

	// Perform SSO with the new user

	let spInstancePage = await browser.newPage({
		baseURL: DEFAULT_SP_URL,
	});

	await spInstancePage.goto('/');

	const signInButton = await spInstancePage.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.click();

	// Verify user is redirected to the IdP selection page

	await spInstancePage
		.getByRole('paragraph')
		.getByText('Please select your identity provider.')
		.waitFor({timeout: 30 * 1000});

	// Perform SSO using localhost

	await spInstancePage
		.getByLabel('Identity Provider')
		.selectOption('localhost');

	await spInstancePage
		.locator('#content')
		.getByRole('button', {
			exact: true,
			name: 'Sign In',
		})
		.click();

	await spInstancePage
		.getByLabel('Email Address')
		.waitFor({timeout: 30 * 1000});

	await spInstancePage
		.getByLabel('Email Address')
		.fill(userAccount.emailAddress);
	await spInstancePage.getByLabel('Password').fill('test');
	await spInstancePage.getByLabel('Remember Me').check();
	await spInstancePage.getByRole('button', {name: 'Sign In'}).click();

	await spInstancePage
		.getByTitle('User Profile Menu')
		.waitFor({timeout: 30 * 1000});

	await performLogout(spInstancePage);

	await expect(await signInButton).toBeVisible();

	await signInButton.click();

	// Verify user is redirected to the IdP selection page

	await spInstancePage
		.getByRole('paragraph')
		.getByText('Please select your identity provider.')
		.waitFor({timeout: 30 * 1000});

	// Perform SSO again, this time using www.able.com

	await spInstancePage
		.getByLabel('Identity Provider')
		.selectOption(DEFAULT_IDP_NAME);

	await spInstancePage
		.locator('#content')
		.getByRole('button', {
			exact: true,
			name: 'Sign In',
		})
		.click();

	await spInstancePage
		.getByLabel('Email Address')
		.waitFor({timeout: 30 * 1000});

	await spInstancePage
		.getByLabel('Email Address')
		.fill(userAccount.emailAddress);
	await spInstancePage.getByLabel('Password').fill('test');
	await spInstancePage.getByLabel('Remember Me').check();
	await spInstancePage.getByRole('button', {name: 'Sign In'}).click();

	await spInstancePage
		.getByTitle('User Profile Menu')
		.waitFor({timeout: 30 * 1000});

	await performLogout(spInstancePage);

	// Perform reindex on User object

	await searchAdminPage.goto();

	await searchAdminPage.goToIndexActionsTab();

	await searchAdminPage.reindexIndexActionsItem('User');

	// Login to SP as admin, verify user custom field was imported properly

	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = DEFAULT_SP_URL;

	spInstancePage = await performSamlSafeAdminLogin(browser, DEFAULT_SP_NAME);

	usersAndOrganizationsPage = await new UsersAndOrganizationsPage(
		spInstancePage
	);

	await usersAndOrganizationsPage.goToUsers(false);

	await (
		await usersAndOrganizationsPage.usersTableRowLink(
			userAccount.alternateName
		)
	).click();

	editUserPage = await new EditUserPage(spInstancePage);

	await expect(await editUserPage.customField(customFieldName)).toHaveValue(
		'ableStartingValue',
		{timeout: 30 * 1000}
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	// Delete both virtual instances and SAML config, reset keystore target

	await deleteVirtualInstance(DEFAULT_IDP_NAME, page);

	await deleteVirtualInstance(DEFAULT_SP_NAME, page);

	await samlAdminPage.configureSAML(false);

	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.deleteServiceProviderConnection(
		DEFAULT_SP_NAME
	);

	const apiHelpers = new ApiHelpers(page);

	await apiHelpers.headlessAdminUser.deleteUserAccount(
		Number(userAccount.id)
	);

	const viewAttributePage = new ViewAttributesPage(page);

	await viewAttributePage.deleteCustomField(
		customFieldName,
		customField.resource
	);

	await resetSamlKeystoreManagerTarget(page);
});
