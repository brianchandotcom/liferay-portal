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
    public getMyUserAccountSitesPage({
        page,
        pageSize,
    }: {
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Site>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/my-user-account/sites',
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns Site
     * @throws ApiError
     */
    public getSiteByFriendlyUrlPath({
        friendlyUrlPath,
    }: {
        friendlyUrlPath: string,
    }): CancelablePromise<Site> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/sites/by-friendly-url-path/{friendlyUrlPath}',
            path: {
                'friendlyUrlPath': friendlyUrlPath,
            },
        });
    }
    /**
     * @returns Site
     * @throws ApiError
     */
    public getSite({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<Site> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/sites/{siteId}',
            path: {
                'siteId': siteId,
            },
        });
    }
}
