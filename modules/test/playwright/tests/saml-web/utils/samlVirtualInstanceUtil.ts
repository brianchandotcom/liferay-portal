/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {SystemSettingsPage} from '../../../pages/configuration-admin-web/SystemSettingsPage';
import {VirtualInstancesPage} from '../../../pages/portal-instances-web/VirtualInstancesPage';
import {SamlAdminPage} from '../../../pages/saml-web/SamlAdminPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import performLogin, {performLogout} from '../../../utils/performLogin';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import {connectSpAndIdp} from './samlProviderConnectionUtil';

export const DEFAULT_IDP_NAME = 'idp';
export const DEFAULT_IDP_URL = `http://${DEFAULT_IDP_NAME}:8080`;
export const DEFAULT_SP_NAME = 'sp';
export const DEFAULT_SP_URL = `http://${DEFAULT_SP_NAME}:8080`;

export async function createServiceProviderVirtualInstance(
	name: string,
	entityId: string,
	page
) {
	await _createSamlVirtualInstance(name, entityId, 'Service Provider', page);
}

export async function createIdentityProviderVirtualInstance(
	name = DEFAULT_IDP_NAME,
	entityId = name,
	page
) {
	await _createSamlVirtualInstance(name, entityId, 'Identity Provider', page);
}

export async function deleteVirtualInstance(name: string, page) {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.deleteVirtualInstance(name);
}

export async function setupSamlInstances(
	idpInstanceName = DEFAULT_IDP_NAME,
	spInstanceName = DEFAULT_SP_NAME,
	idpEntityId = idpInstanceName,
	spEntityId = spInstanceName,
	page
) {
	await createIdentityProviderVirtualInstance(
		idpInstanceName,
		idpEntityId,
		page
	);

	// Create new sp virtual instance

	await page.goto('/');

	await createServiceProviderVirtualInstance(
		spInstanceName,
		spEntityId,
		page
	);

	// Add a new connection for each provider, of the opposite provider

	await connectSpAndIdp(
		idpInstanceName,
		spInstanceName,
		idpEntityId,
		spEntityId,
		page
	);
}

export async function createSpAndIdpUser(
	idpInstanceName = DEFAULT_IDP_NAME,
	spInstanceName = DEFAULT_SP_NAME,
	userId: number,
	page,
	browser
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${idpInstanceName}:8080`;

	// Create new page and apiHelper implementation based off IdP virtual instance

	const idpVirtualInstancePage = await browser.newPage({
		baseURL: liferayConfig.environment.baseUrl,
	});

	await performLogin(
		idpVirtualInstancePage,
		'test',
		undefined,
		`@${idpInstanceName}.com`
	);

	const idpApiHelpers = new ApiHelpers(idpVirtualInstancePage);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	// Create user in IdP instance

	const userAccount = await idpApiHelpers.headlessAdminUser.postUserAccount(
		undefined,
		userId
	);

	await performLogout(idpVirtualInstancePage);

	liferayConfig.environment.baseUrl = `http://${spInstanceName}:8080`;

	// Create new page and apiHelper implementation based off IdP virtual instance

	const spVirtualInstancePage = await browser.newPage({
		baseURL: `http://${spInstanceName}:8080`,
	});

	await performLogin(
		spVirtualInstancePage,
		'test',
		'?p_p_id=com_liferay_login_web_portlet_LoginPortlet&p_p_state=maximized',
		`@${spInstanceName}.com`
	);

	const spApiHelpers = new ApiHelpers(spVirtualInstancePage);

	liferayConfig.environment.baseUrl = defaultBaseUrl;

	// Create user in SP instance, using the same information as IdP user

	await spApiHelpers.headlessAdminUser.postUserAccount(undefined, userId);

	await performLogout(spVirtualInstancePage);

	return userAccount;
}

async function _createSamlVirtualInstance(
	name: string,
	entityId: string,
	samlRole: string,
	page
) {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.addNewVirtualInstance(
		undefined,
		undefined,
		name,
		undefined
	);

	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${name}:8080`;

	await performLogin(
		page,
		'test',
		liferayConfig.environment.baseUrl,
		`@${name}.com`
	);

	const samlAdminPage = new SamlAdminPage(page);

	await samlAdminPage.configureSAML(true, entityId, samlRole);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

export async function updateSamlKeystoreManagerTarget(target: string, page) {
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

	systemSettingsPage.page
		.getByRole('link', {name: 'Reset Default Values'})
		.click();

	await waitForSuccessAlert(page);
}
