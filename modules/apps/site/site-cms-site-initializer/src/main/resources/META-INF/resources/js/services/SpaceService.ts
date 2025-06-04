/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Space} from '../types/Space';
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
		}
	);
}

async function getSpace(externalReferenceCode: string): Promise<Space> {
	const {data, error} = await ApiHelper.get<Space>(
		`/o/headless-asset-library/v1.0/asset-libraries/by-external-reference-code/${externalReferenceCode}`
	);

	if (data) {
		return data;
	}

	throw new Error(error);
}

async function getSpaces(): Promise<Space[]> {
	const {data, error} = await ApiHelper.get<{items: Space[]}>(
		'/o/headless-asset-library/v1.0/asset-libraries'
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

export default {
	addSpace,
	getSpace,
	getSpaces,
	linkUserGroupToSpace,
	linkUserToSpace,
	unlinkUserFromSpace,
	unlinkUserGroupFromSpace,
};
