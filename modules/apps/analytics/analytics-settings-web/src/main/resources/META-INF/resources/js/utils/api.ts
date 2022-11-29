/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {TTableRequestParams} from '../components/table/types';
import {serializeTableRequestParams} from '../components/table/utils';
import request from './request';

export function createProperty(name: string) {
	return request('/channels', {
		body: JSON.stringify({
			name,
		}),
		method: 'POST',
	});
}

export function deleteConnection() {
	return request('/data-sources', {method: 'DELETE'});
}

export function fetchAccountGroups(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(`/contacts/account-groups?${queryString}`, {
		method: 'GET',
	});
}

export function fetchChannels(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(`/commerce-channels?${queryString}`, {
		method: 'GET',
	});
}

export function fetchConnection(token: string) {
	return request('/data-sources', {
		body: JSON.stringify({
			token,
		}),
		method: 'POST',
	});
}

export function fetchContactsOrganization(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(`/contacts/organizations?${queryString}`, {
		method: 'GET',
	});
}

export function fetchContactsUsersGroup(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(`/contacts/user-groups?${queryString}`, {
		method: 'GET',
	});
}

export function fetchAttributesConfiguration() {
	return request('/contacts/configuration', {
		method: 'GET',
	});
}

export function fetchProperties() {
	return request('/channels?sort=createDate:desc', {method: 'GET'});
}

export function fetchSites(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(`/sites?${queryString}`, {
		method: 'GET',
	});
}

export function updateProperty({
	channelId,
	commerceChannelIds = [],
	commerceSyncEnabled,
	dataSourceId,
	siteIds = [],
}: {
	channelId: string;
	commerceChannelIds?: number[];
	commerceSyncEnabled?: boolean;
	dataSourceId?: string;
	siteIds?: number[];
}) {
	return request('/channels', {
		body: JSON.stringify({
			channelId,
			commerceSyncEnabled,
			dataSources: [
				{
					commerceChannelIds,
					dataSourceId,
					siteIds,
				},
			],
		}),
		method: 'PATCH',
	});
}

export function updatecommerceSyncEnabled({
	channelId,
	commerceSyncEnabled,
}: {
	channelId: string;
	commerceChannelIds?: number[];
	commerceSyncEnabled?: boolean;
	dataSourceId?: string;
	siteIds?: number[];
}) {
	return request('/channels', {
		body: JSON.stringify({
			channelId,
			commerceSyncEnabled,
		}),
		method: 'PATCH',
	});
}

export function updateAttributesConfiguration({
	syncAllAccounts,
	syncAllContacts,
	syncedAccountGroupIds,
	syncedOrganizationIds,
	syncedUserGroupIds,
}: {
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
	syncedAccountGroupIds?: string[];
	syncedOrganizationIds?: string[];
	syncedUserGroupIds?: string[];
}) {
	return request('/contacts/configuration', {
		body: JSON.stringify({
			syncAllAccounts,
			syncAllContacts,
			syncedAccountGroupIds,
			syncedOrganizationIds,
			syncedUserGroupIds,
		}),
		method: 'PUT',
	});
}

export function fetchSelectedFields() {
	return request('/fields', {method: 'GET'});
}

export function fetchPeopleFields(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(
		`/fields/people?${queryString.replace('keywords', 'keyword')}`,
		{method: 'GET'}
	);
}

export function fetchAccountsFields(params: TTableRequestParams) {
	const queryString = serializeTableRequestParams(params);

	return request(
		`/fields/accounts?${queryString.replace('keywords', 'keyword')}`,
		{method: 'GET'}
	);
}

type TField = {
	example: string;
	name: string;
	required: boolean;
	selected: boolean;
	source: string;
	type: string;
};

export function updatePeopleFields(fields: TField[]) {
	return request('/fields/people', {
		body: JSON.stringify(fields),
		method: 'PATCH',
	});
}

export function updateAccountsFields(fields: TField[]) {
	return request('/fields/accounts', {
		body: JSON.stringify(fields),
		method: 'PATCH',
	});
}
