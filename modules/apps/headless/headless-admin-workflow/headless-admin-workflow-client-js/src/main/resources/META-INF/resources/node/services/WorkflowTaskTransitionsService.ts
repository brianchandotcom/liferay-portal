/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WorkflowTaskIds } from '../models/WorkflowTaskIds';
import type { WorkflowTaskTransitions } from '../models/WorkflowTaskTransitions';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WorkflowTaskTransitionsService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns WorkflowTaskTransitions
     * @throws ApiError
     */
    public postWorkflowTaskTransition({
        requestBody,
    }: {
        requestBody?: WorkflowTaskIds,
    }): CancelablePromise<WorkflowTaskTransitions> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/next-transitions',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
