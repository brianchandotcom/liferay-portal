/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TimeRange } from '../models/TimeRange';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TimeRangeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns TimeRange
     * @throws ApiError
     */
    public getTimeRangesPage(): CancelablePromise<Array<TimeRange>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/time-ranges',
        });
    }
}
