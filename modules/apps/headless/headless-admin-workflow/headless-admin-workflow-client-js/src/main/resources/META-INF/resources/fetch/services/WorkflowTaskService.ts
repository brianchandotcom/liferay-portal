/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ChangeTransition } from '../models/ChangeTransition';
import type { WorkflowTask } from '../models/WorkflowTask';
import type { WorkflowTaskAssignToMe } from '../models/WorkflowTaskAssignToMe';
import type { WorkflowTaskAssignToRole } from '../models/WorkflowTaskAssignToRole';
import type { WorkflowTaskAssignToUser } from '../models/WorkflowTaskAssignToUser';
import type { WorkflowTasksBulkSelection } from '../models/WorkflowTasksBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WorkflowTaskService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowInstanceWorkflowTasksPage({
        workflowInstanceId,
        completed,
        page,
        pageSize,
    }: {
        workflowInstanceId: number,
        completed?: boolean,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
            query: {
                'completed': completed,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowInstanceWorkflowTasksAssignedToMePage({
        workflowInstanceId,
        completed,
        page,
        pageSize,
    }: {
        workflowInstanceId: number,
        completed?: boolean,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-me',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
            query: {
                'completed': completed,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowInstanceWorkflowTasksAssignedToUserPage({
        workflowInstanceId,
        assigneeId,
        completed,
        page,
        pageSize,
    }: {
        workflowInstanceId: number,
        assigneeId?: number,
        completed?: boolean,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-user',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
            query: {
                'assigneeId': assigneeId,
                'completed': completed,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTasksPage({
        page,
        pageSize,
        sort,
        requestBody,
    }: {
        page?: number,
        pageSize?: number,
        sort?: string,
        requestBody?: WorkflowTasksBulkSelection,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks',
            query: {
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchWorkflowTaskAssignToUser({
        requestBody,
    }: {
        requestBody?: Array<WorkflowTaskAssignToUser>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assign-to-user',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksAssignedToMePage({
        page,
        pageSize,
    }: {
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me',
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksAssignedToMyRolesPage({
        page,
        pageSize,
    }: {
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles',
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksAssignedToRolePage({
        roleId,
        page,
        pageSize,
    }: {
        roleId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-role',
            query: {
                'roleId': roleId,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksAssignedToUserPage({
        assigneeId,
        page,
        pageSize,
    }: {
        assigneeId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user',
            query: {
                'assigneeId': assigneeId,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksAssignedToUserRolesPage({
        assigneeId,
        page,
        pageSize,
    }: {
        assigneeId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user-roles',
            query: {
                'assigneeId': assigneeId,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchWorkflowTaskChangeTransition({
        requestBody,
    }: {
        requestBody?: Array<ChangeTransition>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/change-transition',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTasksSubmittingUserPage({
        creatorId,
        page,
        pageSize,
    }: {
        creatorId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowTask>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/submitting-user',
            query: {
                'creatorId': creatorId,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchWorkflowTaskUpdateDueDate({
        requestBody,
    }: {
        requestBody?: Array<WorkflowTaskAssignToMe>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/update-due-date',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public getWorkflowTask({
        workflowTaskId,
    }: {
        workflowTaskId: number,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}',
            path: {
                'workflowTaskId': workflowTaskId,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTaskAssignToMe({
        workflowTaskId,
        requestBody,
    }: {
        workflowTaskId: number,
        requestBody?: WorkflowTaskAssignToMe,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-me',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTaskAssignToRole({
        workflowTaskId,
        requestBody,
    }: {
        workflowTaskId: number,
        requestBody?: WorkflowTaskAssignToRole,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-role',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTaskAssignToUser({
        workflowTaskId,
        requestBody,
    }: {
        workflowTaskId: number,
        requestBody?: WorkflowTaskAssignToUser,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-user',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTaskChangeTransition({
        workflowTaskId,
        requestBody,
    }: {
        workflowTaskId: number,
        requestBody?: ChangeTransition,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/change-transition',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns boolean
     * @throws ApiError
     */
    public getWorkflowTaskHasAssignableUsers({
        workflowTaskId,
    }: {
        workflowTaskId: number,
    }): CancelablePromise<boolean> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/has-assignable-users',
            path: {
                'workflowTaskId': workflowTaskId,
            },
        });
    }
    /**
     * @returns WorkflowTask
     * @throws ApiError
     */
    public postWorkflowTaskUpdateDueDate({
        workflowTaskId,
        requestBody,
    }: {
        workflowTaskId: number,
        requestBody?: WorkflowTaskAssignToMe,
    }): CancelablePromise<WorkflowTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/update-due-date',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
