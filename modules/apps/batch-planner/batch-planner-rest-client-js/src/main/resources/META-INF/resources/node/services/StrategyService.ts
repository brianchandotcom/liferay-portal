/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Strategy } from '../models/Strategy';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class StrategyService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Strategy
     * @throws ApiError
     */
    public getPlanInternalClassNameKeyStrategiesPage({
        internalClassNameKey,
    }: {
        internalClassNameKey: string,
    }): CancelablePromise<Array<Strategy>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans/{internalClassNameKey}/strategies',
            path: {
                'internalClassNameKey': internalClassNameKey,
            },
        });
    }
}
