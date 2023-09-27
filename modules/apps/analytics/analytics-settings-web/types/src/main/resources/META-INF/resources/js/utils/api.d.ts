/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TTableRequestParams} from '../components/table/types';
export declare function createProperty(name: string): Promise<any>;
export declare function deleteConnection(): Promise<any>;
export declare function sync(): Promise<any>;
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
export declare function fetchProperties(
	params: TTableRequestParams
): Promise<any>;
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
	commerceSyncEnabled: boolean;
	dataSourceId?: string;
	siteIds?: number[];
}): Promise<any>;
export declare function updatecommerceSyncEnabled({
	channelId,
	commerceSyncEnabled,
}: {
	channelId: string;
	commerceSyncEnabled: boolean;
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
export declare function fetchAccountsFields(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchOrdersFields(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchPeopleFields(
	params: TTableRequestParams
): Promise<any>;
export declare function fetchProductsFields(
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
export declare function updateAccountsFields(fields: TField[]): Promise<any>;
export declare function updateOrdersFields(fields: TField[]): Promise<any>;
export declare function updatePeopleFields(fields: TField[]): Promise<any>;
export declare function updateProductsFields(fields: TField[]): Promise<any>;
export {};
