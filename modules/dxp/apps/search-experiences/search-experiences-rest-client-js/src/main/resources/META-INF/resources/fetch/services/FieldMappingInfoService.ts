/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FieldMappingInfo } from '../models/FieldMappingInfo';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FieldMappingInfoService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns FieldMappingInfo
     * @throws ApiError
     */
    public getFieldMappingInfosPage({
        external,
        indexName,
        query,
    }: {
        external?: boolean,
        indexName?: string,
        query?: string,
    }): CancelablePromise<Array<FieldMappingInfo>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/field-mapping-infos',
            query: {
                'external': external,
                'indexName': indexName,
                'query': query,
            },
        });
    }
}
