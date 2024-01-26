/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ExperimentRun } from '../models/ExperimentRun';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ExperimentRunService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ExperimentRun
     * @throws ApiError
     */
    public postExperimentRun({
        experimentId,
        requestBody,
    }: {
        experimentId: number,
        requestBody?: ExperimentRun,
    }): CancelablePromise<ExperimentRun> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/segments-asah/v1.0/experiments/{experimentId}/run',
            path: {
                'experimentId': experimentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
