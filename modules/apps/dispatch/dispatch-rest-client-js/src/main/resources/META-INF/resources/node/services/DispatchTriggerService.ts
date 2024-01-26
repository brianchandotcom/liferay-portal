/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DispatchTrigger } from '../models/DispatchTrigger';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DispatchTriggerService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DispatchTrigger
     * @throws ApiError
     */
    public getDispatchTriggersPage(): CancelablePromise<Array<DispatchTrigger>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/dispatch-rest/v1.0/dispatch-triggers',
        });
    }
    /**
     * @returns DispatchTrigger
     * @throws ApiError
     */
    public postDispatchTrigger({
        requestBody,
    }: {
        requestBody?: DispatchTrigger,
    }): CancelablePromise<DispatchTrigger> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/dispatch-rest/v1.0/dispatch-triggers',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postDispatchTriggerRun({
        dispatchTriggerId,
    }: {
        dispatchTriggerId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/dispatch-rest/v1.0/dispatch-triggers/{dispatchTriggerId}/run',
            path: {
                'dispatchTriggerId': dispatchTriggerId,
            },
        });
    }
}
