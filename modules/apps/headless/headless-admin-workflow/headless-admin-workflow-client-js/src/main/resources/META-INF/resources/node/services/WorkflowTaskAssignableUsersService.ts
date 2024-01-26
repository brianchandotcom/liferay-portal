/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WorkflowTaskAssignableUsers } from '../models/WorkflowTaskAssignableUsers';
import type { WorkflowTaskIds } from '../models/WorkflowTaskIds';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WorkflowTaskAssignableUsersService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns WorkflowTaskAssignableUsers
     * @throws ApiError
     */
    public postWorkflowTaskAssignableUser({
        requestBody,
    }: {
        requestBody?: WorkflowTaskIds,
    }): CancelablePromise<WorkflowTaskAssignableUsers> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/assignable-users',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
