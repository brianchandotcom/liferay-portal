/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Index } from '../models/Index';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class IndexService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Index
     * @throws ApiError
     */
    public getIndexesPage(): CancelablePromise<Array<Index>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/indexes',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchIndexRefresh({
        requestBody,
    }: {
        requestBody?: Index,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/indexes/refresh',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchIndexReindex({
        requestBody,
    }: {
        requestBody?: Index,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/indexes/reindex',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
