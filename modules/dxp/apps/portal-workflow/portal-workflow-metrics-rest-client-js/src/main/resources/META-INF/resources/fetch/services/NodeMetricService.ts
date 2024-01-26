/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NodeMetric } from '../models/NodeMetric';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class NodeMetricService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns NodeMetric
     * @throws ApiError
     */
    public getProcessNodeMetricsPage({
        processId,
        completed,
        dateEnd,
        dateStart,
        key,
        processVersion,
        page,
        pageSize,
        sort,
    }: {
        processId: number,
        completed?: boolean,
        dateEnd?: string,
        dateStart?: string,
        key?: string,
        processVersion?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<NodeMetric>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/nodes/metrics',
            path: {
                'processId': processId,
            },
            query: {
                'completed': completed,
                'dateEnd': dateEnd,
                'dateStart': dateStart,
                'key': key,
                'processVersion': processVersion,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
}
