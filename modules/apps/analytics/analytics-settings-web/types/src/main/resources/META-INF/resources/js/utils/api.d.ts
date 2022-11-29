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
export declare function createProperty(name: string): Promise<any>;
export declare function deleteConnection(): Promise<any>;
export declare function fetchAccountGroups(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchChannels(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchConnection(token: string): Promise<any>;
export declare function fetchContactsOrganization(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchContactsUsersGroup(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchAttributesConfiguration(): Promise<any>;
export declare function fetchProperties(): Promise<any>;
export declare function fetchSites(params: TTableRequestParams): Promise<any>;
export declare function updateProperty({
	channelId,
	commerceChannelIds,
	commerceSyncEnabled,
	dataSourceId,
	siteIds,
}: {
	channelId: string;
	commerceChannelIds?: number[];
	commerceSyncEnabled?: boolean;
	dataSourceId?: string;
	siteIds?: number[];
}): Promise<any>;
export declare function updatecommerceSyncEnabled({
	channelId,
	commerceSyncEnabled,
}: {
	channelId: string;
	commerceChannelIds?: number[];
	commerceSyncEnabled?: boolean;
	dataSourceId?: string;
	siteIds?: number[];
}): Promise<any>;
export declare function updateAttributesConfiguration({
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
}): Promise<any>;
export declare function fetchSelectedFields(): Promise<any>;
export declare function fetchPeopleFields(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchAccountsFields(
	params: TTableRequestParams
): Promise<any>;
declare type TField = {
	example: string;
	name: string;
	required: boolean;
	selected: boolean;
	source: string;
	type: string;
};
export declare function updatePeopleFields(fields: TField[]): Promise<any>;
export declare function updateAccountsFields(fields: TField[]): Promise<any>;
export {};
