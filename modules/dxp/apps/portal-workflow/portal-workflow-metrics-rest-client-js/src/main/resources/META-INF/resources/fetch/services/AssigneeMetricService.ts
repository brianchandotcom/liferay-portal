/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssigneeMetric } from '../models/AssigneeMetric';
import type { AssigneeMetricBulkSelection } from '../models/AssigneeMetricBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AssigneeMetricService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns AssigneeMetric
     * @throws ApiError
     */
    public postProcessAssigneeMetricsPage({
        processId,
        page,
        pageSize,
        sort,
        requestBody,
    }: {
        processId: number,
        page?: number,
        pageSize?: number,
        sort?: string,
        requestBody?: AssigneeMetricBulkSelection,
    }): CancelablePromise<Array<AssigneeMetric>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/assignees/metrics',
            path: {
                'processId': processId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
