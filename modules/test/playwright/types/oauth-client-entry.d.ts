/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type TCustomClaim = {
	expandoColumnName: string;
	index?: number;
	oidcProviderCustomClaim: string;
};

type TInfoJSON = {
	application_type?: string;
	client_id?: string;
	client_name?: string;
	client_secret?: string;
	client_secret_expires_at?: number;
	grant_types?: string[];
	id_token_signed_response_alg?: string;
	response_types?: string[];
	scope?: string;
	token_endpoint_auth_method?: string;
};

type TOAuthClientEntry = {
	authServerWellKnownURI?: string;
	customClaims?: TCustomClaim[];
	infoJSON?: TInfoJSON;
	matcherField?: 'email' | 'screenName';
	oidcUserInfoMapperJSON?: TOIDCUserInfoMapperJSON;
};

type TOIDCUser = {
	emailAddress?: string;
	firstName?: string;
	jobTitle?: string;
	languageId?: string;
	lastName?: string;
	middleName?: string;
	screenName?: string;
};

type TOIDCUserAddress = {
	addressType?: string;
	city?: string;
	country?: string;
	region?: string;
	street?: string;
	zip?: string;
};

type TOIDCUserContact = {
	birthdate?: string;
	gender?: string;
};

type TOIDCUserGroups = {
	groups?: string;
};

type TOIDCUserInfoMapperJSON = {
	address?: TOIDCUserAddress;
	contact?: TOIDCUserContact;
	phone?: TOIDCUserPhone;
	user?: TOIDCUser;
	users_groups?: TOIDCUserGroups;
	users_roles?: TOIDCUserRoles;
};

type TOIDCUserPhone = {
	phone?: string;
	phoneType?: string;
};

type TOIDCUserRoles = {
	roles?: string;
};
