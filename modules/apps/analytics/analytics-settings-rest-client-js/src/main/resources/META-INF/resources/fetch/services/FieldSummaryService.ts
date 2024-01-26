/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FieldSummary } from '../models/FieldSummary';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FieldSummaryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns FieldSummary
     * @throws ApiError
     */
    public getField(): CancelablePromise<FieldSummary> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/fields',
        });
    }
}
