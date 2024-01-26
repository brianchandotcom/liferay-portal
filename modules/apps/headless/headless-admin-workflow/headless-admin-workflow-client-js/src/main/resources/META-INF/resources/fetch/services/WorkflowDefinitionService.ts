/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WorkflowDefinition } from '../models/WorkflowDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WorkflowDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public getWorkflowDefinitionsPage({
        active,
        page,
        pageSize,
        sort,
    }: {
        active?: boolean,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<WorkflowDefinition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-definitions',
            query: {
                'active': active,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public postWorkflowDefinition({
        requestBody,
    }: {
        requestBody?: WorkflowDefinition,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-definitions',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public getWorkflowDefinitionByName({
        name,
        contentFormat,
        version,
    }: {
        name: string,
        contentFormat?: 'JSON' | 'XML',
        version?: number,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/by-name/{name}',
            path: {
                'name': name,
            },
            query: {
                'contentFormat': contentFormat,
                'version': version,
            },
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public postWorkflowDefinitionDeploy({
        requestBody,
    }: {
        requestBody?: WorkflowDefinition,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/deploy',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public postWorkflowDefinitionSave({
        requestBody,
    }: {
        requestBody?: WorkflowDefinition,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/save',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteWorkflowDefinitionUndeploy({
        name,
        version,
    }: {
        name: string,
        version: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/undeploy',
            query: {
                'name': name,
                'version': version,
            },
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public postWorkflowDefinitionUpdateActive({
        active,
        name,
        version,
    }: {
        active: boolean,
        name: string,
        version: string,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/update-active',
            query: {
                'active': active,
                'name': name,
                'version': version,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteWorkflowDefinition({
        workflowDefinitionId,
    }: {
        workflowDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
            path: {
                'workflowDefinitionId': workflowDefinitionId,
            },
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public getWorkflowDefinition({
        workflowDefinitionId,
    }: {
        workflowDefinitionId: number,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
            path: {
                'workflowDefinitionId': workflowDefinitionId,
            },
        });
    }
    /**
     * @returns WorkflowDefinition
     * @throws ApiError
     */
    public putWorkflowDefinition({
        workflowDefinitionId,
        requestBody,
    }: {
        workflowDefinitionId: number,
        requestBody?: WorkflowDefinition,
    }): CancelablePromise<WorkflowDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
            path: {
                'workflowDefinitionId': workflowDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
