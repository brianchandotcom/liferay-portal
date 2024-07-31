/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DEFAULT_IDP_CONNECTION_VALUES,
	DEFAULT_SP_CONNECTION_VALUES,
	TIdpConnection,
	TSpConnection,
} from '../../../helpers/SamlProviderConnectionHelper';
import {liferayConfig} from '../../../liferay.config';
import {IdentityProviderConnectionsPage} from '../../../pages/saml-web/IdentityProviderConnectionsPage';
import {ServiceProviderConnectionsPage} from '../../../pages/saml-web/ServiceProviderConnectionsPage';

const _DEFAULT_METADATA_PATH = '/c/portal/saml/metadata';

async function addIdentityProviderConnection(
	idpConnection: TIdpConnection,
	page
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${idpConnection.spName}:8080`;

	const identityProviderConnectionsPage = new IdentityProviderConnectionsPage(
		page
	);

	await identityProviderConnectionsPage.addIdentityProviderConnection(
		idpConnection
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

async function addServiceProviderConnection(page, spConnection: TSpConnection) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${spConnection.idpName}:8080`;

	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.addServiceProviderConnection(
		spConnection
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

export async function connectSpAndIdp(
	idpName: string,
	page,
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

	await addServiceProviderConnection(page, spConnection);

	const idpConnection: TIdpConnection = {
		entityId: idpEntityId,
		idpDomain: `http://${idpName}:8080`,
		idpName,
		metadataURL: `http://${idpName}:8080${_DEFAULT_METADATA_PATH}`,
		spName,
		...DEFAULT_IDP_CONNECTION_VALUES,
	};

	await addIdentityProviderConnection(idpConnection, page);
}
