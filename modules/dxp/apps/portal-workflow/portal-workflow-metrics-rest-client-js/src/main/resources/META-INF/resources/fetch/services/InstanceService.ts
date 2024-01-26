/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Instance } from '../models/Instance';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class InstanceService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Instance
     * @throws ApiError
     */
    public getProcessInstancesPage({
        processId,
        assigneeIds,
        classPKs,
        dateEnd,
        dateStart,
        slaStatuses,
        statuses,
        taskNames,
        page,
        pageSize,
        sort,
    }: {
        processId: number,
        assigneeIds?: Array<number>,
        classPKs?: Array<number>,
        dateEnd?: string,
        dateStart?: string,
        slaStatuses?: Array<'OnTime' | 'Overdue' | 'Untracked'>,
        statuses?: Array<'Completed' | 'Pending'>,
        taskNames?: Array<string>,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Instance>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances',
            path: {
                'processId': processId,
            },
            query: {
                'assigneeIds': assigneeIds,
                'classPKs': classPKs,
                'dateEnd': dateEnd,
                'dateStart': dateStart,
                'slaStatuses': slaStatuses,
                'statuses': statuses,
                'taskNames': taskNames,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns Instance
     * @throws ApiError
     */
    public postProcessInstance({
        processId,
        requestBody,
    }: {
        processId: number,
        requestBody?: Instance,
    }): CancelablePromise<Instance> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances',
            path: {
                'processId': processId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteProcessInstance({
        processId,
        instanceId,
    }: {
        processId: number,
        instanceId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances/{instanceId}',
            path: {
                'processId': processId,
                'instanceId': instanceId,
            },
        });
    }
    /**
     * @returns Instance
     * @throws ApiError
     */
    public getProcessInstance({
        processId,
        instanceId,
    }: {
        processId: number,
        instanceId: number,
    }): CancelablePromise<Instance> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances/{instanceId}',
            path: {
                'processId': processId,
                'instanceId': instanceId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchProcessInstance({
        processId,
        instanceId,
        requestBody,
    }: {
        processId: number,
        instanceId: number,
        requestBody?: Instance,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances/{instanceId}',
            path: {
                'processId': processId,
                'instanceId': instanceId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchProcessInstanceComplete({
        processId,
        instanceId,
        requestBody,
    }: {
        processId: number,
        instanceId: number,
        requestBody?: Instance,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/instances/{instanceId}/complete',
            path: {
                'processId': processId,
                'instanceId': instanceId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
