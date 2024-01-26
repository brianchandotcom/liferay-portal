/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProcessVersion } from '../models/ProcessVersion';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProcessVersionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ProcessVersion
     * @throws ApiError
     */
    public getProcessProcessVersionsPage({
        processId,
    }: {
        processId: number,
    }): CancelablePromise<Array<ProcessVersion>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/process-versions',
            path: {
                'processId': processId,
            },
        });
    }
}
