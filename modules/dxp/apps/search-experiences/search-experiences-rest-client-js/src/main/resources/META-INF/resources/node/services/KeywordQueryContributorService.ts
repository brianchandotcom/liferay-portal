/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { KeywordQueryContributor } from '../models/KeywordQueryContributor';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KeywordQueryContributorService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns KeywordQueryContributor
     * @throws ApiError
     */
    public getKeywordQueryContributorsPage(): CancelablePromise<Array<KeywordQueryContributor>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/keyword-query-contributors',
        });
    }
}
