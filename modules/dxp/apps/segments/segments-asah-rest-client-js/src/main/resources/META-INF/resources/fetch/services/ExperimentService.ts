/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Experiment } from '../models/Experiment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ExperimentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteExperiment({
        experimentId,
    }: {
        experimentId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/segments-asah/v1.0/experiments/{experimentId}',
            path: {
                'experimentId': experimentId,
            },
        });
    }
    /**
     * @returns Experiment
     * @throws ApiError
     */
    public getExperiment({
        experimentId,
    }: {
        experimentId: string,
    }): CancelablePromise<Experiment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/segments-asah/v1.0/experiments/{experimentId}',
            path: {
                'experimentId': experimentId,
            },
        });
    }
}
