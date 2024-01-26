/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Site } from '../models/Site';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SiteService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Site
     * @throws ApiError
     */
    public getSitesPage({
        keywords,
        page,
        pageSize,
        sort,
    }: {
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Site>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/sites',
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
}
