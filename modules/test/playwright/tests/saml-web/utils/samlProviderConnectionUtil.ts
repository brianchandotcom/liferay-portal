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
	spDomain: string,
	idpDomain = idpName,
	idpEntityId = idpName,
	page
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${spDomain}:8080`;

	const identityProviderConnectionsPage = new IdentityProviderConnectionsPage(
		page
	);

	await identityProviderConnectionsPage.addIdentityProviderConnection(
		idpName,
		idpEntityId,
		undefined,
		undefined,
		undefined,
		undefined,
		`http://${idpDomain}:8080${_DEFAULT_METADATA_PATH}`
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

async function addServiceProviderConnection(
	idpDomain: string,
	spName: string,
	spDomain = spName,
	spEntityId = spName,
	page
) {
	const defaultBaseUrl = liferayConfig.environment.baseUrl;

	liferayConfig.environment.baseUrl = `http://${idpDomain}:8080`;

	const serviceProviderConnectionsPage = new ServiceProviderConnectionsPage(
		page
	);

	await serviceProviderConnectionsPage.addServiceProviderConnection(
		spName,
		spEntityId,
		undefined,
		undefined,
		undefined,
		`http://${spDomain}:8080${_DEFAULT_METADATA_PATH}`
	);

	liferayConfig.environment.baseUrl = defaultBaseUrl;
}

export async function connectSpAndIdp(
	idpName: string,
	spName: string,
	idpEntityId = idpName,
	spEntityId = spName,
	page
) {
	await addServiceProviderConnection(
		idpName,
		spName,
		spName,
		spEntityId,
		page
	);

	await addIdentityProviderConnection(
		idpName,
		spName,
		idpName,
		idpEntityId,
		page
	);
}
