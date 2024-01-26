/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ActiveViewService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns any
     * @throws ApiError
     */
    public getFrontendViewStateV10ActiveViewPageLayoutPortlet({
        activeViewId,
        pageLayoutId,
        portletId,
    }: {
        activeViewId: string,
        pageLayoutId?: number,
        portletId?: string,
    }): CancelablePromise<Record<string, any>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/frontend-view-state/v1.0/active-view/{activeViewId}/page-layout/{pageLayoutId}/portlet/{portletId}',
            path: {
                'activeViewId': activeViewId,
                'pageLayoutId': pageLayoutId,
                'portletId': portletId,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public putFrontendViewStateV10ActiveViewPageLayoutPortlet({
        activeViewId,
        pageLayoutId,
        portletId,
        requestBody,
    }: {
        activeViewId: string,
        pageLayoutId?: number,
        portletId?: string,
        requestBody?: string,
    }): CancelablePromise<Record<string, any>> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/frontend-view-state/v1.0/active-view/{activeViewId}/page-layout/{pageLayoutId}/portlet/{portletId}',
            path: {
                'activeViewId': activeViewId,
                'pageLayoutId': pageLayoutId,
                'portletId': portletId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
