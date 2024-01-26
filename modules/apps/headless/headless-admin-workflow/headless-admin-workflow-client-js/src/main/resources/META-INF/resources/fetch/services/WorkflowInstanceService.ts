/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ChangeTransition } from '../models/ChangeTransition';
import type { WorkflowInstance } from '../models/WorkflowInstance';
import type { WorkflowInstanceSubmit } from '../models/WorkflowInstanceSubmit';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WorkflowInstanceService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns WorkflowInstance
     * @throws ApiError
     */
    public getWorkflowInstancesPage({
        assetClassName,
        assetPrimaryKey,
        completed,
        page,
        pageSize,
    }: {
        assetClassName?: string,
        assetPrimaryKey?: number,
        completed?: boolean,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WorkflowInstance>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances',
            query: {
                'assetClassName': assetClassName,
                'assetPrimaryKey': assetPrimaryKey,
                'completed': completed,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns WorkflowInstance
     * @throws ApiError
     */
    public postWorkflowInstanceSubmit({
        requestBody,
    }: {
        requestBody?: WorkflowInstanceSubmit,
    }): CancelablePromise<WorkflowInstance> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-instances/submit',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteWorkflowInstance({
        workflowInstanceId,
    }: {
        workflowInstanceId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
        });
    }
    /**
     * @returns WorkflowInstance
     * @throws ApiError
     */
    public getWorkflowInstance({
        workflowInstanceId,
    }: {
        workflowInstanceId: number,
    }): CancelablePromise<WorkflowInstance> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
        });
    }
    /**
     * @returns WorkflowInstance
     * @throws ApiError
     */
    public postWorkflowInstanceChangeTransition({
        workflowInstanceId,
        requestBody,
    }: {
        workflowInstanceId: number,
        requestBody?: ChangeTransition,
    }): CancelablePromise<WorkflowInstance> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/change-transition',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
