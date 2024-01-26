/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SLAResult } from '../models/SLAResult';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SlaResultService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SLAResult
     * @throws ApiError
     */
    public getProcessLastSlaResult({
        processId,
    }: {
        processId: number,
    }): CancelablePromise<SLAResult> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/last-sla-result',
            path: {
                'processId': processId,
            },
        });
    }
}
