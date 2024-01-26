/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SearchRequestBody } from '../models/SearchRequestBody';
import type { SearchResult } from '../models/SearchResult';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SearchResultService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Search the company index for matching content. This endpoint is beta and requires setting the portal property 'feature.flag.LPS-179669' to true or enabling via Instance Settings > Feature Flags: Beta.
     * @returns SearchResult
     * @throws ApiError
     */
    public postSearchPage({
        entryClassNames,
        fields,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        requestBody,
    }: {
        /**
         * Model class names to be searched for. Defaults to all.
         */
        entryClassNames?: string,
        /**
         * The list of fields to be returned.
         */
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        requestBody?: SearchRequestBody,
    }): CancelablePromise<Array<SearchResult>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-search-rest/v1.0/search',
            query: {
                'entryClassNames': entryClassNames,
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
