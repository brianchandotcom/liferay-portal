/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AssignableUser} from '../types/AssignableUser';
import {WorkflowTask} from '../types/WorkflowTask';
import ApiHelper from './ApiHelper';

export async function getWorkflowTasksAssignedToMe({
	page,
	pageSize,
}: {
	page: number;
	pageSize: number;
}): Promise<{items: WorkflowTask[]; totalCount: number}> {
	let fetchUrl =
		'/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me?nestedFields=workflowLogs&';

	const pageParam = String(page);
	const pageSizeParam = String(pageSize);

	fetchUrl =
		fetchUrl +
		new URLSearchParams({
			page: pageParam,
			pageSize: pageSizeParam,
		}).toString();

	const {data, error} = await ApiHelper.get<{
		items: WorkflowTask[];
		totalCount: number;
	}>(fetchUrl);

	if (data) {
		return {items: data.items, totalCount: data.totalCount};
	}

	throw new Error(error);
}

export async function getWorkflowTasksAssignedToMyRoles({
	page,
	pageSize,
}: {
	page: number;
	pageSize: number;
}): Promise<{items: WorkflowTask[]; totalCount: number}> {
	let fetchUrl =
		'/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles?nestedFields=workflowLogs&';

	const pageParam = String(page);
	const pageSizeParam = String(pageSize);

	fetchUrl =
		fetchUrl +
		new URLSearchParams({
			page: pageParam,
			pageSize: pageSizeParam,
		}).toString();

	const {data, error} = await ApiHelper.get<{
		items: WorkflowTask[];
		totalCount: number;
	}>(fetchUrl);

	if (data) {
		return {items: data.items, totalCount: data.totalCount};
	}

	throw new Error(error);
}

export async function getAssignableUsers(
	workflowTaskId: number
): Promise<AssignableUser[]> {
	const {data, error} = await ApiHelper.get<{items: AssignableUser[]}>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/assignable-users`
	);

	if (data) {
		return data.items;
	}

	throw new Error(error);
}

export async function assignToMe({
	comment,
	workflowTaskId,
}: {
	comment: string;
	workflowTaskId: number;
}) {
	return await ApiHelper.post<WorkflowTask>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/assign-to-me`,
		{
			comment,
			workflowTaskId,
		}
	);
}

export async function assignToUser({
	assigneeId,
	comment,
	workflowTaskId,
}: {
	assigneeId: number;
	comment: string;
	workflowTaskId: number;
}) {
	return await ApiHelper.post<WorkflowTask>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/assign-to-user`,
		{
			assigneeId,
			comment,
			workflowTaskId,
		}
	);
}

export async function updateDueDate({
	comment,
	dueDate,
	workflowTaskId,
}: {
	comment: string;
	dueDate: string;
	workflowTaskId: number;
}) {
	return await ApiHelper.post<WorkflowTask>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/update-due-date`,
		{
			comment,
			dueDate,
			workflowTaskId,
		}
	);
}

export async function transitionWorkflowState({
	comment,
	transitionName,
	workflowTaskId,
}: {
	comment: string;
	transitionName: string;
	workflowTaskId: number;
}) {
	return await ApiHelper.post<WorkflowTask>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/change-transition`,
		{
			comment,
			transitionName,
			workflowTaskId,
		}
	);
}

export async function getLatestWorkflowLogByTaskId({
	workflowTaskId,
}: {
	workflowTaskId: number;
}) {
	return await ApiHelper.get<WorkflowTask[]>(
		`/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/workflow-logs`
	);
}
