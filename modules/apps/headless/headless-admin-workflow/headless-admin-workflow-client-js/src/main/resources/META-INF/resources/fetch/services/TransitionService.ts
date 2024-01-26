/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Transition } from '../models/Transition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TransitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Transition
     * @throws ApiError
     */
    public getWorkflowInstanceNextTransitionsPage({
        workflowInstanceId,
        page,
        pageSize,
    }: {
        workflowInstanceId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Transition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/next-transitions',
            path: {
                'workflowInstanceId': workflowInstanceId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns Transition
     * @throws ApiError
     */
    public getWorkflowTaskNextTransitionsPage({
        workflowTaskId,
        page,
        pageSize,
    }: {
        workflowTaskId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Transition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/next-transitions',
            path: {
                'workflowTaskId': workflowTaskId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
