/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { AssignableUser } from '../types/AssignableUser';
import { WorkflowTaskResponse } from '../types/WorkflowTaskResponse';
import ApiHelper from './ApiHelper';

export async function getWorkflowTasksAssignedToMe({page, pageSize} : {page: number, pageSize: number}): Promise<{items: WorkflowTaskResponse[], totalCount: number}> {
    let fetchUrl = '/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me/?';

    const pageParam = String(page);
    const pageSizeParam = String(pageSize);

    fetchUrl = fetchUrl + new URLSearchParams({
        page: pageParam,
        pageSize: pageSizeParam,
    }).toString();

    const {data, error} = await ApiHelper.get<{items: WorkflowTaskResponse[], totalCount: number}>(
        fetchUrl
    );
    

    if (data) {
        const incompleteItems = data.items.filter((item) => !item.completed && item.name === 'review');

        return {items: incompleteItems, totalCount: incompleteItems.length};
    }

    throw new Error(error);
}

export async function getWorkflowTasksAssignedToMyRoles({page, pageSize} : {page: number, pageSize: number}): Promise<{items: WorkflowTaskResponse[], totalCount: number}> {
    let fetchUrl = '/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles/?'

    const pageParam = String(page);
    const pageSizeParam = String(pageSize);

    fetchUrl = fetchUrl + new URLSearchParams({
        page: pageParam,
        pageSize: pageSizeParam
    }).toString();

    const {data, error} = await ApiHelper.get<{items: WorkflowTaskResponse[], totalCount: number}>(
        fetchUrl
    );

    if (data) {
        const incompleteItems = data.items.filter((item) => !item.completed && item.name === 'review');

        return {items: incompleteItems, totalCount: incompleteItems.length};
    }

    throw new Error(error);
}

export async function getAssignableUsers (workflowTaskId: number): Promise<AssignableUser[]> {

    const workflowTaskIdParam = String(workflowTaskId);

    const fetchUrl = `/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskIdParam}/assignable-users`;

    const {data, error} = await ApiHelper.get<{items: AssignableUser[]}>(
        fetchUrl
    );

    if (data) {
        return data.items;
    }

    throw new Error(error);
}

export async function assignToUser ({assigneeId, comment = "", workflowTaskId}: {assigneeId: number, comment: string, workflowTaskId: number}) {
    const fetchUrl = `/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/assign-to-user`;

    return await ApiHelper.post<{items: AssignableUser[]}>(
        fetchUrl,
        {
            assigneeId,
            comment,
            workflowTaskId
        }
    );
}

export async function updateDueDate ({comment, dueDate, workflowTaskId}: { comment: string, dueDate: string, workflowTaskId: number}) {
    const fetchUrl = `/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/update-due-date`;

    return await ApiHelper.post<{items: AssignableUser[]}>(
        fetchUrl,
        {
            comment,
            dueDate,
            workflowTaskId
        }
    );
}

export async function transitionWorkflowState({transitionName, workflowTaskId}: {transitionName: string, workflowTaskId: number}) {
    const fetchUrl = `/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskId}/change-transition`;

    return await ApiHelper.post<{items: AssignableUser[]}>(
        fetchUrl,
        {
            transitionName,
            workflowTaskId
        }
    );
}

export async function getLatestWorkflowLogByTaskId({workflowTaskId}: {workflowTaskId: number}) {

    const workflowTaskIdParam = String(workflowTaskId);

    const fetchUrl = `/o/headless-admin-workflow/v1.0/workflow-tasks/${workflowTaskIdParam}/workflow-logs`;

    const {data, error} = await ApiHelper.get<{items: AssignableUser[]}>(
        fetchUrl
    );

    if (data) {
        return data.items;
    }

    throw new Error(error);
}