/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Space} from '../../common/types/Space';
import {UserAccount, UserGroup} from '../../common/types/UserAccount';
import ApiHelper from './ApiHelper';

async function addSpace({
	description,
	name,
	settings,
}: {
	description?: string;
	name: string;
	settings?: {logoColor: string};
}) {
	return await ApiHelper.post<{id: number}>(
		'/o/headless-asset-library/v1.0/asset-libraries',
		{
			description,
			name,
			settings,
			type: 'Space',
		}
	);
}

async function getSpace({
	externalReferenceCode,
	spaceId,
}:
	| {externalReferenceCode: string; spaceId?: undefined}
	| {externalReferenceCode?: undefined; spaceId: string}): Promise<Space> {
	let url = `/o/headless-asset-library/v1.0/asset-libraries/${spaceId}`;

	if (externalReferenceCode) {
		url = `/o/headless-asset-library/v1.0/asset-libraries/by-external-reference-code/${externalReferenceCode}`;
	}

	const {data, error} = await ApiHelper.get<Space>(url);

	if (data) {
		return data;
	}

	throw new Error(error);
}

async function getSpaceUserGroups({
	nestedFields,
	page,
	pageSize,
	spaceId,
}: {
	nestedFields?: string;
	page?: number;
	pageSize?: number;
	spaceId: string;
}): Promise<{
	items: UserGroup[];
	lastPage: number;
	page: number;
	totalCount: number;
}> {
	const urlParams = new URLSearchParams();

	if (page) {
		urlParams.set('page', String(page));
	}

	if (pageSize) {
		urlParams.set('pageSize', String(pageSize));
	}

	const {data, error} = await ApiHelper.get<{
		items: UserGroup[];
		lastPage: number;
		page: number;
		totalCount: number;
	}>(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-groups?${urlParams.toString()}${nestedFields ? '&nestedFields=' + nestedFields : ''}`
	);

	if (data) {
		return data;
	}

	throw new Error(error);
}

async function getSpaceUsers({
	nestedFields,
	page,
	pageSize,
	spaceId,
}: {
	nestedFields?: string;
	page?: number;
	pageSize?: number;
	spaceId: string;
}): Promise<{
	items: UserAccount[];
	lastPage: number;
	page: number;
	totalCount: number;
}> {
	const urlParams = new URLSearchParams();

	if (page) {
		urlParams.set('page', String(page));
	}

	if (pageSize) {
		urlParams.set('pageSize', String(pageSize));
	}

	const {data, error} = await ApiHelper.get<{
		items: UserAccount[];
		lastPage: number;
		page: number;
		totalCount: number;
	}>(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-accounts?${urlParams.toString()}${nestedFields ? '&nestedFields=' + nestedFields : ''}`
	);

	if (data) {
		return data;
	}

	throw new Error(error);
}

async function getSpaces(): Promise<Space[]> {
	const {data, error} = await ApiHelper.get<{items: Space[]}>(
		"/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'"
	);

	if (data) {
		return data.items;
	}

	throw new Error(error);
}

async function linkUserToSpace({
	spaceId,
	userId,
}: {
	spaceId: string;
	userId: string;
}) {
	return await ApiHelper.put(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-accounts/${userId}`
	);
}

async function linkUserGroupToSpace({
	spaceId,
	userGroupId,
}: {
	spaceId: string;
	userGroupId: string;
}) {
	return await ApiHelper.put(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-groups/${userGroupId}`
	);
}

async function unlinkUserFromSpace({
	spaceId,
	userId,
}: {
	spaceId: string;
	userId: string;
}) {
	return await ApiHelper.delete(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-accounts/${userId}`
	);
}

async function unlinkUserGroupFromSpace({
	spaceId,
	userGroupId,
}: {
	spaceId: string;
	userGroupId: string;
}) {
	return await ApiHelper.delete(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-groups/${userGroupId}`
	);
}

async function updateSpace(externalReferenceCode: string, body: any) {
	return await ApiHelper.patch(
		body,
		`/o/headless-asset-library/v1.0/asset-libraries/by-external-reference-code/${externalReferenceCode}`
	);
}

async function updateUserRoles(payload: {
	roleNames: string[];
	spaceId: string;
	userId: string;
}) {
	const {roleNames, spaceId, userId} = payload;

	const body = roleNames.map((roleName) => ({
		name: roleName,
	}));

	return await ApiHelper.put(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-accounts/${userId}/roles`,
		body
	);
}

async function updateUserGroupRoles(payload: {
	roleNames: string[];
	spaceId: string;
	userGroupId: string;
}) {
	const {roleNames, spaceId, userGroupId} = payload;

	const body = roleNames.map((roleName) => ({
		name: roleName,
	}));

	return await ApiHelper.put(
		`/o/headless-asset-library/v1.0/asset-libraries/${spaceId}/user-groups/${userGroupId}/roles`,
		body
	);
}

export default {
	addSpace,
	getSpace,
	getSpaceUserGroups,
	getSpaceUsers,
	getSpaces,
	linkUserGroupToSpace,
	linkUserToSpace,
	unlinkUserFromSpace,
	unlinkUserGroupFromSpace,
	updateSpace,
	updateUserGroupRoles,
	updateUserRoles,
};
