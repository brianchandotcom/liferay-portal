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
import {reloadUntilVisible} from '../../utils/reloadUntilVisible';
import {
	TIdentityProvider,
	configureIdentityProvider,
} from './utils/IdentityProviderUtil';
import {
	TServiceProvider,
	configureServiceProvider,
} from './utils/ServiceProviderUtil';
import {
	performIdpInitiatedSSO,
	performSpInitiatedSSO,
} from './utils/samlAuthUtil';
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
	createUser,
	deleteVirtualInstance,
	performSamlSafeLogin,
	resetSamlConfiguration,
	resetSamlKeystoreManagerTarget,
	setupSamlInstances,
	updateRuntimeMetadataRefreshInterval,
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

	// Reset saml configuration, in cases where test failed before doing so

	await resetSamlConfiguration(newPage);

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

		const newPage = await performSamlSafeLogin(browser, instanceName);

		const samlAdminPage = new SamlAdminPage(newPage);

		await samlAdminPage.configureSAML(false);

		// Delete all connections

		if ((await samlAdminPage.samlRoleField.inputValue()) === 'idp') {
			const serviceProviderConnectionsPage =
				new ServiceProviderConnectionsPage(samlAdminPage.page);

			await serviceProviderConnectionsPage.goTo();

			await serviceProviderConnectionsPage.deleteServiceProviderConnections();

			await configureIdentityProvider(newPage);
		}
		else {
			const identityProviderConnectionsPage =
				new IdentityProviderConnectionsPage(samlAdminPage.page);

			await identityProviderConnectionsPage.goTo();

			await identityProviderConnectionsPage.deleteIdentityProviderConnections();

			await configureServiceProvider(newPage);
		}

		await newPage.close();
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

	// Update Runtime Metadata Refresh Interval value to a low value, otherwise
	// the tests may update faster than the interval, causing errors.

	await updateRuntimeMetadataRefreshInterval(newPage, '4');

	// Create virtual instances

	await setupSamlInstances(browser, newPage);

	await newPage.close();
});

test('Create two virtual instances, one IdP and one SP, connect them, perform SP initiated SSO, perform SP initiated SLO', async ({
	browser,
}) => {
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

	// Create a user with identical credentials on each instance

	const userAccount = await createUser(idpAdminPage, DEFAULT_IDP_NAME);

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
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

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

	await createCustomField(idpAdminPage, customField);

	fieldValues.startingValue = 'spStartingValue';

	customField.fieldValues = fieldValues;

	await createCustomField(spAdminPage, customField);

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

	await editIdentityProviderConnection(spAdminPage, idpConnection);

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

	await editServiceProviderConnection(idpAdminPage, spConnection);

	// Create a user on the IdP instance

	const userAccount = await createUser(idpAdminPage, DEFAULT_IDP_NAME);

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

	spInstancePage = await performSamlSafeLogin(browser, DEFAULT_SP_NAME);

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
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const localhostIdpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		'localhost',
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

	await connectSpAndIdp(
		localhostIdpAdminPage,
		'localhost',
		spAdminPage,
		DEFAULT_SP_NAME
	);

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

	await createCustomField(idpAdminPage, customField);

	fieldValues.startingValue = 'localhostStartingValue';

	customField.fieldValues = fieldValues;

	deleteAfterTestCustomFields.push(customFieldName);

	await createCustomField(localhostIdpAdminPage, customField);

	fieldValues.startingValue = 'bakerStartingValue';

	customField.fieldValues = fieldValues;

	await createCustomField(spAdminPage, customField);

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

	await editIdentityProviderConnection(spAdminPage, idpConnection);

	idpConnection = {
		attributeMappings,
		entityId: 'localhost',
		idpDomain: `http://localhost:8080`,
		idpName: 'localhost',
		spName: DEFAULT_SP_NAME,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await editIdentityProviderConnection(spAdminPage, idpConnection);

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

	await editServiceProviderConnection(idpAdminPage, spConnection);

	// Create a user on the IdP instances

	const userId = getRandomInt();

	const userAccount = await createUser(
		localhostIdpAdminPage,
		'localhost',
		userId
	);

	deleteAfterTestUserIds.push(userAccount.id);

	await createUser(idpAdminPage, DEFAULT_IDP_NAME, userId);

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

	spInstancePage = await performSamlSafeLogin(browser, DEFAULT_SP_NAME);

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
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

	const customFieldName = 'CustomField' + getRandomInt();

	const customField: TCustomField = {
		fieldName: customFieldName,
		fieldType: 'inputField',
		resource: 'User',
	};

	await createCustomField(idpAdminPage, customField);

	await createCustomField(spAdminPage, customField);

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

	await editIdentityProviderConnection(
		spAdminPage,
		idpConnection,
		errorMessage
	);
});

test('Verify a Message context is not authenticated when Require Authn Request Signature and Sign Authn Requests are disabled.  Replaces SAML.AssertSSOWithSignAuthnRequests, see LPD-32545.', async ({
	browser,
}) => {
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

	// Disable auth and request signature required on IdP

	const identityProvider: TIdentityProvider = {
		requireAuthnRequestSignature: false,
	};

	await configureIdentityProvider(idpAdminPage, identityProvider);

	const localhostAdminPage = await browser.newPage();

	await performLogin(localhostAdminPage, 'test');

	await updateRuntimeMetadataRefreshInterval(localhostAdminPage, '2');

	// Create new user in IdP instance

	const userAccount = await createUser(idpAdminPage, DEFAULT_IDP_NAME);

	// Execute IdP initiated SSO

	const idpInstancePage = await performIdpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_IDP_URL,
		DEFAULT_SP_URL,
		DEFAULT_SP_NAME
	);

	// Assert authentication and SP redirection

	expect(await idpInstancePage.getByTitle('User Profile Menu')).toBeVisible();

	expect(await idpInstancePage.url()).toContain(DEFAULT_SP_URL);

	// Reset IdP configuration settings

	await configureIdentityProvider(idpAdminPage);

	// Execute SP initiated SLO and assert logged out

	await idpInstancePage.getByTitle('User Profile Menu').click();

	await idpInstancePage.getByRole('menuitem', {name: 'Sign Out'}).click();

	await expect(
		await idpInstancePage.getByRole('button', {name: 'Sign In'})
	).toBeVisible();

	// Execute SP initiated SSO

	let spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL
	);

	// Assert authentication and SP redirection

	expect(await spInstancePage.getByTitle('User Profile Menu')).toBeVisible();

	expect(await spInstancePage.url()).toContain(DEFAULT_SP_URL);

	// Disable auth and request signature required on IdP

	await configureIdentityProvider(idpAdminPage, identityProvider);

	// Disable Sign Authn Requests on SP

	const serviceProvider: TServiceProvider = {
		signAuthnRequests: false,
	};

	await configureServiceProvider(spAdminPage, serviceProvider);

	// Execute SP initiated SLO and assert logged out

	await spInstancePage.getByTitle('User Profile Menu').click();

	await spInstancePage.getByRole('menuitem', {name: 'Sign Out'}).click();

	await expect(
		await spInstancePage.getByRole('button', {name: 'Sign In'})
	).toBeVisible();

	// Execute SP initiated SSO

	spInstancePage = await performSpInitiatedSSO(
		browser,
		userAccount.emailAddress,
		DEFAULT_SP_URL
	);

	// Assert logged in

	expect(await spInstancePage.getByTitle('User Profile Menu')).toBeVisible();

	// Update Runtime Metadata Refresh Interval value to a high value

	await updateRuntimeMetadataRefreshInterval(localhostAdminPage, '9999');

	// Reset IdP configuration settings

	await configureIdentityProvider(idpAdminPage);

	// Execute SP initiated SLO

	await spInstancePage.getByTitle('User Profile Menu').click();

	await spInstancePage.getByRole('menuitem', {name: 'Sign Out'}).click();

	await spInstancePage
		.getByRole('button', {name: 'Sign In'})
		.waitFor({timeout: 30 * 1000});

	// Go to SP, click Sign in, and assert error message

	await spInstancePage
		.getByRole('button', {
			name: 'Sign In',
		})
		.click();

	// Assert the SAML Message context was not authenticated, because the IdP
	// requires Authn Request Signature, but the SP didn't have a chance to
	// refresh and pull the IdP configuration change

	await spInstancePage.waitForTimeout(2000);

	// Sometimes the error banner does not display, even if the message context
	// was not authenticated.  To make test less flaky, when the banner is not
	// present, verify user was not logged in and is still on SP instance.  This
	// result should still be considered as passing.

	if (
		await spInstancePage
			.getByRole('heading', {
				name: 'Unable to process SAML',
			})
			.isHidden()
	) {
		expect(
			await spInstancePage.getByRole('button', {name: 'Sign In'})
		).toBeVisible();
		expect(await spInstancePage.url()).toContain(DEFAULT_SP_URL);
	}

	await updateRuntimeMetadataRefreshInterval(localhostAdminPage, '4');
});

test('Verify IdP initiated SLO also logs out of authenticated SP when Require Authn Request Signature and Sign Metadata are enabled.  See LPS-128578.', async ({
	browser,
}) => {
	const idpAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_IDP_NAME,
		'Identity Provider'
	);

	const spAdminPage = await configureVirtualInstanceForSaml(
		browser,
		DEFAULT_SP_NAME,
		'Service Provider'
	);

	await connectSpAndIdp(
		idpAdminPage,
		DEFAULT_IDP_NAME,
		spAdminPage,
		DEFAULT_SP_NAME
	);

	// Create IdP User

	const userAccount = await createUser(idpAdminPage, DEFAULT_IDP_NAME);

	// Login to IdP.  The Remember Me checkbox must be disabled.

	const newPage = await performSamlSafeLogin(
		browser,
		DEFAULT_IDP_NAME,
		'@liferay.com',
		false,
		userAccount.alternateName
	);

	// Clicking Sign In button on SP page should automatically authenticate

	await newPage.goto(DEFAULT_SP_URL);

	const signInButton = await newPage.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.waitFor();

	await signInButton.click();

	await newPage.getByTitle('User Profile Menu').waitFor({timeout: 30 * 1000});

	// Idp initiated SLO

	await newPage.goto(DEFAULT_IDP_URL);

	await newPage.getByTitle('User Profile Menu').click();

	await newPage.getByRole('menuitem', {name: 'Sign Out'}).click();

	await newPage.waitForTimeout(8000);

	// SP should also be logged out after IdP initiated SLO

	await newPage.goto(DEFAULT_SP_URL);

	await reloadUntilVisible({
		myLocator: signInButton,
		page: newPage,
	});

	expect(await signInButton).toBeVisible();
});
