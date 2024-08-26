/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AttributeMapping} from '../pages/saml-web/IdentityProviderConnectionsPage';

export const DEFAULT_IDP_CONNECTION_VALUES = {
	enabled: true,
};

export const DEFAULT_SP_CONNECTION_VALUES = {
	attributes: 'firstName\nlastName\nemailAddress\nscreenName\nuuid',
	attributesEnabled: true,
	enabled: true,
	nameIdentifierAttributeName: 'emailAddress',
};

export type TIdpConnection = {
	attributeMappings?: AttributeMapping[];
	clockSkew?: string;
	enabled?: boolean;
	entityId?: string;
	forceAuthn?: boolean;
	idpDomain: string;
	idpName: string;
	keepAliveUrl?: string;
	metadataURL?: string;
	nameIdentifierFormat?: string;
	spName: string;
	unknownUsersAreStrangers?: boolean;
	userResolution?: 'attribute' | 'dynamic' | 'none';
};

export type TSpConnection = {
	assertionLifetime?: string;
	attributes?: string;
	attributesEnabled?: boolean;
	attributesNamespaceEnabled?: boolean;
	enabled?: boolean;
	entityId: string;
	forceEncrytion?: boolean;
	idpName: string;
	keepAliveUrl?: string;
	metadataURL?: string;
	nameIdentifierAttributeName?: string;
	nameIdentifierFormat?: string;
	spDomain: string;
	spName: string;
};
