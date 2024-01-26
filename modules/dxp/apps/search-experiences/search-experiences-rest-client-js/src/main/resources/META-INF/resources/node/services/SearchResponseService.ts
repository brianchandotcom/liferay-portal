/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SearchResponse } from '../models/SearchResponse';
import type { SXPBlueprint } from '../models/SXPBlueprint';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SearchResponseService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SearchResponse
     * @throws ApiError
     */
    public postSearch({
        query,
        page,
        pageSize,
        requestBody,
    }: {
        query?: string,
        page?: number,
        pageSize?: number,
        requestBody?: SXPBlueprint,
    }): CancelablePromise<SearchResponse> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/search',
            query: {
                'query': query,
                'page': page,
                'pageSize': pageSize,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
