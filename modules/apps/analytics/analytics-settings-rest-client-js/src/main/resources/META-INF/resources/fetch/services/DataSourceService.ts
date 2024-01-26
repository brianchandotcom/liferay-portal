/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataSourceToken } from '../models/DataSourceToken';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataSourceService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataSource(): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/analytics-settings-rest/v1.0/data-sources',
            errors: {
                500: `Unexpected error`,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postDataSource({
        requestBody,
    }: {
        requestBody?: DataSourceToken,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/analytics-settings-rest/v1.0/data-sources',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                500: `Unexpected error`,
            },
        });
    }
}
