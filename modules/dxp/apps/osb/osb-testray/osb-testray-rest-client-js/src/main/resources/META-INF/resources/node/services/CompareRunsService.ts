/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CompareRuns } from '../models/CompareRuns';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CompareRunsService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns CompareRuns
     * @throws ApiError
     */
    public getCompareRuns({
        idRunA,
        idRunB,
    }: {
        idRunA: number,
        idRunB: number,
    }): CancelablePromise<CompareRuns> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/osb-testray-rest/v1.0/compare-runs/{idRunA}/{idRunB}',
            path: {
                'idRunA': idRunA,
                'idRunB': idRunB,
            },
        });
    }
}
