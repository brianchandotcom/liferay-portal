/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayPageTemplate_properties_pageDefinition } from '../models/DisplayPageTemplate_properties_pageDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PageDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Renders and retrieves HTML for the page definition using the theme of specified site.
     * @returns string
     * @throws ApiError
     */
    public postSitePageDefinitionPreview({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: DisplayPageTemplate_properties_pageDefinition,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-content/v1.0/sites/{siteId}/page-definitions/preview',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
