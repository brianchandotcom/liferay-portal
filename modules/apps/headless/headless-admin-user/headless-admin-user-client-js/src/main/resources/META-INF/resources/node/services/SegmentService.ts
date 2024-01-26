/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Segment } from '../models/Segment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SegmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a site's segments.
     * @returns Segment
     * @throws ApiError
     */
    public getSiteSegmentsPage({
        siteId,
        page,
        pageSize,
    }: {
        siteId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Segment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/sites/{siteId}/segments',
            path: {
                'siteId': siteId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Gets a user's segments. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request.
     * @returns Segment
     * @throws ApiError
     */
    public getSiteUserAccountSegmentsPage({
        siteId,
        userAccountId,
    }: {
        siteId: number,
        userAccountId: number,
    }): CancelablePromise<Array<Segment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/sites/{siteId}/user-accounts/{userAccountId}/segments',
            path: {
                'siteId': siteId,
                'userAccountId': userAccountId,
            },
        });
    }
}
