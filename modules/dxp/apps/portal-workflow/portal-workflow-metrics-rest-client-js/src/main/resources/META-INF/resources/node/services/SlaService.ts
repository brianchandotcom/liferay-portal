/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SLA } from '../models/SLA';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SlaService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SLA
     * @throws ApiError
     */
    public getProcessSlAsPage({
        processId,
        status,
        page,
        pageSize,
    }: {
        processId: number,
        status?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<SLA>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/slas',
            path: {
                'processId': processId,
            },
            query: {
                'status': status,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns SLA
     * @throws ApiError
     */
    public postProcessSla({
        processId,
        requestBody,
    }: {
        processId: number,
        requestBody?: SLA,
    }): CancelablePromise<SLA> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/slas',
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
    public deleteSla({
        slaId,
    }: {
        slaId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/portal-workflow-metrics/v1.0/slas/{slaId}',
            path: {
                'slaId': slaId,
            },
        });
    }
    /**
     * @returns SLA
     * @throws ApiError
     */
    public getSla({
        slaId,
    }: {
        slaId: number,
    }): CancelablePromise<SLA> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/slas/{slaId}',
            path: {
                'slaId': slaId,
            },
        });
    }
    /**
     * @returns SLA
     * @throws ApiError
     */
    public putSla({
        slaId,
        requestBody,
    }: {
        slaId: number,
        requestBody?: SLA,
    }): CancelablePromise<SLA> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/portal-workflow-metrics/v1.0/slas/{slaId}',
            path: {
                'slaId': slaId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
