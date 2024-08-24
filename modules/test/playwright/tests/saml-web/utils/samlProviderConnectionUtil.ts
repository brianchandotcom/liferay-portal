/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {
	DEFAULT_IDP_CONNECTION_VALUES,
	DEFAULT_SP_CONNECTION_VALUES,
	TIdpConnection,
	TSpConnection,
} from '../../../helpers/SamlProviderConnectionHelper';
import {IdentityProviderConnectionsPage} from '../../../pages/saml-web/IdentityProviderConnectionsPage';
import {ServiceProviderConnectionsPage} from '../../../pages/saml-web/ServiceProviderConnectionsPage';

const _DEFAULT_METADATA_PATH = '/c/portal/saml/metadata';

export async function addIdentityProviderConnection(
	idpConnection: TIdpConnection,
	page
) {
	const identityProviderConnectionsPage = new IdentityProviderConnectionsPage(
		page
	);

	await identityProviderConnectionsPage.goTo();

	await identityProviderConnectionsPage.addIdentityProviderConnection(
		idpConnection
	);
}

async function addServiceProviderConnection(
	page: Page,
	spConnection: TSpConnection
) {
	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.goTo();

	await serviceProviderConnectionsPage.addServiceProviderConnection(
		spConnection
	);
}

export async function connectSpAndIdp(
	idpAdminPage: Page,
	idpName: string,
	spAdminPage: Page,
	spName: string,
	idpEntityId = idpName,
	spEntityId = spName
) {
	const spConnection: TSpConnection = {
		entityId: spEntityId,
		idpName,
		metadataURL: `http://${spName}:8080${_DEFAULT_METADATA_PATH}`,
		spDomain: `http://${spName}:8080`,
		spName,
		...DEFAULT_SP_CONNECTION_VALUES,
	};

	await addServiceProviderConnection(idpAdminPage, spConnection);

	const idpConnection: TIdpConnection = {
		entityId: idpEntityId,
		idpDomain: `http://${idpName}:8080`,
		idpName,
		metadataURL: `http://${idpName}:8080${_DEFAULT_METADATA_PATH}`,
		spName,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await addIdentityProviderConnection(idpConnection, spAdminPage);
}

export async function editIdentityProviderConnection(
	page: Page,
	idpConnection: TIdpConnection,
	expectedMessage?: string
) {
	const identityProviderConnectionsPage = new IdentityProviderConnectionsPage(
		page
	);

	await identityProviderConnectionsPage.goTo();

	await identityProviderConnectionsPage.editIdentityProviderConnection(
		idpConnection,
		expectedMessage
	);
}

export async function editServiceProviderConnection(
	page: Page,
	spConnection: TSpConnection
) {
	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.goTo();

	await serviceProviderConnectionsPage.editServiceProviderConnection(
		spConnection
	);
}
