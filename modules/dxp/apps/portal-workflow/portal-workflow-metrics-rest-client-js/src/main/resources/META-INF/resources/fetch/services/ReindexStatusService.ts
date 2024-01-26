/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReindexStatus } from '../models/ReindexStatus';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ReindexStatusService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ReindexStatus
     * @throws ApiError
     */
    public getReindexStatusesPage(): CancelablePromise<Array<ReindexStatus>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/reindex/statuses',
        });
    }
}
