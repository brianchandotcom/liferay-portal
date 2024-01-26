/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WebUrl } from '../models/WebUrl';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WebUrlService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the organization's URLs.
     * @returns WebUrl
     * @throws ApiError
     */
    public getOrganizationWebUrlsPage({
        organizationId,
    }: {
        organizationId: string,
    }): CancelablePromise<Array<WebUrl>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/web-urls',
            path: {
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Retrieves the user's URLs.
     * @returns WebUrl
     * @throws ApiError
     */
    public getUserAccountWebUrlsPage({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Array<WebUrl>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/web-urls',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Retrieves the web URL.
     * @returns WebUrl
     * @throws ApiError
     */
    public getWebUrl({
        webUrlId,
    }: {
        webUrlId: number,
    }): CancelablePromise<WebUrl> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/web-urls/{webUrlId}',
            path: {
                'webUrlId': webUrlId,
            },
        });
    }
}
