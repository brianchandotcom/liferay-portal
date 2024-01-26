/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Experiment } from '../models/Experiment';
import type { Status } from '../models/Status';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class StatusService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Experiment
     * @throws ApiError
     */
    public postExperimentStatus({
        experimentId,
        requestBody,
    }: {
        experimentId: number,
        requestBody?: Status,
    }): CancelablePromise<Experiment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/segments-asah/v1.0/experiments/{experimentId}/status',
            path: {
                'experimentId': experimentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
