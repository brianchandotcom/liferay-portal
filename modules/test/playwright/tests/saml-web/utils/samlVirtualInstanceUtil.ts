/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {TCustomField} from '../../../helpers/CustomFieldTypesHelper';
import {liferayConfig} from '../../../liferay.config';
import {SystemSettingsPage} from '../../../pages/configuration-admin-web/SystemSettingsPage';
import {AddCustomFieldPage} from '../../../pages/expando-web/AddCustomFieldPage';
import {VirtualInstancesPage} from '../../../pages/portal-instances-web/VirtualInstancesPage';
import {SamlAdminPage} from '../../../pages/saml-web/SamlAdminPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import performLogin, {performLogout} from '../../../utils/performLogin';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import {deleteAfterTestProviderConnections} from '../saml.spec';
import {connectSpAndIdp} from './samlProviderConnectionUtil';

export const DEFAULT_IDP_NAME = 'www.able.com';
export const DEFAULT_IDP_URL = `http://${DEFAULT_IDP_NAME}:8080`;
export const DEFAULT_SP_NAME = 'www.baker.com';
export const DEFAULT_SP_URL = `http://${DEFAULT_SP_NAME}:8080`;

export async function createCustomField(
	browser,
	customField: TCustomField,
	instanceName: string
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${instanceName}:8080`;

	const page = await performSamlSafeAdminLogin(browser, instanceName);

	const addCustomFieldPage = new AddCustomFieldPage(page);

	await addCustomFieldPage.addCustomField(customField);

	await performLogout(page);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

export async function createIdentityProviderVirtualInstance(
	browser,
	page,
	name = DEFAULT_IDP_NAME,
	entityId = name
) {
	await createSamlVirtualInstance(
		browser,
		entityId,
		name,
		page,
		'Identity Provider'
	);
}

export async function createIdpUser(
	browser,
	idpInstanceName = DEFAULT_IDP_NAME,
	userId = getRandomInt()
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${idpInstanceName}:8080`;

	// Create new page and apiHelper implementation for IdP virtual instance

	const idpVirtualInstancePage = await performSamlSafeAdminLogin(
		browser,
		idpInstanceName
	);

	const idpApiHelpers = new ApiHelpers(idpVirtualInstancePage);

	// Create user in IdP instance

	const userAccount = await idpApiHelpers.headlessAdminUser.postUserAccount(
		undefined,
		userId
	);

	await performLogout(idpVirtualInstancePage);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	return userAccount;
}

async function createSamlVirtualInstance(
	browser,
	entityId: string,
	name: string,
	page,
	samlRole: string
) {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.addNewVirtualInstance(name);

	await configureVirtualInstanceForSaml(browser, entityId, samlRole);
}

export async function configureVirtualInstanceForSaml(
	browser,
	entityId: string,
	samlRole: string
) {
	deleteAfterTestProviderConnections.push(entityId);

	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${entityId}:8080`;

	const newPage = await performSamlSafeAdminLogin(browser, entityId);

	const samlAdminPage = new SamlAdminPage(newPage);

	await samlAdminPage.configureSAML(true, entityId, samlRole);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

export async function createServiceProviderVirtualInstance(
	browser,
	entityId: string,
	name: string,
	page
) {
	await createSamlVirtualInstance(
		browser,
		entityId,
		name,
		page,
		'Service Provider'
	);
}

export async function deleteVirtualInstance(name: string, page) {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.deleteVirtualInstance(name);
}

export async function performSamlSafeAdminLogin(browser, domain: string) {
	const page = await browser.newPage({
		baseURL: `http://${domain}:8080`,
	});

	const mailId = domain !== 'localhost' ? `@${domain}.com` : undefined;

	await performLogin(
		page,
		'test',
		'?p_p_id=com_liferay_login_web_portlet_LoginPortlet&' +
			'p_p_state=maximized',
		mailId
	);

	return page;
}

export async function resetSamlKeystoreManagerTarget(page) {
	const systemSettingsPage = new SystemSettingsPage(page);

	await systemSettingsPage.goToSystemSetting(
		'SSO',
		'SAML KeyStoreManager Implementation Configuration'
	);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: systemSettingsPage.page.getByRole('button', {name: 'Actions'}),
		trigger: systemSettingsPage.page.getByRole('link', {
			name: 'Reset Default Values',
		}),
	});

	await systemSettingsPage.page
		.getByRole('link', {name: 'Reset Default Values'})
		.click();

	await waitForSuccessAlert(page);
}

export async function setupSamlInstances(
	browser,
	page,
	idpInstanceName = DEFAULT_IDP_NAME,
	idpEntityId = idpInstanceName,
	spInstanceName = DEFAULT_SP_NAME,
	spEntityId = spInstanceName
) {
	await createIdentityProviderVirtualInstance(
		browser,
		page,
		idpInstanceName,
		idpEntityId
	);

	// Create new sp virtual instance

	await createServiceProviderVirtualInstance(
		browser,
		spEntityId,
		spInstanceName,
		page
	);

	// Add a new connection for each provider, of the opposite provider

	await connectSpAndIdp(
		browser,
		idpInstanceName,
		spInstanceName,
		idpEntityId,
		spEntityId
	);
}

export async function updateSamlKeystoreManagerTarget(page, target: string) {
	const systemSettingsPage = new SystemSettingsPage(page);

	await systemSettingsPage.goToSystemSetting(
		'SSO',
		'SAML KeyStoreManager Implementation Configuration'
	);

	await systemSettingsPage.page.getByLabel('Keystore Manager Target').click();

	await systemSettingsPage.page.getByRole('option', {name: target}).click();

	let updateButton = await systemSettingsPage.page.getByRole('button', {
		name: 'Update',
	});

	if (!(await updateButton.isVisible())) {
		updateButton = await systemSettingsPage.page.getByRole('button', {
			name: 'Save',
		});
	}

	await updateButton.click();

	await waitForSuccessAlert(page);
}
