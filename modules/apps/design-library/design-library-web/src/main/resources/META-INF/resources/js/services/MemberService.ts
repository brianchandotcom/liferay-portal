/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {addParams, fetch} from 'frontend-js-web';

import {Member} from '../types';

const BASE_PATH = '/o/headless-asset-library/v1.0/asset-libraries';

interface RequestOptions extends RequestInit {
	params?: Record<string, string>;
}

async function request(path: string, {params, ...init}: RequestOptions = {}) {
	const pathContext = Liferay.ThemeDisplay.getPathContext() || '';

	const url = addParams(params ?? {}, `${pathContext}${BASE_PATH}${path}`);

	const response = await fetch(url, {
		headers: DEFAULT_FETCH_HEADERS,
		...init,
	});

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	if (response.status === 204) {
		return null;
	}

	return await response.json();
}

async function addUser(
	externalReferenceCode: string,
	userExternalReferenceCode: string
): Promise<Member> {
	return await request(
		`/${externalReferenceCode}/user-accounts/${userExternalReferenceCode}`,
		{method: 'PUT'}
	);
}

async function addUserGroup(
	externalReferenceCode: string,
	userGroupExternalReferenceCode: string
): Promise<Member> {
	return await request(
		`/${externalReferenceCode}/user-groups/${userGroupExternalReferenceCode}`,
		{method: 'PUT'}
	);
}

async function getUserGroupMembers(
	externalReferenceCode: string
): Promise<{items: Member[]}> {
	return await request(`/${externalReferenceCode}/user-groups`, {
		params: {
			nestedFields: 'roles,numberOfUserAccounts',
			pageSize: '200',
		},
	});
}

async function getUserMembers(
	externalReferenceCode: string
): Promise<{items: Member[]}> {
	return await request(`/${externalReferenceCode}/user-accounts`, {
		params: {
			nestedFields: 'roles',
			pageSize: '200',
		},
	});
}

export default {
	addUser,
	addUserGroup,
	getUserGroupMembers,
	getUserMembers,
};
