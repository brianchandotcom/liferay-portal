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
import {
	AttributeMapping,
	IdentityProviderConnectionsPage,
} from '../../pages/saml-web/IdentityProviderConnectionsPage';
import {SamlAdminPage} from '../../pages/saml-web/SamlAdminPage';
import {ServiceProviderConnectionsPage} from '../../pages/saml-web/ServiceProviderConnectionsPage';
import {EditUserPage} from '../../pages/users-admin-web/EditUserPage';
import {UsersAndOrganizationsPage} from '../../pages/users-admin-web/UsersAndOrganizationsPage';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';
import {performSpInitiatedSSO} from './utils/samlAuthUtil';
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
	configureVirtualInstanceForSaml,
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

const deleteAfterTestCustomFields: string[] = [];
export const deleteAfterTestProviderConnections: string[] = [];
const deleteAfterTestUserIds: string[] = [];

test.afterAll(async ({browser}) => {

	// Remove virtual instances

	const newPage = await browser.newPage();

	await performLogin(newPage, 'test');

	await deleteVirtualInstance(DEFAULT_IDP_NAME, newPage);

	await deleteVirtualInstance(DEFAULT_SP_NAME, newPage);

	// Reset saml keystore

	await resetSamlKeystoreManagerTarget(newPage);

	// Remove localhost SAML users

	const apiHelpers = new ApiHelpers(newPage);

	for (const userId of deleteAfterTestUserIds) {
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(userId));
	}

	// Remove localhost Custom Fields

	const viewAttributePage = new ViewAttributesPage(newPage);

	for (const customFieldName of deleteAfterTestCustomFields) {
		await viewAttributePage.deleteCustomField(customFieldName, 'User');
	}
});

test.afterEach(async ({browser}) => {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	for (const instanceName of deleteAfterTestProviderConnections) {
		liferayConfig.environment.baseUrl = `http://${instanceName}:8080`;

		// Reset general tab

		const newPage = await performSamlSafeAdminLogin(browser, instanceName);

		const samlAdminPage = new SamlAdminPage(newPage);

		await samlAdminPage.configureSAML(false);

		// Delete all connections

		if ((await samlAdminPage.samlRoleField.inputValue()) === 'idp') {
			const serviceProviderConnectionsPage =
				new ServiceProviderConnectionsPage(samlAdminPage.page);

			await serviceProviderConnectionsPage.goTo();

			await serviceProviderConnectionsPage.deleteServiceProviderConnections();
		}
		else {
			const identityProviderConnectionsPage =
				new IdentityProviderConnectionsPage(samlAdminPage.page);

			await identityProviderConnectionsPage.goTo();

			await identityProviderConnectionsPage.deleteIdentityProviderConnections();
		}
	}
	deleteAfterTestProviderConnections.length = 0;

	liferayConfig.environment.baseUrl = defaultBaseUrl;
});

test.beforeAll(async ({browser}) => {

	// Set saml keystore

	const newPage = await browser.newPage();

	await performLogin(newPage, 'test');

	await updateSamlKeystoreManagerTarget(
		newPage,
		'Document Library Keystore Manager'
	);

	// Create virtual instances

	await setupSamlInstances(browser, newPage);
});

test('Create two virtual instances, one IdP and one SP, connect them, perform SP initiated SSO, perform SP initiated SLO', async ({
	browser,
}) => {
	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(browser, DEFAULT_IDP_NAME, DEFAULT_SP_NAME);

	// Create a user with identical credentials on each instance

	const userAccount = await createIdpUser(browser, DEFAULT_IDP_NAME);

	// Perform SP initiated SSO

	const spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL
	);

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
	searchAdminPage,
	usersAndOrganizationsPage,
}) => {
	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(browser, DEFAULT_IDP_NAME, DEFAULT_SP_NAME);

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

	// Perform Sp initiated SSO with the new user

	let spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL
	);

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
});

test('Create two virtual instances, set localhost and one to IdP and one SP, and verify Custom User Attributes', async ({
	browser,
	editUserPage,
	searchAdminPage,
	usersAndOrganizationsPage,
}) => {
	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	await configureVirtualInstanceForSaml(
		browser,
		'localhost',
		'Identity Provider'
	);

	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(browser, 'localhost', DEFAULT_SP_NAME);

	await connectSpAndIdp(browser, DEFAULT_IDP_NAME, DEFAULT_SP_NAME);

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

	deleteAfterTestCustomFields.push(customFieldName);

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

	deleteAfterTestUserIds.push(userAccount.id);

	await createIdpUser(browser, DEFAULT_IDP_NAME, userId);

	// Perform SP initiated SSO, using localhost as the IdP

	let spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL,
		'localhost'
	);

	await performLogout(spInstancePage);

	// Perform SP initiated SSO again, this time using www.able.com as the IdP

	spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL,
		DEFAULT_IDP_NAME
	);

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
});

test('SAML connection cannot be saved if a custom field value is used more than once', async ({
	browser,
}) => {
	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(browser, DEFAULT_IDP_NAME, DEFAULT_SP_NAME);

	const customFieldName = 'CustomField' + getRandomInt();

	const customField: TCustomField = {
		fieldName: customFieldName,
		fieldType: 'inputField',
		resource: 'User',
	};

	await createCustomField(browser, customField, DEFAULT_IDP_NAME);

	await createCustomField(browser, customField, DEFAULT_SP_NAME);

	// Edit IdP Connection to include duplicate Custom Field attribute mappings

	const attributeMappings: AttributeMapping[] = [
		{
			attributeMappingType: 'User Custom Fields',
			samlAttribute: customFieldName,
			userFieldExpression: customFieldName,
		},
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

	// IdP connection should display the following error message

	const errorMessage =
		'User Custom Fields: Each user field can only be mapped to one SAML attribute.';

	await editIdentityProviderConnection(browser, idpConnection, errorMessage);
});
