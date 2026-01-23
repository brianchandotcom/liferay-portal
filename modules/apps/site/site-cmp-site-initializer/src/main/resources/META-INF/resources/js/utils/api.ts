/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelper} from '@liferay/site-cms-site-initializer';

export async function deleteTaskById({taskId}: {taskId: string}) {
	return await ApiHelper.delete(`/o/cmp/tasks/${taskId}`);
}

export async function getAllProjects() {
	return await ApiHelper.get(
		`/o/search/v1.0/search?emptySearch=true&filter=objectDefinitionExternalReferenceCode eq 'L_CMP_PROJECT'&nestedFields=embedded`
	);
}

export async function getAllStates() {
	return await ApiHelper.get(
		'/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/L_CMP_STATES/list-type-entries'
	);
}

export async function getUserAccount(id: string) {
	return ApiHelper.get(`/o/headless-admin-user/v1.0/user-accounts/${id}`)
		.then((response) => {
			return response.data;
		})
		.catch(() => {
			throw new Error('Failed to fetch user data.');
		});
}

export async function patchProjectById({
	body,
	projectId,
}: {
	body: {[key: string]: any};
	projectId: string;
}) {
	return await ApiHelper.patch(body, `/o/cmp/projects/${projectId}`);
}

export async function patchTaskById({
	body,
	taskId,
}: {
	body: {[key: string]: any};
	taskId: string;
}) {
	return await ApiHelper.patch(body, `/o/cmp/tasks/${taskId}`);
}

export async function postTaskByScope({
	body,
	scopeKey,
}: {
	body: {[key: string]: any};
	scopeKey: string;
}) {
	return await ApiHelper.post(`/o/cmp/tasks/scopes/${scopeKey}`, body);
}
