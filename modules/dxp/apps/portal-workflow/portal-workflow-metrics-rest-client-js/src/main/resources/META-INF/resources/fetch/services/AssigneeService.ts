/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Assignee } from '../models/Assignee';
import type { AssigneeBulkSelection } from '../models/AssigneeBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AssigneeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Assignee
     * @throws ApiError
     */
    public postProcessAssigneesPage({
        processId,
        requestBody,
    }: {
        processId: number,
        requestBody?: AssigneeBulkSelection,
    }): CancelablePromise<Array<Assignee>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/assignees',
            path: {
                'processId': processId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
