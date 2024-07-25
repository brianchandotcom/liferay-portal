/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../../liferay.config';
import {IdentityProviderConnectionsPage} from '../../../pages/saml-web/IdentityProviderConnectionsPage';
import {ServiceProviderConnectionsPage} from '../../../pages/saml-web/ServiceProviderConnectionsPage';

const _DEFAULT_METADATA_PATH = '/c/portal/saml/metadata';

async function addIdentityProviderConnection(
	idpName: string,
	page,
	spDomain: string,
	idpDomain = idpName,
	idpEntityId = idpName
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${spDomain}:8080`;

	const identityProviderConnectionsPage = new IdentityProviderConnectionsPage(
		page
	);

	await identityProviderConnectionsPage.addIdentityProviderConnection(
		`http://${idpDomain}:8080${_DEFAULT_METADATA_PATH}`,
		idpName,
		undefined,
		undefined,
		idpEntityId
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

async function addServiceProviderConnection(
	idpDomain: string,
	page,
	spName: string,
	spDomain = spName,
	spEntityId = spName
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${idpDomain}:8080`;

	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.addServiceProviderConnection(
		`http://${spDomain}:8080${_DEFAULT_METADATA_PATH}`,
		spName,
		undefined,
		undefined,
		undefined,
		undefined,
		undefined,
		spEntityId
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
	await addServiceProviderConnection(
		idpName,
		page,
		spName,
		spName,
		spEntityId
	);

	await addIdentityProviderConnection(
		idpName,
		page,
		spName,
		idpName,
		idpEntityId
	);
}
