/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SearchIndex } from '../models/SearchIndex';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SearchIndexService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SearchIndex
     * @throws ApiError
     */
    public getSearchIndexesPage(): CancelablePromise<Array<SearchIndex>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/search-indexes',
        });
    }
}
