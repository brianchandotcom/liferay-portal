/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { HistogramMetric } from '../models/HistogramMetric';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class HistogramMetricService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns HistogramMetric
     * @throws ApiError
     */
    public getProcessHistogramMetric({
        processId,
        unit,
        dateEnd,
        dateStart,
    }: {
        processId: number,
        unit: 'Days' | 'Hours' | 'Months' | 'Weeks' | 'Years',
        dateEnd?: string,
        dateStart?: string,
    }): CancelablePromise<HistogramMetric> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/histograms/metrics',
            path: {
                'processId': processId,
            },
            query: {
                'dateEnd': dateEnd,
                'dateStart': dateStart,
                'unit': unit,
            },
        });
    }
}
